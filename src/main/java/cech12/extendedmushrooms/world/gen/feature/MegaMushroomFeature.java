package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

import java.util.Random;

public abstract class MegaMushroomFeature extends SingleBigMushroomFeature {

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
    protected boolean hasValidGround(IWorld level, BlockPos mushroomPos) {
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(mushroomPos.getX(), mushroomPos.getY(), mushroomPos.getZ());
        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                if (!super.hasValidGround(level, mutableBlockPos.set(mushroomPos).move(x, 0, z))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected boolean canPlaceTrunk(IWorld level, BlockPos blockPos, int size, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                if (!super.canPlaceTrunk(level, mutableBlockPos.set(blockPos).move(x, 0, z), size, mutableBlockPos, config)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void placeTrunk(IWorld level, Random random, BlockPos blockPos, BigMushroomFeatureConfig config, int size, BlockPos.Mutable mutableBlockPos) {
        for(int y = 0; y < size; ++y) {
            for (int x = 0; x < 2; x++) {
                for (int z = 0; z < 2; z++) {
                    mutableBlockPos.set(blockPos).move(x, y, z);
                    if (level.getBlockState(mutableBlockPos).canBeReplacedByLogs(level, mutableBlockPos)) {
                        this.setBlock(level, mutableBlockPos, config.stemProvider.getState(random, blockPos));
                    }
                }
            }
        }
    }

}
