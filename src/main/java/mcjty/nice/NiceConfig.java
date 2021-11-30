package mcjty.nice;

import mcjty.lib.modules.Modules;

public class NiceConfig {

//    @Config.Comment(value = "Render distance at which the particles disappear")
//    @Config.RangeDouble(min = 1, max = 200)
    public static double maxRenderDist = 20;

//    @Config.Comment(value = "Particle color/intensity (red)")
//    @Config.RangeDouble(min = 0, max = 1)
    public static double particleBrightnessR = 1.0;

//    @Config.Comment(value = "Particle color/intensity (green)")
//    @Config.RangeDouble(min = 0, max = 1)
    public static double particleBrightnessG = 1.0;

//    @Config.Comment(value = "Particle color/intensity (blue)")
//    @Config.RangeDouble(min = 0, max = 1)
    public static double particleBrightnessB = 1.0;

    public static void register(Modules modules) {
        modules.initConfig();

//        SERVER_CONFIG = SERVER_BUILDER.build();
//        CLIENT_CONFIG = CLIENT_BUILDER.build();

//        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
//        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
    }
}
