package mcjty.nice.blocks;

import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.builder.BlockBuilder;
import mcjty.nice.particle.ParticleType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import java.util.function.Function;

import static mcjty.lib.builder.TooltipBuilder.*;

public class GenericParticleBlock extends BaseBlock {

    public static final Properties OCCLUSION_PROPERTIES = Properties.of(Material.STONE).sound(SoundType.GLASS);
    public static final Properties NOOCCLUSION_PROPERTIES = Properties.of(Material.STONE).sound(SoundType.GLASS).noOcclusion();
    private final float scale;

    private final Function<DyeColor, Block> siblingGetter;

    public GenericParticleBlock(float scale, boolean noOcclusion, Function<DyeColor, Block> siblingGetter) {
        super(new BlockBuilder()
                .properties(noOcclusion ? NOOCCLUSION_PROPERTIES : OCCLUSION_PROPERTIES)
                .tileEntitySupplier(GenericParticleTileEntity::new)
                .harvestLevel(ToolType.PICKAXE, 1)
                .info(key("message.nice.shiftmessage"))
                .infoShift(header(),
                        general("diamond", GenericParticleBlock::hasParticles, TextFormatting.AQUA),
                        general("water", GenericParticleBlock::hasParticles, TextFormatting.AQUA),
                        general("wool", GenericParticleBlock::hasParticles, TextFormatting.AQUA),
                        general("fish", GenericParticleBlock::hasParticles, TextFormatting.AQUA),
                        general("string", GenericParticleBlock::hasParticles, TextFormatting.AQUA),
                        general("dye", TextFormatting.AQUA)));
        this.scale = scale;
        this.siblingGetter = siblingGetter;
    }

    private static boolean hasParticles(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem) {
            BlockItem bi = (BlockItem) stack.getItem();
            if (bi.getBlock() instanceof GenericParticleBlock) {
                GenericParticleBlock gpb = (GenericParticleBlock) bi.getBlock();
                return gpb.supportsParticles();
            }
        }
        return false;
    }

    public Block recolor(DyeColor color) {
        return siblingGetter.apply(color);
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
            if (blockEntity instanceof GenericParticleTileEntity) {
                GenericParticleTileEntity pt = (GenericParticleTileEntity) blockEntity;
                if (!supportsParticles()) {
                    if (heldItem.getItem().is(Tags.Items.DYES)) {
                        DyeColor color = DyeColor.getColor(heldItem);
                        if (color != null) {
                            pt.setColor(color);
                            return ActionResultType.SUCCESS;
                        }
                    } else {
                        if (world.isClientSide) {
                            player.sendMessage(new StringTextComponent("No particles supported!"), Util.NIL_UUID);
                        }
                    }
                } else {
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
        }
        return super.use(state, world, pos, player, hand, result);
    }
}
