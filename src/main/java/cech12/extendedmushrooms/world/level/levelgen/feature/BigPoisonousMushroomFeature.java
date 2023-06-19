package cech12.extendedmushrooms.world.level.levelgen.feature;

import cech12.extendedmushrooms.world.level.levelgen.feature.configurations.ExtendedMushroomFeatureConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;

public class BigPoisonousMushroomFeature extends SingleBigMushroomFeature {

    public BigPoisonousMushroomFeature(Codec<ExtendedMushroomFeatureConfiguration> config) {
        super(config);
    }

    @Override
    protected int getSize(RandomSource random) {
        return (int) (super.getSize(random) * 1.2D); //a bit taller than a normal big mushroom
    }

    @Override
    protected int getCapRadius(RandomSource random) {
        if (random.nextInt(12) == 0) {
            return 3;
        }
        return 2;
    }

    @Override
    protected boolean canPlaceCap(LevelAccessor level, BlockPos blockPos, int size, int capRadius, BlockPos.MutableBlockPos mutableBlockPos, ExtendedMushroomFeatureConfiguration config) {
        for (int x = -capRadius; x <= capRadius; ++x) {
            for (int z = -capRadius; z <= capRadius; ++z) {
                mutableBlockPos.set(blockPos).move(x, size, z);
                if (!isReplaceable(level, mutableBlockPos)) {
                    return false;
                }
                mutableBlockPos.move(Direction.DOWN, 1);
                if (!isReplaceable(level, mutableBlockPos)) {
                    return false;
                }
                mutableBlockPos.move(Direction.DOWN, 1);
                if (!isReplaceable(level, mutableBlockPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void placeCap(LevelAccessor level, RandomSource random, BlockPos blockPos, int size, int capRadius, BlockPos.MutableBlockPos mutableBlockPos, ExtendedMushroomFeatureConfiguration config) {
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
        //layer below second layer: outer circle
        for (int i = 0; i < sideLength; i++) {
            boolean begin = i == 0;
            boolean end = i == sideLength - 1;
            this.placeCapBlockIfPossible(level, random, config, mutableBlockPos.set(blockPos).move(i - capRadius+1, size - 2, -capRadius), begin, end, true, false, false);
            this.placeCapBlockIfPossible(level, random, config, mutableBlockPos.set(blockPos).move(i - capRadius+1, size - 2, capRadius), begin, end, false, true, false);
            this.placeCapBlockIfPossible(level, random, config, mutableBlockPos.set(blockPos).move(-capRadius, size - 2, i - capRadius+1), true, false, begin, end, false);
            this.placeCapBlockIfPossible(level, random, config, mutableBlockPos.set(blockPos).move(capRadius, size - 2, i - capRadius+1), false, true, begin, end, false);
        }
    }
}
