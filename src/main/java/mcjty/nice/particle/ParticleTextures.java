package mcjty.nice.particle;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import mcjty.nice.Nice;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticleTextures extends SimplePreparableReloadListener<Map<String, ParticleTextures.Definition>> {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ResourceLocation LOCATION = new ResourceLocation(Nice.MODID, "textures/block/effects/particles.json");
    private static final int ATLAS_SIZE = 256;
    private static final int TILE_SIZE = 32;
    // JSON frame order loops at ten frames per second.
    private static final long FRAME_TIME = 100;
    private static volatile Map<String, Definition> definitions = Map.of();

    public record Frame(int u, int v) {
        public double u1() { return (double) u / ATLAS_SIZE; }
        public double u2() { return (double) (u + TILE_SIZE) / ATLAS_SIZE; }
        public double v1() { return (double) v / ATLAS_SIZE; }
        public double v2() { return (double) (v + TILE_SIZE) / ATLAS_SIZE; }
    }

    public record Definition(List<Frame> textures, boolean animation, boolean fullbright, boolean glow) {
        public Frame frame(int seed, long age) {
            int index = animation
                    ? (int) ((Math.max(0, age) / FRAME_TIME) % textures.size())
                    : Math.floorMod(seed, textures.size());
            return textures.get(index);
        }
    }

    public static Definition get(String type) {
        return type == null ? null : definitions.get(type);
    }

    static Map<String, Definition> parse(Reader reader) {
        JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
        Map<String, Definition> result = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
            JsonObject object = entry.getValue().getAsJsonObject();
            List<Frame> frames = new ArrayList<>();
            for (JsonElement element : GsonHelper.getAsJsonArray(object, "textures")) {
                JsonObject texture = element.getAsJsonObject();
                int u = GsonHelper.getAsInt(texture, "u");
                int v = GsonHelper.getAsInt(texture, "v");
                if (u < 0 || v < 0 || u > ATLAS_SIZE - TILE_SIZE || v > ATLAS_SIZE - TILE_SIZE) {
                    throw new IllegalArgumentException("Particle texture outside atlas: " + entry.getKey());
                }
                frames.add(new Frame(u, v));
            }
            if (frames.isEmpty()) {
                throw new IllegalArgumentException("Empty particle textures: " + entry.getKey());
            }
            result.put(entry.getKey(), new Definition(List.copyOf(frames),
                    GsonHelper.getAsBoolean(object, "animation", false),
                    GsonHelper.getAsBoolean(object, "fullbright", false),
                    GsonHelper.getAsBoolean(object, "glow", false)));
        }
        for (String type : List.of("smoke", "fish1", "fish2", "fish3", "sparkle", "bubble")) {
            if (!result.containsKey(type)) {
                throw new IllegalArgumentException("Missing particle texture type: " + type);
            }
        }
        return Map.copyOf(result);
    }

    @Override
    protected Map<String, Definition> prepare(ResourceManager manager, ProfilerFiller profiler) {
        try (Reader reader = manager.getResourceOrThrow(LOCATION).openAsReader()) {
            return parse(reader);
        } catch (IOException | RuntimeException e) {
            LOGGER.error("Unable to load particle textures from {}", LOCATION, e);
            return definitions;
        }
    }

    @Override
    protected void apply(Map<String, Definition> loaded, ResourceManager manager, ProfilerFiller profiler) {
        definitions = loaded;
    }
}
