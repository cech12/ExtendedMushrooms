package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public abstract class SingleBigMushroomFeature extends BigMushroomFeature {

    public SingleBigMushroomFeature(Codec<BigMushroomFeatureConfig> config) {
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

    protected boolean canGrow(IWorld world, BlockPos blockPos, int size, int capRadius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        return isInWorldBounds(world, blockPos, size)
                && this.hasValidGround(world, blockPos)
                && canPlaceTrunk(world, blockPos, size, mutableBlockPos, config)
                && canPlaceCap(world, blockPos, size, capRadius, mutableBlockPos, config);
    }

    protected abstract boolean canPlaceCap(IWorld level, BlockPos blockPos, int size, int capRadius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config);

    protected abstract void placeCap(IWorld level, Random random, BlockPos blockPos, int size, int capRadius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config);

    protected boolean canPlaceTrunk(IWorld level, BlockPos blockPos, int size, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        for(int i = 0; i < size; ++i) {
            mutableBlockPos.set(blockPos).move(Direction.UP, i);
            if (!level.getBlockState(mutableBlockPos).canBeReplacedByLogs(level, mutableBlockPos)) {
                return false;
            }
        }
        return true;
    }

    protected void placeTrunk(IWorld level, Random random, BlockPos blockPos, BigMushroomFeatureConfig config, int size, BlockPos.Mutable mutableBlockPos) {
        for(int i = 0; i < size; ++i) {
            mutableBlockPos.set(blockPos).move(Direction.UP, i);
            placeTrunkBlockIfPossible(level, random, config, mutableBlockPos);
        }
    }

    @Override
    public boolean place(@Nonnull ISeedReader level, @Nullable ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BigMushroomFeatureConfig config) {
        int size = this.getSize(rand);
        int capRadius = this.getCapRadius(rand);
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable();
        if (!this.canGrow(level, pos, size, capRadius, mutableBlockPos, config)) {
            return false;
        } else {
            this.placeCap(level, rand, pos, size, capRadius, mutableBlockPos, config);
            this.placeTrunk(level, rand, pos, config, size, mutableBlockPos);
            return true;
        }
    }

}
