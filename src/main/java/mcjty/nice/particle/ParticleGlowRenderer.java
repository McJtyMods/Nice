package mcjty.nice.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class ParticleGlowRenderer {

    private static final int SEGMENTS = 24;
    private static final int RINGS = 6;
    private static final float RADIUS = 2.0f;
    private static final float INTENSITY = .6f;
    private static final float[] X = new float[SEGMENTS + 1];
    private static final float[] Y = new float[SEGMENTS + 1];
    private static final float[] FALLOFF = new float[RINGS + 1];

    static {
        for (int i = 0; i <= SEGMENTS; i++) {
            double angle = i * Math.PI * 2 / SEGMENTS;
            X[i] = (float) Math.cos(angle);
            Y[i] = (float) Math.sin(angle);
        }
        for (int i = 0; i <= RINGS; i++) {
            float radius = (float) i / RINGS;
            FALLOFF[i] = (float) Math.pow(1 - radius * radius, 3);
        }
    }

    public static void render(VertexConsumer buffer, PoseStack.Pose pose, float scale, int r, int g, int b, int a) {
        if (scale <= 0 || a <= 0) {
            return;
        }
        float radius = scale * RADIUS;
        // Additive blending adds RGB even when alpha is zero. Bake opacity and
        // radial falloff into RGB so the edge and disappearing particles vanish.
        float brightness = INTENSITY * Math.min(a, 255) / 255f;
        r = Math.max(0, Math.min(r, 255));
        g = Math.max(0, Math.min(g, 255));
        b = Math.max(0, Math.min(b, 255));
        for (int ring = 0; ring < RINGS; ring++) {
            float inner = radius * ring / RINGS;
            float outer = radius * (ring + 1) / RINGS;
            float innerBrightness = brightness * FALLOFF[ring];
            float outerBrightness = brightness * FALLOFF[ring + 1];
            for (int segment = 0; segment < SEGMENTS; segment++) {
                vertex(buffer, pose, inner * X[segment], inner * Y[segment], r, g, b, innerBrightness);
                vertex(buffer, pose, inner * X[segment + 1], inner * Y[segment + 1], r, g, b, innerBrightness);
                vertex(buffer, pose, outer * X[segment + 1], outer * Y[segment + 1], r, g, b, outerBrightness);
                vertex(buffer, pose, outer * X[segment], outer * Y[segment], r, g, b, outerBrightness);
            }
        }
    }

    public static void renderCore(VertexConsumer buffer, PoseStack.Pose pose, float scale,
                                  float u1, float v1, float u2, float v2, int r, int g, int b, int a) {
        if (scale <= 0 || a <= 0) {
            return;
        }
        r = Math.max(0, Math.min(r, 255));
        g = Math.max(0, Math.min(g, 255));
        b = Math.max(0, Math.min(b, 255));
        a = Math.min(a, 255);
        // Match the upright texture orientation of the normal particle pass.
        coreVertex(buffer, pose, -scale, -scale, u1, v2, r, g, b, a);
        coreVertex(buffer, pose, -scale, scale, u1, v1, r, g, b, a);
        coreVertex(buffer, pose, scale, scale, u2, v1, r, g, b, a);
        coreVertex(buffer, pose, scale, -scale, u2, v2, r, g, b, a);
    }

    private static void coreVertex(VertexConsumer buffer, PoseStack.Pose pose, float x, float y, float u, float v, int r, int g, int b, int a) {
        buffer.vertex(pose.pose(), x, y, 0)
                .color(r, g, b, a)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(pose.normal(), 0, 0, -1)
                .endVertex();
    }

    private static void vertex(VertexConsumer buffer, PoseStack.Pose pose, float x, float y, int r, int g, int b, float brightness) {
        buffer.vertex(pose.pose(), x, y, 0)
                .color((int) (r * brightness), (int) (g * brightness), (int) (b * brightness), 255)
                .uv(.5f, .5f)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(pose.normal(), 0, 0, -1)
                .endVertex();
    }
}
