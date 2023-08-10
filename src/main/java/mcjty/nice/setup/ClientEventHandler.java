package mcjty.nice.setup;

import mcjty.nice.particle.DelayedParticleRenderer;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
