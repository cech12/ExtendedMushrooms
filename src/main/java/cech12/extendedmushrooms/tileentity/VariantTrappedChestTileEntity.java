package cech12.extendedmushrooms.tileentity;

import cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.tileentity.ChestTileEntity;

public class VariantTrappedChestTileEntity extends ChestTileEntity {

    public MushroomWoodType woodType;

    public VariantTrappedChestTileEntity() {
        super(ExtendedMushroomsTileEntities.VARIANT_TRAPPED_CHEST);
    }

    public VariantTrappedChestTileEntity(MushroomWoodType woodType) {
        this();
        this.woodType = woodType;
    }

    protected void signalOpenCount() {
        super.signalOpenCount();
        this.level.updateNeighborsAt(this.worldPosition.below(), this.getBlockState().getBlock());
    }

}
