package mcjty.nice.blocks;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticleProvider;
import mcjty.nice.particle.IParticleSystem;
import mcjty.nice.particle.ParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.jspecify.annotations.Nullable;

public class GenericParticleTileEntity extends BlockEntity implements IParticleProvider {

    private ParticleType type = ParticleType.SMOKE;
    private boolean visible = true;

    public GenericParticleTileEntity(BlockPos pos, BlockState state) {
        super(mcjty.nice.setup.Registration.TYPE_PARTICLE.get(), pos, state);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        type = ParticleType.getByName(input.getStringOr("type", "smoke"));
        visible = input.getBooleanOr("visible", true);
        calculatedParticleSystem = null;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putString("type", type.getName());
        output.putBoolean("visible", visible);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void markDirtyClient() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
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
            level.setBlock(worldPosition, newblock.withPropertiesOf(getBlockState()), Block.UPDATE_ALL);
            // Recoloring replaces the block entity, so preserve its particle settings.
            if (level.getBlockEntity(worldPosition) instanceof GenericParticleTileEntity recolored) {
                recolored.type = type;
                recolored.visible = visible;
                recolored.calculatedParticleSystem = null;
                recolored.markDirtyClient();
            }
        }
    }

    private ICalculatedParticleSystem calculatedParticleSystem;

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
