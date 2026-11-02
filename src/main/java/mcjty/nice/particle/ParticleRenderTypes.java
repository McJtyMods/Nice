package mcjty.nice.particle;

import com.mojang.renderpearl.api.pipeline.BlendFunction;
import com.mojang.renderpearl.api.pipeline.ColorTargetState;
import com.mojang.renderpearl.api.pipeline.CompareOp;
import com.mojang.renderpearl.api.pipeline.DepthStencilState;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import mcjty.nice.Nice;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.oit.OitPipelineSet;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public final class ParticleRenderTypes {

    // Vanilla's entityTranslucentEmissive still applies PER_FACE_LIGHTING.
    // Billboards need their original color regardless of the camera-facing normal.
    public static final RenderPipeline PARTICLE_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(Nice.MODID, "pipeline/particles"))
            .withShaderDefine("NO_CARDINAL_LIGHTING")
            .withBindGroupLayout(BindGroupLayouts.SAMPLER1)
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withCull(false)
            .withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false))
            .build();

    public static final OitPipelineSet PARTICLE_OIT_PIPELINES = OitPipelineSet.builder("nice_particles",
            RenderPipeline.builder(RenderPipelines.OIT_ENTITY_SNIPPET)
                    .withCull(false))
            .withAccumulateModifier(accumulate -> accumulate.withShaderDefine("NO_CARDINAL_LIGHTING")
                    .withBindGroupLayout(BindGroupLayouts.SAMPLER1)
                    .withBindGroupLayout(BindGroupLayouts.SAMPLER2))
            .build();

    public static final RenderType PARTICLES = RenderType.create("nice_particles",
            RenderSetup.builder(PARTICLE_PIPELINE)
                    .setOitPipelines(PARTICLE_OIT_PIPELINES)
                    .withTexture("Sampler0", ParticleRenderer.PARTICLES)
                    .useOverlay()
                    .useLightmap()
                    .sortOnUpload()
                    .createRenderSetup());

    // Use emissive entity passes for glow, with color-only writes and depth testing.
    public static final RenderPipeline GLOW_PIPELINE = emissionPipeline("particle_glow", BlendFunction.ADDITIVE);
    public static final RenderPipeline EMISSION_PIPELINE = emissionPipeline("particle_emission", BlendFunction.LIGHTNING);
    public static final OitPipelineSet GLOW_OIT_PIPELINES = emissionOitPipelines("particle_glow");
    public static final OitPipelineSet EMISSION_OIT_PIPELINES = emissionOitPipelines("particle_emission");
    public static final RenderType PARTICLE_GLOW = emissionType("particle_glow", GLOW_PIPELINE,
            Identifier.fromNamespaceAndPath(Nice.MODID, "textures/effects/white.png"), GLOW_OIT_PIPELINES);
    public static final RenderType PARTICLE_EMISSION = emissionType("particle_emission", EMISSION_PIPELINE, ParticleRenderer.PARTICLES, EMISSION_OIT_PIPELINES);

    private static RenderPipeline emissionPipeline(String name, BlendFunction blend) {
        return RenderPipeline.builder(RenderPipelines.ENTITY_EMISSIVE_SNIPPET)
                .withLocation(Identifier.fromNamespaceAndPath(Nice.MODID, "pipeline/" + name))
                .withShaderDefine("NO_CARDINAL_LIGHTING")
                .withBindGroupLayout(BindGroupLayouts.SAMPLER1)
                .withColorTargetState(new ColorTargetState(blend))
                .withCull(false)
                .withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false))
                .build();
    }

    private static OitPipelineSet emissionOitPipelines(String name) {
        return OitPipelineSet.builder("nice_" + name,
                RenderPipeline.builder(RenderPipelines.OIT_ENTITY_SNIPPET)
                        .withShaderDefine("OIT_ADDITIVE")
                        .withCull(false))
                .withAccumulateModifier(accumulate -> accumulate.withShaderDefine("EMISSIVE")
                        .withShaderDefine("NO_CARDINAL_LIGHTING")
                        .withBindGroupLayout(BindGroupLayouts.SAMPLER1))
                .build();
    }

    private static RenderType emissionType(String name, RenderPipeline pipeline, Identifier texture, OitPipelineSet oit) {
        return RenderType.create("nice_" + name, RenderSetup.builder(pipeline)
                .setOitPipelines(oit)
                .withTexture("Sampler0", texture)
                .useOverlay()
                .sortOnUpload()
                .createRenderSetup());
    }

    public static void register() {
        RenderPipelines.register(PARTICLE_PIPELINE);
        RenderPipelines.register(GLOW_PIPELINE);
        RenderPipelines.register(EMISSION_PIPELINE);
        RenderPipelines.register(PARTICLE_OIT_PIPELINES);
        RenderPipelines.register(GLOW_OIT_PIPELINES);
        RenderPipelines.register(EMISSION_OIT_PIPELINES);
    }

    private ParticleRenderTypes() {}
}
