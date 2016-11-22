package mcjty.nice.blocks;

import mcjty.nice.Nice;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

public class AltarCenterBlock extends GenericBlock implements ITileEntityProvider {

    public AltarCenterBlock() {
        super("altarcenter", Material.ROCK);
        setHardness(3.0f);
        setResistance(5.0f);
        setSoundType(SoundType.STONE);
        GameRegistry.registerTileEntity(AltarCenterTileEntity.class, Nice.MODID + "_altarcenter");
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new AltarCenterTileEntity();
    }
}
