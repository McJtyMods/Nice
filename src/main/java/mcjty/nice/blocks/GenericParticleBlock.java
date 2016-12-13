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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

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

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add(TextFormatting.BLUE + "Select with item to change");
        tooltip.add(TextFormatting.BLUE + "(the item is not consumed)");
        tooltip.add("    Diamond for sparkles");
        tooltip.add("    Water bucket for bubbles");
        tooltip.add("    Wool for smoke");
        tooltip.add("    Fish for fish");
        if (stack.hasTagCompound()) {
            int color = stack.getTagCompound().getInteger("color");
            tooltip.add(TextFormatting.YELLOW + "Block color: " + BlockColor.values()[color].getName());
        }
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
                return true;
            } else if (Items.FISH.equals(heldItem.getItem())) {
                cylinder.setType(ParticleType.FISH);
                return true;
            } else if (Item.getItemFromBlock(Blocks.WOOL).equals(heldItem.getItem())) {
                cylinder.setType(ParticleType.SMOKE);
                return true;
            } else if (Items.WATER_BUCKET.equals(heldItem.getItem())) {
                cylinder.setType(ParticleType.BUBBLE);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof GenericParticleTileEntity) {
            int col = 0;
            if (stack.hasTagCompound()) {
                col = stack.getTagCompound().getInteger("color");
            }
            ((GenericParticleTileEntity) te).setColor(BlockColor.values()[col]);
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof GenericParticleTileEntity) {
            BlockColor color = ((GenericParticleTileEntity) te).getColor();
            ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setInteger("color", color.ordinal());
            stack.setTagCompound(tagCompound);
            List<ItemStack> result = new ArrayList<>();
            result.add(stack);
            return result;
        } else {
            return super.getDrops(world, pos, state, fortune);
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (willHarvest) {
            return true; // If it will harvest, delay deletion of the block until after getDrops
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }


    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        super.harvestBlock(world, player, pos, state, te, stack);
        world.setBlockToAir(pos);
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
