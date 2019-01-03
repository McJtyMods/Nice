package mcjty.nice.blocks;

import mcjty.nice.particle.ParticleRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SolidRenderer extends TileEntitySpecialRenderer<SolidTileEntity> {

//    ResourceLocation blueSphereTexture = new ResourceLocation(Nice.MODID, "textures/effects/bluesphere.png");


    @Override
    public void render(SolidTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        IBlockState blockState = getWorld().getBlockState(te.getPos());
        if (blockState.getBlock() != ModBlocks.solidBlock) {
            return;
        }
        this.bindTexture(ParticleRenderer.particles);
        ParticleRenderer.renderSystem(te, (float) x, (float) y, (float) z);
    }

    public static void register() {
        ClientRegistry.bindTileEntitySpecialRenderer(SolidTileEntity.class, new SolidRenderer());
    }
}
