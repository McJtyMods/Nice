package mcjty.nice.setup;

import mcjty.nice.Nice;
import mcjty.nice.blocks.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static mcjty.nice.Nice.MODID;

public class Registration {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final Map<DyeColor, DeferredBlock<GenericParticleBlock>> SOLID_BLOCKS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.registerBlock("solid_block_" + c.getName(), SolidBlock::new)));
    public static final Map<DyeColor, DeferredBlock<GenericParticleBlock>> PARTICLE_BLOCKS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.registerBlock("particle_block_" + c.getName(), ParticleBlock::new)));
    public static final Map<DyeColor, DeferredBlock<GenericParticleBlock>> CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.registerBlock("cylinder_" + c.getName(), props -> new CylinderBlock(props, .8f, color -> Registration.CYLINDERS.get(color).get()))));
    public static final Map<DyeColor, DeferredBlock<GenericParticleBlock>> SMALL_CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.registerBlock("small_cylinder_" + c.getName(), props -> new CylinderBlock(props, .3f, color -> Registration.SMALL_CYLINDERS.get(color).get()))));
    public static final Map<DyeColor, DeferredBlock<GenericParticleBlock>> SOLID_CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.registerBlock("solid_cylinder_" + c.getName(), props -> new SolidCylinderBlock(props, .8f, color -> Registration.SOLID_CYLINDERS.get(color).get()))));
    public static final Map<DyeColor, DeferredBlock<GenericParticleBlock>> SOLID_SMALL_CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.registerBlock("solid_small_cylinder_" + c.getName(), props -> new SolidCylinderBlock(props, .3f, color -> Registration.SOLID_SMALL_CYLINDERS.get(color).get()))));

    public static final Map<DyeColor, DeferredItem<Item>> SOLID_BLOCK_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.registerItem("solid_" + c.getName(), props -> new NiceBlockItem(SOLID_BLOCKS.get(c).get(), props.useBlockDescriptionPrefix()))));
    public static final Map<DyeColor, DeferredItem<Item>> PARTICLE_BLOCK_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.registerItem("particle_" + c.getName(), props -> new NiceBlockItem(PARTICLE_BLOCKS.get(c).get(), props.useBlockDescriptionPrefix()))));
    public static final Map<DyeColor, DeferredItem<Item>> CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.registerItem("cylinder_" + c.getName(), props -> new NiceBlockItem(CYLINDERS.get(c).get(), props.useBlockDescriptionPrefix()))));
    public static final Map<DyeColor, DeferredItem<Item>> SMALL_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.registerItem("small_cylinder_" + c.getName(), props -> new NiceBlockItem(SMALL_CYLINDERS.get(c).get(), props.useBlockDescriptionPrefix()))));
    public static final Map<DyeColor, DeferredItem<Item>> SOLID_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.registerItem("solid_cylinder_" + c.getName(), props -> new NiceBlockItem(SOLID_CYLINDERS.get(c).get(), props.useBlockDescriptionPrefix()))));
    public static final Map<DyeColor, DeferredItem<Item>> SOLID_SMALL_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.registerItem("solid_small_cylinder_" + c.getName(), props -> new NiceBlockItem(SOLID_SMALL_CYLINDERS.get(c).get(), props.useBlockDescriptionPrefix()))));

    public static final Supplier<BlockEntityType<GenericParticleTileEntity>> TYPE_PARTICLE = TILES.register("generic_particle", () -> new BlockEntityType<>(GenericParticleTileEntity::new,
            collect(CYLINDERS, SMALL_CYLINDERS, SOLID_CYLINDERS, SOLID_SMALL_CYLINDERS, SOLID_BLOCKS, PARTICLE_BLOCKS)));

    public static final TagKey<Item> SOLID_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "solid"));
    public static final TagKey<Item> PARTICLE_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "particle"));
    public static final TagKey<Item> CYLINDER_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "cylinder"));
    public static final TagKey<Item> SMALL_CYLINDER_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "small_cylinder"));
    public static final TagKey<Item> SOLID_CYLINDER_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "solid_cylinder"));
    public static final TagKey<Item> SOLID_SMALL_CYLINDER_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "solid_small_cylinder"));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TILES.register(bus);
        TABS.register(bus);
    }

    @SafeVarargs
    public static GenericParticleBlock[] collect(Map<DyeColor, DeferredBlock<GenericParticleBlock>>... maps) {
        List<GenericParticleBlock> b = new ArrayList<>();
        for (Map<DyeColor, DeferredBlock<GenericParticleBlock>> map : maps) {
            map.values().forEach(g -> b.add(g.get()));
        }
        return b.toArray(GenericParticleBlock[]::new);
    }

    public static Supplier<CreativeModeTab> TAB = TABS.register("nice", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MODID))
            .icon(() -> new ItemStack(CYLINDER_ITEMS.get(DyeColor.RED).get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((featureFlags, output) -> {
                for (DyeColor color : DyeColor.values()) {
                    output.accept(SOLID_BLOCK_ITEMS.get(color).get());
                    output.accept(PARTICLE_BLOCK_ITEMS.get(color).get());
                    output.accept(CYLINDER_ITEMS.get(color).get());
                    output.accept(SMALL_CYLINDER_ITEMS.get(color).get());
                    output.accept(SOLID_CYLINDER_ITEMS.get(color).get());
                    output.accept(SOLID_SMALL_CYLINDER_ITEMS.get(color).get());
                }
            })
            .build());
}
