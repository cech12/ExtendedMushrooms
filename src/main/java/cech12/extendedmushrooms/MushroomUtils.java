package cech12.extendedmushrooms;

import cech12.extendedmushrooms.init.ModTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

public class MushroomUtils {

    private MushroomUtils() {}

    public static boolean isValidMushroomPosition(LevelReader world, BlockPos pos) {
        BlockState block = world.getBlockState(pos.below());
        return block.is(BlockTags.MUSHROOM_GROW_BLOCK) ||
                (block.is(ModTags.Blocks.MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL) && world.getRawBrightness(pos, 0) < 13);
    }

}
