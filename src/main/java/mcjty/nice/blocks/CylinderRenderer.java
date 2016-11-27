package mcjty.nice.blocks;

import mcjty.nice.Nice;
import mcjty.nice.client.RenderTools;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class CylinderRenderer extends TileEntitySpecialRenderer<CylinderTileEntity> {

    ResourceLocation blueSphereTexture = new ResourceLocation(Nice.MODID, "textures/effects/bluesphere.png");
    ResourceLocation gasTexture = new ResourceLocation(Nice.MODID, "textures/effects/gas.png");

    private static class Offset {
        private final float dx;
        private final float dy;

        public Offset(float dx, float dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public float getDx() {
            return dx;
        }

        public float getDy() {
            return dy;
        }
    }

    private static Offset[] offsets = new Offset[6];

    static {
        offsets[0] = new Offset(0, .3f);
        offsets[1] = new Offset(-.3f, 2f);
        offsets[2] = new Offset(.1f, 0);
        offsets[3] = new Offset(0, .4f);
        offsets[4] = new Offset(.2f, .6f);
        offsets[5] = new Offset(-.4f, .8f);
    }

    @Override
    public void renderTileEntityAt(CylinderTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        IBlockState blockState = getWorld().getBlockState(te.getPos());
        if (blockState.getBlock() != ModBlocks.cylinderBlock) {
            return;
        }

        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

//        GlStateManager.enableAlpha();
        GlStateManager.disableAlpha();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0F, (float) z + 0.5F);
//        GlStateManager.scale(.1f, .1f, .1f);

//        this.bindTexture(blueSphereTexture);
        this.bindTexture(gasTexture);

        long time = System.currentTimeMillis();
        for (Offset o : offsets) {
            float offset = (time % 2000) / 2000.0f;
            RenderTools.renderBillboardQuadBright(/*.1f*/.4f, 240, o.dx, (o.dy + offset) % 1f);
        }

        GlStateManager.popMatrix();

//        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
}
