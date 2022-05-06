package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

import java.util.Random;

public class MegaBrownMushroomFeature extends MegaMushroomFeature {

    public MegaBrownMushroomFeature(Codec<BigMushroomFeatureConfig> config) {
        super(config);
    }

    @Override
    protected int getCapRadius(Random random) {
        return 3 + random.nextInt(2);
    }

    @Override
    protected boolean canPlaceCap(IWorld world, BlockPos blockPos, int size, int capRadius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        for (int x = -capRadius; x <= capRadius+1; ++x) {
            for (int z = -capRadius; z <= capRadius+1; ++z) {
                mutableBlockPos.set(blockPos).move(x, size, z);
                if (!world.getBlockState(mutableBlockPos).canBeReplacedByLeaves(world, mutableBlockPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void placeCap(IWorld world, Random random, BlockPos blockPos, int size, int radius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        //top layer: "radius" blocks in each direction without corners
        for(int x = -radius; x <= radius+1; ++x) {
            for(int z = -radius; z <= radius+1; ++z) {
                boolean flag = x == -radius;
                boolean flag1 = x == radius+1;
                boolean flag2 = z == -radius;
                boolean flag3 = z == radius+1;
                boolean flag4 = flag || flag1;
                boolean flag5 = flag2 || flag3;
                if (!flag4 || !flag5) {
                    boolean flag6 = flag || flag5 && x == 1 - radius;
                    boolean flag7 = flag1 || flag5 && x == radius;
                    boolean flag8 = flag2 || flag4 && z == 1 - radius;
                    boolean flag9 = flag3 || flag4 && z == radius;
                    this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.set(blockPos).move(x, size, z), flag6, flag7, flag8, flag9);
                }
            }
        }
        //layer below top: outer circle (4 blocks away in each direction without corners
        for (int i = 0; i < radius*2; i++) {
            boolean begin = i == 0;
            boolean end = i == radius*2-1;
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.set(blockPos).move(i-radius+1, size-1, -radius-1), begin, end, true, false);
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.set(blockPos).move(i-radius+1, size-1, radius+2), begin, end, false, true);
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.set(blockPos).move(-radius-1, size-1, i-radius+1), true, false, begin, end);
            this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.set(blockPos).move(radius+2, size-1, i-radius+1), false, true, begin, end);
        }
        this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.set(blockPos).move(-radius, size-1, -radius), true, false, true, false);
        this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.set(blockPos).move(-radius, size-1, radius+1), true, false , false, true);
        this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.set(blockPos).move(radius+1, size-1, -radius), false, true, true, false);
        this.placeCapBlockIfPossible(world, random, config, mutableBlockPos.set(blockPos).move(radius+1, size-1, radius+1), false, true, false, true);
    }

}
