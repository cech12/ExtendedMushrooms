package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class SplitBigMushroomFeature extends BigMushroomFeature {

    public SplitBigMushroomFeature(Codec<HugeMushroomFeatureConfiguration> config) {
        super(config);
    }

    protected abstract int getSize(RandomSource random);
    protected abstract int getCapRadius(RandomSource random);

    protected abstract int getSmallSize(RandomSource random);
    protected abstract int getSmallCapRadius(RandomSource random);

    protected abstract boolean canPlaceCap(LevelAccessor level, BlockPos center, HugeMushroomFeatureConfiguration config, int capRadius, BlockPos.MutableBlockPos mutableBlockPos);

    protected abstract void placeCap(LevelAccessor level, RandomSource random, BlockPos center, HugeMushroomFeatureConfiguration config, int capRadius, BlockPos.MutableBlockPos mutableBlockPos);

    protected boolean canPlaceCaps(LevelAccessor level, HugeMushroomFeatureConfiguration config, Pair<BlockPos, Integer>[] capPairs, BlockPos.MutableBlockPos mutableBlockPos) {
        AtomicBoolean canPlaceCaps = new AtomicBoolean(true);
        Arrays.stream(capPairs).forEach(pair -> canPlaceCaps.set(canPlaceCaps.get() && canPlaceCap(level, pair.getLeft(), config, pair.getRight(), mutableBlockPos)));
        return canPlaceCaps.get();
    }

    protected void placeCaps(LevelAccessor level, RandomSource random, HugeMushroomFeatureConfiguration config, Pair<BlockPos, Integer>[] capPairs, BlockPos.MutableBlockPos mutableBlockPos) {
        Arrays.stream(capPairs).forEach(pair -> placeCap(level, random, pair.getLeft(), config, pair.getRight(), mutableBlockPos));
    }

    protected Direction[] getDirections(RandomSource random) {
        ArrayList<Direction> directions = new ArrayList<>();
        //add 2 different directions
        while (directions.size() < 2) {
            Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            if (!directions.contains(direction)) {
                directions.add(direction);
            }
        }
        //add 3rd direction if first 2 are not opposite & a RandomSource value is found.
        if (directions.get(1).getOpposite() != directions.get(0) && random.nextInt(12) == 0) {
            directions.add(directions.get(1).getOpposite());
        }
        return directions.toArray(new Direction[0]);
    }

    protected Triple<Direction, Integer, Integer>[] getTrunkTriples(Direction[] directions, RandomSource random) {
        Triple<Direction, Integer, Integer>[] triples = new Triple[directions.length];
        for (int i = 0; i < directions.length; i++) {
            triples[i] = Triple.of(directions[i], i == 0 ? getSize(random) : getSmallSize(random), getDistanceToCenter(random));
        }
        return triples;
    }

    protected Pair<BlockPos, Integer>[] getCapPairs(BlockPos mushroomPos, Triple<Direction, Integer, Integer>[] trunkTriples, RandomSource random) {
        Pair<BlockPos, Integer>[] pairs = new Pair[trunkTriples.length];
        for (int i = 0; i < trunkTriples.length; i++) {
            pairs[i] = Pair.of(getCapCenter(mushroomPos, trunkTriples[i]), i == 0 ? getCapRadius(random) : getSmallCapRadius(random));
        }
        return pairs;
    }

    protected int getDistanceToCenter(RandomSource random) {
        return 2 + random.nextInt(1);
    }

    protected BlockPos getCapCenter(BlockPos mushroomPos, Triple<Direction, Integer, Integer> trunkTriple) {
        return new BlockPos.MutableBlockPos().set(mushroomPos).move(Direction.UP, trunkTriple.getMiddle()).move(trunkTriple.getLeft(), trunkTriple.getRight()).immutable();
    }

    protected boolean canPlaceTrunks(LevelAccessor level, BlockPos blockPos, HugeMushroomFeatureConfiguration config, Triple<Direction, Integer, Integer>[] directions, BlockPos.MutableBlockPos mutableBlockPos) {
        AtomicBoolean canPlaceTrunks = new AtomicBoolean(true);
        Arrays.stream(directions).forEach(triple -> canPlaceTrunks.set(canPlaceTrunks.get() && canPlaceTrunk(level, blockPos, config, triple.getLeft(), triple.getMiddle(), triple.getRight(), mutableBlockPos)));
        return canPlaceTrunks.get();
    }

    protected boolean canPlaceTrunk(LevelAccessor level, BlockPos blockPos, HugeMushroomFeatureConfiguration config, Direction direction, int size, int distanceToCenter, BlockPos.MutableBlockPos mutableBlockPos) {
        mutableBlockPos.set(blockPos);
        for (int i = 0; i < size; ++i) {
            if (i > 0) {
                if (i <= distanceToCenter) {
                    mutableBlockPos.move(direction);
                }
                mutableBlockPos.move(Direction.UP);
            }
            if (!isReplaceable(level, mutableBlockPos, true)) {
                return false;
            }
        }
        return true;
    }

    protected void placeTrunks(LevelAccessor level, RandomSource random, BlockPos blockPos, HugeMushroomFeatureConfiguration config, Triple<Direction, Integer, Integer>[] directions, BlockPos.MutableBlockPos mutableBlockPos) {
        Arrays.stream(directions).forEach(triple -> placeTrunk(level, random, blockPos, config, triple.getLeft(), triple.getMiddle(), triple.getRight(), mutableBlockPos));
    }

    protected void placeTrunk(LevelAccessor level, RandomSource random, BlockPos blockPos, HugeMushroomFeatureConfiguration config, Direction direction, int size, int distanceToCenter, BlockPos.MutableBlockPos mutableBlockPos) {
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
    public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> context) {
        Direction[] directions = this.getDirections(context.random());
        Triple<Direction, Integer, Integer>[] trunkTriples = this.getTrunkTriples(directions, context.random());
        Pair<BlockPos, Integer>[] capPairs = this.getCapPairs(context.origin(), trunkTriples, context.random());
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        if (isInWorldBounds(context.level(), context.origin(), trunkTriples[0].getMiddle())
                && hasValidGround(context.level(), context.origin())
                && canPlaceTrunks(context.level(), context.origin(), context.config(), trunkTriples, mutableBlockPos)
                && canPlaceCaps(context.level(), context.config(), capPairs, mutableBlockPos)) {
            this.placeTrunks(context.level(), context.random(), context.origin(), context.config(), trunkTriples, mutableBlockPos);
            this.placeCaps(context.level(), context.random(), context.config(), capPairs, mutableBlockPos);
            return true;
        }
        return false;
    }

}
