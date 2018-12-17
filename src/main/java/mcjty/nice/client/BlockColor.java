package mcjty.nice.client;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.IStringSerializable;

public enum BlockColor implements IStringSerializable {
    LIGHT_BLUE("light_blue", EnumDyeColor.LIGHT_BLUE),
    GREEN("green", EnumDyeColor.GREEN),
    RED("red", EnumDyeColor.RED),
    YELLOW("yellow", EnumDyeColor.YELLOW),
    TRANSP("transp", EnumDyeColor.BLACK),
    BROWN("brown", EnumDyeColor.BROWN),
    CYAN("cyan", EnumDyeColor.CYAN),
    GRAY("gray", EnumDyeColor.GRAY),
    BLUE("blue", EnumDyeColor.BLUE),
    LIME("lime", EnumDyeColor.LIME),
    MAGENTA("magenta", EnumDyeColor.MAGENTA),
    ORANGE("orange", EnumDyeColor.ORANGE),
    PINK("pink", EnumDyeColor.PINK),
    PURPLE("purple", EnumDyeColor.PURPLE),
    WHITE("white", EnumDyeColor.WHITE),
    SILVER("silver", EnumDyeColor.SILVER);

    private final String name;
    private final EnumDyeColor dye;

    BlockColor(String name, EnumDyeColor dye) {
        this.name = name;
        this.dye = dye;
    }

    @Override
    public String getName() {
        return name;
    }

    public EnumDyeColor getDye() {
        return dye;
    }
}
