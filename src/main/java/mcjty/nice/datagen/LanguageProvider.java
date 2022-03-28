package mcjty.nice.datagen;

import mcjty.lib.blocks.BaseBlock;
import mcjty.nice.Nice;
import mcjty.nice.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.DyeColor;
import net.minecraftforge.fml.RegistryObject;

import java.util.Map;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public LanguageProvider(DataGenerator gen, String locale) {
        super(gen, Nice.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.nice", "NICE");
        add("message.nice.shiftmessage", "<Press Shift>");
        addBlocks("Solid Block", Registration.SOLID_BLOCKS, false);
        addBlocks("Particle Block", Registration.PARTICLE_BLOCKS, true);
        addBlocks("Particle Cylinder", Registration.CYLINDERS, true);
        addBlocks("Small Particle Cylinder", Registration.SMALL_CYLINDERS, true);
        addBlocks("Solid Cylinder", Registration.SOLID_CYLINDERS, false);
        addBlocks("Solid Small Cylinder", Registration.SOLID_SMALL_CYLINDERS, false);
    }

    private void addBlocks(String name, Map<DyeColor, RegistryObject<BaseBlock>> blocks, boolean withParticles) {
        for (Map.Entry<DyeColor, RegistryObject<BaseBlock>> entry : blocks.entrySet()) {
            BaseBlock block = entry.getValue().get();
            String colorName = entry.getKey().getName();
            add(block, name + " (" + colorName + ")");
            addBlockMessages(block, withParticles);
        }
    }

    private void addBlockMessages(BaseBlock block, boolean withParticles) {
        String path = block.getRegistryName().getPath();
        add("message.nice." + path + ".header", "Use item to change (not consumed)");
        if (withParticles) {
            add("message.nice." + path + ".diamond", "    Diamond for sparkles");
            add("message.nice." + path + ".water", "    Water bucket for bubbles");
            add("message.nice." + path + ".wool", "    Wool for smoke");
            add("message.nice." + path + ".fish", "    Fish for fish");
            add("message.nice." + path + ".string", "    String for nothing");
            add("message.nice." + path + ".glass", "    Glass to toggle visibility");
        }
        add("message.nice." + path + ".dye", "    A dye to change the color");
    }
}
