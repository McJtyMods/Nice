package mcjty.nice.blocks;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nullable;
import java.util.function.Function;

public class SolidCylinderBlock extends GenericParticleBlock {

    public SolidCylinderBlock(float scale, Function<DyeColor, Block> siblingGetter) {
        super(scale, true, siblingGetter);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getClickedFace());
    }

    @Override
    protected boolean supportsParticles() {
        return false;
    }
}

