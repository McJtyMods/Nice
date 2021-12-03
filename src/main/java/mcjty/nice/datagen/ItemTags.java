package mcjty.nice.datagen;

import mcjty.nice.Nice;
import mcjty.nice.setup.Registration;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class ItemTags extends ItemTagsProvider {

    public ItemTags(DataGenerator generator, BlockTagsProvider blockTagsProvider, ExistingFileHelper helper) {
        super(generator, blockTagsProvider, Nice.MODID, helper);
    }

    @Override
    protected void addTags() {
        Builder<Item> tag = tag(Registration.SOLID_ITEM_TAG);
        Registration.SOLID_BLOCK_ITEMS.values().forEach(s -> tag.add(s.get()));
    }

    @Nonnull
    @Override
    public String getName() {
        return "Nice Tags";
    }
}
