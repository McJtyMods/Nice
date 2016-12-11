package mcjty.nice.blocks;

import mcjty.nice.particle.ParticleRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CylinderRenderer extends TileEntitySpecialRenderer<CylinderTileEntity> {

    @Override
    public void renderTileEntityAt(CylinderTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        IBlockState blockState = getWorld().getBlockState(te.getPos());
        if (blockState.getBlock() != ModBlocks.cylinderBlock) {
            return;
        }
        this.bindTexture(ParticleRenderer.particles);
        ParticleRenderer.renderSystem(te, (float) x, (float) y, (float) z);
    }

}
