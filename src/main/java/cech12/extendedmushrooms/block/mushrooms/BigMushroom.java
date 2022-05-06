package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.MushroomUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class BigMushroom {

    public BigMushroom() {
    }

    @Nonnull
    protected abstract ConfiguredFeature<?, ?> getBigMushroomFeature();

    protected static BlockState getDefaultStemState(Block stemBlock) {
        return stemBlock.defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false);
    }

    protected static BlockState getDefaultCapState(Block capBlock) {
        return capBlock.defaultBlockState().setValue(HugeMushroomBlock.DOWN, false);
    }

    public boolean growMushroom(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random) {
        if (!MushroomUtils.isValidMushroomPosition(world, blockPos)) {
            return false;
        }
        ConfiguredFeature<?, ?> feature = this.getBigMushroomFeature();
        world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 4);
        if (feature.place(world, chunkGenerator, random, blockPos)) {
            return true;
        } else {
            world.setBlock(blockPos, blockState, 4);
            return false;
        }
    }

}
