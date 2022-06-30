package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.tileentity.MushroomSignTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nonnull;

public class MushroomWallSignBlock extends WallSignBlock {

    public MushroomWallSignBlock(BlockBehaviour.Properties propertiesIn, WoodType woodTypeIn) {
        super(propertiesIn, woodTypeIn);
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new MushroomSignTileEntity(pos, state);
    }
}
