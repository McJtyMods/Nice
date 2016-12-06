package mcjty.nice.blocks;

import mcjty.nice.Nice;
import mcjty.nice.client.RenderTools;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
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

    private static Vec3d[] offsets = new Vec3d[6];

    static {
        offsets[0] = new Vec3d(0, .3f, .2f);
        offsets[1] = new Vec3d(-.3f, 2f, 0f);
        offsets[2] = new Vec3d(.1f, 0, -.3f);
        offsets[3] = new Vec3d(0, .4f, .5f);
        offsets[4] = new Vec3d(.2f, .6f, -.3f);
        offsets[5] = new Vec3d(-.4f, .8f, -.2f);
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

        int brightness = 240;
        double scale = .4;
        RenderTools.rotateToPlayer();

        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);

        long time = System.currentTimeMillis();
        for (Vec3d o : offsets) {
            float offset = (time % 2000) / 2000.0f;
            double ox = o.xCoord;
            double oy = (o.yCoord + offset) % 1f;
            double oz = o.zCoord;

            int b1 = brightness >> 16 & 65535;
            int b2 = brightness & 65535;
            buffer.pos(ox - scale, oy-scale, oz).tex(0.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
            buffer.pos(ox - scale, oy+scale, oz).tex(0.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
            buffer.pos(ox + scale, oy+scale, oz).tex(1.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
            buffer.pos(ox + scale, oy-scale, oz).tex(1.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
        }

        tessellator.draw();
        GlStateManager.popMatrix();

//        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
}
