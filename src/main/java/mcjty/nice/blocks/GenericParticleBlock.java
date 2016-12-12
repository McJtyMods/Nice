package mcjty.nice.blocks;

import mcjty.lib.tools.ItemStackTools;
import mcjty.nice.client.BlockColor;
import mcjty.nice.particle.ParticleType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

    public static final PropertyEnum<BlockColor> COLOR = PropertyEnum.<BlockColor>create("color", BlockColor.class);

    public GenericParticleBlock(String name) {
        super(name, Material.ROCK);
        setHardness(3.0f);
        setResistance(5.0f);
        setSoundType(SoundType.GLASS);
        setDefaultState(getState());
    }

    protected IBlockState getState() {
        return blockState.getBaseState().withProperty(COLOR, BlockColor.BLUE);
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

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        BlockColor color = BlockColor.BLUE;
        if (te instanceof GenericParticleTileEntity) {
            color = ((GenericParticleTileEntity) te).getColor();
        }
        return state.withProperty(COLOR, color);
    }

    public static ItemStack makeColoredBlock(Block block, BlockColor color, int amount) {
        ItemStack i = new ItemStack(block, amount);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("color", color.ordinal());
        i.setTagCompound(nbt);
        return i;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR);
    }
}
