package mcjty.nice.setup;

import mcjty.nice.particle.DelayedParticleRenderer;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler {

    @SubscribeEvent
    public void onGameRenderOverlay(RenderLevelLastEvent e) {
        DelayedParticleRenderer.render(e.getPoseStack());
    }
}
