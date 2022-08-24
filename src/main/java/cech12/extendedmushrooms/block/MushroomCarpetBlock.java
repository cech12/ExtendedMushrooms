package cech12.extendedmushrooms.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

public class MushroomCarpetBlock extends WoolCarpetBlock {

    /**
     * CarpetBlock has protected constructor.
     */
    public MushroomCarpetBlock(DyeColor color, Properties properties) {
        super(color, properties);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 20;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }

}
