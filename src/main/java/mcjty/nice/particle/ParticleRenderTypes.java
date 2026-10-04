package mcjty.nice.particle;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import mcjty.nice.Nice;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public final class ParticleRenderTypes {
    // Vanilla's entityTranslucentEmissive still applies PER_FACE_LIGHTING.
    // Billboards need their original color regardless of the camera-facing normal.
    public static final RenderPipeline PARTICLE_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_EMISSIVE_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(Nice.MODID, "pipeline/particles"))
            .withShaderDefine("ALPHA_CUTOUT", 0.1F)
            .withShaderDefine("NO_CARDINAL_LIGHTING")
            .withBindGroupLayout(BindGroupLayouts.SAMPLER1)
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withCull(false)
            .withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false))
            .build();

    public static final RenderType PARTICLES = RenderType.create("nice_particles",
            RenderSetup.builder(PARTICLE_PIPELINE)
                    .withTexture("Sampler0", ParticleRenderer.PARTICLES)
                    .useOverlay()
                    .sortOnUpload()
                    .createRenderSetup());

    private ParticleRenderTypes() {}
}
