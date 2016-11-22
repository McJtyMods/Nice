package mcjty.nice.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static AltarCenterBlock altarCenterBlock;

    public static void init() {
        altarCenterBlock = new AltarCenterBlock();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        altarCenterBlock.initClient();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
//        altarCenterBlock.initItemModel();
    }
}
