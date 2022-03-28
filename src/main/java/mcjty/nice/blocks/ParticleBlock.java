package mcjty.nice.blocks;

import mcjty.lib.blocks.RotationType;
import mcjty.nice.setup.Registration;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class ParticleBlock extends GenericParticleBlock {

    public ParticleBlock() {
        super(0.8f, true, color -> Registration.PARTICLE_BLOCKS.get(color).get());
    }

    @Override
    public RotationType getRotationType() {
        return RotationType.NONE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
