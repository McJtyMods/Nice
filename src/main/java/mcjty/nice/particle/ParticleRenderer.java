package mcjty.nice.particle;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcjty.nice.Nice;
import mcjty.nice.NiceConfig;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class ParticleRenderer {

    public static ResourceLocation particles = new ResourceLocation(Nice.MODID, "textures/effects/particles.png");

    public static void renderParticleSystem(IParticleProvider provider, BufferBuilder buffer, MatrixStack matrixStack) {
        long time = System.currentTimeMillis();

        int brightness = 240;
        int b1 = brightness >> 16 & 65535;
        int b2 = brightness & 65535;
        IParticleSystem system = provider.getParticleSystem();
        ICalculatedParticleSystem calculated = provider.getCalculatedParticleSystem();
        if (calculated == null) {
            return;
        }
        system.update(calculated, time);
        for (IParticle particle : calculated.getParticles()) {
            double ox = particle.getOffset().x;
            double oy = particle.getOffset().y;
            double oz = particle.getOffset().z;
            double u1 = particle.getU1();
            double u2 = particle.getU2();
            double v1 = particle.getV1();
            double v2 = particle.getV2();
            int r = (int) (particle.getR() * NiceConfig.particleBrightnessR);
            int g = (int) (particle.getG() * NiceConfig.particleBrightnessG);
            int b = (int) (particle.getB() * NiceConfig.particleBrightnessB);
            int a = particle.getA();
            double scale = particle.getScale();

            Matrix4f pose = matrixStack.last().pose();
            buffer.vertex(pose, (float)(ox - scale), (float)(oy-scale), (float)oz).uv((float)u1, (float)v1).uv2(b1, b2).color(r, g, b, a).endVertex();
            buffer.vertex(pose, (float)(ox - scale), (float)(oy+scale), (float)oz).uv((float)u1, (float)v2).uv2(b1, b2).color(r, g, b, a).endVertex();
            buffer.vertex(pose, (float)(ox + scale), (float)(oy+scale), (float)oz).uv((float)u2, (float)v2).uv2(b1, b2).color(r, g, b, a).endVertex();
            buffer.vertex(pose, (float)(ox + scale), (float)(oy-scale), (float)oz).uv((float)u2, (float)v1).uv2(b1, b2).color(r, g, b, a).endVertex();
        }
    }

    public static void renderSystem(IParticleProvider provider, float x, float y, float z) {
        // @todo
//        GlStateManager.depthMask(false);
//        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
//
//        GlStateManager.disableAlpha();
//
//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);
//
////        this.bindTexture(blueSphereTexture);
//
//        RenderHelper.rotateToPlayer();
//
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.getBuffer();
//        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
//
//        renderParticleSystem(provider, buffer);
//
//        tessellator.draw();
//        GlStateManager.popMatrix();
//
////        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
}
