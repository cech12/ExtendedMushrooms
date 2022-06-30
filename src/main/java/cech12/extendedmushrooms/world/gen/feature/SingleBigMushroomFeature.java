package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

import java.util.Random;

public abstract class SingleBigMushroomFeature extends BigMushroomFeature {

    public SingleBigMushroomFeature(Codec<HugeMushroomFeatureConfiguration> config) {
        super(config);
    }


    protected abstract int getCapRadius(Random random);

    protected int getSize(Random random) {
        int i = random.nextInt(3) + 4;
        if (random.nextInt(12) == 0) {
            i *= 2;
        }
        return i;
    }

    protected boolean canGrow(LevelAccessor world, BlockPos blockPos, int size, int capRadius, BlockPos.MutableBlockPos mutableBlockPos, HugeMushroomFeatureConfiguration config) {
        return isInWorldBounds(world, blockPos, size)
                && this.hasValidGround(world, blockPos)
                && canPlaceTrunk(world, blockPos, size, mutableBlockPos, config)
                && canPlaceCap(world, blockPos, size, capRadius, mutableBlockPos, config);
    }

    protected abstract boolean canPlaceCap(LevelAccessor level, BlockPos blockPos, int size, int capRadius, BlockPos.MutableBlockPos mutableBlockPos, HugeMushroomFeatureConfiguration config);

    protected abstract void placeCap(LevelAccessor level, Random random, BlockPos blockPos, int size, int capRadius, BlockPos.MutableBlockPos mutableBlockPos, HugeMushroomFeatureConfiguration config);

    protected boolean canPlaceTrunk(LevelAccessor level, BlockPos blockPos, int size, BlockPos.MutableBlockPos mutableBlockPos, HugeMushroomFeatureConfiguration config) {
        for (int i = 0; i < size; ++i) {
            mutableBlockPos.set(blockPos).move(Direction.UP, i);
            if (!isReplaceable(level, mutableBlockPos, true)) {
                return false;
            }
        }
        return true;
    }

    protected void placeTrunk(LevelAccessor level, Random random, BlockPos blockPos, HugeMushroomFeatureConfiguration config, int size, BlockPos.MutableBlockPos mutableBlockPos) {
        for(int i = 0; i < size; ++i) {
            mutableBlockPos.set(blockPos).move(Direction.UP, i);
            placeTrunkBlockIfPossible(level, random, config, mutableBlockPos);
        }
    }

    @Override
    public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> context) {
        int size = this.getSize(context.random());
        int capRadius = this.getCapRadius(context.random());
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        if (!this.canGrow(context.level(), context.origin(), size, capRadius, mutableBlockPos, context.config())) {
            return false;
        } else {
            this.placeCap(context.level(), context.random(), context.origin(), size, capRadius, mutableBlockPos, context.config());
            this.placeTrunk(context.level(), context.random(), context.origin(), context.config(), size, mutableBlockPos);
            return true;
        }
    }

}
