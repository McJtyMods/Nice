package mcjty.nice.blocks;

import mcjty.lib.tools.ItemStackTools;
import mcjty.nice.particle.ParticleType;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GenericParticleBlock extends GenericBlock {

    public GenericParticleBlock(String name) {
        super(name, Material.ROCK);
        setHardness(3.0f);
        setResistance(5.0f);
        setSoundType(SoundType.GLASS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    protected boolean clOnBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (ItemStackTools.isEmpty(heldItem)) {
            return false;
        }
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof GenericParticleTileEntity) {
            GenericParticleTileEntity cylinder = (GenericParticleTileEntity) te;
            if (Items.DIAMOND.equals(heldItem.getItem())) {
                cylinder.setType(ParticleType.BLINK);
            } else if (Items.FISH.equals(heldItem.getItem())) {
                cylinder.setType(ParticleType.FISH);
            } else if (Items.STRING.equals(heldItem.getItem())) {
                cylinder.setType(ParticleType.SMOKE);
            } else if (Items.SLIME_BALL.equals(heldItem.getItem())) {
                cylinder.setType(ParticleType.BUBBLE);
            }
        }
        return false;
    }
}
