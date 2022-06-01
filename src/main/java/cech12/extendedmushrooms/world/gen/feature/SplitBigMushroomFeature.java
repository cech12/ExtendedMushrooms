package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class SplitBigMushroomFeature extends BigMushroomFeature {

    public SplitBigMushroomFeature(Codec<BigMushroomFeatureConfig> config) {
        super(config);
    }

    protected abstract int getSize(Random random);
    protected abstract int getCapRadius(Random random);

    protected abstract int getSmallSize(Random random);
    protected abstract int getSmallCapRadius(Random random);

    protected abstract boolean canPlaceCap(IWorld level, BlockPos center, BigMushroomFeatureConfig config, int capRadius, BlockPos.Mutable mutableBlockPos);

    protected abstract void placeCap(IWorld level, Random random, BlockPos center, BigMushroomFeatureConfig config, int capRadius, BlockPos.Mutable mutableBlockPos);

    protected boolean canPlaceCaps(IWorld level, BigMushroomFeatureConfig config, Pair<BlockPos, Integer>[] capPairs, BlockPos.Mutable mutableBlockPos) {
        AtomicBoolean canPlaceCaps = new AtomicBoolean(true);
        Arrays.stream(capPairs).forEach(pair -> canPlaceCaps.set(canPlaceCaps.get() && canPlaceCap(level, pair.getLeft(), config, pair.getRight(), mutableBlockPos)));
        return canPlaceCaps.get();
    }

    protected void placeCaps(IWorld level, Random random, BigMushroomFeatureConfig config, Pair<BlockPos, Integer>[] capPairs, BlockPos.Mutable mutableBlockPos) {
        Arrays.stream(capPairs).forEach(pair -> placeCap(level, random, pair.getLeft(), config, pair.getRight(), mutableBlockPos));
    }

    protected Direction[] getDirections(Random random) {
        ArrayList<Direction> directions = new ArrayList<>();
        //add 2 different directions
        while (directions.size() < 2) {
            Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            if (!directions.contains(direction)) {
                directions.add(direction);
            }
        }
        //add 3rd direction if first 2 are not opposite & a random value is found.
        if (directions.get(1).getOpposite() != directions.get(0) && random.nextInt(12) == 0) {
            directions.add(directions.get(1).getOpposite());
        }
        return directions.toArray(new Direction[0]);
    }

    protected Triple<Direction, Integer, Integer>[] getTrunkTriples(Direction[] directions, Random random) {
        Triple<Direction, Integer, Integer>[] triples = new Triple[directions.length];
        for (int i = 0; i < directions.length; i++) {
            triples[i] = Triple.of(directions[i], i == 0 ? getSize(random) : getSmallSize(random), getDistanceToCenter(random));
        }
        return triples;
    }

    protected Pair<BlockPos, Integer>[] getCapPairs(BlockPos mushroomPos, Triple<Direction, Integer, Integer>[] trunkTriples, Random random) {
        Pair<BlockPos, Integer>[] pairs = new Pair[trunkTriples.length];
        for (int i = 0; i < trunkTriples.length; i++) {
            pairs[i] = Pair.of(getCapCenter(mushroomPos, trunkTriples[i]), i == 0 ? getCapRadius(random) : getSmallCapRadius(random));
        }
        return pairs;
    }

    protected int getDistanceToCenter(Random random) {
        return 2 + random.nextInt(1);
    }

    protected BlockPos getCapCenter(BlockPos mushroomPos, Triple<Direction, Integer, Integer> trunkTriple) {
        return new BlockPos.Mutable().set(mushroomPos).move(Direction.UP, trunkTriple.getMiddle()).move(trunkTriple.getLeft(), trunkTriple.getRight()).immutable();
    }

    protected boolean canPlaceTrunks(IWorld level, BlockPos blockPos, BigMushroomFeatureConfig config, Triple<Direction, Integer, Integer>[] directions, BlockPos.Mutable mutableBlockPos) {
        AtomicBoolean canPlaceTrunks = new AtomicBoolean(true);
        Arrays.stream(directions).forEach(triple -> canPlaceTrunks.set(canPlaceTrunks.get() && canPlaceTrunk(level, blockPos, config, triple.getLeft(), triple.getMiddle(), triple.getRight(), mutableBlockPos)));
        return canPlaceTrunks.get();
    }

    protected boolean canPlaceTrunk(IWorld level, BlockPos blockPos, BigMushroomFeatureConfig config, Direction direction, int size, int distanceToCenter, BlockPos.Mutable mutableBlockPos) {
        mutableBlockPos.set(blockPos);
        for (int i = 0; i < size; ++i) {
            if (i > 0) {
                if (i <= distanceToCenter) {
                    mutableBlockPos.move(direction);
                }
                mutableBlockPos.move(Direction.UP);
            }
            if (!level.getBlockState(mutableBlockPos).canBeReplacedByLogs(level, mutableBlockPos)) {
                return false;
            }
        }
        return true;
    }

    protected void placeTrunks(IWorld level, Random random, BlockPos blockPos, BigMushroomFeatureConfig config, Triple<Direction, Integer, Integer>[] directions, BlockPos.Mutable mutableBlockPos) {
        Arrays.stream(directions).forEach(triple -> placeTrunk(level, random, blockPos, config, triple.getLeft(), triple.getMiddle(), triple.getRight(), mutableBlockPos));
    }

    protected void placeTrunk(IWorld level, Random random, BlockPos blockPos, BigMushroomFeatureConfig config, Direction direction, int size, int distanceToCenter, BlockPos.Mutable mutableBlockPos) {
        mutableBlockPos.set(blockPos);
        for (int i = 0; i < size; ++i) {
            boolean up = i == 0;
            boolean down = i == distanceToCenter;
            if (i > 0) {
                if (i <= distanceToCenter) {
                    up = true;
                    down = true;
                    mutableBlockPos.move(direction);
                }
                mutableBlockPos.move(Direction.UP);
            }
            placeTrunkBlockIfPossible(level, random, config, mutableBlockPos, up, down);
        }
    }

    @Override
    public boolean place(@Nonnull ISeedReader level, @Nullable ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BigMushroomFeatureConfig config) {
        Direction[] directions = this.getDirections(rand);
        Triple<Direction, Integer, Integer>[] trunkTriples = this.getTrunkTriples(directions, rand);
        Pair<BlockPos, Integer>[] capPairs = this.getCapPairs(pos, trunkTriples, rand);
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable();
        if (isInWorldBounds(level, pos, trunkTriples[0].getMiddle())
                && hasValidGround(level, pos)
                && canPlaceTrunks(level, pos, config, trunkTriples, mutableBlockPos)
                && canPlaceCaps(level, config, capPairs, mutableBlockPos)) {
            this.placeTrunks(level, rand, pos, config, trunkTriples, mutableBlockPos);
            this.placeCaps(level, rand, config, capPairs, mutableBlockPos);
            return true;
        }
        return false;
    }

}
