package cech12.extendedmushrooms.tileentity;

import cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class VariantChestTileEntity extends ChestBlockEntity {

    public MushroomWoodType woodType;

    public VariantChestTileEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        super(ExtendedMushroomsTileEntities.VARIANT_CHEST, pos, state);
    }

    public VariantChestTileEntity(MushroomWoodType woodType, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        this(pos, state);
        this.woodType = woodType;
    }

}
