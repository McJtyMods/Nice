package mcjty.nice.datagen;

import mcjty.lib.datagen.BaseBlockStateProvider;
import mcjty.nice.Nice;
import mcjty.nice.blocks.GenericParticleBlock;
import mcjty.nice.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.loaders.OBJLoaderBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;

public class BlockStates extends BaseBlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Nice.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Registration.SOLID_BLOCKS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName();
            ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/solid_" + colorname);
            simpleBlock(entry.getValue().get(), models().cube("solid_" + colorname, rl, rl, rl, rl, rl, rl));
        });

        Registration.CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName();
            ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/buis_" + colorname);
            simpleBlock(entry.getValue().get(), cylinderModel(entry.getValue().get(), "cylinder", rl));
        });
        Registration.SMALL_CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName();
            ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/buis_" + colorname);
            simpleBlock(entry.getValue().get(), cylinderModel(entry.getValue().get(), "smallcylinder", rl));
        });
        Registration.SOLID_CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName();
            ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/solid_" + colorname);
            simpleBlock(entry.getValue().get(), cylinderModel(entry.getValue().get(), "cylinder", rl));
        });
        Registration.SOLID_SMALL_CYLINDERS.entrySet().forEach(entry -> {
            String colorname = entry.getKey().getName();
            ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/solid_" + colorname);
            simpleBlock(entry.getValue().get(), cylinderModel(entry.getValue().get(), "smallcylinder", rl));
        });
    }

    private BlockModelBuilder cylinderModel(Block block, String objName, ResourceLocation rl) {
        return models().getBuilder(block.getRegistryName().getPath())
                .customLoader(OBJLoaderBuilder::begin)
                .modelLocation(new ResourceLocation(Nice.MODID, "models/block/" + objName + ".obj"))
                .flipV(true)
                .end()
                .texture("buis", rl);
    }

}
