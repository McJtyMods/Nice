package mcjty.nice.datagen;

import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.datagen.DataGen;
import mcjty.lib.datagen.Dob;
import mcjty.lib.setup.DeferredBlock;
import mcjty.nice.setup.Registration;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.registries.RegistryObject;

import java.util.Map;

public class LanguageProvider {

    public static void addTranslations(DataGen dataGen) {
        dataGen.add(Dob.builder()
                .message("itemGroup.nice", "NICE")
                .message("message.nice.shiftmessage", "<Press Shift>")
        );
        addBlocks(dataGen, "Solid Block", Registration.SOLID_BLOCKS, false);
        addBlocks(dataGen, "Particle Block", Registration.PARTICLE_BLOCKS, true);
        addBlocks(dataGen, "Particle Cylinder", Registration.CYLINDERS, true);
        addBlocks(dataGen, "Small Particle Cylinder", Registration.SMALL_CYLINDERS, true);
        addBlocks(dataGen, "Solid Cylinder", Registration.SOLID_CYLINDERS, false);
        addBlocks(dataGen, "Solid Small Cylinder", Registration.SOLID_SMALL_CYLINDERS, false);
    }

    private static void addBlocks(DataGen dataGen, String name, Map<DyeColor, DeferredBlock<BaseBlock>> blocks, boolean withParticles) {
        for (Map.Entry<DyeColor, DeferredBlock<BaseBlock>> entry : blocks.entrySet()) {
            String colorName = entry.getKey().getName();
            Dob.Builder builder = Dob.blockBuilder(entry.getValue()).name(name + " (" + colorName + ")");
            addBlockMessages(builder, withParticles);
            dataGen.add(builder);
        }
    }

    private static void addBlockMessages(Dob.Builder builder, boolean withParticles) {
        builder.keyedMessage(".header", "Use item to change (not consumed)");
        if (withParticles) {
            builder.keyedMessage(".diamond", "    Diamond for sparkles");
            builder.keyedMessage(".water", "    Water bucket for bubbles");
            builder.keyedMessage(".wool", "    Wool for smoke");
            builder.keyedMessage(".fish", "    Fish for fish");
            builder.keyedMessage(".string", "    String for nothing");
            builder.keyedMessage(".glass", "    Glass to toggle visibility");
        }
        builder.keyedMessage(".dye", "    A dye to change the color");
    }
}
