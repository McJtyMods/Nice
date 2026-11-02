package mcjty.nice.blocks;

import mcjty.nice.setup.Registration;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class ParticleBlock extends WaterloggedParticleBlock {

    public ParticleBlock(Properties properties) {
        super(properties, 0.8f, color -> Registration.PARTICLE_BLOCKS.get(color).get());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }
}
