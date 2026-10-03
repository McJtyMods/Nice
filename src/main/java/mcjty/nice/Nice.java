package mcjty.nice;

import mcjty.nice.datagen.DataGenerators;
import mcjty.nice.setup.ClientSetup;
import mcjty.nice.setup.Registration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import java.util.Random;

@Mod(Nice.MODID)
public class Nice {
    public static final String MODID = "nice";
    public static final Random random = new Random();

    public Nice(ModContainer mod, IEventBus bus, Dist dist) {
        NiceConfig.register(mod);
        Registration.register(bus);
        bus.addListener(DataGenerators::gatherServer);
        if (dist.isClient()) {
            ClientSetup.register(bus);
        }
    }
}
