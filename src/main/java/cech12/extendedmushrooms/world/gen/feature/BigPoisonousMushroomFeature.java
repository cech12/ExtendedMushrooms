package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class BigPoisonousMushroomFeature extends BigMushroomFeature {

    public BigPoisonousMushroomFeature(Function<Dynamic<?>, ? extends BigMushroomFeatureConfig> config) {
        super(config);
    }

    @Override
    protected int getSize(Random random) {
        return (int) (super.getSize(random) * 1.2D); //a bit taller than a normal big mushroom
    }

    @Override
    protected int getCapRadius(Random random) {
        if (random.nextInt(12) == 0) {
            return 3;
        }
        return 2;
    }

    @Override
    protected boolean canPlaceCap(IWorld world, BlockPos blockPos, int size, int capRadius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        for (int x = -capRadius; x <= capRadius; ++x) {
            for (int z = -capRadius; z <= capRadius; ++z) {
                mutableBlockPos.setPos(blockPos).move(x, size, z);
                if (!world.getBlockState(mutableBlockPos).canBeReplacedByLeaves(world, mutableBlockPos)) {
                    return false;
                }
                mutableBlockPos.move(Direction.DOWN, 1);
                if (!world.getBlockState(mutableBlockPos).canBeReplacedByLeaves(world, mutableBlockPos)) {
                    return false;
                }
                mutableBlockPos.move(Direction.DOWN, 1);
                if (!world.getBlockState(mutableBlockPos).canBeReplacedByLeaves(world, mutableBlockPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void placeCap(IWorld world, Random random, BlockPos blockPos, int size, int capRadius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        //top layer: "radius-1" blocks in each direction
        int topRadius = capRadius - 1;
        for(int x = -topRadius; x <= topRadius; ++x) {
            for (int z = -topRadius; z <= topRadius; ++z) {
                boolean flag = x == -topRadius;
                boolean flag1 = x == topRadius;
                boolean flag2 = z == -topRadius;
                boolean flag3 = z == topRadius;
                this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.setPos(blockPos).move(x, size, z), flag, flag1, flag2, flag3);
            }
        }
        //layer below top: outer circle ("radius" away in each direction without corners
        int sideLength = topRadius*2 + 1;
        for (int i = 0; i < sideLength; i++) {
            boolean begin = i == 0;
            boolean end = i == sideLength - 1;
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.setPos(blockPos).move(i - capRadius+1, size - 1, -capRadius), begin, end, true, false);
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.setPos(blockPos).move(i - capRadius+1, size - 1, capRadius), begin, end, false, true);
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.setPos(blockPos).move(-capRadius, size - 1, i - capRadius+1), true, false, begin, end);
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.setPos(blockPos).move(capRadius, size - 1, i - capRadius+1), false, true, begin, end);
        }
        //layer below second layer: outer circle
        for (int i = 0; i < sideLength; i++) {
            boolean begin = i == 0;
            boolean end = i == sideLength - 1;
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.setPos(blockPos).move(i - capRadius+1, size - 2, -capRadius), begin, end, true, false, false);
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.setPos(blockPos).move(i - capRadius+1, size - 2, capRadius), begin, end, false, true, false);
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.setPos(blockPos).move(-capRadius, size - 2, i - capRadius+1), true, false, begin, end, false);
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.setPos(blockPos).move(capRadius, size - 2, i - capRadius+1), false, true, begin, end, false);
        }
    }
}
