package cech12.extendedmushrooms.blockentity;

import cech12.extendedmushrooms.init.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class MushroomHangingSignBlockEntity extends HangingSignBlockEntity {

    public MushroomHangingSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Nonnull
    @Override
    public BlockEntityType<MushroomHangingSignBlockEntity> getType() {
        return ModBlockEntityTypes.MUSHROOM_HANGING_SIGN.get();
    }

}
