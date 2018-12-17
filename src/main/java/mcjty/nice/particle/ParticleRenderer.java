package mcjty.nice.particle;

import mcjty.lib.client.RenderHelper;
import mcjty.nice.Config;
import mcjty.nice.Nice;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ParticleRenderer {

    public static ResourceLocation particles = new ResourceLocation(Nice.MODID, "textures/effects/particles.png");

    public static void renderParticleSystem(IParticleProvider provider, BufferBuilder buffer) {
        long time = System.currentTimeMillis();

        int brightness = 240;
        int b1 = brightness >> 16 & 65535;
        int b2 = brightness & 65535;
        IParticleSystem system = provider.getParticleSystem();
        ICalculatedParticleSystem calculated = provider.getCalculatedParticleSystem();
        system.update(calculated, time);
        for (IParticle particle : calculated.getParticles()) {
            double ox = particle.getOffset().x;
            double oy = particle.getOffset().y;
            double oz = particle.getOffset().z;
            double u1 = particle.getU1();
            double u2 = particle.getU2();
            double v1 = particle.getV1();
            double v2 = particle.getV2();
            int r = (int) (particle.getR() * Config.particleBrightnessR);
            int g = (int) (particle.getG() * Config.particleBrightnessG);
            int b = (int) (particle.getB() * Config.particleBrightnessB);
            int a = particle.getA();
            double scale = particle.getScale();

            buffer.pos(ox - scale, oy-scale, oz).tex(u1, v1).lightmap(b1, b2).color(r, g, b, a).endVertex();
            buffer.pos(ox - scale, oy+scale, oz).tex(u1, v2).lightmap(b1, b2).color(r, g, b, a).endVertex();
            buffer.pos(ox + scale, oy+scale, oz).tex(u2, v2).lightmap(b1, b2).color(r, g, b, a).endVertex();
            buffer.pos(ox + scale, oy-scale, oz).tex(u2, v1).lightmap(b1, b2).color(r, g, b, a).endVertex();
        }
    }

    public static void renderSystem(IParticleProvider provider, float x, float y, float z) {
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

//        GlStateManager.enableAlpha();
        GlStateManager.disableAlpha();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);
//        GlStateManager.scale(.1f, .1f, .1f);

//        this.bindTexture(blueSphereTexture);

        RenderHelper.rotateToPlayer();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);

        renderParticleSystem(provider, buffer);

        tessellator.draw();
        GlStateManager.popMatrix();

//        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
}
