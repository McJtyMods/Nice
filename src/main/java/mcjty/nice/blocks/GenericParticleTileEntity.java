package mcjty.nice.blocks;

import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticleProvider;
import mcjty.nice.particle.IParticleSystem;
import mcjty.nice.particle.systems.BlinkSystem;
import mcjty.nice.particle.systems.BubbleSystem;
import mcjty.nice.particle.systems.FishSystem;
import mcjty.nice.particle.systems.SmokeSystem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class GenericParticleTileEntity extends GenericTileEntity implements IParticleProvider {

    private static final IParticleSystem SMOKE = new SmokeSystem();
    private static final IParticleSystem BLINK = new BlinkSystem();
    private static final IParticleSystem FISH = new FishSystem();
    private static final IParticleSystem BUBBLE = new BubbleSystem();

    private int type = 0;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        type = compound.getInteger("type");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("type", type);
        return super.writeToNBT(compound);
    }

    public void setType(int type) {
        this.type = type;
        calculatedParticleSystem = null;
        markDirtyClient();
    }

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
        switch(type) {
            case 0: return SMOKE;
            case 1: return BLINK;
            case 2: return FISH;
            case 3: return BUBBLE;
        }
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
