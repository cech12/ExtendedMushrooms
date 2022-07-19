package cech12.extendedmushrooms.blockentity;

import cech12.extendedmushrooms.init.ModBlockEntityTypes;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class VariantChestBlockEntity extends ChestBlockEntity {

    public MushroomWoodType woodType;

    public VariantChestBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        super(ModBlockEntityTypes.VARIANT_CHEST.get(), pos, state);
    }

    public VariantChestBlockEntity(MushroomWoodType woodType, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        this(pos, state);
        this.woodType = woodType;
    }

}
