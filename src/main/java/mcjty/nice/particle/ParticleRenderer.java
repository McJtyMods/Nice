package mcjty.nice.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import mcjty.lib.client.RenderHelper;
import mcjty.nice.Nice;
import mcjty.nice.NiceConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

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
            // @todo 1.19.3
            RenderHelper.vt(buffer, pose, (float)(ox - scale), (float)(oy-scale), (float)oz, (float)u1, (float)v1, b1, b2,r, g, b, a);
            RenderHelper.vt(buffer, pose, (float)(ox - scale), (float)(oy+scale), (float)oz, (float)u1, (float)v2, b1, b2,r, g, b, a);
            RenderHelper.vt(buffer, pose, (float)(ox + scale), (float)(oy+scale), (float)oz, (float)u2, (float)v2, b1, b2,r, g, b, a);
            RenderHelper.vt(buffer, pose, (float)(ox + scale), (float)(oy-scale), (float)oz, (float)u2, (float)v1, b1, b2,r, g, b, a);
        }
    }

    public static void renderSystem(PoseStack stack, MultiBufferSource buf, IParticleProvider provider) {
        VertexConsumer buffer = buf.getBuffer(ParticleRenderTypes.TRANSLUCENT_PARTICLES);
        stack.translate(0.5F, 0.5F, 0.5F);
        RenderHelper.rotateToPlayer(stack);
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(PARTICLES);
        renderParticleSystem(provider, buffer, stack, sprite);
    }

    public static void renderBlock(PoseStack stack, MultiBufferSource buf, BlockState state, int combinedLight, int combinedOverlay, RenderType renderType) {
        BlockRenderDispatcher renderer = Minecraft.getInstance().getBlockRenderer();
        BakedModel model = renderer.getBlockModel(state);
        int color = Minecraft.getInstance().getBlockColors().getColor(state, null, null, 0);
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        VertexConsumer buffer = buf.getBuffer(ParticleRenderTypes.TRANSLUCENT_PARTICLES);
        renderer.getModelRenderer().renderModel(stack.last(), buffer, state, model, r, g, b, combinedLight, combinedOverlay, ModelData.EMPTY, renderType);
    }

}
