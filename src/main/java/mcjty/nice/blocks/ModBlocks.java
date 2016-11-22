package mcjty.nice.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static CylinderBlock cylinderBlock;
    public static SolidBlock solidBlock;

    public static void init() {
        cylinderBlock = new CylinderBlock();
        solidBlock = new SolidBlock();
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
