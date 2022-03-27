package mcjty.nice.setup;

import mcjty.nice.blocks.CylinderRenderer;
import mcjty.nice.particle.ParticleRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void initClient(FMLClientSetupEvent e) {
        Registration.SOLID_BLOCKS.values().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.solid()));
        Registration.PARTICLE_BLOCKS.values().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.translucent()));
        Registration.CYLINDERS.values().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.translucent()));
        Registration.SMALL_CYLINDERS.values().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.translucent()));
        Registration.SOLID_CYLINDERS.values().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
        Registration.SOLID_SMALL_CYLINDERS.values().forEach(b -> RenderTypeLookup.setRenderLayer(b.get(), RenderType.cutout()));
        CylinderRenderer.register();
    }


    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
            return;
        }
        event.addSprite(ParticleRenderer.PARTICLES);
    }
}
