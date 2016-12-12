package mcjty.nice.blocks;

import mcjty.lib.tools.ItemStackTools;
import mcjty.nice.Nice;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class SolidBlock extends GenericBlock implements ITileEntityProvider {

    public SolidBlock() {
        super("solid", Material.ROCK);
        setHardness(3.0f);
        setResistance(5.0f);
        setSoundType(SoundType.STONE);
        GameRegistry.registerTileEntity(SolidTileEntity.class, Nice.MODID + "_solid");
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new SolidTileEntity();
    }


    @Override
    protected boolean clOnBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (ItemStackTools.isEmpty(heldItem)) {
            return false;
        }
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof SolidTileEntity) {
            SolidTileEntity cylinder = (SolidTileEntity) te;
            if (Items.DIAMOND.equals(heldItem.getItem())) {
                System.out.println("CylinderBlock.onBlockActivated: set type 1");
                cylinder.setType(1);
            } else if (Items.FISH.equals(heldItem.getItem())) {
                System.out.println("CylinderBlock.onBlockActivated: set type 2");
                cylinder.setType(2);
            } else if (Items.STRING.equals(heldItem.getItem())) {
                System.out.println("CylinderBlock.onBlockActivated: set type 0");
                cylinder.setType(0);
            } else if (Items.EMERALD.equals(heldItem.getItem())) {
                System.out.println("CylinderBlock.onBlockActivated: set type 3");
                cylinder.setType(3);
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initClient() {
        super.initClient();
        ClientRegistry.bindTileEntitySpecialRenderer(SolidTileEntity.class, new SolidRenderer());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        if (blockState != iblockstate) {
            return true;
        }

        if (block == this) {
            return false;
        }
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
}
