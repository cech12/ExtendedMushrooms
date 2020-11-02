package cech12.extendedmushrooms;

import cech12.extendedmushrooms.init.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class MushroomUtils {

    private MushroomUtils() {}

    public static boolean isValidMushroomPosition(IWorldReader world, BlockPos pos) {
        BlockState block = world.getBlockState(pos.down());
        return block.isIn(ModTags.Blocks.MUSHROOM_GROWING_BLOCKS) ||
                (block.isIn(ModTags.Blocks.MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL) && world.getLightSubtracted(pos, 0) < 13);
    }

}
