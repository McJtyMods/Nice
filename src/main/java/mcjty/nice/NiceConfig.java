package mcjty.nice;

import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class NiceConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.DoubleValue MAX_RENDER_DIST = BUILDER
            .comment("Distance at which decorative particles disappear")
            .defineInRange("maxRenderDist", 20.0, 1.0, 200.0);
    public static final ModConfigSpec.DoubleValue BRIGHTNESS_R = BUILDER.defineInRange("particleBrightnessR", 1.0, 0.0, 1.0);
    public static final ModConfigSpec.DoubleValue BRIGHTNESS_G = BUILDER.defineInRange("particleBrightnessG", 1.0, 0.0, 1.0);
    public static final ModConfigSpec.DoubleValue BRIGHTNESS_B = BUILDER.defineInRange("particleBrightnessB", 1.0, 0.0, 1.0);
    private static final ModConfigSpec SPEC = BUILDER.build();

    public static void register() {
        ConfigRegistry.INSTANCE.register(Nice.MODID, ModConfig.Type.CLIENT, SPEC);
    }
}
