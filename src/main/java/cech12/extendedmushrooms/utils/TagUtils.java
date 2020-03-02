package cech12.extendedmushrooms.utils;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class TagUtils {

    public static ResourceLocation MUSHROOM_GROWING_BLOCKS = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_growing_blocks");
    public static ResourceLocation MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_growing_blocks_lightlevel");
    public static ResourceLocation MUSHROOM_VALID_BLOCKS = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_valid_blocks");

    private TagUtils() {}

    public static boolean hasTag(Block block, ResourceLocation tag) {
        for (ResourceLocation blockTag : block.getTags()) {
            if (blockTag.equals(tag)) {
                return true;
            }
        }
        return false;
    }

}
