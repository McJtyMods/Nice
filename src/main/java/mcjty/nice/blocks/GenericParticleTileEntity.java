package mcjty.nice.blocks;

import mcjty.lib.tileentity.GenericTileEntity;
import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticleProvider;
import mcjty.nice.particle.IParticleSystem;
import mcjty.nice.particle.ParticleType;
import mcjty.nice.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class GenericParticleTileEntity extends GenericTileEntity implements IParticleProvider {

    private ParticleType type = ParticleType.SMOKE;
    private boolean visible = true;

    public GenericParticleTileEntity() {
        super(Registration.TYPE_PARTICLE.get());
    }

    @Override
    public void loadClientDataFromNBT(CompoundNBT tag) {
        if (tag.contains("type")) {
            this.type = ParticleType.getByName(tag.getString("type"));
        }
        this.visible = tag.getBoolean("visible");
    }

    @Override
    public void saveClientDataToNBT(CompoundNBT tag) {
        tag.putString("type", type.getName());
        tag.putBoolean("visible", visible);
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        loadClientDataFromNBT(tag);
        super.load(state, tag);
    }

    @Override
    public void saveAdditional(CompoundNBT tag) {
        saveClientDataToNBT(tag);
        super.saveAdditional(tag);
    }

    public void setType(ParticleType type) {
        this.type = type;
        calculatedParticleSystem = null;
        markDirtyClient();
    }

    public boolean isVisible() {
        return visible;
    }

    public void toggleVisibility() {
        visible = !visible;
        markDirtyClient();
    }

    public void setColor(DyeColor color) {
        if (getBlockState().getBlock() instanceof GenericParticleBlock) {
            GenericParticleBlock block = (GenericParticleBlock) getBlockState().getBlock();
            Block newblock = block.recolor(color);
            level.setBlock(worldPosition, newblock.defaultBlockState(), Constants.BlockFlags.DEFAULT_AND_RERENDER);
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
