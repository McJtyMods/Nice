package mcjty.nice.blocks;

import mcjty.nice.Nice;
import mcjty.nice.client.RenderTools;
import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticle;
import mcjty.nice.particle.IParticleProvider;
import mcjty.nice.particle.IParticleSystem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class CylinderRenderer extends TileEntitySpecialRenderer<CylinderTileEntity> {

    ResourceLocation blueSphereTexture = new ResourceLocation(Nice.MODID, "textures/effects/bluesphere.png");
    ResourceLocation gasTexture = new ResourceLocation(Nice.MODID, "textures/effects/gas.png");

    @Override
    public void renderTileEntityAt(CylinderTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        IBlockState blockState = getWorld().getBlockState(te.getPos());
        if (blockState.getBlock() != ModBlocks.cylinderBlock) {
            return;
        }
        IParticleProvider provider = te;

        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

//        GlStateManager.enableAlpha();
        GlStateManager.disableAlpha();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0F, (float) z + 0.5F);
//        GlStateManager.scale(.1f, .1f, .1f);

//        this.bindTexture(blueSphereTexture);
        this.bindTexture(gasTexture);

        RenderTools.rotateToPlayer();

        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);

        renderParticleSystem(provider, buffer);

        tessellator.draw();
        GlStateManager.popMatrix();

//        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void renderParticleSystem(IParticleProvider provider, VertexBuffer buffer) {
        long time = System.currentTimeMillis();

        int brightness = 240;
        int b1 = brightness >> 16 & 65535;
        int b2 = brightness & 65535;
        IParticleSystem system = provider.getParticleSystem();
        ICalculatedParticleSystem calculated = provider.getCalculatedParticleSystem();
        system.update(calculated, time);
        for (IParticle particle : calculated.getParticles()) {
            double ox = particle.getOffset().xCoord;
            double oy = particle.getOffset().yCoord;
            double oz = particle.getOffset().zCoord;
            double scale = particle.getScale();
            buffer.pos(ox - scale, oy-scale, oz).tex(0.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
            buffer.pos(ox - scale, oy+scale, oz).tex(0.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
            buffer.pos(ox + scale, oy+scale, oz).tex(1.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
            buffer.pos(ox + scale, oy-scale, oz).tex(1.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
        }
    }
}
