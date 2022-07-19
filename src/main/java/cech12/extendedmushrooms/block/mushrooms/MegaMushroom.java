package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.MushroomUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class MegaMushroom extends BigMushroom {

    public MegaMushroom() {
    }

    @Override
    public boolean growMushroom(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random) {
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
    protected abstract RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> getMegaMushroomFeature();

    protected boolean growMegaMushroom(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random, int x, int z) {
        ConfiguredFeature<?, ?> feature = this.getMegaMushroomFeature().get();
        BlockState lvt_9_1_ = Blocks.AIR.defaultBlockState();
        world.setBlock(blockPos.offset(x, 0, z), lvt_9_1_, 4);
        world.setBlock(blockPos.offset(x + 1, 0, z), lvt_9_1_, 4);
        world.setBlock(blockPos.offset(x, 0, z + 1), lvt_9_1_, 4);
        world.setBlock(blockPos.offset(x + 1, 0, z + 1), lvt_9_1_, 4);
        if (feature.place(world, chunkGenerator, random, blockPos.offset(x, 0, z))) {
            return true;
        } else {
            world.setBlock(blockPos.offset(x, 0, z), blockState, 4);
            world.setBlock(blockPos.offset(x + 1, 0, z), blockState, 4);
            world.setBlock(blockPos.offset(x, 0, z + 1), blockState, 4);
            world.setBlock(blockPos.offset(x + 1, 0, z + 1), blockState, 4);
            return false;
        }
    }

    public static boolean canMegaMushroomSpawnAt(BlockState blockState, BlockGetter blockReader, BlockPos blockPos, int x, int z) {
        Block block = blockState.getBlock();
        return block == blockReader.getBlockState(blockPos.offset(x, 0, z)).getBlock() && block == blockReader.getBlockState(blockPos.offset(x + 1, 0, z)).getBlock() && block == blockReader.getBlockState(blockPos.offset(x, 0, z + 1)).getBlock() && block == blockReader.getBlockState(blockPos.offset(x + 1, 0, z + 1)).getBlock();
    }

}
