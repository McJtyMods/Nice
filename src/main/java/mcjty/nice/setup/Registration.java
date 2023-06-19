package mcjty.nice.setup;


import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.varia.TagTools;
import mcjty.nice.Nice;
import mcjty.nice.blocks.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static mcjty.nice.Nice.MODID;
import static mcjty.nice.Nice.tab;

public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final Map<DyeColor, RegistryObject<BaseBlock>> SOLID_BLOCKS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.register("solid_block_" + c.getName(), SolidBlock::new)));
    public static final Map<DyeColor, RegistryObject<BaseBlock>> PARTICLE_BLOCKS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.register("particle_block_" + c.getName(), ParticleBlock::new)));
    public static final Map<DyeColor, RegistryObject<BaseBlock>> CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.register("cylinder_" + c.getName(), () -> new CylinderBlock(.8f, color -> Registration.CYLINDERS.get(color).get()))));
    public static final Map<DyeColor, RegistryObject<BaseBlock>> SMALL_CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.register("small_cylinder_" + c.getName(), () -> new CylinderBlock(.3f, color -> Registration.SMALL_CYLINDERS.get(color).get()))));
    public static final Map<DyeColor, RegistryObject<BaseBlock>> SOLID_CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.register("solid_cylinder_" + c.getName(), () -> new SolidCylinderBlock(.8f, color -> Registration.SOLID_CYLINDERS.get(color).get()))));
    public static final Map<DyeColor, RegistryObject<BaseBlock>> SOLID_SMALL_CYLINDERS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> BLOCKS.register("solid_small_cylinder_" + c.getName(), () -> new SolidCylinderBlock(.3f, color -> Registration.SOLID_SMALL_CYLINDERS.get(color).get()))));

    public static final Map<DyeColor, RegistryObject<Item>> SOLID_BLOCK_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.register("solid_" + c.getName(), tab(() -> new BlockItem(SOLID_BLOCKS.get(c).get(), Registration.createStandardProperties())))));
    public static final Map<DyeColor, RegistryObject<Item>> PARTICLE_BLOCK_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.register("particle_" + c.getName(), tab(() -> new BlockItem(PARTICLE_BLOCKS.get(c).get(), Registration.createStandardProperties())))));
    public static final Map<DyeColor, RegistryObject<Item>> CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.register("cylinder_" + c.getName(), tab(() -> new BlockItem(CYLINDERS.get(c).get(), Registration.createStandardProperties())))));
    public static final Map<DyeColor, RegistryObject<Item>> SMALL_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.register("small_cylinder_" + c.getName(), tab(() -> new BlockItem(SMALL_CYLINDERS.get(c).get(), Registration.createStandardProperties())))));
    public static final Map<DyeColor, RegistryObject<Item>> SOLID_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.register("solid_cylinder_" + c.getName(), tab(() -> new BlockItem(SOLID_CYLINDERS.get(c).get(), Registration.createStandardProperties())))));
    public static final Map<DyeColor, RegistryObject<Item>> SOLID_SMALL_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.register("solid_small_cylinder_" + c.getName(), tab(() -> new BlockItem(SOLID_SMALL_CYLINDERS.get(c).get(), Registration.createStandardProperties())))));

    public static final RegistryObject<BlockEntityType<GenericParticleTileEntity>> TYPE_PARTICLE = TILES.register("generic_particle", () -> BlockEntityType.Builder.of(GenericParticleTileEntity::new,
            collect(CYLINDERS, SMALL_CYLINDERS, SOLID_CYLINDERS, SOLID_SMALL_CYLINDERS, SOLID_BLOCKS, PARTICLE_BLOCKS)).build(null));

    public static final TagKey<Item> SOLID_ITEM_TAG = TagTools.createItemTagKey(new ResourceLocation(Nice.MODID, "solid"));
    public static final TagKey<Item> PARTICLE_ITEM_TAG = TagTools.createItemTagKey(new ResourceLocation(Nice.MODID, "particle"));
    public static final TagKey<Item> CYLINDER_ITEM_TAG = TagTools.createItemTagKey(new ResourceLocation(Nice.MODID, "cylinder"));
    public static final TagKey<Item> SMALL_CYLINDER_ITEM_TAG = TagTools.createItemTagKey(new ResourceLocation(Nice.MODID, "small_cylinder"));
    public static final TagKey<Item> SOLID_CYLINDER_ITEM_TAG = TagTools.createItemTagKey(new ResourceLocation(Nice.MODID, "solid_cylinder"));
    public static final TagKey<Item> SOLID_SMALL_CYLINDER_ITEM_TAG = TagTools.createItemTagKey(new ResourceLocation(Nice.MODID, "solid_small_cylinder"));

    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TILES.register(bus);
        TABS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static Item.Properties createStandardProperties() {
        return Nice.setup.defaultProperties();
    }

    public static BaseBlock[] collect(Map<DyeColor, RegistryObject<BaseBlock>>... maps) {
        List<BaseBlock> b = new ArrayList<>();
        for (Map<DyeColor, RegistryObject<BaseBlock>> map : maps) {
            map.values().forEach(g -> b.add(g.get()));
        }
        return b.toArray(new BaseBlock[b.size()]);
    }

//    @SubscribeEvent
//    public static void registerBlocks(RegistryEvent.Register<Block> event) {
//        ModFixs modFixs = FMLCommonHandler.instance().getDataFixer().init(MODID, 1);
//        Map<String, String> oldToNewIdMap = new HashMap<>();
//
//        event.getRegistry().register(ModBlocks.solidBlock);
//        event.getRegistry().register(ModBlocks.cylinderBlock);
//        event.getRegistry().register(ModBlocks.smallCylinderBlock);
//        event.getRegistry().register(ModBlocks.solidCylinderBlock);
//        event.getRegistry().register(ModBlocks.solidSmallCylinderBlock);
//        GameRegistry.registerTileEntity(CylinderTileEntity.class, MODID + ":cylinder");
//        GameRegistry.registerTileEntity(SolidTileEntity.class, MODID + ":solid");
//
//        // We used to accidentally register TEs with names like "minecraft:nice_cylinder" instead of "nice:cylinder".
//        // Set up a DataFixer to map these incorrect names to the correct ones, so that we don't break old saved games.
//        // @todo Remove all this if we ever break saved-game compatibility.
//        oldToNewIdMap.put(MODID + "_cylinder", MODID + ":cylinder");
//        oldToNewIdMap.put("minecraft:" + MODID + "_cylinder", MODID + ":cylinder");
//        oldToNewIdMap.put(MODID + "_solid", MODID + ":solid");
//        oldToNewIdMap.put("minecraft:" + MODID + "_solid", MODID + ":solid");
//        modFixs.registerFix(FixTypes.BLOCK_ENTITY, new TileEntityNamespace(oldToNewIdMap, 1));
//    }
//
//    @SubscribeEvent
//    public static void registerItems(RegistryEvent.Register<Item> event) {
//        event.getRegistry().register(new ItemBlock(ModBlocks.solidBlock).setRegistryName(ModBlocks.solidBlock.getRegistryName()));
//        event.getRegistry().register(new ItemBlock(ModBlocks.cylinderBlock).setRegistryName(ModBlocks.cylinderBlock.getRegistryName()));
//        event.getRegistry().register(new ItemBlock(ModBlocks.smallCylinderBlock).setRegistryName(ModBlocks.smallCylinderBlock.getRegistryName()));
//        event.getRegistry().register(new ItemBlock(ModBlocks.solidCylinderBlock).setRegistryName(ModBlocks.solidCylinderBlock.getRegistryName()));
//        event.getRegistry().register(new ItemBlock(ModBlocks.solidSmallCylinderBlock).setRegistryName(ModBlocks.solidSmallCylinderBlock.getRegistryName()));
//    }

    public static RegistryObject<CreativeModeTab> TAB = TABS.register("nice", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MODID))
            .icon(() -> new ItemStack(CYLINDER_ITEMS.get(DyeColor.RED).get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((featureFlags, output) -> {
                Nice.setup.populateTab(output);
            })
            .build());
}
