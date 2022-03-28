package mcjty.nice.particle;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

public class ParticleRenderTypes extends RenderType {

    // Dummy
    public ParticleRenderTypes(String name, VertexFormat format, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable setup, Runnable clear) {
        super(name, format, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, setup, clear);
    }

    public static final RenderType TRANSLUCENT_ADD = create("translucent_add", DefaultVertexFormats.BLOCK, GL11.GL_QUADS, 262144, true, false,
            State.builder().setShadeModelState(SMOOTH_SHADE)
                    .setLightmapState(LIGHTMAP)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(true));

    public static final RenderType TRANSLUCENT_ADD_NOLIGHTMAPS = create("translucent_add_nolightmaps", DefaultVertexFormats.BLOCK, GL11.GL_QUADS, 262144, true, false,
            State.builder().setShadeModelState(SMOOTH_SHADE)
                    .setLightmapState(RenderState.NO_LIGHTMAP)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(true));


    public static final RenderType TRANSLUCENT_LIGHTNING_NOLIGHTMAPS = create("translucent_lightning_nolightmaps", DefaultVertexFormats.BLOCK, GL11.GL_QUADS, 262144, true, false,
            State.builder().setShadeModelState(SMOOTH_SHADE)
                    .setLightmapState(RenderState.NO_LIGHTMAP)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(true));

    public static final RenderType TRANSLUCENT_LIGHTNING = create("translucent_lightning", DefaultVertexFormats.BLOCK, GL11.GL_QUADS, 262144, true, false,
            State.builder().setShadeModelState(SMOOTH_SHADE)
                    .setLightmapState(LIGHTMAP)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(true));

    public static final RenderType TRANSLUCENT_TEST = create("translucent_test", DefaultVertexFormats.BLOCK, GL11.GL_QUADS, 262144, true, true,
            State.builder().setShadeModelState(SMOOTH_SHADE)
                    .setLightmapState(LIGHTMAP)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setOutputState(TRANSLUCENT_TARGET)
                    .createCompositeState(true));

    public static final RenderType TRANSLUCENT_TEST2 = create("translucent_test2", DefaultVertexFormats.BLOCK, GL11.GL_QUADS, 262144, true, true,
            State.builder().setShadeModelState(SMOOTH_SHADE)
                    .setLightmapState(LIGHTMAP)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setOutputState(TRANSLUCENT_TARGET)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(true));

    public static final RenderType TRANSLUCENT_PARTICLES = create("translucent_particles", DefaultVertexFormats.BLOCK, GL11.GL_QUADS, 262144, true, true,
            State.builder().setShadeModelState(SMOOTH_SHADE)
                    .setLightmapState(LIGHTMAP)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
//                    .setOutputState(TRANSLUCENT_TARGET)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(true));

}
