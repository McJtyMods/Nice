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
        Registration.SOLID_BLOCK_ITEMS.values().forEach(s -> tag(Registration.SOLID_ITEM_TAG).add(s.get()));
        Registration.PARTICLE_BLOCK_ITEMS.values().forEach(s -> tag(Registration.PARTICLE_ITEM_TAG).add(s.get()));
        Registration.CYLINDER_ITEMS.values().forEach(s -> tag(Registration.CYLINDER_ITEM_TAG).add(s.get()));
        Registration.SMALL_CYLINDER_ITEMS.values().forEach(s -> tag(Registration.SMALL_CYLINDER_ITEM_TAG).add(s.get()));
        Registration.SOLID_CYLINDER_ITEMS.values().forEach(s -> tag(Registration.SOLID_CYLINDER_ITEM_TAG).add(s.get()));
        Registration.SOLID_SMALL_CYLINDER_ITEMS.values().forEach(s -> tag(Registration.SOLID_SMALL_CYLINDER_ITEM_TAG).add(s.get()));
    }

    @Nonnull
    @Override
    public String getName() {
        return "Nice Tags";
    }
}
