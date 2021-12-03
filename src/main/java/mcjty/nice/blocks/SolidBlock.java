package mcjty.nice.blocks;

import mcjty.lib.blocks.RotationType;
import mcjty.nice.Nice;
import mcjty.nice.setup.Registration;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;

public class SolidBlock extends GenericParticleBlock {

    public static final AxisAlignedBB EMPTY = new AxisAlignedBB(0, 0, 0, 0, 0, 0);

    public SolidBlock() {
        super(0.8f, false);
    }

    @Override
    public RotationType getRotationType() {
        return RotationType.NONE;
    }

//    @Override
//    public void fillItemCategory(ItemGroup tab, NonNullList<ItemStack> items) {
//        if (tab == Nice.setup.getTab()) {
//            for (DyeColor color : DyeColor.values()) {
//                items.add(new ItemStack(Registration.SOLID_BLOCK_ITEMS.get(color).get()));
//            }
//        } else {
//            super.fillItemCategory(tab, items);
//        }
//    }
//

    //    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
//        for (BlockColor color : BlockColor.values()) {
//            items.add(makeColoredBlock(this, color, 1));
//        }
//    }
}
