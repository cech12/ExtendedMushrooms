package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

/**
 * To make a difference for data generation.
 * Only mushroom stem blocks should be an object of this class. Stripped variants not!
 */
public class MushroomStemBlock extends HugeMushroomBlock {

    private final MushroomWoodType type;

    public MushroomStemBlock(MushroomWoodType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public MushroomWoodType getType() {
        return this.type;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 5;
    }

}
