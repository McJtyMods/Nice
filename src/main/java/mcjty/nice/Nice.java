package mcjty.nice;

import mcjty.lib.modules.Modules;
import mcjty.nice.setup.ModSetup;
import mcjty.nice.setup.Registration;
import net.minecraftforge.fml.common.Mod;

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
    }

    private void setupModules() {
//        modules.register(new CrafterModule());
    }
}
