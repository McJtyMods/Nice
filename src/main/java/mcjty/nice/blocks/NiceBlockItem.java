package mcjty.nice.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import java.util.function.Consumer;

public class NiceBlockItem extends BlockItem {
    public static java.util.function.BooleanSupplier shiftDown = () -> false;

    public NiceBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display,
                                Consumer<Component> output, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, output, flag);
        if (!shiftDown.getAsBoolean() && !flag.isCreative()) {
            output.accept(Component.translatable("message.nice.shiftmessage"));
            return;
        }
        String prefix = getBlock().getDescriptionId() + ".";
        output.accept(Component.translatable(prefix + "header"));
        if (((GenericParticleBlock) getBlock()).supportsParticles()) {
            for (String key : new String[]{"diamond", "water", "wool", "fish", "string", "glass"}) {
                output.accept(Component.translatable(prefix + key).withStyle(ChatFormatting.AQUA));
            }
        }
        output.accept(Component.translatable(prefix + "dye").withStyle(ChatFormatting.AQUA));
    }
}
