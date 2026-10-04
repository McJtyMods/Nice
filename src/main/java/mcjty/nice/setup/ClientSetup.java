package mcjty.nice.setup;

import mcjty.nice.blocks.CylinderRenderer;
import mcjty.nice.particle.ParticleRenderTypes;
import mcjty.nice.datagen.DataGenerators;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

public final class ClientSetup {
    public static void register(IEventBus bus) {
        bus.addListener(ClientSetup::registerRenderers);
        bus.addListener(ClientSetup::registerPipelines);
        bus.addListener(DataGenerators::gatherClient);
    }

    private static void registerPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(ParticleRenderTypes.PARTICLE_PIPELINE);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(Registration.TYPE_PARTICLE.get(), CylinderRenderer::new);
    }
}
