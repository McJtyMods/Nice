package mcjty.nice.blocks;

import mcjty.lib.blocks.RotationType;
import net.minecraft.util.math.AxisAlignedBB;

public class SolidBlock extends GenericParticleBlock {

    public static final AxisAlignedBB EMPTY = new AxisAlignedBB(0, 0, 0, 0, 0, 0);

    public SolidBlock() {
        super(0.8f);
    }

    @Override
    public RotationType getRotationType() {
        return RotationType.NONE;
    }

    //    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
//        for (BlockColor color : BlockColor.values()) {
//            items.add(makeColoredBlock(this, color, 1));
//        }
//    }
}
