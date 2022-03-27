package mcjty.nice.datagen;

import mcjty.nice.Nice;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class BlockTags extends BlockTagsProvider {

    public BlockTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, Nice.MODID, helper);
    }

    @Override
    protected void addTags() {
    }

    @Override
    @Nonnull
    public String getName() {
        return "Nice Tags";
    }
}
