package mcjty.nice.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mcjty.lib.client.RenderHelper;
import mcjty.nice.Nice;
import mcjty.nice.NiceConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.model.data.ModelData;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ParticleRenderer {

    public static ResourceLocation PARTICLES = new ResourceLocation(Nice.MODID, "block/effects/particles");

    public static void renderParticleSystem(IParticleProvider provider, VertexConsumer buffer, PoseStack matrixStack, TextureAtlasSprite sprite) {
        renderParticleSystem(provider, buffer, matrixStack, sprite, new Quaternionf(), System.currentTimeMillis());
    }

    private static void renderParticleSystem(IParticleProvider provider, VertexConsumer buffer, PoseStack matrixStack, TextureAtlasSprite sprite, Quaternionf rotation, long time) {
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
        int combinedLight = 0;
        if (provider instanceof BlockEntity blockEntity && blockEntity.getLevel() != null) {
            combinedLight = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos());
        }

        for (IParticle particle : calculated.getParticles()) {
            ParticleTextures.Definition definition = ParticleTextures.get(particle.getTextureType());
            if (particle.getTextureType() != null && definition == null) {
                continue;
            }
            ParticleTextures.Frame frame = definition == null ? null
                    : definition.frame(particle.getTextureSeed(), time - particle.getTextureStartTime());
            Vector3f offset = new Vector3f((float) particle.getOffset().x, (float) particle.getOffset().y, (float) particle.getOffset().z);
            offset.rotate(rotation);
            double u1 = u0 + (frame == null ? particle.getU1() : frame.u1()) * du;
            double u2 = u0 + (frame == null ? particle.getU2() : frame.u2()) * du;
            double v1 = v0 + (frame == null ? particle.getV1() : frame.v1()) * dv;
            double v2 = v0 + (frame == null ? particle.getV2() : frame.v2()) * dv;
            int light = definition != null && definition.fullbright() ? LightTexture.FULL_BRIGHT : combinedLight;
            int r = (int) (particle.getR() * NiceConfig.particleBrightnessR);
            int g = (int) (particle.getG() * NiceConfig.particleBrightnessG);
            int b = (int) (particle.getB() * NiceConfig.particleBrightnessB);
            int a = particle.getA();
            double scale = particle.getScale();

            // Rotate the position with the cylinder, then billboard only the sprite.
            matrixStack.pushPose();
            matrixStack.translate(offset.x, offset.y, offset.z);
            RenderHelper.rotateToPlayer(matrixStack);
            // Atlas V increases downward; local billboard Y increases upward.
            renderParticleVertex(buffer, matrixStack.last(), (float)-scale, (float)-scale, (float)u1, (float)v2, r, g, b, a, light);
            renderParticleVertex(buffer, matrixStack.last(), (float)-scale, (float)scale, (float)u1, (float)v1, r, g, b, a, light);
            renderParticleVertex(buffer, matrixStack.last(), (float)scale, (float)scale, (float)u2, (float)v1, r, g, b, a, light);
            renderParticleVertex(buffer, matrixStack.last(), (float)scale, (float)-scale, (float)u2, (float)v2, r, g, b, a, light);
            matrixStack.popPose();
        }
    }

    private static void renderParticleVertex(VertexConsumer buffer, PoseStack.Pose pose, float x, float y, float u, float v, int r, int g, int b, int a, int light) {
        buffer.vertex(pose.pose(), x, y, 0)
                .color(r, g, b, a)
                .uv(u, v)
                .uv2(light)
                // The quad faces local -Z. Rotate its normal with the billboard too.
                .normal(pose.normal(), 0, 0, -1)
                .endVertex();
    }

    public static void renderSystem(PoseStack stack, MultiBufferSource buf, IParticleProvider provider) {
        renderSystem(stack, buf, provider, Direction.UP);
    }

    public static void renderSystem(PoseStack stack, MultiBufferSource buf, IParticleProvider provider, Direction facing) {
        // Match the rotations generated by directionalBlock for an upright model.
        int x = facing == Direction.DOWN ? 180 : facing.getAxis().isHorizontal() ? 90 : 0;
        int y = facing.getAxis().isVertical() ? 0 : ((int) facing.toYRot() + 180) % 360;
        Quaternionf rotation = BlockModelRotation.by(x, y).getRotation().getLeftRotation();
        VertexConsumer buffer = buf.getBuffer(ParticleRenderTypes.TRANSLUCENT_PARTICLES);
        stack.pushPose();
        stack.translate(0.5F, 0.5F, 0.5F);
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(PARTICLES);
        long time = System.currentTimeMillis();
        renderParticleSystem(provider, buffer, stack, sprite, rotation, time);
        renderParticleGlows(provider, buf, stack, rotation);
        renderParticleGlowCores(provider, buf, stack, sprite, rotation, time);
        stack.popPose();
    }

    private static void renderParticleGlowCores(IParticleProvider provider, MultiBufferSource source, PoseStack stack, TextureAtlasSprite sprite, Quaternionf rotation, long time) {
        ICalculatedParticleSystem calculated = provider.getCalculatedParticleSystem();
        if (calculated == null) {
            return;
        }
        VertexConsumer buffer = null;
        for (IParticle particle : calculated.getParticles()) {
            ParticleTextures.Definition definition = ParticleTextures.get(particle.getTextureType());
            if (definition == null || !definition.glow() || particle.getScale() <= 0 || particle.getA() <= 0) {
                continue;
            }
            if (buffer == null) {
                buffer = source.getBuffer(ParticleRenderTypes.PARTICLE_EMISSION);
            }
            ParticleTextures.Frame frame = definition.frame(particle.getTextureSeed(), time - particle.getTextureStartTime());
            float du = sprite.getU1() - sprite.getU0();
            float dv = sprite.getV1() - sprite.getV0();
            Vector3f offset = new Vector3f((float) particle.getOffset().x, (float) particle.getOffset().y, (float) particle.getOffset().z);
            offset.rotate(rotation);
            stack.pushPose();
            stack.translate(offset.x, offset.y, offset.z);
            RenderHelper.rotateToPlayer(stack);
            ParticleGlowRenderer.renderCore(buffer, stack.last(), (float) particle.getScale(),
                    sprite.getU0() + (float) frame.u1() * du, sprite.getV0() + (float) frame.v1() * dv,
                    sprite.getU0() + (float) frame.u2() * du, sprite.getV0() + (float) frame.v2() * dv,
                    (int) (particle.getR() * NiceConfig.particleBrightnessR),
                    (int) (particle.getG() * NiceConfig.particleBrightnessG),
                    (int) (particle.getB() * NiceConfig.particleBrightnessB), particle.getA());
            stack.popPose();
        }
    }

    private static void renderParticleGlows(IParticleProvider provider, MultiBufferSource source, PoseStack stack, Quaternionf rotation) {
        ICalculatedParticleSystem calculated = provider.getCalculatedParticleSystem();
        if (calculated == null) {
            return;
        }
        // Finish all sprite vertices before requesting another render type: the
        // buffer source can flush its shared builder when the render type changes.
        VertexConsumer buffer = null;
        for (IParticle particle : calculated.getParticles()) {
            ParticleTextures.Definition definition = ParticleTextures.get(particle.getTextureType());
            if (definition == null || !definition.glow() || particle.getScale() <= 0 || particle.getA() <= 0) {
                continue;
            }
            if (buffer == null) {
                buffer = source.getBuffer(ParticleRenderTypes.PARTICLE_GLOW);
            }
            Vector3f offset = new Vector3f((float) particle.getOffset().x, (float) particle.getOffset().y, (float) particle.getOffset().z);
            offset.rotate(rotation);
            stack.pushPose();
            stack.translate(offset.x, offset.y, offset.z);
            RenderHelper.rotateToPlayer(stack);
            ParticleGlowRenderer.render(buffer, stack.last(), (float) particle.getScale(),
                    (int) (particle.getR() * NiceConfig.particleBrightnessR),
                    (int) (particle.getG() * NiceConfig.particleBrightnessG),
                    (int) (particle.getB() * NiceConfig.particleBrightnessB), particle.getA());
            stack.popPose();
        }
    }

    public static void renderBlock(PoseStack stack, MultiBufferSource buf, BlockState state, int combinedLight, int combinedOverlay, RenderType renderType) {
        BlockRenderDispatcher renderer = Minecraft.getInstance().getBlockRenderer();
        BakedModel model = renderer.getBlockModel(state);
        int color = Minecraft.getInstance().getBlockColors().getColor(state, null, null, 0);
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        VertexConsumer buffer = buf.getBuffer(ParticleRenderTypes.TRANSLUCENT_PARTICLES);
        RenderHelper.renderModel(renderer, stack, buffer, state, model, r, g, b, combinedLight, combinedOverlay, ModelData.EMPTY, renderType);
    }

}
