package mcjty.nice.blocks;

import mcjty.nice.Nice;
import mcjty.nice.client.BlockColor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public class GenericBlock extends Block {

    public GenericBlock(String name, Material materialIn) {
        super(materialIn);
        setRegistryName(name);
        setUnlocalizedName(Nice.MODID + "." + name);
        setCreativeTab(Nice.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initClient() {
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(this), new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                int col = 0;
                if (stack.hasTagCompound()) {
                    col = stack.getTagCompound().getInteger("color");
                }
                BlockColor color = BlockColor.values()[col];
                BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                Map<IBlockState, ModelResourceLocation> variants = dispatcher.getBlockModelShapes().getBlockStateMapper().getVariants(GenericBlock.this);
                return variants.get(GenericBlock.this.getDefaultState().withProperty(GenericParticleBlock.COLOR, color));
            }
        });
    }

}
