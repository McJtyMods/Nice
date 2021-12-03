package mcjty.nice.datagen;

import mcjty.lib.datagen.BaseItemModelProvider;
import mcjty.nice.Nice;
import mcjty.nice.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.Map;

class Items extends BaseItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Nice.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Map.Entry<DyeColor, RegistryObject<Item>> entry : Registration.SOLID_BLOCK_ITEMS.entrySet()) {
            parentedItem(entry.getValue().get(), "block/solid_" + entry.getKey().getName());
        }
        for (Map.Entry<DyeColor, RegistryObject<Item>> entry : Registration.CYLINDER_ITEMS.entrySet()) {
            parentedItem(entry.getValue().get(), "block/cylinder_" + entry.getKey().getName());
        }
        for (Map.Entry<DyeColor, RegistryObject<Item>> entry : Registration.SMALL_CYLINDER_ITEMS.entrySet()) {
            parentedItem(entry.getValue().get(), "block/small_cylinder_" + entry.getKey().getName());
        }
        for (Map.Entry<DyeColor, RegistryObject<Item>> entry : Registration.SOLID_CYLINDER_ITEMS.entrySet()) {
            parentedItem(entry.getValue().get(), "block/solid_cylinder_" + entry.getKey().getName());
        }
        for (Map.Entry<DyeColor, RegistryObject<Item>> entry : Registration.SOLID_SMALL_CYLINDER_ITEMS.entrySet()) {
            parentedItem(entry.getValue().get(), "block/solid_small_cylinder_" + entry.getKey().getName());
        }
    }

}
