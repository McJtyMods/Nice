package mcjty.nice;

import mcjty.lib.base.ModBase;
import mcjty.nice.blocks.ModBlocks;
import mcjty.nice.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod(modid = Nice.MODID, name = Nice.MODNAME,
        dependencies =
                "required-after:mcjtylib_ng@[" + Nice.MIN_MCJTYLIB_VER + ",);" +
                "after:forge@[" + Nice.MIN_FORGE11_VER + ",)",
        acceptedMinecraftVersions = "[1.12,1.13)",
        version = Nice.VERSION)
public class Nice implements ModBase {
    public static final String MODID = "nice";
    public static final String MODNAME = "NICE";
    public static final String VERSION = "0.3.0-alpha";
    public static final String MIN_MCJTYLIB_VER = "3.0.0-alpha";
    public static final String MIN_FORGE11_VER = "13.19.0.2176";

    @SidedProxy(clientSide="mcjty.nice.proxy.ClientProxy", serverSide="mcjty.nice.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static Nice instance;
    public static Logger logger;
    public static CreativeTabs creativeTab;
    public static Random random;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = LogManager.getLogger(MODNAME.replace(" ", ""));
        creativeTab = new CreativeTabs("Nice") {
            @Override
            public ItemStack getTabIconItem() {
                return new ItemStack(ModBlocks.cylinderBlock);
            }
        };
        random = new Random();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }


    @Override
    public String getModId() {
        return Nice.MODID;
    }

    @Override
    public void openManual(EntityPlayer entityPlayer, int i, String s) {
        // @todo
    }
}
