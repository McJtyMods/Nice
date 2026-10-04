package mcjty.nice.setup;

import mcjty.nice.NiceConfig;
import mcjty.nice.blocks.CylinderRenderer;
import mcjty.nice.blocks.NiceBlockItem;
import mcjty.nice.client.ObjModel;
import mcjty.nice.particle.ParticleRenderTypes;
import net.minecraft.client.Minecraft;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public final class ClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NiceConfig.register();
        NiceBlockItem.shiftDown = () -> Minecraft.getInstance().hasShiftDown();
        ObjModel.register();
        ParticleRenderTypes.register();
        BlockEntityRenderers.register(Registration.TYPE_PARTICLE.get(), CylinderRenderer::new);
    }
}
