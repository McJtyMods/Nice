package mcjty.nice.blocks;

import mcjty.lib.blocks.BaseBlock;
import mcjty.nice.setup.Registration;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import static mcjty.nice.setup.Registration.*;

public class ModBlocks {

    public static final RegistryObject<BaseBlock> CYLINDER = BLOCKS.register("cylinder", () -> new CylinderBlock(.8f));
    public static final RegistryObject<Item> CYLINDER_ITEM = ITEMS.register("cylinder", () -> new BlockItem(CYLINDER.get(), Registration.createStandardProperties()));

    public static final RegistryObject<BaseBlock> SMALL_CYLINDER = BLOCKS.register("small_cylinder", () -> new CylinderBlock(.3f));
    public static final RegistryObject<Item> SMALL_CYLINDER_ITEM = ITEMS.register("small_cylinder", () -> new BlockItem(SMALL_CYLINDER.get(), Registration.createStandardProperties()));

    public static final RegistryObject<BaseBlock> SOLID_CYLINDER = BLOCKS.register("solid_cylinder", () -> new SolidCylinderBlock(.8f));
    public static final RegistryObject<Item> SOLID_CYLINDER_ITEM = ITEMS.register("solid_cylinder", () -> new BlockItem(SOLID_CYLINDER.get(), Registration.createStandardProperties()));

    public static final RegistryObject<BaseBlock> SOLID_SMALL_CYLINDER = BLOCKS.register("solid_small_cylinder", () -> new SolidCylinderBlock(.3f));
    public static final RegistryObject<Item> SOLID_SMALL_CYLINDER_ITEM = ITEMS.register("solid_small_cylinder", () -> new BlockItem(SOLID_SMALL_CYLINDER.get(), Registration.createStandardProperties()));

    public static final RegistryObject<BaseBlock> SOLID_BLOCK = BLOCKS.register("solid_block", SolidBlock::new);
    public static final RegistryObject<Item> SOLID_BLOCK_ITEM = ITEMS.register("solid_block", () -> new BlockItem(SOLID_BLOCK.get(), Registration.createStandardProperties()));

    public static final RegistryObject<TileEntityType<GenericParticleTileEntity>> TYPE_PARTICLE = TILES.register("generic_particle", () -> TileEntityType.Builder.of(GenericParticleTileEntity::new,
            CYLINDER.get(), SMALL_CYLINDER.get(), SOLID_CYLINDER.get(), SOLID_SMALL_CYLINDER.get(), SOLID_BLOCK.get()).build(null));
}
