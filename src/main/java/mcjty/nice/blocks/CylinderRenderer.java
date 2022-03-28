package mcjty.nice.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcjty.nice.particle.ParticleRenderer;
import mcjty.nice.setup.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class CylinderRenderer<T extends GenericParticleTileEntity> extends TileEntityRenderer<T> {

    public CylinderRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(T blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        BlockPos pos = blockEntity.getBlockPos();
        BlockState blockState = blockEntity.getLevel().getBlockState(pos);
        if (blockState.getBlock() instanceof SolidCylinderBlock) {
            return;
        }
        if (blockState.getBlock() instanceof SolidBlock) {
            return;
        }

        if (blockEntity.isVisible()) {
            ParticleRenderer.renderBlock(matrixStack, buffer, blockState, combinedLight, combinedOverlay);
        }
        ParticleRenderer.renderSystem(matrixStack, buffer, blockEntity);
    }


    public static void register() {
        ClientRegistry.bindTileEntityRenderer(Registration.TYPE_PARTICLE.get(), CylinderRenderer::new);
    }
}
