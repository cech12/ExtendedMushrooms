package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.blockentity.VariantChestBlockEntity;
import cech12.extendedmushrooms.init.ModBlockEntityTypes;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class VariantChestBlock extends ChestBlock {

    private final MushroomWoodType woodType;

    public VariantChestBlock(MushroomWoodType woodType, Properties p_i225757_1_) {
        super(p_i225757_1_, () -> ModBlockEntityTypes.VARIANT_CHEST.get());
        this.woodType = woodType;
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new VariantChestBlockEntity(pos, state);
    }

    public MushroomWoodType getWoodType() {
        return this.woodType;
    }

}
