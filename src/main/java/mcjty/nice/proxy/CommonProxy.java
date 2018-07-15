package mcjty.nice.proxy;

import mcjty.lib.McJtyLib;
import mcjty.lib.datafix.fixes.TileEntityNamespace;
import mcjty.lib.proxy.AbstractCommonProxy;
import mcjty.nice.Config;
import mcjty.nice.Nice;
import mcjty.nice.blocks.CylinderTileEntity;
import mcjty.nice.blocks.ModBlocks;
import mcjty.nice.blocks.SolidTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CommonProxy extends AbstractCommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        MinecraftForge.EVENT_BUS.register(this);

        mainConfig = new Configuration(new File(modConfigDir.getPath(), "nice.cfg"));
        Config.readConfig(mainConfig);

        ModBlocks.init();

        // Initialize our packet handler. Make sure the name is
        // 20 characters or less!
//        PacketHandler.registerMessages("modtut");

//        MainCompatHandler.registerWaila();
//        MainCompatHandler.registerTOP();

    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        ModFixs modFixs = FMLCommonHandler.instance().getDataFixer().init(Nice.MODID, 1);
        Map<String, String> oldToNewIdMap = new HashMap<>();

        event.getRegistry().register(ModBlocks.solidBlock);
        event.getRegistry().register(ModBlocks.cylinderBlock);
        GameRegistry.registerTileEntity(CylinderTileEntity.class, Nice.MODID + ":cylinder");
        GameRegistry.registerTileEntity(SolidTileEntity.class, Nice.MODID + ":solid");

        // We used to accidentally register TEs with names like "minecraft:nice_cylinder" instead of "nice:cylinder".
        // Set up a DataFixer to map these incorrect names to the correct ones, so that we don't break old saved games.
        // @todo Remove all this if we ever break saved-game compatibility.
        oldToNewIdMap.put(Nice.MODID + "_cylinder", Nice.MODID + ":cylinder");
        oldToNewIdMap.put("minecraft:" + Nice.MODID + "_cylinder", Nice.MODID + ":cylinder");
        oldToNewIdMap.put(Nice.MODID + "_solid", Nice.MODID + ":solid");
        oldToNewIdMap.put("minecraft:" + Nice.MODID + "_solid", Nice.MODID + ":solid");
        modFixs.registerFix(FixTypes.BLOCK_ENTITY, new TileEntityNamespace(oldToNewIdMap, 1));
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.solidBlock).setRegistryName(ModBlocks.solidBlock.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.cylinderBlock).setRegistryName(ModBlocks.cylinderBlock.getRegistryName()));
    }



    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
//        NetworkRegistry.INSTANCE.registerGuiHandler(Nice.instance, new GuiProxy());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        if (mainConfig.hasChanged()) {
            mainConfig.save();
        }
    }
}
