package mcjty.nice.datagen;

import mcjty.lib.datagen.BaseBlockStateProvider;
import mcjty.lib.datagen.BaseItemModelProvider;
import mcjty.lib.datagen.DataGen;
import mcjty.lib.datagen.Dob;
import mcjty.nice.Nice;
import mcjty.nice.setup.Registration;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.ObjModelBuilder;

import java.util.List;

public final class DataGenerators {

    public static void datagen(DataGen datagen) {
        Registration.SOLID_BLOCKS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName().toLowerCase();
            ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(Nice.MODID, "block/solid_" + colorname);
            datagen.add(Dob.builder(entry.getValue(), Registration.SOLID_BLOCK_ITEMS.get(entry.getKey()))
                    .stonePickaxeTags()
                    .simpleLoot()
                    .itemTags(List.of(Registration.SOLID_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, Registration.SOLID_BLOCK_ITEMS.get(entry.getKey()).get(), "block/solid_" + colorname))
                    .blockState(p -> p.simpleBlock(entry.getValue().get(), p.models().cube("solid_" + colorname, rl, rl, rl, rl, rl, rl)
                            .texture("particle", rl))));
        });
        Registration.PARTICLE_BLOCKS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName().toLowerCase();
            ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(Nice.MODID, "block/buis_" + colorname);
            datagen.add(Dob.builder(entry.getValue(), Registration.PARTICLE_BLOCK_ITEMS.get(entry.getKey()))
                    .stonePickaxeTags()
                    .simpleLoot()
                    .itemTags(List.of(Registration.PARTICLE_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, Registration.PARTICLE_BLOCK_ITEMS.get(entry.getKey()).get(), "block/particle_" + colorname))
                    .blockState(p -> p.simpleBlock(entry.getValue().get(), p.models().cube("particle_" + colorname, rl, rl, rl, rl, rl, rl)
                            .texture("particle", rl))));
        });
        Registration.CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName().toLowerCase();
            ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(Nice.MODID, "block/buis_" + colorname);
            datagen.add(Dob.builder(entry.getValue(), Registration.CYLINDER_ITEMS.get(entry.getKey()))
                    .stonePickaxeTags()
                    .simpleLoot()
                    .itemTags(List.of(Registration.CYLINDER_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, Registration.CYLINDER_ITEMS.get(entry.getKey()).get(), "block/cylinder_" + colorname))
                    .blockState(p -> p.directionalBlock(entry.getValue().get(), cylinderModel(p, entry.getValue().get(), "cylinder", rl))));
        });
        Registration.SMALL_CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName().toLowerCase();
            ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(Nice.MODID, "block/buis_" + colorname);
            datagen.add(Dob.builder(entry.getValue(), Registration.SMALL_CYLINDER_ITEMS.get(entry.getKey()))
                    .stonePickaxeTags()
                    .simpleLoot()
                    .itemTags(List.of(Registration.SMALL_CYLINDER_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, Registration.SMALL_CYLINDER_ITEMS.get(entry.getKey()).get(), "block/small_cylinder_" + colorname))
                    .blockState(p -> p.directionalBlock(entry.getValue().get(), cylinderModel(p, entry.getValue().get(), "smallcylinder", rl))));
        });
        Registration.SOLID_CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName().toLowerCase();
            ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(Nice.MODID, "block/solid_" + colorname);
            datagen.add(Dob.builder(entry.getValue(), Registration.SOLID_CYLINDER_ITEMS.get(entry.getKey()))
                    .stonePickaxeTags()
                    .simpleLoot()
                    .itemTags(List.of(Registration.SOLID_CYLINDER_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, Registration.SOLID_CYLINDER_ITEMS.get(entry.getKey()).get(), "block/solid_cylinder_" + colorname))
                    .blockState(p -> p.directionalBlock(entry.getValue().get(), cylinderModel(p, entry.getValue().get(), "cylinder", rl))));
        });
        Registration.SOLID_SMALL_CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName().toLowerCase();
            ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(Nice.MODID, "block/solid_" + colorname);
            datagen.add(Dob.builder(entry.getValue(), Registration.SOLID_SMALL_CYLINDER_ITEMS.get(entry.getKey()))
                    .stonePickaxeTags()
                    .simpleLoot()
                    .itemTags(List.of(Registration.SOLID_SMALL_CYLINDER_ITEM_TAG))
                    .itemModel(p -> inventoryModel(p, Registration.SOLID_SMALL_CYLINDER_ITEMS.get(entry.getKey()).get(), "block/solid_small_cylinder_" + colorname))
                    .blockState(p -> p.directionalBlock(entry.getValue().get(), cylinderModel(p, entry.getValue().get(), "smallcylinder", rl))));
        });

        Recipes.buildCraftingRecipes(datagen);
    }

    private static void inventoryModel(BaseItemModelProvider provider, Item item, String blockModel) {
        // Show the top and sides, with directional lighting to reveal the model's depth.
        provider.getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath())
                .parent(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(Nice.MODID, blockModel)))
                .guiLight(BlockModel.GuiLight.SIDE)
                .transforms()
                .transform(ItemDisplayContext.GUI)
                .rotation(30, 225, 0)
                .scale(0.625f)
                .end()
                .end();
    }

    private static BlockModelBuilder cylinderModel(BaseBlockStateProvider provider, Block block, String objName, ResourceLocation rl) {
        return provider.models().getBuilder(BuiltInRegistries.BLOCK.getKey(block).getPath())
                .customLoader(ObjModelBuilder::begin)
                .modelLocation(ResourceLocation.fromNamespaceAndPath(Nice.MODID, "models/block/" + objName + ".obj"))
                .flipV(true)
                .end()
                .texture("buis", rl)
                .texture("particle", rl);
    }
}
