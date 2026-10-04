package mcjty.nice.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import mcjty.nice.NiceConfig;
import mcjty.nice.particle.ParticleRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CylinderRenderer implements BlockEntityRenderer<GenericParticleTileEntity, CylinderRenderer.State> {
    public static class State extends BlockEntityRenderState {
        public List<BlockStateModelPart> parts = List.of();
        public List<ParticleRenderer.Particle> particles = List.of();
    }

    public CylinderRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public int getViewDistance() {
        return Math.max(64, (int) Math.ceil(NiceConfig.MAX_RENDER_DIST.get()));
    }

    @Override
    public boolean shouldRenderOffScreen() {
        // Particles can extend beyond the block's bounds at the edge of the screen.
        return true;
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(GenericParticleTileEntity entity, State state, float partialTicks,
                                   Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(entity, state, partialTicks, cameraPosition, breakProgress);
        state.parts = List.of();
        state.particles = List.of();
        BlockState block = entity.getBlockState();
        if (!(block.getBlock() instanceof GenericParticleBlock generic) || !generic.supportsParticles()) return;
        if (entity.isVisible()) {
            List<BlockStateModelPart> parts = new ArrayList<>();
            Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(block)
                    .collectParts(RandomSource.create(block.getSeed(entity.getBlockPos())), parts);
            state.parts = List.copyOf(parts);
        }
        if (Vec3.atCenterOf(entity.getBlockPos()).closerThan(cameraPosition, NiceConfig.MAX_RENDER_DIST.get())) {
            Direction facing = block.getBlock() instanceof CylinderBlock ? block.getValue(BlockStateProperties.FACING) : Direction.UP;
            state.particles = ParticleRenderer.extract(entity, facing);
        }
    }

    @Override
    public void submit(State state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState camera) {
        if (!state.parts.isEmpty()) {
            collector.submitBlockModel(stack, RenderTypes.translucentMovingBlock(), state.parts, new int[0],
                    state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        }
        ParticleRenderer.submit(state.particles, stack, collector, camera.orientation);
    }
}
