package mcjty.nice.setup;

import mcjty.nice.blocks.CylinderRenderer;
import mcjty.nice.particle.ParticleRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void initClient(FMLClientSetupEvent e) {
        Registration.SOLID_BLOCKS.values().forEach(b -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.solid()));
        Registration.PARTICLE_BLOCKS.values().forEach(b -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.translucent()));
        Registration.CYLINDERS.values().forEach(b -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.translucent()));
        Registration.SMALL_CYLINDERS.values().forEach(b -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.translucent()));
        Registration.SOLID_CYLINDERS.values().forEach(b -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        Registration.SOLID_SMALL_CYLINDERS.values().forEach(b -> ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout()));
        CylinderRenderer.register();
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }


    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
            return;
        }
        event.addSprite(ParticleRenderer.PARTICLES);
    }
}
