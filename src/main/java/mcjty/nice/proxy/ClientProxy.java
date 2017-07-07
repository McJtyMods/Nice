package mcjty.nice.proxy;

import mcjty.nice.Nice;
import mcjty.nice.blocks.ModBlocks;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        MinecraftForge.EVENT_BUS.register(this);

        OBJLoader.INSTANCE.addDomain(Nice.MODID);
//        ModelLoaderRegistry.registerLoader(new BakedModelLoader());

    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        // Typically initialization of models and such goes here:
        ModBlocks.initModels();
    }


    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);

        // Initialize our input handler so we can listen to keys
//        MinecraftForge.EVENT_BUS.register(new InputHandler());
//        KeyBindings.init();

        ModBlocks.initItemModels();
    }
}
