package mcjty.nice.blocks;

import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SolidCylinderBlock extends CylinderBlock {

    public SolidCylinderBlock(String name, float scale) {
        super(name, scale);
    }

    @Override
    protected boolean supportsParticles() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }
}

