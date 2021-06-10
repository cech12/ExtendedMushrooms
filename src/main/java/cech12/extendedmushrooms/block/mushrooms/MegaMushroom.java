package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.MushroomUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class MegaMushroom extends BigMushroom {

    public MegaMushroom() {
    }

    @Override
    public boolean growMushroom(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random) {
        if (!MushroomUtils.isValidMushroomPosition(world, blockPos)) {
            return false;
        }
        for(int x = 0; x >= -1; --x) {
            for(int z = 0; z >= -1; --z) {
                if (canMegaMushroomSpawnAt(blockState, world, blockPos, x, z)) {
                    return this.growMegaMushroom(world, chunkGenerator, blockPos, blockState, random, x, z);
                }
            }
        }
        return super.growMushroom(world, chunkGenerator, blockPos, blockState, random);
    }

    @Nonnull
    protected abstract ConfiguredFeature<?, ?> getMegaMushroomFeature();

    protected boolean growMegaMushroom(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random, int x, int z) {
        ConfiguredFeature<?, ?> feature = this.getMegaMushroomFeature();
        BlockState lvt_9_1_ = Blocks.AIR.getDefaultState();
        world.setBlockState(blockPos.add(x, 0, z), lvt_9_1_, 4);
        world.setBlockState(blockPos.add(x + 1, 0, z), lvt_9_1_, 4);
        world.setBlockState(blockPos.add(x, 0, z + 1), lvt_9_1_, 4);
        world.setBlockState(blockPos.add(x + 1, 0, z + 1), lvt_9_1_, 4);
        if (feature.generate(world, chunkGenerator, random, blockPos.add(x, 0, z))) {
            return true;
        } else {
            world.setBlockState(blockPos.add(x, 0, z), blockState, 4);
            world.setBlockState(blockPos.add(x + 1, 0, z), blockState, 4);
            world.setBlockState(blockPos.add(x, 0, z + 1), blockState, 4);
            world.setBlockState(blockPos.add(x + 1, 0, z + 1), blockState, 4);
            return false;
        }
    }

    public static boolean canMegaMushroomSpawnAt(BlockState blockState, IBlockReader blockReader, BlockPos blockPos, int x, int z) {
        Block block = blockState.getBlock();
        return block == blockReader.getBlockState(blockPos.add(x, 0, z)).getBlock() && block == blockReader.getBlockState(blockPos.add(x + 1, 0, z)).getBlock() && block == blockReader.getBlockState(blockPos.add(x, 0, z + 1)).getBlock() && block == blockReader.getBlockState(blockPos.add(x + 1, 0, z + 1)).getBlock();
    }

}
