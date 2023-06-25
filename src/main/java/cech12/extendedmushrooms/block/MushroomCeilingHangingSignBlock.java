package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.blockentity.MushroomHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import javax.annotation.Nonnull;

public class MushroomCeilingHangingSignBlock extends CeilingHangingSignBlock {

    public MushroomCeilingHangingSignBlock(Properties propertiesIn, WoodType woodTypeIn) {
        super(propertiesIn, woodTypeIn);
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new MushroomHangingSignBlockEntity(pos, state);
    }
}
