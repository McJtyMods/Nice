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

    ResourceLocation particles = new ResourceLocation(Nice.MODID, "textures/effects/particles.png");

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
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
//        GlStateManager.scale(.1f, .1f, .1f);

//        this.bindTexture(blueSphereTexture);
        this.bindTexture(particles);

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
            double u1 = particle.getU1();
            double u2 = particle.getU2();
            double v1 = particle.getV1();
            double v2 = particle.getV2();
            int r = particle.getR();
            int g = particle.getG();
            int b = particle.getB();
            int a = particle.getA();
            double scale = particle.getScale();

            buffer.pos(ox - scale, oy-scale, oz).tex(u1, v1).lightmap(b1, b2).color(r, g, b, a).endVertex();
            buffer.pos(ox - scale, oy+scale, oz).tex(u1, v2).lightmap(b1, b2).color(r, g, b, a).endVertex();
            buffer.pos(ox + scale, oy+scale, oz).tex(u2, v2).lightmap(b1, b2).color(r, g, b, a).endVertex();
            buffer.pos(ox + scale, oy-scale, oz).tex(u2, v1).lightmap(b1, b2).color(r, g, b, a).endVertex();
        }
    }
}
