package mcjty.nice.blocks;

import mcjty.nice.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.item.DyeColor;

import java.util.function.Function;

public class SolidCylinderBlock extends CylinderBlock {

    public SolidCylinderBlock(float scale, Function<DyeColor, Block> siblingGetter) {
        super(scale, siblingGetter);
    }

    @Override
    protected boolean supportsParticles() {
        return false;
    }
}

