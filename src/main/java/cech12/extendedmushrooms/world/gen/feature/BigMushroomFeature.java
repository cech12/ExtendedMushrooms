package cech12.extendedmushrooms.world.gen.feature;

import cech12.extendedmushrooms.MushroomUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public abstract class BigMushroomFeature extends Feature<BigMushroomFeatureConfig> {

    public BigMushroomFeature(Codec<BigMushroomFeatureConfig> config) {
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

    protected boolean isInWorldBounds(IWorld world, BlockPos mushroomPos, int size) {
        int y = mushroomPos.getY();
        return y >= 1 && y + size + 1 < world.getHeight(); //getMaxHeight
    }

    protected boolean hasValidGround(IWorld world, BlockPos mushroomPos) {
        return MushroomUtils.isValidMushroomPosition(world, mushroomPos);
    }

    protected boolean canGrow(IWorld world, BlockPos blockPos, int size, int capRadius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        return isInWorldBounds(world, blockPos, size)
                && this.hasValidGround(world, blockPos)
                && canPlaceTrunk(world, blockPos, size, mutableBlockPos, config)
                && canPlaceCap(world, blockPos, size, capRadius, mutableBlockPos, config);
    }

    protected abstract boolean canPlaceCap(IWorld world, BlockPos blockPos, int size, int capRadius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config);

    protected abstract void placeCap(IWorld world, Random random, BlockPos blockPos, int size, int capRadius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config);

    protected boolean canPlaceTrunk(IWorld world, BlockPos blockPos, int size, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        for(int i = 0; i < size; ++i) {
            mutableBlockPos.set(blockPos).move(Direction.UP, i);
            if (!world.getBlockState(mutableBlockPos).canBeReplacedByLogs(world, mutableBlockPos)) {
                return false;
            }
        }
        return true;
    }

    protected void placeTrunk(IWorld world, Random random, BlockPos blockPos, BigMushroomFeatureConfig config, int size, BlockPos.Mutable mutableBlockPos) {
        for(int i = 0; i < size; ++i) {
            mutableBlockPos.set(blockPos).move(Direction.UP, i);
            if (world.getBlockState(mutableBlockPos).canBeReplacedByLogs(world, mutableBlockPos)) {
                this.setBlock(world, mutableBlockPos, config.stemProvider.getState(random, blockPos));
            }
        }
    }

    @Override
    public boolean place(@Nonnull ISeedReader worldIn, @Nullable ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BigMushroomFeatureConfig config) {
        int size = this.getSize(rand);
        int capRadius = this.getCapRadius(rand);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        if (!this.canGrow(worldIn, pos, size, capRadius, blockpos$mutable, config)) {
            return false;
        } else {
            this.placeCap(worldIn, rand, pos, size, capRadius, blockpos$mutable, config);
            this.placeTrunk(worldIn, rand, pos, config, size, blockpos$mutable);
            return true;
        }
    }

    protected void placeCapBlockIfPossible(IWorld world, Random random, BigMushroomFeatureConfig config, BlockPos blockPos, boolean west, boolean east, boolean north, boolean south) {
        this.placeCapBlockIfPossible(world, random, config, blockPos, west, east, north, south, true);
    }

    protected void placeCapBlockIfPossible(IWorld world, Random random, BigMushroomFeatureConfig config, BlockPos blockPos, boolean west, boolean east, boolean north, boolean south, boolean up) {
        if (world.getBlockState(blockPos).canBeReplacedByLeaves(world, blockPos)) {
            this.setBlock(world, blockPos, config.capProvider.getState(random, blockPos).setValue(HugeMushroomBlock.WEST, west).setValue(HugeMushroomBlock.EAST, east).setValue(HugeMushroomBlock.NORTH, north).setValue(HugeMushroomBlock.SOUTH, south).setValue(HugeMushroomBlock.UP, up));
        }
    }

}
