package mcjty.nice.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import mcjty.lib.client.RenderHelper;
import mcjty.nice.Nice;
import mcjty.nice.NiceConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class ParticleRenderer {

    public static ResourceLocation PARTICLES = new ResourceLocation(Nice.MODID, "effects/particles");

    public static void renderParticleSystem(IParticleProvider provider, VertexConsumer buffer, PoseStack matrixStack, TextureAtlasSprite sprite) {
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

        float u0 = sprite.getU0();
        float v0 = sprite.getV0();
        float uM = sprite.getU1();
        float vM = sprite.getV1();
        float du = uM - u0;
        float dv = vM - v0;

        for (IParticle particle : calculated.getParticles()) {
            double ox = particle.getOffset().x;
            double oy = particle.getOffset().y;
            double oz = particle.getOffset().z;
            double u1 = u0 + particle.getU1() * du;
            double u2 = u0 + particle.getU2() * du;
            double v1 = v0 + particle.getV1() * dv;
            double v2 = v0 + particle.getV2() * dv;
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

    public static void renderSystem(IParticleProvider provider, BlockPos pos) {
        DelayedParticleRenderer.addRender(RenderType.translucent(), pos, (stack, buf) -> {
            stack.translate(0.5F, 0.5F, 0.5F);
            RenderHelper.rotateToPlayer(stack);
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(PARTICLES);
            renderParticleSystem(provider, buf, stack, sprite);
        });
    }
}
