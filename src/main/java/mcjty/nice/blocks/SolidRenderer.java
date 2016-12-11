package mcjty.nice.blocks;

import mcjty.nice.client.RenderTools;
import mcjty.nice.particle.IParticleProvider;
import mcjty.nice.particle.ParticleRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SolidRenderer extends TileEntitySpecialRenderer<SolidTileEntity> {

//    ResourceLocation blueSphereTexture = new ResourceLocation(Nice.MODID, "textures/effects/bluesphere.png");

    @Override
    public void renderTileEntityAt(SolidTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        IBlockState blockState = getWorld().getBlockState(te.getPos());
        if (blockState.getBlock() != ModBlocks.solidBlock) {
            return;
        }
        this.bindTexture(ParticleRenderer.particles);
        ParticleRenderer.renderSystem(te, (float) x, (float) y, (float) z);
    }
}
