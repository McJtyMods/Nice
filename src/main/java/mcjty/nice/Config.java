package mcjty.nice;

import mcjty.nice.proxy.CommonProxy;

public class Config {

    public static final String CONFIG_RENDERING = "rendering";

    public static double maxRenderDistSquared;

    public static void readConfig() {
        double d = CommonProxy.config.getFloat("maxRenderDist", CONFIG_RENDERING, 20, 1, 200, "Render distance at which the particles disappear");
        maxRenderDistSquared = d * d;
    }
}
