package mcjty.nice.datagen;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/** Generates the block families, including Fabric OBJ models and modern item definitions. */
public final class DataGenerators implements DataProvider {
    private static final Gson GSON = new Gson();
    private final Path root;
    private final Map<String, JsonElement> files = new LinkedHashMap<>();

    private record Family(String item, String block, String model, String name, String texture,
                          String obj, boolean particles, String... pattern) {}
    private static final List<Family> FAMILIES = List.of(
            new Family("solid", "solid_block", "solid", "Solid Block", "solid", null, false, "ggg", "gwg", "ggg"),
            new Family("particle", "particle_block", "particle", "Particle Block", "buis", null, true, "gwg", "ggg", "ggg"),
            new Family("cylinder", "cylinder", "cylinder", "Particle Cylinder", "buis", "cylinder", true, "g g", "gwg", "g g"),
            new Family("small_cylinder", "small_cylinder", "small_cylinder", "Small Particle Cylinder", "buis", "smallcylinder", true, "g g", "gwg"),
            new Family("solid_cylinder", "solid_cylinder", "solid_cylinder", "Solid Cylinder", "solid", "cylinder", false, "g g", "gwg", "g g"),
            new Family("solid_small_cylinder", "solid_small_cylinder", "solid_small_cylinder", "Solid Small Cylinder", "solid", "smallcylinder", false, "g g", "gwg")
    );

    public DataGenerators(PackOutput output) {
        root = output.getOutputFolder();
    }

    private void add(String path, Object json) {
        files.put(path, GSON.toJsonTree(json));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        files.clear();
        Map<String, String> language = new LinkedHashMap<>();
        language.put("itemGroup.nice", "NICE");
        language.put("message.nice.shiftmessage", "<Press Shift>");
        List<String> blocks = new ArrayList<>();
        for (Family family : FAMILIES) {
            language.put("tag.item.nice." + family.item, family.name + "s");
            List<String> items = new ArrayList<>();
            for (DyeColor dye : DyeColor.values()) {
                String color = dye.getName();
                String block = family.block + "_" + color;
                String item = family.item + "_" + color;
                items.add("nice:" + item);
                blocks.add("nice:" + block);
                client(family, color, block, item, language);
                server(family, color, block, item);
            }
            add("data/nice/tags/item/" + family.item + ".json", Map.of("values", items));
        }
        add("assets/nice/lang/en_us.json", language);
        add("data/minecraft/tags/block/mineable/pickaxe.json", Map.of("values", blocks));
        add("data/minecraft/tags/block/needs_stone_tool.json", Map.of("values", blocks));
        return CompletableFuture.allOf(files.entrySet().stream()
                .map(e -> DataProvider.saveStable(output, e.getValue(), root.resolve(e.getKey())))
                .toArray(CompletableFuture[]::new));
    }

    private void client(Family family, String color, String block, String item, Map<String, String> language) {
        String model = family.model + "_" + color;
        String texture = "nice:block/" + family.texture + "_" + color;
        // 26.2 stores translucency in the texture material rather than the old render layer API.
        Object material = family.particles ? Map.of("sprite", texture, "force_translucent", true) : texture;
        if (family.obj == null) {
            add("assets/nice/models/block/" + model + ".json", Map.of("parent", "minecraft:block/cube_all",
                    "textures", Map.of("all", material, "particle", material)));
            add("assets/nice/blockstates/" + block + ".json", Map.of("variants", Map.of("", Map.of("model", "nice:block/" + model))));
        } else {
            add("assets/nice/models/block/" + model + ".json", Map.of("fabric:type", "nice:obj", "flip_v", true,
                    "model", "nice:models/block/" + family.obj + ".obj", "textures", Map.of("buis", material, "particle", material)));
            Map<String, Object> variants = new LinkedHashMap<>();
            variants.put("facing=up", Map.of("model", "nice:block/" + model));
            variants.put("facing=down", Map.of("model", "nice:block/" + model, "x", 180));
            variants.put("facing=north", Map.of("model", "nice:block/" + model, "x", 90));
            variants.put("facing=east", Map.of("model", "nice:block/" + model, "x", 90, "y", 90));
            variants.put("facing=south", Map.of("model", "nice:block/" + model, "x", 90, "y", 180));
            variants.put("facing=west", Map.of("model", "nice:block/" + model, "x", 90, "y", 270));
            add("assets/nice/blockstates/" + block + ".json", Map.of("variants", variants));
        }
        add("assets/nice/models/item/" + item + ".json", Map.of("parent", "nice:block/" + model, "gui_light", "side",
                "display", Map.of("gui", Map.of("rotation", List.of(30, 225, 0), "scale", List.of(0.625, 0.625, 0.625)))));
        add("assets/nice/items/" + item + ".json", Map.of("model", Map.of("type", "minecraft:model", "model", "nice:item/" + item)));
        String key = "block.nice." + block;
        language.put(key, family.name + " (" + color + ")");
        language.put(key + ".header", "Use item to change (not consumed)");
        if (family.particles) {
            language.put(key + ".diamond", "    Diamond for sparkles");
            language.put(key + ".water", "    Water bucket for bubbles");
            language.put(key + ".wool", "    Wool for smoke");
            language.put(key + ".fish", "    Fish for fish");
            language.put(key + ".string", "    String for nothing");
            language.put(key + ".glass", "    Glass to toggle visibility");
        }
        language.put(key + ".dye", "    A dye to change the color");
    }

    private void server(Family family, String color, String block, String item) {
        String base = "minecraft:" + color + (family.texture.equals("buis") && family.obj != null ? "_stained_glass" : "_concrete");
        add("data/nice/recipe/" + item + ".json", Map.of("type", "minecraft:crafting_shaped", "category", "misc",
                "key", Map.of("g", base, "w", "#minecraft:wool"), "pattern", List.of(family.pattern),
                "result", Map.of("id", "nice:" + item, "count", 8)));
        String recolor = family.item + "_recolor_" + color;
        add("data/nice/recipe/" + recolor + ".json", Map.of("type", "minecraft:crafting_shapeless", "category", "misc",
                "ingredients", List.of("#nice:" + family.item, "minecraft:" + color + "_dye"), "result", Map.of("id", "nice:" + item, "count", 1)));
        advancement(item, base);
        advancement(recolor, base);
        add("data/nice/loot_table/blocks/" + block + ".json", Map.of("type", "minecraft:block",
                "pools", List.of(Map.of("rolls", 1, "entries", List.of(Map.of("type", "minecraft:item", "name", "nice:" + item)),
                        "conditions", List.of(Map.of("condition", "minecraft:survives_explosion"))))));
    }

    private void advancement(String recipe, String base) {
        add("data/nice/advancement/recipes/misc/" + recipe + ".json", Map.of("parent", "minecraft:recipes/root",
                "criteria", Map.of("base", Map.of("trigger", "minecraft:inventory_changed", "conditions", Map.of("items", List.of(Map.of("items", base)))),
                        "has_the_recipe", Map.of("trigger", "minecraft:recipe_unlocked", "conditions", Map.of("recipe", "nice:" + recipe))),
                "requirements", List.of(List.of("base", "has_the_recipe")), "rewards", Map.of("recipes", List.of("nice:" + recipe))));
    }

    @Override
    public String getName() {
        return "NICE resources";
    }
}
