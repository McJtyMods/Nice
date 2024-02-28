package mcjty.nice.setup;

import mcjty.nice.particle.DelayedParticleRenderer;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler {

    // @todo 1.20 correct?
    @SubscribeEvent
    public void onGameRenderOverlay(RenderLevelStageEvent e) {
        if (e.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
            return;
        }
        DelayedParticleRenderer.render(e.getPoseStack());
    }
}
