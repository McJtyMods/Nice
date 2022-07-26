package mcjty.nice.blocks;

import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.builder.BlockBuilder;
import mcjty.lib.varia.TagTools;
import mcjty.nice.particle.ParticleType;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.awt.*;
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
//                .harvestLevel(ToolType.PICKAXE, 1)    // @todo tags
                .info(key("message.nice.shiftmessage"))
                .infoShift(header(),
                        general("diamond", GenericParticleBlock::hasParticles, ChatFormatting.AQUA),
                        general("water", GenericParticleBlock::hasParticles, ChatFormatting.AQUA),
                        general("wool", GenericParticleBlock::hasParticles, ChatFormatting.AQUA),
                        general("fish", GenericParticleBlock::hasParticles, ChatFormatting.AQUA),
                        general("string", GenericParticleBlock::hasParticles, ChatFormatting.AQUA),
                        general("glass", GenericParticleBlock::hasParticles, ChatFormatting.AQUA),
                        general("dye", ChatFormatting.AQUA)));
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
    public InteractionResult use(@Nonnull BlockState state, Level world, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult result) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (!heldItem.isEmpty()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GenericParticleTileEntity) {
                GenericParticleTileEntity pt = (GenericParticleTileEntity) blockEntity;
                if (!supportsParticles()) {
                    if (TagTools.hasTag(heldItem.getItem(), Tags.Items.DYES)) {
                        DyeColor color = DyeColor.getColor(heldItem);
                        if (color != null) {
                            pt.setColor(color);
                            return InteractionResult.SUCCESS;
                        }
                    } else {
                        if (world.isClientSide) {
                            player.sendSystemMessage(Component.literal("No particles supported!"));
                        }
                    }
                } else {
                    if (Items.DIAMOND.equals(heldItem.getItem())) {
                        pt.setType(ParticleType.BLINK);
                        return InteractionResult.SUCCESS;
                    } else if (TagTools.hasTag(heldItem.getItem(), ItemTags.FISHES)) {
                        pt.setType(ParticleType.FISH);
                        return InteractionResult.SUCCESS;
                    } else if (TagTools.hasTag(heldItem.getItem(), ItemTags.WOOL)) {
                        pt.setType(ParticleType.SMOKE);
                        return InteractionResult.SUCCESS;
                    } else if (Items.WATER_BUCKET.equals(heldItem.getItem())) {
                        pt.setType(ParticleType.BUBBLE);
                        return InteractionResult.SUCCESS;
                    } else if (Items.STRING.equals(heldItem.getItem())) {
                        pt.setType(ParticleType.NONE);
                        return InteractionResult.SUCCESS;
                    } else if (TagTools.hasTag(heldItem.getItem(), Tags.Items.GLASS)) {
                        pt.toggleVisibility();
                        return InteractionResult.SUCCESS;
                    } else if (TagTools.hasTag(heldItem.getItem(), Tags.Items.DYES)) {
                        DyeColor color = DyeColor.getColor(heldItem);
                        if (color != null) {
                            pt.setColor(color);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return super.use(state, world, pos, player, hand, result);
    }
}
