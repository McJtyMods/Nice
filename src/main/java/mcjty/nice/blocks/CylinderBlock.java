package mcjty.nice.blocks;

import mcjty.nice.Nice;
import mcjty.nice.client.BlockColor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class CylinderBlock extends GenericParticleBlock implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public CylinderBlock() {
        super("cylinder");
        GameRegistry.registerTileEntity(CylinderTileEntity.class, Nice.MODID + "_cylinder");
    }

    @Override
    protected IBlockState getState() {
        return super.getState().withProperty(FACING, EnumFacing.UP);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initClient() {
        super.initClient();
        ClientRegistry.bindTileEntitySpecialRenderer(CylinderTileEntity.class, new CylinderRenderer());
    }


    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new CylinderTileEntity();
    }

    @Override
    protected void clGetSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (BlockColor color : BlockColor.values()) {
            subItems.add(makeColoredBlock(this, color, 1));
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity) {
        return EnumFacing.getFacingFromVector((float) (entity.posX - clickedBlock.getX()), (float) (entity.posY - clickedBlock.getY()), (float) (entity.posZ - clickedBlock.getZ()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, COLOR);
    }
}
