package mcjty.nice;

import mcjty.lib.datagen.DataGen;
import mcjty.lib.modules.Modules;
import mcjty.lib.varia.ClientTools;
import mcjty.nice.datagen.DataGenerators;
import mcjty.nice.setup.ClientSetup;
import mcjty.nice.setup.ModSetup;
import mcjty.nice.setup.Registration;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Random;
import java.util.function.Supplier;

@Mod(Nice.MODID)
public class Nice {
    public static final String MODID = "nice";

    public static final ModSetup setup = new ModSetup();

    public static Nice instance;
    private final Modules modules = new Modules();
    public static Random random = new Random();

    public Nice() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Dist dist = FMLEnvironment.dist;

        instance = this;
        setupModules();

        NiceConfig.register(bus, modules);
        Registration.register(bus);

        bus.addListener(this::onDataGen);

        if (dist.isClient()) {
            bus.addListener(modules::initClient);
            bus.addListener(ClientSetup::initClient);
            ClientTools.onTextureStitch(bus, ClientSetup::onTextureStitch);
        }
    }

    public static <T extends Item> Supplier<T> tab(Supplier<T> supplier) {
        return instance.setup.tab(supplier);
    }

    private void onDataGen(GatherDataEvent event) {
        DataGen datagen = new DataGen(MODID, event);
        DataGenerators.datagen(datagen);
        datagen.generate();
    }

    private void setupModules() {
//        modules.register(new CrafterModule());
    }
}
