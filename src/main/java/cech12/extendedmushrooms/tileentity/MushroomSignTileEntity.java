package cech12.extendedmushrooms.tileentity;

import cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

public class MushroomSignTileEntity extends SignTileEntity {

    @Nonnull
    @Override
    public TileEntityType<MushroomSignTileEntity> getType() {
        return (TileEntityType<MushroomSignTileEntity>) ExtendedMushroomsTileEntities.MUSHROOM_SIGN;
    }

}
