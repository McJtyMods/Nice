package mcjty.nice.client;

import net.minecraft.util.IStringSerializable;

public enum BlockColor implements IStringSerializable {
    BLUE("blue"),
    GREEN("green"),
    RED("red"),
    WHITE("white");

    private final String name;

    BlockColor(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
