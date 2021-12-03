package mcjty.nice.blocks;

import mcjty.nice.Nice;
import mcjty.nice.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;

public class CylinderBlock extends GenericParticleBlock {

    public static final AxisAlignedBB EMPTY = new AxisAlignedBB(0, 0, 0, 0, 0, 0);

    public CylinderBlock(float scale) {
        super(scale, true);
    }

//    @Override
//    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
//        for (BlockColor color : BlockColor.values()) {
//            if (color != BlockColor.TRANSP) {
//                items.add(makeColoredBlock(this, color, 1));
//            }
//        }
//    }


//    @Override
//    public void fillItemCategory(ItemGroup tab, NonNullList<ItemStack> items) {
//        if (tab == Nice.setup.getTab()) {
//            for (DyeColor color : DyeColor.values()) {
//                items.add(new ItemStack(Registration.CYLINDER_ITEMS.get(color).get()));
//            }
//        } else {
//            super.fillItemCategory(tab, items);
//        }
//    }
//
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getClickedFace());
    }
}
