package mcjty.nice;

import mcjty.lib.compat.CompatCreativeTabs;
import mcjty.nice.blocks.ModBlocks;
import mcjty.nice.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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
                        "required-after:compatlayer@[" + Nice.COMPATLAYER_VER + ",);" +
                        "after:Forge@[" + Nice.MIN_FORGE10_VER + ",);" +
                        "after:forge@[" + Nice.MIN_FORGE11_VER + ",)",
        version = Nice.VERSION,
        acceptedMinecraftVersions = "[1.10,1.12)")
public class Nice {
    public static final String MODID = "nice";
    public static final String MODNAME = "NICE";
    public static final String VERSION = "0.0.3";
    public static final String MIN_FORGE10_VER = "12.18.1.2082";
    public static final String MIN_FORGE11_VER = "13.19.0.2176";
    public static final String COMPATLAYER_VER = "0.1.0";

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
        creativeTab = new CompatCreativeTabs("Nice") {
            @Override
            protected Item getItem() {
                return Item.getItemFromBlock(ModBlocks.cylinderBlock);
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
}
