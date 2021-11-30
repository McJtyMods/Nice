package mcjty.nice.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;

public class CylinderBlock extends GenericParticleBlock {

    public static final AxisAlignedBB EMPTY = new AxisAlignedBB(0, 0, 0, 0, 0, 0);

    public CylinderBlock(float scale) {
        super(scale);
    }

//    @Override
//    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
//        for (BlockColor color : BlockColor.values()) {
//            if (color != BlockColor.TRANSP) {
//                items.add(makeColoredBlock(this, color, 1));
//            }
//        }
//    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getClickedFace());
    }
}
