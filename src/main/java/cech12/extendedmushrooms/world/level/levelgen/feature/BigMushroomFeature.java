package cech12.extendedmushrooms.world.level.levelgen.feature;

import cech12.extendedmushrooms.MushroomUtils;
import cech12.extendedmushrooms.world.level.levelgen.feature.configurations.ExtendedMushroomFeatureConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.Feature;

public abstract class BigMushroomFeature extends Feature<ExtendedMushroomFeatureConfiguration> {

    public BigMushroomFeature(Codec<ExtendedMushroomFeatureConfiguration> config) {
        super(config);
    }


    protected boolean isReplaceable(LevelAccessor level, BlockPos blockPos) {
        return level.isStateAtPosition(blockPos, (p_65966_) -> !p_65966_.isSolidRender(level, blockPos));
    }

    protected boolean isInWorldBounds(LevelAccessor level, BlockPos mushroomPos, int size) {
        int y = mushroomPos.getY();
        return y >= level.getMinBuildHeight() && y + size + 1 < level.getMaxBuildHeight();
    }

    protected boolean hasValidGround(LevelAccessor world, BlockPos mushroomPos) {
        return MushroomUtils.isValidMushroomPosition(world, mushroomPos);
    }

    protected void placeTrunkBlockIfPossible(LevelAccessor level, RandomSource random, ExtendedMushroomFeatureConfiguration config, BlockPos blockPos) {
        this.placeTrunkBlockIfPossible(level, random, config, blockPos, false, false);
    }

    protected void placeTrunkBlockIfPossible(LevelAccessor level, RandomSource random, ExtendedMushroomFeatureConfiguration config, BlockPos blockPos, boolean up, boolean down) {
        if (isReplaceable(level, blockPos)) {
            this.setBlock(level, blockPos, config.stemProvider().getState(random, blockPos).setValue(HugeMushroomBlock.UP, up).setValue(HugeMushroomBlock.DOWN, down));
        }
    }

    protected void placeCapBlockIfPossible(LevelAccessor level, RandomSource random, ExtendedMushroomFeatureConfiguration config, BlockPos blockPos, boolean west, boolean east, boolean north, boolean south) {
        this.placeCapBlockIfPossible(level, random, config, blockPos, west, east, north, south, true);
    }

    protected void placeCapBlockIfPossible(LevelAccessor level, RandomSource random, ExtendedMushroomFeatureConfiguration config, BlockPos blockPos, boolean west, boolean east, boolean north, boolean south, boolean up) {
        if (isReplaceable(level, blockPos)) {
            this.setBlock(level, blockPos, config.capProvider().getState(random, blockPos).setValue(HugeMushroomBlock.WEST, west).setValue(HugeMushroomBlock.EAST, east).setValue(HugeMushroomBlock.NORTH, north).setValue(HugeMushroomBlock.SOUTH, south).setValue(HugeMushroomBlock.UP, up).setValue(HugeMushroomBlock.DOWN, false));
        }
    }

}
