package mcjty.nice.proxy;

import mcjty.nice.Config;
import mcjty.nice.blocks.ModBlocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class CommonProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "nice.cfg"));
        Config.readConfig();

        // Initialize our packet handler. Make sure the name is
        // 20 characters or less!
//        PacketHandler.registerMessages("modtut");

        // Initialization of blocks and items typically goes here:
        ModBlocks.init();
//        ModItems.init();

//        MainCompatHandler.registerWaila();
//        MainCompatHandler.registerTOP();

    }

    public void init(FMLInitializationEvent e) {
//        NetworkRegistry.INSTANCE.registerGuiHandler(Nice.instance, new GuiProxy());
        ModBlocks.initCrafting();
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }
}
