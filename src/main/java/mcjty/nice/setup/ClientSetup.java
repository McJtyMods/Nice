package mcjty.nice.setup;

import mcjty.nice.blocks.CylinderRenderer;
import mcjty.nice.particle.ParticleRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Collections;
import java.util.List;

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


    public static List<ResourceLocation> onTextureStitch() {
        return Collections.singletonList(ParticleRenderer.PARTICLES);
    }
}
