package mcjty.nice.blocks;

import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.builder.BlockBuilder;
import mcjty.nice.particle.ParticleType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;

import static mcjty.lib.builder.TooltipBuilder.general;
import static mcjty.lib.builder.TooltipBuilder.header;

public class GenericParticleBlock extends BaseBlock {

    public static final EnumProperty<DyeColor> COLOR = EnumProperty.<DyeColor>create("color", DyeColor.class);
    private final float scale;

    public GenericParticleBlock(float scale) {
        super(new BlockBuilder()
                .properties(Properties.of(Material.STONE).sound(SoundType.GLASS))
                .tileEntitySupplier(GenericParticleTileEntity::new)
                .harvestLevel(ToolType.PICKAXE, 1)
                .info(header(),
                        general("diamond", TextFormatting.AQUA),
                        general("water", TextFormatting.AQUA),
                        general("wool", TextFormatting.AQUA),
                        general("fish", TextFormatting.AQUA),
                        general("string", TextFormatting.AQUA),
                        general("dye", TextFormatting.AQUA)));
        this.scale = scale;
    }

    protected boolean supportsParticles() {
        return true;
    }

    public float getScale() {
        return scale;
    }

    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult result) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (!heldItem.isEmpty()) {
            TileEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GenericParticleTileEntity && supportsParticles()) {
                GenericParticleTileEntity pt = (GenericParticleTileEntity) blockEntity;
                if (Items.DIAMOND.equals(heldItem.getItem())) {
                    pt.setType(ParticleType.BLINK);
                    return ActionResultType.SUCCESS;
                } else if (heldItem.getItem().is(ItemTags.FISHES)) {
                    pt.setType(ParticleType.FISH);
                    return ActionResultType.SUCCESS;
                } else if (heldItem.getItem().is(ItemTags.WOOL)) {
                    pt.setType(ParticleType.SMOKE);
                    return ActionResultType.SUCCESS;
                } else if (Items.WATER_BUCKET.equals(heldItem.getItem())) {
                    pt.setType(ParticleType.BUBBLE);
                    return ActionResultType.SUCCESS;
                } else if (Items.STRING.equals(heldItem.getItem())) {
                    pt.setType(ParticleType.NONE);
                    return ActionResultType.SUCCESS;
                } else if (heldItem.getItem().is(Tags.Items.DYES)) {
                    DyeColor color = DyeColor.getColor(heldItem);
                    if (color != null) {
                        pt.setColor(color);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return super.use(state, world, pos, player, hand, result);
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(COLOR);
    }
}
