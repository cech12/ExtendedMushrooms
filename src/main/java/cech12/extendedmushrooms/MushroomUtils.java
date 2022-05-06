package cech12.extendedmushrooms;

import cech12.extendedmushrooms.init.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class MushroomUtils {

    private MushroomUtils() {}

    public static boolean isValidMushroomPosition(IWorldReader world, BlockPos pos) {
        BlockState block = world.getBlockState(pos.below());
        return block.is(BlockTags.MUSHROOM_GROW_BLOCK) ||
                (block.is(ModTags.Blocks.MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL) && world.getRawBrightness(pos, 0) < 13);
    }

}
