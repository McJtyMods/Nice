package mcjty.nice.setup;

import mcjty.nice.Nice;
import mcjty.nice.blocks.CylinderRenderer;
import mcjty.nice.particle.ParticleTextures;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import mcjty.nice.particle.ParticleRenderTypes;
import mcjty.nice.datagen.DataGenerators;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

public final class ClientSetup {

    public static void register(IEventBus bus) {
        bus.addListener(ClientSetup::registerRenderers);
        bus.addListener(ClientSetup::registerPipelines);
        bus.addListener(ClientSetup::registerReloadListeners);
        bus.addListener(DataGenerators::gatherClient);
    }

    private static void registerReloadListeners(AddClientReloadListenersEvent event) {
        event.addListener(Identifier.fromNamespaceAndPath(Nice.MODID, "particle_textures"), new ParticleTextures());
    }

    private static void registerPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(ParticleRenderTypes.PARTICLE_PIPELINE);
        event.registerPipeline(ParticleRenderTypes.GLOW_PIPELINE);
        event.registerPipeline(ParticleRenderTypes.EMISSION_PIPELINE);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(Registration.TYPE_PARTICLE.get(), CylinderRenderer::new);
    }
}
