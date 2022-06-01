package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

import java.util.Random;

public class BigHoneyFungusFeature extends SplitBigMushroomFeature {

    public BigHoneyFungusFeature(Codec<BigMushroomFeatureConfig> config) {
        super(config);
    }

    @Override
    protected int getSize(Random random) {
        int size = 9;
        if (random.nextInt(12) == 0) {
            size = 13;
        }
        return size + random.nextInt(2);
    }

    @Override
    protected int getCapRadius(Random random) {
        if (random.nextInt(12) == 0) {
            return 4;
        }
        return 3;
    }

    @Override
    protected int getSmallSize(Random random) {
        return 6 + random.nextInt(1);
    }

    @Override
    protected int getSmallCapRadius(Random random) {
        return 1;
    }

    @Override
    protected boolean canPlaceCap(IWorld level, BlockPos center, BigMushroomFeatureConfig config, int capRadius, BlockPos.Mutable mutableBlockPos) {
        boolean hasCorners = capRadius > 2;
        for (int x = -capRadius; x <= capRadius; ++x) {
            for (int z = -capRadius; z <= capRadius; ++z) {
                if (hasCorners && (x == capRadius || x == -capRadius) && (z == capRadius || z == -capRadius)) {
                    continue;
                }
                mutableBlockPos.set(center).move(x, 0, z);
                if (!level.getBlockState(mutableBlockPos).canBeReplacedByLeaves(level, mutableBlockPos)) {
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
                if (!level.getBlockState(mutableBlockPos).canBeReplacedByLeaves(level, mutableBlockPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void placeCap(IWorld level, Random random, BlockPos center, BigMushroomFeatureConfig config, int capRadius, BlockPos.Mutable mutableBlockPos) {
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
