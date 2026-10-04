package mcjty.nice.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mcjty.nice.Nice;
import mcjty.nice.NiceConfig;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import java.util.List;

public final class ParticleRenderer {
    public static final Identifier PARTICLES = Identifier.fromNamespaceAndPath(Nice.MODID, "textures/block/effects/particles.png");

    // Copy animation values during extraction. Submission must not retain mutable particles or block entities.
    public record Particle(Vec3 offset, float scale, float u1, float u2, float v1, float v2, int color) {}

    public static List<Particle> extract(IParticleProvider provider, Direction facing) {
        ICalculatedParticleSystem calculated = provider.getCalculatedParticleSystem();
        if (calculated == null) return List.of();
        provider.getParticleSystem().update(calculated, System.currentTimeMillis());
        int x = facing == Direction.DOWN ? 180 : facing.getAxis().isHorizontal() ? 90 : 0;
        int y = facing.getAxis().isVertical() ? 0 : ((int) facing.toYRot() + 180) % 360;
        Quaternionf rotation = new Quaternionf().rotationY((float) Math.toRadians(-y)).rotateX((float) Math.toRadians(-x));
        return calculated.getParticles().stream().map(p -> {
            Vector3f offset = p.getOffset().toVector3f().rotate(rotation);
            int r = (int) (p.getR() * NiceConfig.BRIGHTNESS_R.get());
            int g = (int) (p.getG() * NiceConfig.BRIGHTNESS_G.get());
            int b = (int) (p.getB() * NiceConfig.BRIGHTNESS_B.get());
            int color = p.getA() << 24 | r << 16 | g << 8 | b;
            return new Particle(new Vec3(offset), (float) p.getScale(), (float) p.getU1(), (float) p.getU2(),
                    (float) p.getV1(), (float) p.getV2(), color);
        }).toList();
    }

    public static void submit(List<Particle> particles, PoseStack stack, SubmitNodeCollector collector, Quaternionf camera) {
        for (Particle p : particles) {
            stack.pushPose();
            stack.translate(0.5 + p.offset.x, 0.5 + p.offset.y, 0.5 + p.offset.z);
            stack.mulPose(camera);
            collector.submitCustomGeometry(stack, ParticleRenderTypes.PARTICLES, (pose, buffer) -> {
                vertex(buffer, pose, -p.scale, -p.scale, p.u1, p.v1, p.color);
                vertex(buffer, pose, -p.scale, p.scale, p.u1, p.v2, p.color);
                vertex(buffer, pose, p.scale, p.scale, p.u2, p.v2, p.color);
                vertex(buffer, pose, p.scale, -p.scale, p.u2, p.v1, p.color);
            });
            stack.popPose();
        }
    }

    private static void vertex(VertexConsumer buffer, PoseStack.Pose pose, float x, float y, float u, float v, int color) {
        buffer.addVertex(pose, x, y, 0).setColor(color).setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(pose, 0, 0, 1);
    }
}
