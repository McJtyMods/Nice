package mcjty.nice;

import net.minecraftforge.common.config.Config;

@Config(modid = Nice.MODID, category = "rendering", name = "nice")
public class NiceConfig {

    @Config.Comment(value = "Render distance at which the particles disappear")
    @Config.RangeDouble(min = 1, max = 200)
    public static double maxRenderDist = 20;

    @Config.Comment(value = "Particle color/intensity (red)")
    @Config.RangeDouble(min = 0, max = 1)
    public static double particleBrightnessR = 1.0;

    @Config.Comment(value = "Particle color/intensity (green)")
    @Config.RangeDouble(min = 0, max = 1)
    public static double particleBrightnessG = 1.0;

    @Config.Comment(value = "Particle color/intensity (blue)")
    @Config.RangeDouble(min = 0, max = 1)
    public static double particleBrightnessB = 1.0;
}
