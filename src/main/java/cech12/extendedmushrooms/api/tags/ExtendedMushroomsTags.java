package cech12.extendedmushrooms.api.tags;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class ExtendedMushroomsTags {

    public static class ForgeBlocks {

        public static final Tag<Block> EDIBLE_MUSHROOMS = tag("edible_mushrooms"); //all edible mushrooms
        public static final Tag<Block> MUSHROOMS = tag("mushrooms"); //all mushrooms

        public static final Tag<Block> MUSHROOM_CAPS = tag("mushroom_caps"); //all mushroom caps
        public static final Tag<Block> MUSHROOM_STEMS = tag("mushroom_stems"); //all mushroom stems

        private static Tag<Block> tag(@Nonnull String name) {
            return new BlockTags.Wrapper(new ResourceLocation("forge", name));
        }
    }

    public static class Blocks {

        public static final Tag<Block> MUSHROOM_GROWING_BLOCKS = tag("mushroom_growing_blocks");
        public static final Tag<Block> MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL = tag("mushroom_growing_blocks_lightlevel");
        public static final Tag<Block> MUSHROOM_VALID_BLOCKS = tag("mushroom_valid_blocks");

        public static final Tag<Block> EDIBLE_MUSHROOMS = tag("edible_mushrooms"); //edible mushrooms of mod (without red & brown mushrooms)
        public static final Tag<Block> MUSHROOMS = tag("mushrooms"); //all mushrooms of mod (without red & brown mushrooms)
        public static final Tag<Block> POISONOUS_MUSHROOMS = tag("poisonous_mushrooms"); //all poisonous mushrooms of mod

        private static Tag<Block> tag(@Nonnull String name) {
            return new BlockTags.Wrapper(new ResourceLocation(ExtendedMushrooms.MOD_ID, name));
        }
    }

    public static class ForgeItems {

        public static final Tag<Item> EDIBLE_MUSHROOMS = tag("edible_mushrooms"); //all edible mushrooms
        //public static final Tag<Item> MUSHROOMS = tag("mushrooms"); //already in Tags class

        public static final Tag<Item> MUSHROOM_CAPS = tag("mushroom_caps"); //all mushroom caps
        public static final Tag<Item> MUSHROOM_STEMS = tag("mushroom_stems"); //all mushroom stems

        private static Tag<Item> tag(@Nonnull String name) {
            return new ItemTags.Wrapper(new ResourceLocation("forge", name));
        }
    }

    public static class Items {

        public static final Tag<Item> EDIBLE_MUSHROOMS = tag("edible_mushrooms"); //edible mushrooms of mod (without red & brown mushrooms)
        public static final Tag<Item> MUSHROOMS = tag("mushrooms"); //all mushrooms of mod (without red & brown mushrooms)
        public static final Tag<Item> POISONOUS_MUSHROOMS = tag("poisonous_mushrooms"); //all poisonous mushrooms of mod

        private static Tag<Item> tag(@Nonnull String name) {
            return new ItemTags.Wrapper(new ResourceLocation(ExtendedMushrooms.MOD_ID, name));
        }

    }

}
