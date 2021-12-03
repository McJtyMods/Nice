package mcjty.nice.datagen;

import mcjty.lib.datagen.BaseBlockStateProvider;
import mcjty.nice.Nice;
import mcjty.nice.blocks.GenericParticleBlock;
import mcjty.nice.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.loaders.OBJLoaderBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;

public class BlockStates extends BaseBlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Nice.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Registration.SOLID_BLOCKS.values().forEach(b -> {
            variantBlock(b.get(), state -> {
                String colorname = state.getValue(GenericParticleBlock.COLOR).getName();
                ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/solid_" + colorname);
                return models().cube("solid_" + colorname, rl, rl, rl, rl, rl, rl);
            });
        });

        Block[] blocks = Registration.collect(Registration.CYLINDERS, Registration.SMALL_CYLINDERS, Registration.SOLID_CYLINDERS, Registration.SOLID_SMALL_CYLINDERS);
        Arrays.stream(blocks).forEach(block -> {
            orientedBlock(block, ((state, builder) -> {
                String colorname = state.getValue(GenericParticleBlock.COLOR).getName();
                ResourceLocation rl = new ResourceLocation(Nice.MODID, "block/solid_" + colorname);
                String objName;
                if (block.getRegistryName().getPath().contains("small")) {
                    objName = "smallcylinder";
                } else {
                    objName = "cylinder";
                }
                builder.modelFile(models().withExistingParent(block.getRegistryName().getPath() + "_" + colorname, "cube").customLoader(OBJLoaderBuilder::begin)
                        .modelLocation(new ResourceLocation(Nice.MODID, "models/block/" + objName + ".obj"))
                        .flipV(true)
                        .end()
                        .texture("buis", rl));
            }));
        });
    }

}
