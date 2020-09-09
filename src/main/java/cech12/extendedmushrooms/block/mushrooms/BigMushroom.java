package cech12.extendedmushrooms.block.mushrooms;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Random;

public abstract class BigMushroom {

    public BigMushroom() {
    }

    @Nullable
    protected abstract ConfiguredFeature<?, ?> getBigMushroomFeature(Random var1, boolean var2);

    protected static BlockState getDefaultStemState(Block stemBlock) {
        return stemBlock.getDefaultState().with(HugeMushroomBlock.UP, false).with(HugeMushroomBlock.DOWN, false);
    }

    protected static BlockState getDefaultCapState(Block capBlock) {
        return capBlock.getDefaultState().with(HugeMushroomBlock.DOWN, false);
    }

    public boolean growMushroom(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, Random random) {
        ConfiguredFeature<?, ?> feature = this.getBigMushroomFeature(random, this.canGrowBigMushroom(world, blockPos));
        if (feature == null) {
            return false;
        } else {
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 4);
            if (feature.func_242765_a(world, chunkGenerator, random, blockPos)) { //place
                return true;
            } else {
                world.setBlockState(blockPos, blockState, 4);
                return false;
            }
        }
    }

    private boolean canGrowBigMushroom(IWorld world, BlockPos blockPos) { //TODO
        Iterator var3 = BlockPos.Mutable.getAllInBoxMutable(blockPos.down().north(2).west(2), blockPos.up().south(2).east(2)).iterator();

        BlockPos pos;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            pos = (BlockPos)var3.next();
        } while(!world.getBlockState(pos).isIn(BlockTags.FLOWERS));

        return true;
    }

}
