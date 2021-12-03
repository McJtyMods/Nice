package mcjty.nice.blocks;

import mcjty.lib.tileentity.GenericTileEntity;
import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticleProvider;
import mcjty.nice.particle.IParticleSystem;
import mcjty.nice.particle.ParticleType;
import mcjty.nice.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class GenericParticleTileEntity extends GenericTileEntity implements IParticleProvider {

    private ParticleType type = ParticleType.SMOKE;

    public GenericParticleTileEntity() {
        super(Registration.TYPE_PARTICLE.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        if (tag.contains("type")) {
            this.type = ParticleType.getByName(tag.getString("type"));
        }
        super.load(state, tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.putString("type", type.getName());
        return super.save(tag);
    }

    public void setType(ParticleType type) {
        this.type = type;
        calculatedParticleSystem = null;
        markDirtyClient();
    }

    public void setColor(DyeColor color) {
//        level.setBlock(worldPosition, getBlockState().setValue(GenericParticleBlock.COLOR, color), Constants.BlockFlags.DEFAULT_AND_RERENDER);
        // @todo todo todo
    }

    private ICalculatedParticleSystem calculatedParticleSystem;

//    @SideOnly(Side.CLIENT)
//    public AxisAlignedBB getRenderBoundingBox() {
//        int xCoord = getPos().getX();
//        int yCoord = getPos().getY();
//        int zCoord = getPos().getZ();
//        return new AxisAlignedBB(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 3, zCoord + 2);
//    }

    //    @SideOnly(Side.CLIENT)
//    @Override
//    public double getMaxRenderDistanceSquared() {
//        return NiceConfig.maxRenderDist * NiceConfig.maxRenderDist;
//    }

    @Override
    public IParticleSystem getParticleSystem() {
        return type.getParticleSystem();
    }

    @Nullable
    @Override
    public ICalculatedParticleSystem getCalculatedParticleSystem() {
        if (calculatedParticleSystem == null) {
            BlockState state = getBlockState();
            if (!(state.getBlock() instanceof GenericParticleBlock)) {
                return null;
            }
            calculatedParticleSystem = getParticleSystem().createCalculatedParticleSystem(((GenericParticleBlock) state.getBlock()).getScale());
        }
        return calculatedParticleSystem;
    }
}
