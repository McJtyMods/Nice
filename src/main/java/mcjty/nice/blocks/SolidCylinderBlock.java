package mcjty.nice.blocks;

public class SolidCylinderBlock extends CylinderBlock {

    public SolidCylinderBlock(float scale) {
        super(scale);
    }

    @Override
    protected boolean supportsParticles() {
        return false;
    }
}

