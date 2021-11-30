package mcjty.nice.datagen;

import mcjty.lib.datagen.BaseItemModelProvider;
import mcjty.nice.Nice;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

class Items extends BaseItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Nice.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }

}
