package mcjty.nice.setup;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void initClient(FMLClientSetupEvent e) {
        RenderTypeLookup.setRenderLayer(Registration.CYLINDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(Registration.SMALL_CYLINDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(Registration.SOLID_CYLINDER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(Registration.SOLID_SMALL_CYLINDER.get(), RenderType.cutout());
    }
}
