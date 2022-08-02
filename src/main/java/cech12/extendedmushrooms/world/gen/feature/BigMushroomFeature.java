package cech12.extendedmushrooms.world.gen.feature;

import cech12.extendedmushrooms.MushroomUtils;
import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.material.Material;

public abstract class BigMushroomFeature extends Feature<HugeMushroomFeatureConfiguration> {

    public BigMushroomFeature(Codec<HugeMushroomFeatureConfiguration> config) {
        super(config);
    }


    protected boolean isReplaceable(LevelAccessor level, BlockPos blockPos, boolean isTrunk) {
        return level.isStateAtPosition(blockPos, (p_65966_) -> {
            Material material = p_65966_.getMaterial();
            return p_65966_.getMaterial().isReplaceable() || isTrunk && material == Material.PLANT;
        });
    }

    protected boolean isInWorldBounds(LevelAccessor level, BlockPos mushroomPos, int size) {
        int y = mushroomPos.getY();
        return y >= level.getMinBuildHeight() && y + size + 1 < level.getMaxBuildHeight();
    }

    protected boolean hasValidGround(LevelAccessor world, BlockPos mushroomPos) {
        return MushroomUtils.isValidMushroomPosition(world, mushroomPos);
    }

    protected void placeTrunkBlockIfPossible(LevelAccessor level, RandomSource random, HugeMushroomFeatureConfiguration config, BlockPos blockPos) {
        this.placeTrunkBlockIfPossible(level, random, config, blockPos, false, false);
    }

    protected void placeTrunkBlockIfPossible(LevelAccessor level, RandomSource random, HugeMushroomFeatureConfiguration config, BlockPos blockPos, boolean up, boolean down) {
        if (isReplaceable(level, blockPos, true)) {
            this.setBlock(level, blockPos, config.stemProvider.getState(random, blockPos).setValue(HugeMushroomBlock.UP, up).setValue(HugeMushroomBlock.DOWN, down));
        }
    }

    protected void placeCapBlockIfPossible(LevelAccessor level, RandomSource random, HugeMushroomFeatureConfiguration config, BlockPos blockPos, boolean west, boolean east, boolean north, boolean south) {
        this.placeCapBlockIfPossible(level, random, config, blockPos, west, east, north, south, true);
    }

    protected void placeCapBlockIfPossible(LevelAccessor level, RandomSource random, HugeMushroomFeatureConfiguration config, BlockPos blockPos, boolean west, boolean east, boolean north, boolean south, boolean up) {
        if (isReplaceable(level, blockPos, false)) {
            this.setBlock(level, blockPos, config.capProvider.getState(random, blockPos).setValue(HugeMushroomBlock.WEST, west).setValue(HugeMushroomBlock.EAST, east).setValue(HugeMushroomBlock.NORTH, north).setValue(HugeMushroomBlock.SOUTH, south).setValue(HugeMushroomBlock.UP, up));
        }
    }

}
