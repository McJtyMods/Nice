package mcjty.nice.datagen;

import mcjty.lib.datagen.BaseRecipeProvider;
import mcjty.nice.Nice;
import mcjty.nice.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Recipes extends BaseRecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
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
            build(consumer, ShapedRecipeBuilder.shaped(Registration.SOLID_BLOCK_ITEMS.get(color).get())
                            .define('g', concretes.get(color) )
                            .define('w', ItemTags.WOOL)
                            .unlockedBy("concrete", has(concretes.get(color))),
                    "ggg", "gwg", "ggg"
            );
//            build(consumer, new ResourceLocation(Nice.MODID, "color"), ShapelessRecipeBuilder.shapeless(Registration.SOLID_BLOCK_ITEMS.get(color).get())
//                    .requires(Registration.SOLID_ITEM_TAG)
//                    .requires(DyeItem.byColor(color))
//                    .unlockedBy("concrete", has(concretes.get(color).asItem()))
//            );
            build(consumer, ShapedRecipeBuilder.shaped(Registration.SOLID_CYLINDER_ITEMS.get(color).get())
                            .define('g', concretes.get(color).asItem())
                            .define('w', ItemTags.WOOL)
                            .unlockedBy("concrete", has(concretes.get(color).asItem())),
                    "g g", "gwg", "g g"
            );
            build(consumer, ShapedRecipeBuilder.shaped(Registration.SOLID_SMALL_CYLINDER_ITEMS.get(color).get())
                            .define('g', concretes.get(color).asItem())
                            .define('w', ItemTags.WOOL)
                            .unlockedBy("concrete", has(concretes.get(color).asItem())),
                    "g g", "gwg"
            );
            build(consumer, ShapedRecipeBuilder.shaped(Registration.CYLINDER_ITEMS.get(color).get())
                            .define('g', glasses.get(color).asItem())
                            .define('w', ItemTags.WOOL)
                            .unlockedBy("glasses", has(glasses.get(color).asItem())),
                    "g g", "gwg", "g g"
            );
            build(consumer, ShapedRecipeBuilder.shaped(Registration.SMALL_CYLINDER_ITEMS.get(color).get())
                            .define('g', glasses.get(color).asItem())
                            .define('w', ItemTags.WOOL)
                            .unlockedBy("glasses", has(glasses.get(color).asItem())),
                    "g g", "gwg"
            );
        }
    }
}
