package mcjty.nice.datagen;

import mcjty.lib.datagen.BaseRecipeProvider;
import mcjty.nice.Nice;
import mcjty.nice.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Recipes extends BaseRecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        Map<DyeColor, Block> concretes = new HashMap<>();
        concretes.put(DyeColor.WHITE, Blocks.WHITE_CONCRETE);
        concretes.put(DyeColor.ORANGE, Blocks.ORANGE_CONCRETE);
        concretes.put(DyeColor.MAGENTA, Blocks.MAGENTA_CONCRETE);
        concretes.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_CONCRETE);
        concretes.put(DyeColor.YELLOW, Blocks.YELLOW_CONCRETE);
        concretes.put(DyeColor.LIME, Blocks.LIME_CONCRETE);
        concretes.put(DyeColor.PINK, Blocks.PINK_CONCRETE);
        concretes.put(DyeColor.GRAY, Blocks.GRAY_CONCRETE);
        concretes.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_CONCRETE);
        concretes.put(DyeColor.CYAN, Blocks.CYAN_CONCRETE);
        concretes.put(DyeColor.PURPLE, Blocks.PURPLE_CONCRETE);
        concretes.put(DyeColor.BLUE, Blocks.BLUE_CONCRETE);
        concretes.put(DyeColor.BROWN, Blocks.BROWN_CONCRETE);
        concretes.put(DyeColor.GREEN, Blocks.GREEN_CONCRETE);
        concretes.put(DyeColor.RED, Blocks.RED_CONCRETE);
        concretes.put(DyeColor.BLACK, Blocks.BLACK_CONCRETE);

        Map<DyeColor, Block> glasses = new HashMap<>();
        glasses.put(DyeColor.WHITE, Blocks.WHITE_STAINED_GLASS);
        glasses.put(DyeColor.ORANGE, Blocks.ORANGE_STAINED_GLASS);
        glasses.put(DyeColor.MAGENTA, Blocks.MAGENTA_STAINED_GLASS);
        glasses.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_STAINED_GLASS);
        glasses.put(DyeColor.YELLOW, Blocks.YELLOW_STAINED_GLASS);
        glasses.put(DyeColor.LIME, Blocks.LIME_STAINED_GLASS);
        glasses.put(DyeColor.PINK, Blocks.PINK_STAINED_GLASS);
        glasses.put(DyeColor.GRAY, Blocks.GRAY_STAINED_GLASS);
        glasses.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_STAINED_GLASS);
        glasses.put(DyeColor.CYAN, Blocks.CYAN_STAINED_GLASS);
        glasses.put(DyeColor.PURPLE, Blocks.PURPLE_STAINED_GLASS);
        glasses.put(DyeColor.BLUE, Blocks.BLUE_STAINED_GLASS);
        glasses.put(DyeColor.BROWN, Blocks.BROWN_STAINED_GLASS);
        glasses.put(DyeColor.GREEN, Blocks.GREEN_STAINED_GLASS);
        glasses.put(DyeColor.RED, Blocks.RED_STAINED_GLASS);
        glasses.put(DyeColor.BLACK, Blocks.BLACK_STAINED_GLASS);

        for (DyeColor color : DyeColor.values()) {
            generateRecipes(consumer, color, Registration.SOLID_BLOCK_ITEMS, concretes, Registration.SOLID_ITEM_TAG, "solid_recolor_", "ggg", "gwg", "ggg");
            generateRecipes(consumer, color, Registration.PARTICLE_BLOCK_ITEMS, concretes, Registration.PARTICLE_ITEM_TAG, "particle_recolor_", "gwg", "ggg", "ggg");
            generateRecipes(consumer, color, Registration.SOLID_CYLINDER_ITEMS, concretes, Registration.SOLID_CYLINDER_ITEM_TAG, "solid_cylinder_recolor_", "g g", "gwg", "g g");
            generateRecipes(consumer, color, Registration.SOLID_SMALL_CYLINDER_ITEMS, concretes, Registration.SOLID_SMALL_CYLINDER_ITEM_TAG, "solid_small_cylinder_recolor_", "g g", "gwg");
            generateRecipes(consumer, color, Registration.CYLINDER_ITEMS, glasses, Registration.CYLINDER_ITEM_TAG, "cylinder_recolor_", "g g", "gwg", "g g");
            generateRecipes(consumer, color, Registration.SMALL_CYLINDER_ITEMS, glasses, Registration.SMALL_CYLINDER_ITEM_TAG, "small_cylinder_recolor_", "g g", "gwg");
        }
    }

    private void generateRecipes(Consumer<FinishedRecipe> consumer, DyeColor color, Map<DyeColor, RegistryObject<Item>> items, Map<DyeColor, Block> base, TagKey<Item> tag, String prefix, String... pattern) {
        build(consumer, ShapedRecipeBuilder.shaped(items.get(color).get(), 8)
                        .define('g', base.get(color))
                        .define('w', ItemTags.WOOL)
                        .unlockedBy("base", has(base.get(color))),
                pattern
        );
        build(consumer, new ResourceLocation(Nice.MODID, prefix + color.getName()), ShapelessRecipeBuilder.shapeless(items.get(color).get())
                .requires(tag)
                .requires(DyeItem.byColor(color))
                .unlockedBy("base", has(base.get(color)))
        );
    }
}
