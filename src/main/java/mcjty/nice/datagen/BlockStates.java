package mcjty.nice.datagen;

import mcjty.lib.datagen.BaseBlockStateProvider;
import mcjty.nice.Nice;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BaseBlockStateProvider {

    private static final ResourceLocation DEFAULT_TOP = new ResourceLocation(Nice.MODID, "block/machine_top");
    private static final ResourceLocation DEFAULT_SIDE = new ResourceLocation(Nice.MODID, "block/machine_side");
    private static final ResourceLocation DEFAULT_BOTTOM = new ResourceLocation(Nice.MODID, "block/machine_bottom");

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Nice.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }

}
