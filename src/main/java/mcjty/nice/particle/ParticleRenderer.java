package mcjty.nice.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mcjty.nice.Nice;
import mcjty.nice.NiceConfig;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public final class ParticleRenderer {

    public static final Identifier PARTICLES = Identifier.fromNamespaceAndPath(Nice.MODID, "textures/block/effects/particles.png");

    // Freeze texture frames, lighting, and glow during extraction along with animation values.
    public record Particle(Vec3 offset, float scale, float u1, float u2, float v1, float v2, int color, int light, boolean glow) {}

    public static List<Particle> extract(IParticleProvider provider, Direction facing) {
        ICalculatedParticleSystem calculated = provider.getCalculatedParticleSystem();
        if (calculated == null) {
            return List.of();
        }
        long time = System.currentTimeMillis();
        provider.getParticleSystem().update(calculated, time);
        int x = facing == Direction.DOWN ? 180 : facing.getAxis().isHorizontal() ? 90 : 0;
        int y = facing.getAxis().isVertical() ? 0 : ((int) facing.toYRot() + 180) % 360;
        Quaternionf rotation = new Quaternionf().rotationY((float) Math.toRadians(-y)).rotateX((float) Math.toRadians(-x));
        int combinedLight = 0;
        if (provider instanceof BlockEntity entity && entity.getLevel() != null) {
            combinedLight = LightCoordsUtil.getLightCoords(entity.getLevel(), entity.getBlockPos());
        }
        List<Particle> result = new ArrayList<>();
        for (IParticle particle : calculated.getParticles()) {
            ParticleTextures.Definition definition = ParticleTextures.get(particle.getTextureType());
            if (particle.getTextureType() != null && definition == null) {
                continue;
            }
            ParticleTextures.Frame frame = definition == null ? null
                    : definition.frame(particle.getTextureSeed(), time - particle.getTextureStartTime());
            Vector3f offset = particle.getOffset().toVector3f().rotate(rotation);
            int r = (int) (particle.getR() * NiceConfig.BRIGHTNESS_R.get());
            int g = (int) (particle.getG() * NiceConfig.BRIGHTNESS_G.get());
            int b = (int) (particle.getB() * NiceConfig.BRIGHTNESS_B.get());
            int color = particle.getA() << 24 | r << 16 | g << 8 | b;
            int light = definition != null && definition.fullbright() ? LightCoordsUtil.FULL_BRIGHT : combinedLight;
            result.add(new Particle(new Vec3(offset), (float) particle.getScale(),
                    (float) (frame == null ? particle.getU1() : frame.u1()),
                    (float) (frame == null ? particle.getU2() : frame.u2()),
                    (float) (frame == null ? particle.getV1() : frame.v1()),
                    (float) (frame == null ? particle.getV2() : frame.v2()), color, light,
                    definition != null && definition.glow()));
        }
        return List.copyOf(result);
    }

    public static void submit(List<Particle> particles, PoseStack stack, SubmitNodeCollector collector, Quaternionf camera) {
        for (Particle p : particles) {
            stack.pushPose();
            stack.translate(0.5 + p.offset.x, 0.5 + p.offset.y, 0.5 + p.offset.z);
            stack.rotate(camera);
            collector.submitCustomGeometry(stack, ParticleRenderTypes.PARTICLES, (pose, buffer) -> {
                // Atlas V increases downward, while billboard Y increases upward.
                vertex(buffer, pose, -p.scale, -p.scale, p.u1, p.v2, p.color, p.light);
                vertex(buffer, pose, -p.scale, p.scale, p.u1, p.v1, p.color, p.light);
                vertex(buffer, pose, p.scale, p.scale, p.u2, p.v1, p.color, p.light);
                vertex(buffer, pose, p.scale, -p.scale, p.u2, p.v2, p.color, p.light);
            });
            if (p.glow) {
                int r = p.color >> 16 & 255;
                int g = p.color >> 8 & 255;
                int b = p.color & 255;
                int a = p.color >>> 24;
                collector.submitCustomGeometry(stack, ParticleRenderTypes.PARTICLE_GLOW,
                        (pose, buffer) -> ParticleGlowRenderer.render(buffer, pose, p.scale, r, g, b, a));
                collector.submitCustomGeometry(stack, ParticleRenderTypes.PARTICLE_EMISSION,
                        (pose, buffer) -> ParticleGlowRenderer.renderCore(buffer, pose, p.scale,
                                p.u1, p.v1, p.u2, p.v2, r, g, b, a));
            }
            stack.popPose();
        }
    }

    private static void vertex(VertexConsumer buffer, PoseStack.Pose pose, float x, float y, float u, float v, int color, int light) {
        buffer.addVertex(pose, x, y, 0).setColor(color).setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, -1);
    }
}
