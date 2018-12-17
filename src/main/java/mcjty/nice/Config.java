package mcjty.nice;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static final String CONFIG_RENDERING = "rendering";

    public static double maxRenderDistSquared;
    public static float particleBrightnessR = 1.0f;
    public static float particleBrightnessG = 1.0f;
    public static float particleBrightnessB = 1.0f;

    public static void readConfig(Configuration config) {
        double d = config.getFloat("maxRenderDist", CONFIG_RENDERING, 20, 1, 200, "Render distance at which the particles disappear");
        maxRenderDistSquared = d * d;

        particleBrightnessR = config.getFloat("particleBrightnessR", CONFIG_RENDERING, particleBrightnessR, 0.0f, 1.0f, "Brightness of particles");
        particleBrightnessG = config.getFloat("particleBrightnessG", CONFIG_RENDERING, particleBrightnessG, 0.0f, 1.0f, "Brightness of particles");
        particleBrightnessB = config.getFloat("particleBrightnessB", CONFIG_RENDERING, particleBrightnessB, 0.0f, 1.0f, "Brightness of particles");
    }
}
