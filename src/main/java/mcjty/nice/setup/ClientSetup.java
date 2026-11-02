package mcjty.nice.setup;

import mcjty.nice.Nice;
import mcjty.nice.NiceConfig;
import mcjty.nice.blocks.CylinderRenderer;
import mcjty.nice.client.ObjModel;
import mcjty.nice.particle.ParticleRenderTypes;
import mcjty.nice.particle.ParticleTextures;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

public final class ClientSetup implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        NiceConfig.register();
        ObjModel.register();
        ParticleRenderTypes.register();
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(
                Identifier.fromNamespaceAndPath(Nice.MODID, "particle_textures"), new ParticleTextures());
        BlockEntityRenderers.register(Registration.TYPE_PARTICLE.get(), CylinderRenderer::new);
    }
}
