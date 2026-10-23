package mcjty.nice;

import mcjty.nice.blocks.GenericParticleTileEntity;
import mcjty.nice.particle.ParticleType;
import mcjty.nice.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = Nice.MODID)
public final class PortTests {
    private static final Identifier INTERACTIONS = Identifier.fromNamespaceAndPath(Nice.MODID, "port_interactions");
    private static final Identifier SOLIDS = Identifier.fromNamespaceAndPath(Nice.MODID, "port_solids");
    private static final Identifier COMPATIBILITY = Identifier.fromNamespaceAndPath(Nice.MODID, "port_compatibility");

    @SubscribeEvent
    public static void functions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, INTERACTIONS, () -> PortTests::interactions);
        event.register(Registries.TEST_FUNCTION, SOLIDS, () -> PortTests::solids);
        event.register(Registries.TEST_FUNCTION, COMPATIBILITY, () -> PortTests::compatibility);
    }

    @SubscribeEvent
    public static void tests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(Identifier.fromNamespaceAndPath(Nice.MODID, "port"));
        for (Identifier id : new Identifier[]{INTERACTIONS, SOLIDS, COMPATIBILITY}) {
            event.registerTest(id, new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, id),
                    new TestData<>(environment, Identifier.withDefaultNamespace("empty"), 100, 0, true)));
        }
    }

    private static void use(GameTestHelper helper, BlockPos pos, Item item) {
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(item));
        helper.useBlock(pos, player);
        helper.assertValueEqual(player.getItemInHand(InteractionHand.MAIN_HAND).getCount(), 1, "interaction must not consume item");
    }

    private static GenericParticleTileEntity entity(GameTestHelper helper, BlockPos pos) {
        return helper.getBlockEntity(pos, GenericParticleTileEntity.class);
    }

    private static String type(GameTestHelper helper, BlockPos pos) {
        return entity(helper, pos).saveWithoutMetadata(helper.getLevel().registryAccess()).getStringOr("type", "missing");
    }

    private static void interactions(GameTestHelper helper) {
        BlockPos pos = new BlockPos(0, 1, 0);
        for (var family : java.util.List.of(Registration.PARTICLE_BLOCKS, Registration.CYLINDERS, Registration.SMALL_CYLINDERS)) {
            for (Direction direction : Direction.values()) {
                helper.setBlock(pos, family.get(DyeColor.RED).get().defaultBlockState().setValue(BlockStateProperties.FACING, direction));
                helper.assertValueEqual(type(helper, pos), "smoke", "default particle type");
                use(helper, pos, Items.DIAMOND);
                helper.assertValueEqual(type(helper, pos), "blink", "diamond interaction");
                use(helper, pos, Items.COD);
                helper.assertValueEqual(type(helper, pos), "fish", "fish interaction");
                use(helper, pos, Items.WOOL.pick(DyeColor.WHITE));
                helper.assertValueEqual(type(helper, pos), "smoke", "wool interaction");
                use(helper, pos, Items.WATER_BUCKET);
                helper.assertValueEqual(type(helper, pos), "bubble", "bucket interaction");
                use(helper, pos, Items.GLASS);
                helper.assertTrue(!entity(helper, pos).isVisible(), "glass hides block");
                var level = helper.getLevel();
                var absolute = helper.absolutePos(pos);
                helper.assertTrue(level.getBlockState(absolute).getCollisionShape(level, absolute).isEmpty(), "hidden block has no collision");
                use(helper, pos, Items.DYE.pick(DyeColor.BLUE));
                helper.assertBlockPresent(family.get(DyeColor.BLUE).get(), pos);
                helper.assertValueEqual(level.getBlockState(absolute).getValue(BlockStateProperties.FACING), direction, "recolor keeps direction");
                helper.assertValueEqual(type(helper, pos), "bubble", "recolor keeps particle type");
                helper.assertTrue(!entity(helper, pos).isVisible(), "recolor keeps hidden state");
                var saved = entity(helper, pos).saveWithFullMetadata(level.registryAccess());
                var restored = (GenericParticleTileEntity) BlockEntity.loadStatic(absolute, level.getBlockState(absolute), saved, level.registryAccess());
                helper.assertTrue(restored != null && !restored.isVisible(), "visibility survives serialization");
                helper.assertValueEqual(restored.saveWithoutMetadata(level.registryAccess()).getStringOr("type", "missing"), "bubble", "type survives serialization");
                use(helper, pos, Items.GLASS);
                helper.assertTrue(entity(helper, pos).isVisible(), "glass reveals block");
                helper.assertTrue(!level.getBlockState(absolute).getCollisionShape(level, absolute).isEmpty(), "visible block has collision");
                use(helper, pos, Items.STRING);
                helper.assertValueEqual(type(helper, pos), "none", "string removes particles");
            }
        }
        helper.succeed();
    }

    private static void solids(GameTestHelper helper) {
        BlockPos pos = new BlockPos(0, 1, 0);
        for (var family : java.util.List.of(Registration.SOLID_BLOCKS, Registration.SOLID_CYLINDERS, Registration.SOLID_SMALL_CYLINDERS)) {
            helper.setBlock(pos, family.get(DyeColor.RED).get());
            use(helper, pos, Items.DIAMOND);
            helper.assertValueEqual(type(helper, pos), "smoke", "solid blocks reject particle changes");
            use(helper, pos, Items.DYE.pick(DyeColor.BLUE));
            helper.assertBlockPresent(family.get(DyeColor.BLUE).get(), pos);
            helper.assertTrue(entity(helper, pos).isVisible(), "solid block remains visible");
        }
        helper.succeed();
    }

    private static void compatibility(GameTestHelper helper) {
        BlockPos pos = new BlockPos(0, 1, 0);
        var registries = helper.getLevel().registryAccess();
        for (var family : java.util.List.of(Registration.SOLID_BLOCKS, Registration.PARTICLE_BLOCKS,
                Registration.CYLINDERS, Registration.SMALL_CYLINDERS, Registration.SOLID_CYLINDERS,
                Registration.SOLID_SMALL_CYLINDERS)) {
            for (DyeColor color : DyeColor.values()) {
                for (Direction direction : Direction.values()) {
                    var state = family.get(color).get().defaultBlockState().setValue(BlockStateProperties.FACING, direction);
                    for (ParticleType particle : ParticleType.values()) {
                        for (boolean visible : new boolean[]{false, true}) {
                            // The pre-McJtyLib 26.2 block entity format, with no library fields.
                            var legacy = new CompoundTag();
                            legacy.putString("id", "nice:generic_particle");
                            legacy.putInt("x", 0);
                            legacy.putInt("y", 1);
                            legacy.putInt("z", 0);
                            legacy.putString("type", particle.getName());
                            legacy.putBoolean("visible", visible);
                            var restored = (GenericParticleTileEntity) BlockEntity.loadStatic(pos, state, legacy, registries);
                            helper.assertTrue(restored != null, "legacy block entity loads");
                            helper.assertTrue(restored.getParticleSystem() == particle.getParticleSystem(), "legacy particle type loads");
                            helper.assertValueEqual(restored.isVisible(), visible, "legacy visibility loads");
                            helper.assertValueEqual(restored.getBlockState().getValue(BlockStateProperties.FACING), direction, "legacy facing preserved");
                            var saved = restored.saveWithFullMetadata(registries);
                            helper.assertValueEqual(saved.getStringOr("id", "missing"), "nice:generic_particle", "block entity id preserved");
                            helper.assertValueEqual(saved.getStringOr("type", "missing"), particle.getName(), "saved type stays at root");
                            helper.assertValueEqual(saved.getBooleanOr("visible", !visible), visible, "saved visibility stays at root");
                            var client = new GenericParticleTileEntity(pos, state);
                            // Prime the cache so the test also checks invalidation on updates.
                            client.getCalculatedParticleSystem();
                            client.handleUpdateTag(TagValueInput.create(ProblemReporter.DISCARDING, registries, restored.getUpdateTag(registries)));
                            helper.assertTrue(client.getParticleSystem() == particle.getParticleSystem(), "client receives particle type");
                            helper.assertValueEqual(client.isVisible(), visible, "client receives visibility");
                            helper.assertTrue(client.getCalculatedParticleSystem() != null, "client can rebuild particle system");
                        }
                    }
                }
            }
        }
        helper.succeed();
    }
}
