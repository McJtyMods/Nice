package mcjty.nice.blocks;

import mcjty.nice.client.BlockColor;
import net.minecraft.init.Blocks;
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
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(cylinderBlock, BlockColor.BLUE, 2), "gdg", "gdg", "gdg", 'g', Blocks.GLASS_PANE, 'd', "dyeBlue"));
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(cylinderBlock, BlockColor.GREEN, 2), "gdg", "gdg", "gdg", 'g', Blocks.GLASS_PANE, 'd', "dyeGreen"));
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(cylinderBlock, BlockColor.RED, 2), "gdg", "gdg", "gdg", 'g', Blocks.GLASS_PANE, 'd', "dyeRed"));
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(cylinderBlock, BlockColor.YELLOW, 2), "gdg", "gdg", "gdg", 'g', Blocks.GLASS_PANE, 'd', "dyeYellow"));
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(solidBlock, BlockColor.TRANSP, 4), "gGg", "GdG", "gGg", 'g', Blocks.GLASS_PANE, 'G', Blocks.GLASS, 'd', Blocks.GLASS_PANE));
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(solidBlock, BlockColor.BLUE, 4), "gGg", "GdG", "gGg", 'g', Blocks.GLASS_PANE, 'G', Blocks.GLASS, 'd', "dyeBlue"));
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(solidBlock, BlockColor.GREEN, 4), "gGg", "GdG", "gGg", 'g', Blocks.GLASS_PANE, 'G', Blocks.GLASS, 'd', "dyeGreen"));
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(solidBlock, BlockColor.RED, 4), "gGg", "GdG", "gGg", 'g', Blocks.GLASS_PANE, 'G', Blocks.GLASS, 'd', "dyeRed"));
        GameRegistry.addRecipe(new ShapedOreRecipe(GenericParticleBlock.makeColoredBlock(solidBlock, BlockColor.YELLOW, 4), "gGg", "GdG", "gGg", 'g', Blocks.GLASS_PANE, 'G', Blocks.GLASS, 'd', "dyeYellow"));
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
