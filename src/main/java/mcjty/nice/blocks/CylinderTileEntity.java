package mcjty.nice.blocks;

import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticleProvider;
import mcjty.nice.particle.IParticleSystem;
import mcjty.nice.particle.systems.SmokeSystem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class CylinderTileEntity extends GenericTileEntity implements IParticleProvider {

    private static final IParticleSystem SMOKE = new SmokeSystem();

    private ICalculatedParticleSystem calculatedParticleSystem;

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        int xCoord = getPos().getX();
        int yCoord = getPos().getY();
        int zCoord = getPos().getZ();
        return new AxisAlignedBB(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 3, zCoord + 2);
    }

    @Override
    public IParticleSystem getParticleSystem() {
        return SMOKE;
    }

    @Override
    public ICalculatedParticleSystem getCalculatedParticleSystem() {
        if (calculatedParticleSystem == null) {
            calculatedParticleSystem = getParticleSystem().createCalculatedParticleSystem();
        }
        return calculatedParticleSystem;
    }
}
