package mcjty.nice.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static CylinderBlock cylinderBlock;
    public static CylinderBlock smallCylinderBlock;
    public static SolidCylinderBlock solidCylinderBlock;
    public static SolidCylinderBlock solidSmallCylinderBlock;
    public static SolidBlock solidBlock;

    public static void init() {
        cylinderBlock = new CylinderBlock("cylinder", .8f);
        smallCylinderBlock = new CylinderBlock("smallcylinder", .3f);
        solidCylinderBlock = new SolidCylinderBlock("solidcylinder", .8f);
        solidSmallCylinderBlock = new SolidCylinderBlock("solidsmallcylinder", .3f);
        solidBlock = new SolidBlock();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        cylinderBlock.initClient();
        smallCylinderBlock.initClient();
        solidCylinderBlock.initClient();
        solidSmallCylinderBlock.initClient();
        solidBlock.initClient();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
//        altarCenterBlock.initItemModel();
    }
}
