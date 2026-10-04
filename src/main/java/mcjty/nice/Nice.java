package mcjty.nice;

import mcjty.nice.setup.Registration;
import net.fabricmc.api.ModInitializer;
import java.util.Random;

public class Nice implements ModInitializer {
    public static final String MODID = "nice";
    public static final Random random = new Random();

    @Override
    public void onInitialize() {
        Registration.register();
    }
}
