package mcjty.nice.blocks;

import mcjty.lib.blocks.RotationType;
import mcjty.nice.setup.Registration;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.AxisAlignedBB;

public class ParticleBlock extends GenericParticleBlock {

    public ParticleBlock() {
        super(0.8f, true, color -> Registration.PARTICLE_BLOCKS.get(color).get());
    }

    @Override
    public RotationType getRotationType() {
        return RotationType.NONE;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState pState) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
