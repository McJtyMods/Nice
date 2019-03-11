package mcjty.nice.setup;

import mcjty.lib.setup.DefaultClientProxy;
import mcjty.nice.Nice;
import mcjty.nice.blocks.ModBlocks;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends DefaultClientProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        OBJLoader.INSTANCE.addDomain(Nice.MODID);
//        ModelLoaderRegistry.registerLoader(new BakedModelLoader());

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
