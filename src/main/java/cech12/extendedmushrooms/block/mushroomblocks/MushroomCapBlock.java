package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

/**
 * To make a difference for data generation.
 * Only mushroom cap blocks should be an object of this class.
 */
public class MushroomCapBlock extends HugeMushroomBlock {

    private final MushroomType type;

    public MushroomCapBlock(MushroomType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public MushroomType getType() {
        return this.type;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 30;
    }

}
