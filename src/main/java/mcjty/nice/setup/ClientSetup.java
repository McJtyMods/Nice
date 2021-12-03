package mcjty.nice.setup;

import mcjty.nice.blocks.CylinderRenderer;
import mcjty.nice.particle.ParticleRenderer;
import mcjty.nice.particle.ParticleType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void initClient(FMLClientSetupEvent e) {
        Registration.CYLINDERS.values().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
        Registration.SMALL_CYLINDERS.values().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
        Registration.SOLID_CYLINDERS.values().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
        Registration.SOLID_SMALL_CYLINDERS.values().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
        CylinderRenderer.register();
    }
}
