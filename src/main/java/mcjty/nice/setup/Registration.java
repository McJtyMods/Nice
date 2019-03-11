package mcjty.nice.setup;


import mcjty.lib.datafix.fixes.TileEntityNamespace;
import mcjty.nice.blocks.CylinderTileEntity;
import mcjty.nice.blocks.ModBlocks;
import mcjty.nice.blocks.SolidTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Map;

import static mcjty.nice.Nice.MODID;

@Mod.EventBusSubscriber
public class Registration {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ModFixs modFixs = FMLCommonHandler.instance().getDataFixer().init(MODID, 1);
        Map<String, String> oldToNewIdMap = new HashMap<>();

        event.getRegistry().register(ModBlocks.solidBlock);
        event.getRegistry().register(ModBlocks.cylinderBlock);
        event.getRegistry().register(ModBlocks.smallCylinderBlock);
        event.getRegistry().register(ModBlocks.solidCylinderBlock);
        event.getRegistry().register(ModBlocks.solidSmallCylinderBlock);
        GameRegistry.registerTileEntity(CylinderTileEntity.class, MODID + ":cylinder");
        GameRegistry.registerTileEntity(SolidTileEntity.class, MODID + ":solid");

        // We used to accidentally register TEs with names like "minecraft:nice_cylinder" instead of "nice:cylinder".
        // Set up a DataFixer to map these incorrect names to the correct ones, so that we don't break old saved games.
        // @todo Remove all this if we ever break saved-game compatibility.
        oldToNewIdMap.put(MODID + "_cylinder", MODID + ":cylinder");
        oldToNewIdMap.put("minecraft:" + MODID + "_cylinder", MODID + ":cylinder");
        oldToNewIdMap.put(MODID + "_solid", MODID + ":solid");
        oldToNewIdMap.put("minecraft:" + MODID + "_solid", MODID + ":solid");
        modFixs.registerFix(FixTypes.BLOCK_ENTITY, new TileEntityNamespace(oldToNewIdMap, 1));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.solidBlock).setRegistryName(ModBlocks.solidBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cylinderBlock).setRegistryName(ModBlocks.cylinderBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.smallCylinderBlock).setRegistryName(ModBlocks.smallCylinderBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.solidCylinderBlock).setRegistryName(ModBlocks.solidCylinderBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.solidSmallCylinderBlock).setRegistryName(ModBlocks.solidSmallCylinderBlock.getRegistryName()));
    }
}
