package cech12.extendedmushrooms.blockentity;

import cech12.extendedmushrooms.init.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class MushroomSignBlockEntity extends SignBlockEntity {

    public MushroomSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Nonnull
    @Override
    public BlockEntityType<MushroomSignBlockEntity> getType() {
        return ModBlockEntityTypes.MUSHROOM_SIGN.get();
    }

}
