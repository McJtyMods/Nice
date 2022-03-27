package mcjty.nice.setup;

import mcjty.lib.setup.DefaultModSetup;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup extends DefaultModSetup {

    public ModSetup() {
        createTab("nice", () -> new ItemStack(Registration.CYLINDER_ITEMS.get(DyeColor.RED).get()));
    }

    @Override
    protected void setupModCompat() {
    }

    @Override
    public void init(FMLCommonSetupEvent e) {
        super.init(e);
    }
}