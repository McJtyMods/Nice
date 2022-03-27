package mcjty.nice.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import mcjty.nice.particle.ParticleRenderer;
import mcjty.nice.setup.Registration;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CylinderRenderer<T extends GenericParticleTileEntity> implements BlockEntityRenderer<T> {

    public CylinderRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(T blockEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BlockPos pos = blockEntity.getBlockPos();
        BlockState blockState = blockEntity.getLevel().getBlockState(pos);
        if (blockState.getBlock() instanceof SolidCylinderBlock) {
            return;
        }
        if (blockState.getBlock() instanceof SolidBlock) {
            return;
        }

        ParticleRenderer.renderSystem(blockEntity, pos);
    }


    public static void register() {
        BlockEntityRenderers.register(Registration.TYPE_PARTICLE.get(), CylinderRenderer::new);
    }
}
