package cech12.extendedmushrooms.world.level.levelgen.feature;

import cech12.extendedmushrooms.world.level.levelgen.feature.configurations.ExtendedMushroomFeatureConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

public class BigHoneyFungusFeature extends SplitBigMushroomFeature {

    public BigHoneyFungusFeature(Codec<ExtendedMushroomFeatureConfiguration> config) {
        super(config);
    }

    @Override
    protected int getSize(RandomSource random) {
        int size = 9;
        if (random.nextInt(12) == 0) {
            size = 13;
        }
        return size + random.nextInt(2);
    }

    @Override
    protected int getCapRadius(RandomSource random) {
        if (random.nextInt(12) == 0) {
            return 4;
        }
        return 3;
    }

    @Override
    protected int getSmallSize(RandomSource random) {
        return 6 + random.nextInt(1);
    }

    @Override
    protected int getSmallCapRadius(RandomSource random) {
        return 1;
    }

    @Override
    protected boolean canPlaceCap(LevelAccessor level, BlockPos center, ExtendedMushroomFeatureConfiguration config, int capRadius, BlockPos.MutableBlockPos mutableBlockPos) {
        boolean hasCorners = capRadius > 2;
        for (int x = -capRadius; x <= capRadius; ++x) {
            for (int z = -capRadius; z <= capRadius; ++z) {
                if (hasCorners && (x == capRadius || x == -capRadius) && (z == capRadius || z == -capRadius)) {
                    continue;
                }
                mutableBlockPos.set(center).move(x, 0, z);
                if (!isReplaceable(level, mutableBlockPos, false)) {
                    return false;
                }
            }
        }
        int lowerCapRadius = 1;
        boolean hasLowerCorners = !hasCorners;
        for (int x = -lowerCapRadius; x <= lowerCapRadius; ++x) {
            for (int z = -lowerCapRadius; z <= lowerCapRadius; ++z) {
                if ((x == 0 && z == 0) || hasLowerCorners && (x == lowerCapRadius || x == -lowerCapRadius) && (z == lowerCapRadius || z == -lowerCapRadius)) {
                    continue;
                }
                mutableBlockPos.set(center).move(x, -2, z);
                if (!isReplaceable(level, mutableBlockPos, false)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void placeCap(LevelAccessor level, RandomSource random, BlockPos center, ExtendedMushroomFeatureConfiguration config, int capRadius, BlockPos.MutableBlockPos mutableBlockPos) {
        boolean hasCorners = capRadius > 2;
        for (int x = -capRadius; x <= capRadius; ++x) {
            for (int z = -capRadius; z <= capRadius; ++z) {
                if (hasCorners && (x == capRadius || x == -capRadius) && (z == capRadius || z == -capRadius)) {
                    continue;
                }
                boolean flag = x == -capRadius || hasCorners && x == -capRadius + 1;
                boolean flag1 = x == capRadius || hasCorners && x == capRadius - 1;
                boolean flag2 = z == -capRadius || hasCorners && z == -capRadius + 1;
                boolean flag3 = z == capRadius || hasCorners && z == capRadius - 1;
                this.placeCapBlockIfPossible(level, random, config, mutableBlockPos.set(center).move(x, 0, z), flag, flag1, flag2, flag3);
            }
        }
        int lowerCapRadius = Math.max(capRadius / 2, 1);
        boolean hasLowerCorners = !hasCorners || lowerCapRadius > 1;
        for (int x = -lowerCapRadius; x <= lowerCapRadius; ++x) {
            for (int z = -lowerCapRadius; z <= lowerCapRadius; ++z) {
                if ((x == 0 && z == 0) || hasLowerCorners && (x == lowerCapRadius || x == -lowerCapRadius) && (z == lowerCapRadius || z == -lowerCapRadius)) {
                    continue;
                }
                boolean flag = x == -lowerCapRadius || hasLowerCorners && x == -lowerCapRadius + 1;
                boolean flag1 = x == lowerCapRadius || hasLowerCorners && x == lowerCapRadius - 1;
                boolean flag2 = z == -lowerCapRadius || hasLowerCorners && z == -lowerCapRadius + 1;
                boolean flag3 = z == lowerCapRadius || hasLowerCorners && z == lowerCapRadius - 1;
                this.placeCapBlockIfPossible(level, random, config, mutableBlockPos.set(center).move(x, -2, z), flag, flag1, flag2, flag3);
            }
        }
    }
}
