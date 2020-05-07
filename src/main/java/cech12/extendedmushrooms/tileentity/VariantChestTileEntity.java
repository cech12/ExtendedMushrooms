package cech12.extendedmushrooms.tileentity;

import cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.tileentity.ChestTileEntity;

public class VariantChestTileEntity extends ChestTileEntity {

    public MushroomWoodType woodType;

    public VariantChestTileEntity() {
        super(ExtendedMushroomsTileEntities.VARIANT_CHEST);
    }

    public VariantChestTileEntity(MushroomWoodType woodType) {
        this();
        this.woodType = woodType;
    }

}
