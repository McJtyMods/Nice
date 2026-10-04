package mcjty.nice.setup;

import mcjty.nice.Nice;
import mcjty.nice.blocks.*;
import net.minecraft.core.Registry;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static mcjty.nice.Nice.MODID;

public class Registration {

    public static final Map<DyeColor, Supplier<GenericParticleBlock>> SOLID_BLOCKS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerBlock("solid_block_" + c.getName(), SolidBlock::new)));
    public static final Map<DyeColor, Supplier<GenericParticleBlock>> PARTICLE_BLOCKS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerBlock("particle_block_" + c.getName(), ParticleBlock::new)));
    public static final Map<DyeColor, Supplier<GenericParticleBlock>> CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerBlock("cylinder_" + c.getName(), props -> new CylinderBlock(props, .8f, color -> Registration.CYLINDERS.get(color).get()))));
    public static final Map<DyeColor, Supplier<GenericParticleBlock>> SMALL_CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerBlock("small_cylinder_" + c.getName(), props -> new CylinderBlock(props, .3f, color -> Registration.SMALL_CYLINDERS.get(color).get()))));
    public static final Map<DyeColor, Supplier<GenericParticleBlock>> SOLID_CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerBlock("solid_cylinder_" + c.getName(), props -> new SolidCylinderBlock(props, .8f, color -> Registration.SOLID_CYLINDERS.get(color).get()))));
    public static final Map<DyeColor, Supplier<GenericParticleBlock>> SOLID_SMALL_CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerBlock("solid_small_cylinder_" + c.getName(), props -> new SolidCylinderBlock(props, .3f, color -> Registration.SOLID_SMALL_CYLINDERS.get(color).get()))));

    public static final Map<DyeColor, Supplier<Item>> SOLID_BLOCK_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerItem("solid_" + c.getName(), props -> new NiceBlockItem(SOLID_BLOCKS.get(c).get(), props.useBlockDescriptionPrefix()))));
    public static final Map<DyeColor, Supplier<Item>> PARTICLE_BLOCK_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerItem("particle_" + c.getName(), props -> new NiceBlockItem(PARTICLE_BLOCKS.get(c).get(), props.useBlockDescriptionPrefix()))));
    public static final Map<DyeColor, Supplier<Item>> CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerItem("cylinder_" + c.getName(), props -> new NiceBlockItem(CYLINDERS.get(c).get(), props.useBlockDescriptionPrefix()))));
    public static final Map<DyeColor, Supplier<Item>> SMALL_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerItem("small_cylinder_" + c.getName(), props -> new NiceBlockItem(SMALL_CYLINDERS.get(c).get(), props.useBlockDescriptionPrefix()))));
    public static final Map<DyeColor, Supplier<Item>> SOLID_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerItem("solid_cylinder_" + c.getName(), props -> new NiceBlockItem(SOLID_CYLINDERS.get(c).get(), props.useBlockDescriptionPrefix()))));
    public static final Map<DyeColor, Supplier<Item>> SOLID_SMALL_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> registerItem("solid_small_cylinder_" + c.getName(), props -> new NiceBlockItem(SOLID_SMALL_CYLINDERS.get(c).get(), props.useBlockDescriptionPrefix()))));

    public static final Supplier<BlockEntityType<GenericParticleTileEntity>> TYPE_PARTICLE = registerType("generic_particle", FabricBlockEntityTypeBuilder.create(GenericParticleTileEntity::new,
            collect(CYLINDERS, SMALL_CYLINDERS, SOLID_CYLINDERS, SOLID_SMALL_CYLINDERS, SOLID_BLOCKS, PARTICLE_BLOCKS)).build());

    public static final TagKey<Item> SOLID_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "solid"));
    public static final TagKey<Item> PARTICLE_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "particle"));
    public static final TagKey<Item> CYLINDER_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "cylinder"));
    public static final TagKey<Item> SMALL_CYLINDER_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "small_cylinder"));
    public static final TagKey<Item> SOLID_CYLINDER_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "solid_cylinder"));
    public static final TagKey<Item> SOLID_SMALL_CYLINDER_ITEM_TAG = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Nice.MODID, "solid_small_cylinder"));

    public static void register() {
        // Loading this class registers the block families before their block entity type and tab.
    }

    private static Supplier<GenericParticleBlock> registerBlock(String name,
            java.util.function.Function<BlockBehaviour.Properties, GenericParticleBlock> factory) {
        var id = Identifier.fromNamespaceAndPath(MODID, name);
        var block = Registry.register(BuiltInRegistries.BLOCK, id,
                factory.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id))));
        return () -> block;
    }

    private static Supplier<Item> registerItem(String name, java.util.function.Function<Item.Properties, Item> factory) {
        var id = Identifier.fromNamespaceAndPath(MODID, name);
        var item = Registry.register(BuiltInRegistries.ITEM, id,
                factory.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
        return () -> item;
    }

    private static <T extends net.minecraft.world.level.block.entity.BlockEntity> Supplier<BlockEntityType<T>> registerType(
            String name, BlockEntityType<T> type) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(MODID, name), type);
        return () -> type;
    }

    @SafeVarargs
    public static GenericParticleBlock[] collect(Map<DyeColor, Supplier<GenericParticleBlock>>... maps) {
        List<GenericParticleBlock> b = new ArrayList<>();
        for (Map<DyeColor, Supplier<GenericParticleBlock>> map : maps) {
            map.values().forEach(g -> b.add(g.get()));
        }
        return b.toArray(GenericParticleBlock[]::new);
    }

    public static Supplier<CreativeModeTab> TAB = registerTab(FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MODID))
            .icon(() -> new ItemStack(CYLINDER_ITEMS.get(DyeColor.RED).get()))
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

    private static Supplier<CreativeModeTab> registerTab(CreativeModeTab tab) {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(MODID, "nice"), tab);
        return () -> tab;
    }
}
