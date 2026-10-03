package mcjty.nice.blocks;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

final class CylinderShapes {

    // Cross-section bounds from smallcylinder.obj; the length fills the block.
    private static final double MIN = 0.2963;
    private static final double MAX = 0.7037;
    private static final VoxelShape SMALL_X = Shapes.box(0, MIN, MIN, 1, MAX, MAX);
    private static final VoxelShape SMALL_Y = Shapes.box(MIN, 0, MIN, MAX, 1, MAX);
    private static final VoxelShape SMALL_Z = Shapes.box(MIN, MIN, 0, MAX, MAX, 1);

    private CylinderShapes() {
    }

    static VoxelShape getShape(BlockState state, float scale) {
        if (scale >= 0.5f) {
            return Shapes.block();
        }
        return switch (state.getValue(BlockStateProperties.FACING).getAxis()) {
            case X -> SMALL_X;
            case Y -> SMALL_Y;
            case Z -> SMALL_Z;
        };
    }
}
