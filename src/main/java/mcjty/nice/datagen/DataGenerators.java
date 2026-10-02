package mcjty.nice.datagen;

import mcjty.lib.datagen.BaseBlockStateProvider;
import mcjty.lib.datagen.BaseItemModelProvider;
import mcjty.lib.datagen.DataGen;
import mcjty.lib.datagen.Dob;
import mcjty.lib.setup.DeferredItem;
import mcjty.nice.Nice;
import mcjty.nice.setup.Registration;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.ObjModelBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;

public final class DataGenerators {

    public static void datagen(DataGen datagen) {
        Registration.SOLID_BLOCKS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName();
            ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/solid_" + colorname);
            datagen.add(Dob.blockBuilder(entry.getValue())
                    .stonePickaxeTags()
                    .simpleLoot()
                    .blockState(p -> p.simpleBlock(entry.getValue().get(), p.models().cube("solid_" + colorname, rl, rl, rl, rl, rl, rl)
                            .texture("particle", rl))));
        });
        Registration.PARTICLE_BLOCKS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName();
            ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/buis_" + colorname);
            datagen.add(Dob.blockBuilder(entry.getValue())
                    .stonePickaxeTags()
                    .simpleLoot()
                    .blockState(p -> p.simpleBlock(entry.getValue().get(), p.models().cube("particle_" + colorname, rl, rl, rl, rl, rl, rl)
                            .texture("particle", rl))));
        });
        Registration.CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName();
            ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/buis_" + colorname);
            datagen.add(Dob.blockBuilder(entry.getValue())
                    .stonePickaxeTags()
                    .simpleLoot()
                    .blockState(p -> p.directionalBlock(entry.getValue().get(), cylinderModel(p, entry.getValue().get(), "cylinder", rl))));
        });
        Registration.SMALL_CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName();
            ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/buis_" + colorname);
            datagen.add(Dob.blockBuilder(entry.getValue())
                    .stonePickaxeTags()
                    .simpleLoot()
                    .blockState(p -> p.directionalBlock(entry.getValue().get(), cylinderModel(p, entry.getValue().get(), "smallcylinder", rl))));
        });
        Registration.SOLID_CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName();
            ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/solid_" + colorname);
            datagen.add(Dob.blockBuilder(entry.getValue())
                    .stonePickaxeTags()
                    .simpleLoot()
                    .blockState(p -> p.directionalBlock(entry.getValue().get(), cylinderModel(p, entry.getValue().get(), "cylinder", rl))));
        });
        Registration.SOLID_SMALL_CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName();
            ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/solid_" + colorname);
            datagen.add(Dob.blockBuilder(entry.getValue())
                    .stonePickaxeTags()
                    .simpleLoot()
                    .blockState(p -> p.directionalBlock(entry.getValue().get(), cylinderModel(p, entry.getValue().get(), "smallcylinder", rl))));
        });

        for (Map.Entry<DyeColor, DeferredItem<Item>> entry : Registration.SOLID_BLOCK_ITEMS.entrySet()) {
            datagen.add(Dob.itemBuilder(entry.getValue())
                    .itemTags(List.of(Registration.SOLID_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, entry.getValue().get(), "block/solid_" + entry.getKey().getName())));
        }
        for (Map.Entry<DyeColor, DeferredItem<Item>> entry : Registration.PARTICLE_BLOCK_ITEMS.entrySet()) {
            datagen.add(Dob.itemBuilder(entry.getValue())
                    .itemTags(List.of(Registration.PARTICLE_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, entry.getValue().get(), "block/particle_" + entry.getKey().getName())));
        }
        for (Map.Entry<DyeColor, DeferredItem<Item>> entry : Registration.CYLINDER_ITEMS.entrySet()) {
            datagen.add(Dob.itemBuilder(entry.getValue())
                    .itemTags(List.of(Registration.CYLINDER_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, entry.getValue().get(), "block/cylinder_" + entry.getKey().getName())));
        }
        for (Map.Entry<DyeColor, DeferredItem<Item>> entry : Registration.SMALL_CYLINDER_ITEMS.entrySet()) {
            datagen.add(Dob.itemBuilder(entry.getValue())
                    .itemTags(List.of(Registration.SMALL_CYLINDER_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, entry.getValue().get(), "block/small_cylinder_" + entry.getKey().getName())));
        }
        for (Map.Entry<DyeColor, DeferredItem<Item>> entry : Registration.SOLID_CYLINDER_ITEMS.entrySet()) {
            datagen.add(Dob.itemBuilder(entry.getValue())
                    .itemTags(List.of(Registration.SOLID_CYLINDER_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, entry.getValue().get(), "block/solid_cylinder_" + entry.getKey().getName())));
        }
        for (Map.Entry<DyeColor, DeferredItem<Item>> entry : Registration.SOLID_SMALL_CYLINDER_ITEMS.entrySet()) {
            datagen.add(Dob.itemBuilder(entry.getValue())
                    .itemTags(List.of(Registration.SOLID_SMALL_CYLINDER_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, entry.getValue().get(), "block/solid_small_cylinder_" + entry.getKey().getName())));
        }
        Recipes.buildCraftingRecipes(datagen);
    }

    private static void inventoryModel(BaseItemModelProvider provider, Item item, String blockModel) {
        // Show the top and sides, with directional lighting to reveal the model's depth.
        provider.getBuilder(ForgeRegistries.ITEMS.getKey(item).getPath())
                .parent(new ModelFile.UncheckedModelFile(new ResourceLocation(Nice.MODID, blockModel)))
                .guiLight(BlockModel.GuiLight.SIDE)
                .transforms()
                .transform(ItemDisplayContext.GUI)
                .rotation(30, 225, 0)
                .scale(0.625f)
                .end()
                .end();
    }

    private static BlockModelBuilder cylinderModel(BaseBlockStateProvider provider, Block block, String objName, ResourceLocation rl) {
        return provider.models().getBuilder(ForgeRegistries.BLOCKS.getKey(block).getPath())
                .customLoader(ObjModelBuilder::begin)
                .modelLocation(new ResourceLocation(Nice.MODID, "models/block/" + objName + ".obj"))
                .flipV(true)
                .end()
                .texture("buis", rl)
                .texture("particle", rl);
    }
}
