package mcjty.nice.particle;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ParticleRenderTypes extends RenderType {

    // The standard emissive eyes pass also gives shader packs an emissive program.
    // It adds color, respects depth testing, and does not write depth for the halo.
    public static final RenderType PARTICLE_GLOW = RenderType.eyes(new ResourceLocation("minecraft", "textures/misc/white.png"));

    // Add the actual sprite's emission on top of its normal fullbright color.
    // Source alpha preserves transparent texels and the particle's opacity.
    public static final RenderType PARTICLE_EMISSION = create("particle_emission", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true,
            CompositeState.builder()
                    .setShaderState(RENDERTYPE_EYES_SHADER)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setCullState(NO_CULL)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));

    // Dummy
    private ParticleRenderTypes(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    public static final RenderType TRANSLUCENT_ADD = create("translucent_add", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 262144, true, false,
            CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setShaderState(RENDERTYPE_TRANSLUCENT_SHADER)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setOutputState(TRANSLUCENT_TARGET)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(true));

    public static final RenderType TRANSLUCENT_ADD_NOLIGHTMAPS = create("translucent_add_nolightmaps", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 262144, true, false,
            CompositeState.builder()
                    .setLightmapState(NO_LIGHTMAP)
                    .setShaderState(RENDERTYPE_TRANSLUCENT_SHADER)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setOutputState(TRANSLUCENT_TARGET)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(true));


    public static final RenderType TRANSLUCENT_LIGHTNING_NOLIGHTMAPS = create("translucent_lightning_nolightmaps", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 262144, true, false,
            CompositeState.builder()
                    .setShaderState(RENDERTYPE_TRANSLUCENT_SHADER)
                    .setLightmapState(RenderStateShard.NO_LIGHTMAP)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setWriteMaskState(COLOR_WRITE)
                    .setOutputState(TRANSLUCENT_TARGET)
                    .createCompositeState(true));


    public static final RenderType TRANSLUCENT_LIGHTNING = create("translucent_lightning", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 262144, true, false,
            CompositeState.builder()
                    .setShaderState(RENDERTYPE_TRANSLUCENT_SHADER)
                    .setLightmapState(RenderStateShard.LIGHTMAP)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setWriteMaskState(COLOR_WRITE)
                    .setOutputState(TRANSLUCENT_TARGET)
                    .createCompositeState(true));

    // Use the entity shader for block-entity geometry so deferred buffer draws
    // cannot select a shader pack's translucent terrain/water program.
    // Keep color-only writes so transparent particles do not occlude their glows.
    public static final RenderType TRANSLUCENT_PARTICLES = create("translucent_particles", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 262144, true, true,
            CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setCullState(NO_CULL)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(true));

    // Shells keep face culling, unlike the billboarded particles.
    public static final RenderType TRANSLUCENT_SHELL = create("translucent_shell", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 262144, true, true,
            CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(true));

}
