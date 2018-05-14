package mcjty.nice;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static final String CONFIG_RENDERING = "rendering";

    public static double maxRenderDistSquared;

    public static void readConfig(Configuration config) {
        double d = config.getFloat("maxRenderDist", CONFIG_RENDERING, 20, 1, 200, "Render distance at which the particles disappear");
        maxRenderDistSquared = d * d;
    }
}
