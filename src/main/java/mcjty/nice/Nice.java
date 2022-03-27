package mcjty.nice;

import mcjty.lib.modules.Modules;
import mcjty.nice.setup.ClientSetup;
import mcjty.nice.setup.ModSetup;
import mcjty.nice.setup.Registration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Random;

@Mod(Nice.MODID)
public class Nice {
    public static final String MODID = "nice";

    public static final ModSetup setup = new ModSetup();

    public static Nice instance;
    private final Modules modules = new Modules();
    public static Random random = new Random();

    public Nice() {
        instance = this;
        setupModules();

        NiceConfig.register(modules);
        Registration.register();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
            bus.addListener(modules::initClient);
            bus.addListener(ClientSetup::initClient);
            bus.addListener(ClientSetup::onTextureStitch);
        });
    }

    private void setupModules() {
//        modules.register(new CrafterModule());
    }
}
