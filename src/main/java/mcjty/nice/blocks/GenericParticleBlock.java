package mcjty.nice.blocks;

import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import mcjty.nice.particle.ParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;

import java.util.function.Function;


public class GenericParticleBlock extends Block implements EntityBlock {

    private final float scale;

    private final Function<DyeColor, Block> siblingGetter;

    public GenericParticleBlock(Properties properties, float scale, boolean noOcclusion, Function<DyeColor, Block> siblingGetter) {
        super(noOcclusion ? properties.sound(SoundType.GLASS).noOcclusion().dynamicShape()
                : properties.sound(SoundType.GLASS));
        this.scale = scale;
        this.siblingGetter = siblingGetter;
        registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GenericParticleTileEntity(pos, state);
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

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (supportsParticles() && world.getBlockEntity(pos) instanceof GenericParticleTileEntity particles && !particles.isVisible()) {
            return Shapes.empty();
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack heldItem, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!heldItem.isEmpty()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GenericParticleTileEntity) {
                GenericParticleTileEntity pt = (GenericParticleTileEntity) blockEntity;
                if (!supportsParticles()) {
                    if (heldItem.is(Tags.Items.DYES)) {
                        DyeColor color = DyeColor.getColor(heldItem);
                        if (color != null) {
                            if (!world.isClientSide()) pt.setColor(color);
                            return InteractionResult.SUCCESS;
                        }
                    } else {
                        if (world.isClientSide()) {
                            player.sendSystemMessage(Component.literal("No particles supported!"));
                        }
                    }
                } else {
                    if (Items.DIAMOND.equals(heldItem.getItem())) {
                        if (!world.isClientSide()) pt.setType(ParticleType.BLINK);
                        return InteractionResult.SUCCESS;
                    } else if (heldItem.is(ItemTags.FISHES)) {
                        if (!world.isClientSide()) pt.setType(ParticleType.FISH);
                        return InteractionResult.SUCCESS;
                    } else if (heldItem.is(ItemTags.WOOL)) {
                        if (!world.isClientSide()) pt.setType(ParticleType.SMOKE);
                        return InteractionResult.SUCCESS;
                    } else if (Items.WATER_BUCKET.equals(heldItem.getItem())) {
                        if (!world.isClientSide()) pt.setType(ParticleType.BUBBLE);
                        return InteractionResult.SUCCESS;
                    } else if (Items.STRING.equals(heldItem.getItem())) {
                        if (!world.isClientSide()) pt.setType(ParticleType.NONE);
                        return InteractionResult.SUCCESS;
                    } else if (heldItem.is(Tags.Items.GLASS_BLOCKS)) {
                        if (!world.isClientSide()) pt.toggleVisibility();
                        return InteractionResult.SUCCESS;
                    } else if (heldItem.is(Tags.Items.DYES)) {
                        DyeColor color = DyeColor.getColor(heldItem);
                        if (color != null) {
                            if (!world.isClientSide()) pt.setColor(color);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return super.useItemOn(heldItem, state, world, pos, player, hand, result);
    }
}
