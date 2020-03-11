package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public abstract class MegaMushroomFeature extends BigMushroomFeature {

    public MegaMushroomFeature(Function<Dynamic<?>, ? extends BigMushroomFeatureConfig> config) {
        super(config);
    }

    protected int getSize(Random random) {
        int i = random.nextInt(5) + 10;
        if (random.nextInt(12) == 0) {
            i *= random.nextInt(7) + 15;
        }
        return i;
    }

    @Override
    protected void placeTrunk(IWorld world, Random random, BlockPos blockPos, BigMushroomFeatureConfig config, int size, BlockPos.Mutable mutableBlockPos) {
        for(int y = 0; y < size; ++y) {
            for (int x = 0; x < 2; x++) {
                for (int z = 0; z < 2; z++) {
                    mutableBlockPos.setPos(blockPos).move(Direction.UP, y).move(Direction.SOUTH, x).move(Direction.EAST, z);
                    if (world.getBlockState(mutableBlockPos).canBeReplacedByLogs(world, mutableBlockPos)) {
                        this.setBlockState(world, mutableBlockPos, config.field_227273_b_.getBlockState(random, blockPos));
                    }
                }
            }
        }
    }

    protected void placeCapBlockIfPossible(IWorld world, Random random, BigMushroomFeatureConfig config, BlockPos blockPos, boolean west, boolean east, boolean north, boolean south) {
        if (world.getBlockState(blockPos).canBeReplacedByLeaves(world, blockPos)) {
            this.setBlockState(world, blockPos, config.field_227272_a_.getBlockState(random, blockPos).with(HugeMushroomBlock.WEST, west).with(HugeMushroomBlock.EAST, east).with(HugeMushroomBlock.NORTH, north).with(HugeMushroomBlock.SOUTH, south));
        }
    }

}
