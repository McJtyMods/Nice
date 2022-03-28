package mcjty.nice.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.BlockStateProperties;

import javax.annotation.Nullable;
import java.util.function.Function;

public class SolidCylinderBlock extends GenericParticleBlock {

    public SolidCylinderBlock(float scale, Function<DyeColor, Block> siblingGetter) {
        super(scale, true, siblingGetter);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getClickedFace());
    }

    @Override
    protected boolean supportsParticles() {
        return false;
    }
}

