package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

import java.util.Random;

public abstract class MegaMushroomFeature extends BigMushroomFeature {

    public MegaMushroomFeature(Codec<BigMushroomFeatureConfig> config) {
        super(config);
    }

    @Override
    protected int getSize(Random random) {
        int i = random.nextInt(5) + 10;
        if (random.nextInt(12) == 0) {
            i = random.nextInt(7) + 15;
        }
        return i;
    }

    @Override
    protected boolean hasValidGround(IWorld world, BlockPos mushroomPos) {
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(mushroomPos.getX(), mushroomPos.getY(), mushroomPos.getZ());
        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                if (!super.hasValidGround(world, mutableBlockPos.setPos(mushroomPos).move(x, 0, z))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected boolean canPlaceTrunk(IWorld world, BlockPos blockPos, int size, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                if (!super.canPlaceTrunk(world, mutableBlockPos.setPos(blockPos).move(x, 0, z), size, mutableBlockPos, config)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void placeTrunk(IWorld world, Random random, BlockPos blockPos, BigMushroomFeatureConfig config, int size, BlockPos.Mutable mutableBlockPos) {
        for(int y = 0; y < size; ++y) {
            for (int x = 0; x < 2; x++) {
                for (int z = 0; z < 2; z++) {
                    mutableBlockPos.setPos(blockPos).move(x, y, z);
                    if (world.getBlockState(mutableBlockPos).canBeReplacedByLogs(world, mutableBlockPos)) {
                        this.setBlockState(world, mutableBlockPos, config.stemProvider.getBlockState(random, blockPos));
                    }
                }
            }
        }
    }

}
