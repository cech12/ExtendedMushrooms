package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

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
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 30;
    }

}
