package mcjty.nice.blocks;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class SolidTileEntity extends GenericTileEntity {

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

//    @Override
//    @Nonnull
//    @SideOnly(Side.CLIENT)
//    public AxisAlignedBB getRenderBoundingBox() {
//        int xCoord = getPos().getX();
//        int yCoord = getPos().getY();
//        int zCoord = getPos().getZ();
//        return new AxisAlignedBB(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 3, zCoord + 2);
//    }
//
}
