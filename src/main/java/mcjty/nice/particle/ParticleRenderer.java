package mcjty.nice.particle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mcjty.lib.client.RenderHelper;
import mcjty.nice.Nice;
import mcjty.nice.NiceConfig;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class ParticleRenderer {

    public static ResourceLocation particles = new ResourceLocation(Nice.MODID, "textures/effects/particles.png");

    public static void renderParticleSystem(IParticleProvider provider, IVertexBuilder buffer, MatrixStack matrixStack) {
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
            RenderHelper.vt(buffer, pose, (float)(ox - scale), (float)(oy-scale), (float)oz, (float)u1, (float)v1, b1, b2,r, g, b, a);
            RenderHelper.vt(buffer, pose, (float)(ox - scale), (float)(oy+scale), (float)oz, (float)u1, (float)v2, b1, b2,r, g, b, a);
            RenderHelper.vt(buffer, pose, (float)(ox + scale), (float)(oy+scale), (float)oz, (float)u2, (float)v2, b1, b2,r, g, b, a);
            RenderHelper.vt(buffer, pose, (float)(ox + scale), (float)(oy-scale), (float)oz, (float)u2, (float)v1, b1, b2,r, g, b, a);
        }
    }

    public static void renderSystem(MatrixStack matrixStack, IRenderTypeBuffer buffer, IParticleProvider provider, float x, float y, float z) {
//        GlStateManager.depthMask(false);
//        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
//
//        GlStateManager.disableAlpha();
//
//        this.bindTexture(blueSphereTexture);

        matrixStack.pushPose();
//        matrixStack.translate(x + 0.5F, y + 0.5F, z + 0.5F);
        RenderHelper.rotateToPlayer(matrixStack);

        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.translucent());

        renderParticleSystem(provider, vertexBuilder, matrixStack);

        matrixStack.popPose();

//        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
}
