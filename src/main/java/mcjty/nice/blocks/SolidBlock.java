package mcjty.nice.blocks;

import mcjty.lib.blocks.RotationType;
import mcjty.nice.setup.Registration;
import net.minecraft.world.phys.AABB;

public class SolidBlock extends GenericParticleBlock {

    public static final AABB EMPTY = new AABB(0, 0, 0, 0, 0, 0);

    public SolidBlock() {
        super(0.8f, false, color -> Registration.SOLID_BLOCKS.get(color).get());
    }

    @Override
    public RotationType getRotationType() {
        return RotationType.NONE;
    }

    @Override
    protected boolean supportsParticles() {
        return false;
    }
}
