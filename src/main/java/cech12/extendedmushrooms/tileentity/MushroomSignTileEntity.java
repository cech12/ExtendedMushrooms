package cech12.extendedmushrooms.tileentity;

import cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class MushroomSignTileEntity extends SignBlockEntity {

    public MushroomSignTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Nonnull
    @Override
    public BlockEntityType<MushroomSignTileEntity> getType() {
        return (BlockEntityType<MushroomSignTileEntity>) ExtendedMushroomsTileEntities.MUSHROOM_SIGN;
    }

}
