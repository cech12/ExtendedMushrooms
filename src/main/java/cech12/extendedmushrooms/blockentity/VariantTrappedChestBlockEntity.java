package cech12.extendedmushrooms.blockentity;

import cech12.extendedmushrooms.init.ModBlockEntityTypes;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class VariantTrappedChestBlockEntity extends ChestBlockEntity {

    public MushroomWoodType woodType;

    public VariantTrappedChestBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        super(ModBlockEntityTypes.VARIANT_TRAPPED_CHEST.get(), pos, state);
    }

    public VariantTrappedChestBlockEntity(MushroomWoodType woodType, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        this(pos, state);
        this.woodType = woodType;
    }

    @Override
    protected void signalOpenCount(@Nonnull Level level, @Nonnull BlockPos blockPos, @Nonnull BlockState blockState, int p_155868_, int p_155869_) {
        super.signalOpenCount(level, blockPos, blockState, p_155868_, p_155869_);
        if (p_155868_ != p_155869_) {
            Block block = blockState.getBlock();
            level.updateNeighborsAt(blockPos, block);
            level.updateNeighborsAt(blockPos.below(), block);
        }
    }

}
