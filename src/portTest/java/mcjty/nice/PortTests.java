package mcjty.nice;

import mcjty.nice.blocks.GenericParticleTileEntity;
import mcjty.nice.blocks.WaterloggedParticleBlock;
import mcjty.nice.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.fabricmc.fabric.api.gametest.v1.GameTest;

import java.util.List;

public final class PortTests {

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
        return entity(helper, pos).getUpdateTag(helper.getLevel().registryAccess()).getCompoundOrEmpty("mcjtylib_client").getStringOr("type", "missing");
    }

    @GameTest(maxTicks = 100)
    public void interactions(GameTestHelper helper) {
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
                helper.assertValueEqual(restored.getUpdateTag(level.registryAccess()).getCompoundOrEmpty("mcjtylib_client").getStringOr("type", "missing"), "bubble", "type survives serialization");
                // Apply the actual client update format through vanilla's load path.
                var clientCopy = new GenericParticleTileEntity(absolute, level.getBlockState(absolute));
                clientCopy.loadWithComponents(net.minecraft.world.level.storage.TagValueInput.create(
                        net.minecraft.util.ProblemReporter.DISCARDING, level.registryAccess(),
                        entity(helper, pos).getUpdateTag(level.registryAccess())));
                helper.assertTrue(!clientCopy.isVisible(), "client update preserves hidden state");
                helper.assertValueEqual(clientCopy.getUpdateTag(level.registryAccess()).getCompoundOrEmpty("mcjtylib_client").getStringOr("type", "missing"), "bubble", "client update preserves particle type");
                helper.assertValueEqual(saved.getStringOr("type", "missing"), "bubble", "disk format keeps particle type at the root");
                use(helper, pos, Items.GLASS);
                helper.assertTrue(entity(helper, pos).isVisible(), "glass reveals block");
                helper.assertTrue(!level.getBlockState(absolute).getCollisionShape(level, absolute).isEmpty(), "visible block has collision");
                use(helper, pos, Items.STRING);
                helper.assertValueEqual(type(helper, pos), "none", "string removes particles");
            }
        }
        helper.succeed();
    }

    @GameTest(maxTicks = 100)
    public void solids(GameTestHelper helper) {
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

    @GameTest(maxTicks = 100)
    public void waterlogging(GameTestHelper helper) {
        BlockPos pos = new BlockPos(0, 1, 0);
        var level = helper.getLevel();
        var absolute = helper.absolutePos(pos);
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var families = List.of(Registration.PARTICLE_BLOCKS, Registration.CYLINDERS, Registration.SMALL_CYLINDERS,
                Registration.SOLID_CYLINDERS, Registration.SOLID_SMALL_CYLINDERS);
        for (var family : families) {
            var block = (WaterloggedParticleBlock) family.get(DyeColor.RED).get();
            for (Direction direction : Direction.values()) {
                for (boolean water : new boolean[]{false, true}) {
                    helper.setBlock(pos, water ? Blocks.WATER : Blocks.AIR);
                    var context = new BlockPlaceContext(level, player, InteractionHand.MAIN_HAND, new ItemStack(block),
                            new BlockHitResult(Vec3.atCenterOf(absolute), direction, absolute, false));
                    var state = block.getStateForPlacement(context);
                    helper.assertTrue(state != null, "block can be placed");
                    helper.assertValueEqual(state.getValue(WaterloggedParticleBlock.WATERLOGGED), water, "only placement acquires water");
                    helper.setBlock(pos, state);
                    helper.assertValueEqual(level.getFluidState(absolute).isSource(), water, "waterlogged block contains source water");
                    helper.assertTrue(!block.canPlaceLiquid(player, level, absolute, state, Fluids.WATER), "bucket filling is disabled");
                    helper.assertTrue(!block.placeLiquid(level, absolute, state, Fluids.WATER.getSource(false)), "flow cannot fill block");
                    if (family == Registration.PARTICLE_BLOCKS || family == Registration.CYLINDERS || family == Registration.SMALL_CYLINDERS) {
                        use(helper, pos, Items.WATER_BUCKET);
                        helper.assertValueEqual(type(helper, pos), "bubble", "water bucket still selects bubbles");
                        use(helper, pos, Items.GLASS);
                        helper.assertTrue(!entity(helper, pos).isVisible(), "waterlogging supports invisible blocks");
                    }
                    entity(helper, pos).setColor(DyeColor.BLUE);
                    var recolored = level.getBlockState(absolute);
                    helper.assertValueEqual(recolored.getValue(WaterloggedParticleBlock.WATERLOGGED), water, "recolor preserves waterlogging");
                    helper.assertValueEqual(recolored.getValue(BlockStateProperties.FACING), state.getValue(BlockStateProperties.FACING), "recolor preserves facing");
                    var saved = entity(helper, pos).saveWithFullMetadata(level.registryAccess());
                    var restored = BlockEntity.loadStatic(absolute, recolored, saved, level.registryAccess());
                    helper.assertTrue(restored != null, "waterlogged block entity loads");
                    helper.assertValueEqual(restored.getBlockState().getValue(WaterloggedParticleBlock.WATERLOGGED), water, "waterlogging survives save/load");
                }
            }
        }
        // Put flowing water directly above each dry block and let fluid ticks run.
        for (int i = 0; i < families.size(); i++) {
            BlockPos flowPos = new BlockPos(i, 1, 0);
            helper.setBlock(flowPos, families.get(i).get(DyeColor.RED).get());
            helper.setBlock(flowPos.above(), Blocks.WATER);
        }
        helper.runAfterDelay(30, () -> {
            for (int i = 0; i < families.size(); i++) {
                BlockPos flowPos = new BlockPos(i, 1, 0);
                helper.assertBlockPresent(families.get(i).get(DyeColor.RED).get(), flowPos);
                helper.assertTrue(!level.getBlockState(helper.absolutePos(flowPos)).getValue(WaterloggedParticleBlock.WATERLOGGED),
                        "flowing water leaves dry cylinders intact and dry");
            }
            helper.succeed();
        });
    }

}
