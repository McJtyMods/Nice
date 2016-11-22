package mcjty.nice.blocks;

import mcjty.nice.Nice;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GenericBlock extends Block {

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
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(AltarCenterTileEntity.class, new AltarCenterRenderer());
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
