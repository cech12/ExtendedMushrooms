package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

import java.util.Random;

public class BigGlowshroomFeature extends SingleBigMushroomFeature {

    public BigGlowshroomFeature(Codec<BigMushroomFeatureConfig> config) {
        super(config);
    }

    @Override
    protected int getCapRadius(Random random) {
        if (random.nextInt(12) == 0) {
            return 3;
        }
        return 2;
    }

    @Override
    protected boolean canPlaceCap(IWorld level, BlockPos blockPos, int size, int capRadius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        for (int x = -capRadius; x <= capRadius; ++x) {
            for (int z = -capRadius; z <= capRadius; ++z) {
                mutableBlockPos.set(blockPos).move(x, size, z);
                if (!level.getBlockState(mutableBlockPos).canBeReplacedByLeaves(level, mutableBlockPos)) {
                    return false;
                }
                mutableBlockPos.move(Direction.DOWN, 1);
                if (!level.getBlockState(mutableBlockPos).canBeReplacedByLeaves(level, mutableBlockPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void placeCap(IWorld level, Random random, BlockPos blockPos, int size, int capRadius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        //top layer: "radius-1" blocks in each direction
        int topRadius = capRadius - 1;
        for(int x = -topRadius; x <= topRadius; ++x) {
            for (int z = -topRadius; z <= topRadius; ++z) {
                boolean flag = x == -topRadius;
                boolean flag1 = x == topRadius;
                boolean flag2 = z == -topRadius;
                boolean flag3 = z == topRadius;
                this.placeCapBlockIfPossible(level, random, config, mutableBlockPos.set(blockPos).move(x, size, z), flag, flag1, flag2, flag3);
            }
        }
        //layer below top: outer circle ("radius" away in each direction without corners
        int sideLength = topRadius*2 + 1;
        for (int i = 0; i < sideLength; i++) {
            boolean begin = i == 0;
            boolean end = i == sideLength - 1;
            this.placeCapBlockIfPossible(level, random, config, mutableBlockPos.set(blockPos).move(i - capRadius+1, size - 1, -capRadius), begin, end, true, false);
            this.placeCapBlockIfPossible(level, random, config, mutableBlockPos.set(blockPos).move(i - capRadius+1, size - 1, capRadius), begin, end, false, true);
            this.placeCapBlockIfPossible(level, random, config, mutableBlockPos.set(blockPos).move(-capRadius, size - 1, i - capRadius+1), true, false, begin, end);
            this.placeCapBlockIfPossible(level, random, config, mutableBlockPos.set(blockPos).move(capRadius, size - 1, i - capRadius+1), false, true, begin, end);
        }
    }
}
