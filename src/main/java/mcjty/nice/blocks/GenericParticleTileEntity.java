package mcjty.nice.blocks;

import mcjty.nice.NiceConfig;
import mcjty.nice.client.BlockColor;
import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticleProvider;
import mcjty.nice.particle.IParticleSystem;
import mcjty.nice.particle.ParticleType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GenericParticleTileEntity extends GenericTileEntity implements IParticleProvider {

    private BlockColor color = BlockColor.BLUE;

    private ParticleType type = ParticleType.SMOKE;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.type = ParticleType.values()[compound.getInteger("type")];
        this.color = BlockColor.values()[compound.getInteger("color")];
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("type", type.ordinal());
        compound.setInteger("color", color.ordinal());
        return super.writeToNBT(compound);
    }

    public void setType(ParticleType type) {
        this.type = type;
        calculatedParticleSystem = null;
        markDirtyClient();
    }

    public BlockColor getColor() {
        return color;
    }

    public void setColor(BlockColor color) {
        this.color = color;
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
        return type.getParticleSystem();
    }

    @Nullable
    @Override
    public ICalculatedParticleSystem getCalculatedParticleSystem() {
        if (calculatedParticleSystem == null) {
            IBlockState state = world.getBlockState(pos);
            if (!(state.getBlock() instanceof GenericParticleBlock)) {
                return null;
            }
            calculatedParticleSystem = getParticleSystem().createCalculatedParticleSystem(((GenericParticleBlock) state.getBlock()).getScale());
        }
        return calculatedParticleSystem;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public double getMaxRenderDistanceSquared() {
        return NiceConfig.maxRenderDist * NiceConfig.maxRenderDist;
    }
}
