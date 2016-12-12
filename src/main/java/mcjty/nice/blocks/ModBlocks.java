package mcjty.nice.blocks;

import mcjty.nice.client.BlockColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModBlocks {

    public static CylinderBlock cylinderBlock;
    public static SolidBlock solidBlock;

    public static void init() {
        cylinderBlock = new CylinderBlock();
        solidBlock = new SolidBlock();
    }

    public static void initCrafting() {
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(cylinderBlock, BlockColor.BLUE, 3), "g g", "GdG", "g g", 'g', Blocks.GLASS_PANE, 'G', Blocks.GLASS, 'd', "dyeBlue"));
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(cylinderBlock, BlockColor.GREEN, 3), "g g", "GdG", "g g", 'g', Blocks.GLASS_PANE, 'G', Blocks.GLASS, 'd', "dyeGreen"));
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(solidBlock, BlockColor.BLUE, 5), "gGg", "GdG", "gGg", 'g', Blocks.GLASS_PANE, 'G', Blocks.GLASS, 'd', "dyeBlue"));
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(solidBlock, BlockColor.GREEN, 5), "gGg", "GdG", "gGg", 'g', Blocks.GLASS_PANE, 'G', Blocks.GLASS, 'd', "dyeGreen"));
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        cylinderBlock.initClient();
        solidBlock.initClient();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
//        altarCenterBlock.initItemModel();
    }
}
