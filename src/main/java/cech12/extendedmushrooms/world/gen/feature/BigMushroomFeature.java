package cech12.extendedmushrooms.world.gen.feature;

import cech12.extendedmushrooms.MushroomUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public abstract class BigMushroomFeature extends Feature<BigMushroomFeatureConfig> {

    public BigMushroomFeature(Codec<BigMushroomFeatureConfig> config) {
        super(config);
    }

    protected boolean isInWorldBounds(IWorld world, BlockPos mushroomPos, int size) {
        int y = mushroomPos.getY();
        return y >= 1 && y + size + 1 < world.getHeight(); //TODO 1.18 negative height
    }

    protected boolean hasValidGround(IWorld world, BlockPos mushroomPos) {
        return MushroomUtils.isValidMushroomPosition(world, mushroomPos);
    }

    protected void placeTrunkBlockIfPossible(IWorld level, Random random, BigMushroomFeatureConfig config, BlockPos blockPos) {
        this.placeTrunkBlockIfPossible(level, random, config, blockPos, false, false);
    }

    protected void placeTrunkBlockIfPossible(IWorld level, Random random, BigMushroomFeatureConfig config, BlockPos blockPos, boolean up, boolean down) {
        if (level.getBlockState(blockPos).canBeReplacedByLogs(level, blockPos)) {
            this.setBlock(level, blockPos, config.stemProvider.getState(random, blockPos).setValue(HugeMushroomBlock.UP, up).setValue(HugeMushroomBlock.DOWN, down));
        }
    }

    protected void placeCapBlockIfPossible(IWorld level, Random random, BigMushroomFeatureConfig config, BlockPos blockPos, boolean west, boolean east, boolean north, boolean south) {
        this.placeCapBlockIfPossible(level, random, config, blockPos, west, east, north, south, true);
    }

    protected void placeCapBlockIfPossible(IWorld level, Random random, BigMushroomFeatureConfig config, BlockPos blockPos, boolean west, boolean east, boolean north, boolean south, boolean up) {
        if (level.getBlockState(blockPos).canBeReplacedByLeaves(level, blockPos)) {
            this.setBlock(level, blockPos, config.capProvider.getState(random, blockPos).setValue(HugeMushroomBlock.WEST, west).setValue(HugeMushroomBlock.EAST, east).setValue(HugeMushroomBlock.NORTH, north).setValue(HugeMushroomBlock.SOUTH, south).setValue(HugeMushroomBlock.UP, up));
        }
    }

}
