package mcjty.nice.blocks;

import mcjty.lib.blocks.RotationType;
import mcjty.nice.setup.Registration;
import net.minecraft.world.phys.AABB;

public class ParticleBlock extends GenericParticleBlock {

    public static final AABB EMPTY = new AABB(0, 0, 0, 0, 0, 0);

    public ParticleBlock() {
        super(0.8f, true, color -> Registration.PARTICLE_BLOCKS.get(color).get());
    }

    @Override
    public RotationType getRotationType() {
        return RotationType.NONE;
    }
}
