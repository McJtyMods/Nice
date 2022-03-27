package mcjty.nice.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class DelayedParticleRenderer {

    // Renderers per render type
    private static final Map<RenderType, List<Pair<BlockPos, BiConsumer<PoseStack, VertexConsumer>>>> renders = new HashMap<>();

    public static void render(PoseStack matrixStack) {
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        Vec3 projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        renders.forEach((type, renderlist) -> {
            VertexConsumer consumer = buffer.getBuffer(type);
            renderlist.forEach(r -> {
                RenderSystem.disableDepthTest();    // @todo this differs from the McJtyLib example!
                BlockPos pos = r.getKey();
                matrixStack.pushPose();
                matrixStack.translate(pos.getX() - projectedView.x, pos.getY() - projectedView.y, pos.getZ() - projectedView.z);
                r.getValue().accept(matrixStack, consumer);
                matrixStack.popPose();
            });
            RenderSystem.disableDepthTest();    // @todo this differs from the McJtyLib example!
            buffer.endBatch(type);
        });
        renders.clear();

        buffer.endBatch();
    }

    public static void addRender(RenderType type, BlockPos pos, BiConsumer<PoseStack, VertexConsumer> renderer) {
        renders.computeIfAbsent(type, renderType -> new ArrayList<>()).add(Pair.of(pos, renderer));
    }
}
