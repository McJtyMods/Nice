package mcjty.nice.blocks;

import mcjty.lib.compat.CompatBlock;
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
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class GenericBlock extends CompatBlock {

    public GenericBlock(String name, Material materialIn) {
        this(name, materialIn, ItemBlock.class);
    }

    public GenericBlock(String name, Material materialIn, Class<? extends ItemBlock> itemBlockClass) {
        super(materialIn);
        setRegistryName(name);
        setUnlocalizedName(Nice.MODID + "." + name);
        setCreativeTab(Nice.creativeTab);
        GameRegistry.register(this);
        GameRegistry.register(createItemBlock(itemBlockClass), getRegistryName());
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

    private ItemBlock createItemBlock(Class<? extends ItemBlock> itemBlockClass) {
        try {
            Class<?>[] ctorArgClasses = new Class<?>[1];
            ctorArgClasses[0] = Block.class;
            Constructor<? extends ItemBlock> itemCtor = itemBlockClass.getConstructor(ctorArgClasses);
            return itemCtor.newInstance(this);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
