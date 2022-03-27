package mcjty.nice.blocks;

import mcjty.lib.tileentity.GenericTileEntity;
import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticleProvider;
import mcjty.nice.particle.IParticleSystem;
import mcjty.nice.particle.ParticleType;
import mcjty.nice.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.DyeColor;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

public class GenericParticleTileEntity extends GenericTileEntity implements IParticleProvider {

    private ParticleType type = ParticleType.SMOKE;

    public GenericParticleTileEntity(BlockPos pos, BlockState state) {
        super(Registration.TYPE_PARTICLE.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("type")) {
            this.type = ParticleType.getByName(tag.getString("type"));
        }
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putString("type", type.getName());
        super.saveAdditional(tag);
    }

    public void setType(ParticleType type) {
        this.type = type;
        calculatedParticleSystem = null;
        markDirtyClient();
    }

    public void setColor(DyeColor color) {
        if (getBlockState().getBlock() instanceof GenericParticleBlock) {
            GenericParticleBlock block = (GenericParticleBlock) getBlockState().getBlock();
            Block newblock = block.recolor(color);
            level.setBlock(worldPosition, newblock.defaultBlockState(), Block.UPDATE_ALL);
        }
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
