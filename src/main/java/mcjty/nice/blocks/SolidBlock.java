package mcjty.nice.blocks;

import mcjty.nice.setup.Registration;

public class SolidBlock extends GenericParticleBlock {

    public SolidBlock(Properties properties) {
        super(properties, 0.8f, false, color -> Registration.SOLID_BLOCKS.get(color).get());
    }

    @Override
    protected boolean supportsParticles() {
        return false;
    }
}
