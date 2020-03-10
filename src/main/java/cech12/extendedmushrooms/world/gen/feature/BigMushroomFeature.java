package cech12.extendedmushrooms.world.gen.feature;

import cech12.extendedmushrooms.utils.TagUtils;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public abstract class BigMushroomFeature extends Feature<BigMushroomFeatureConfig> {

    public BigMushroomFeature(Function<Dynamic<?>, ? extends BigMushroomFeatureConfig> config) {
        super(config);
    }

    protected void placeTrunk(IWorld world, Random random, BlockPos blockPos, BigMushroomFeatureConfig config, int size, BlockPos.Mutable mutableBlockPos) {
        for(int i = 0; i < size; ++i) {
            mutableBlockPos.setPos(blockPos).move(Direction.UP, i);
            if (world.getBlockState(mutableBlockPos).canBeReplacedByLogs(world, mutableBlockPos)) {
                this.setBlockState(world, mutableBlockPos, config.field_227273_b_.getBlockState(random, blockPos));
            }
        }
    }

    protected int getSize(Random random) {
        int i = random.nextInt(3) + 4;
        if (random.nextInt(12) == 0) {
            i *= 2;
        }
        return i;
    }

    protected boolean canGrow(IWorld world, BlockPos blockPos, int size, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        int i = blockPos.getY();
        if (i >= 1 && i + size + 1 < world.getMaxHeight()) {
            Block block = world.getBlockState(blockPos.down()).getBlock();
            if (TagUtils.hasTag(block, TagUtils.MUSHROOM_GROWING_BLOCKS) || TagUtils.hasTag(block, TagUtils.MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL)) {
                for(int y = 0; y <= size; ++y) {
                    //int k = this.getCapRadius(-1, -1, config.field_227274_c_, y);
                    int k = 0;

                    for(int x = -k; x <= k; ++x) {
                        for(int z = -k; z <= k; ++z) {
                            BlockState blockstate = world.getBlockState(mutableBlockPos.setPos(blockPos).move(x, y, z));
                            if (!blockstate.isAir(world, mutableBlockPos) && !blockstate.isIn(BlockTags.LEAVES)) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }


    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, BigMushroomFeatureConfig config) {
        int size = this.getSize(rand);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        if (!this.canGrow(worldIn, pos, size, blockpos$mutable, config)) {
            return false;
        } else {
            this.placeCap(worldIn, rand, pos, size, blockpos$mutable, config);
            this.placeTrunk(worldIn, rand, pos, config, size, blockpos$mutable);
            return true;
        }
    }

    protected abstract void placeCap(IWorld world, Random random, BlockPos blockPos, int size, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config);

}
