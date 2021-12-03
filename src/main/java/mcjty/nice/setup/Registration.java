package mcjty.nice.setup;


import mcjty.lib.blocks.BaseBlock;
import mcjty.nice.Nice;
import mcjty.nice.blocks.CylinderBlock;
import mcjty.nice.blocks.GenericParticleTileEntity;
import mcjty.nice.blocks.SolidBlock;
import mcjty.nice.blocks.SolidCylinderBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static mcjty.nice.Nice.MODID;

public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);

    public static final RegistryObject<BaseBlock> SOLID_BLOCK = BLOCKS.register("solid_block", SolidBlock::new);
    public static final RegistryObject<BaseBlock> SOLID_SMALL_CYLINDER = BLOCKS.register("solid_small_cylinder", () -> new SolidCylinderBlock(.3f));
    public static final RegistryObject<BaseBlock> SOLID_CYLINDER = BLOCKS.register("solid_cylinder", () -> new SolidCylinderBlock(.8f));
    public static final RegistryObject<BaseBlock> SMALL_CYLINDER = BLOCKS.register("small_cylinder", () -> new CylinderBlock(.3f));
    public static final RegistryObject<BaseBlock> CYLINDER = BLOCKS.register("cylinder", () -> new CylinderBlock(.8f));

    public static final Map<DyeColor, RegistryObject<Item>> SOLID_BLOCK_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.register("solid_" + c.getName(), () -> new BlockItem(SOLID_BLOCK.get(), Registration.createStandardProperties()))));
    public static final Map<DyeColor, RegistryObject<Item>> CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.register("cylinder_" + c.getName(), () -> new BlockItem(CYLINDER.get(), Registration.createStandardProperties()))));
    public static final Map<DyeColor, RegistryObject<Item>> SMALL_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.register("small_cylinder_" + c.getName(), () -> new BlockItem(SMALL_CYLINDER.get(), Registration.createStandardProperties()))));
    public static final Map<DyeColor, RegistryObject<Item>> SOLID_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.register("solid_cylinder_" + c.getName(), () -> new BlockItem(SOLID_CYLINDER.get(), Registration.createStandardProperties()))));
    public static final Map<DyeColor, RegistryObject<Item>> SOLID_SMALL_CYLINDER_ITEMS = Arrays.stream(DyeColor.values())
            .collect(Collectors.toMap(c -> c, c -> ITEMS.register("solid_small_cylinder_" + c.getName(), () -> new BlockItem(SOLID_SMALL_CYLINDER.get(), Registration.createStandardProperties()))));

    public static final RegistryObject<TileEntityType<GenericParticleTileEntity>> TYPE_PARTICLE = TILES.register("generic_particle", () -> TileEntityType.Builder.of(GenericParticleTileEntity::new,
            CYLINDER.get(), SMALL_CYLINDER.get(), SOLID_CYLINDER.get(), SOLID_SMALL_CYLINDER.get(), SOLID_BLOCK.get()).build(null));

    public static final Tags.IOptionalNamedTag<Item> SOLID_ITEM_TAG = ItemTags.createOptional(new ResourceLocation(Nice.MODID, "solid_block"));

    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static Item.Properties createStandardProperties() {
        return new Item.Properties().tab(Nice.setup.getTab());
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
}
