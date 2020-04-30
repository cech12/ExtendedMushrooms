package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class ModTags {

    public static class ForgeBlocks {

        public static final Tag<Block> MUSHROOM_CAPS = tag("mushroom_caps");
        public static final Tag<Block> MUSHROOM_CAPS_BROWN = tag("mushroom_caps/brown");
        public static final Tag<Block> MUSHROOM_CAPS_RED = tag("mushroom_caps/red");
        public static final Tag<Block> MUSHROOM_CAPS_GLOWSHROOM = tag("mushroom_caps/glowshroom");
        public static final Tag<Block> MUSHROOM_CAPS_PURPLE = tag("mushroom_caps/purple");

        public static final Tag<Block> MUSHROOM_STEMS = tag("mushroom_stems");
        public static final Tag<Block> MUSHROOM_STEMS_COLORLESS = tag("mushroom_stems/colorless");
        public static final Tag<Block> MUSHROOM_STEMS_GLOWSHROOM = tag("mushroom_stems/glowshroom");
        public static final Tag<Block> MUSHROOM_STEMS_GREEN = tag("mushroom_stems/green");

        public static final Tag<Block> MUSHROOMS = tag("mushrooms");
        public static final Tag<Block> MUSHROOMS_BROWN = tag("mushrooms/brown");
        public static final Tag<Block> MUSHROOMS_RED = tag("mushrooms/red");
        public static final Tag<Block> MUSHROOMS_GLOWSHROOM = tag("mushrooms/glowshroom");
        public static final Tag<Block> MUSHROOMS_PURPLE = tag("mushrooms/purple");
        public static final Tag<Block> MUSHROOMS_EDIBLE = tag("mushrooms/edible");
        public static final Tag<Block> MUSHROOMS_POISONOUS = tag("mushrooms/poisonous");

        private static Tag<Block> tag(@Nonnull String name) {
            return new BlockTags.Wrapper(new ResourceLocation("forge", name));
        }
    }

    public static class Blocks {

        public static final Tag<Block> MUSHROOM_BOOKSHELVES = tag("mushroom_bookshelves");
        public static final Tag<Block> MUSHROOM_BUTTONS_WOOD = tag("mushroom_buttons/wood");
        public static final Tag<Block> MUSHROOM_BUTTONS_WOOL = tag("mushroom_buttons/wool");
        public static final Tag<Block> MUSHROOM_BUTTONS = tag("mushroom_buttons");
        public static final Tag<Block> MUSHROOM_CARPETS = tag("mushroom_carpets");
        public static final Tag<Block> MUSHROOM_DOORS = tag("mushroom_doors");
        public static final Tag<Block> MUSHROOM_FENCE_GATES = tag("mushroom_fence_gates");
        public static final Tag<Block> MUSHROOM_FENCES = tag("mushroom_fences");
        public static final Tag<Block> MUSHROOM_LADDERS = tag("mushroom_ladders");
        public static final Tag<Block> MUSHROOM_PLANKS = tag("mushroom_planks");
        public static final Tag<Block> MUSHROOM_PRESSURE_PLATES_WOOD = tag("mushroom_pressure_plates/wood");
        public static final Tag<Block> MUSHROOM_PRESSURE_PLATES_WOOL = tag("mushroom_pressure_plates/wool");
        public static final Tag<Block> MUSHROOM_PRESSURE_PLATES = tag("mushroom_pressure_plates");
        public static final Tag<Block> MUSHROOM_SLABS = tag("mushroom_slabs");
        public static final Tag<Block> MUSHROOM_STAIRS = tag("mushroom_stairs");
        public static final Tag<Block> MUSHROOM_TRAPDOORS = tag("mushroom_trapdoors");

        public static final Tag<Block> MUSHROOMS_EDIBLE = tag("mushrooms/edible"); // only mod intern edible mushrooms

        public static final Tag<Block> MUSHROOM_GROWING_BLOCKS = tag("mushroom_growing_blocks");
        public static final Tag<Block> MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL = tag("mushroom_growing_blocks_lightlevel");
        public static final Tag<Block> MUSHROOM_VALID_BLOCKS = tag("mushroom_valid_blocks");

        private static Tag<Block> tag(@Nonnull String name) {
            return new BlockTags.Wrapper(new ResourceLocation(ExtendedMushrooms.MOD_ID, name));
        }
    }

    public static class OtherModBlocks {

        public static final Tag<Block> WOOLPLATES_WOOLPLATES = tag("woolplates", "woolplates");

        private static Tag<Block> tag(@Nonnull String mod, @Nonnull String name) {
            return new BlockTags.Wrapper(new ResourceLocation(mod, name));
        }
    }

    public static class ForgeItems {

        public static final Tag<Item> MUSHROOM_CAPS = tag("mushroom_caps");
        public static final Tag<Item> MUSHROOM_CAPS_BROWN = tag("mushroom_caps/brown");
        public static final Tag<Item> MUSHROOM_CAPS_RED = tag("mushroom_caps/red");
        public static final Tag<Item> MUSHROOM_CAPS_GLOWSHROOM = tag("mushroom_caps/glowshroom");
        public static final Tag<Item> MUSHROOM_CAPS_PURPLE = tag("mushroom_caps/purple");

        public static final Tag<Item> MUSHROOM_STEMS = tag("mushroom_stems");
        public static final Tag<Item> MUSHROOM_STEMS_COLORLESS = tag("mushroom_stems/colorless");
        public static final Tag<Item> MUSHROOM_STEMS_GLOWSHROOM = tag("mushroom_stems/glowshroom");
        public static final Tag<Item> MUSHROOM_STEMS_GREEN = tag("mushroom_stems/green");

        // public static final Tag<Item> MUSHROOMS = tag("mushrooms"); // already there!
        public static final Tag<Item> MUSHROOMS_BROWN = tag("mushrooms/brown");
        public static final Tag<Item> MUSHROOMS_RED = tag("mushrooms/red");
        public static final Tag<Item> MUSHROOMS_GLOWSHROOM = tag("mushrooms/glowshroom");
        public static final Tag<Item> MUSHROOMS_PURPLE = tag("mushrooms/purple");
        public static final Tag<Item> MUSHROOMS_EDIBLE = tag("mushrooms/edible");
        public static final Tag<Item> MUSHROOMS_POISONOUS = tag("mushrooms/poisonous");

        public static final Tag<Item> BREAD = tag("bread");
        public static final Tag<Item> SHEARS = tag("shears");

        private static Tag<Item> tag(@Nonnull String name) {
            return new ItemTags.Wrapper(new ResourceLocation("forge", name));
        }
    }

    public static class Items {

        public static final Tag<Item> MUSHROOM_BOATS = tag("mushroom_boats");
        public static final Tag<Item> MUSHROOM_BOOKSHELVES = tag("mushroom_bookshelves");
        public static final Tag<Item> MUSHROOM_BUTTONS_WOOD = tag("mushroom_buttons/wood");
        public static final Tag<Item> MUSHROOM_BUTTONS_WOOL = tag("mushroom_buttons/wool");
        public static final Tag<Item> MUSHROOM_BUTTONS = tag("mushroom_buttons");
        public static final Tag<Item> MUSHROOM_CARPETS = tag("mushroom_carpets");
        public static final Tag<Item> MUSHROOM_DOORS = tag("mushroom_doors");
        public static final Tag<Item> MUSHROOM_FENCE_GATES = tag("mushroom_fence_gates");
        public static final Tag<Item> MUSHROOM_FENCES = tag("mushroom_fences");
        public static final Tag<Item> MUSHROOM_LADDERS = tag("mushroom_ladders");
        public static final Tag<Item> MUSHROOM_PLANKS = tag("mushroom_planks");
        public static final Tag<Item> MUSHROOM_PRESSURE_PLATES_WOOD = tag("mushroom_pressure_plates/wood");
        public static final Tag<Item> MUSHROOM_PRESSURE_PLATES_WOOL = tag("mushroom_pressure_plates/wool");
        public static final Tag<Item> MUSHROOM_PRESSURE_PLATES = tag("mushroom_pressure_plates");
        public static final Tag<Item> MUSHROOM_SLABS = tag("mushroom_slabs");
        public static final Tag<Item> MUSHROOM_STAIRS = tag("mushroom_stairs");
        public static final Tag<Item> MUSHROOM_TRAPDOORS = tag("mushroom_trapdoors");

        public static final Tag<Item> MUSHROOMS_EDIBLE = tag("mushrooms/edible"); // only mod intern edible mushrooms

        private static Tag<Item> tag(@Nonnull String name) {
            return new ItemTags.Wrapper(new ResourceLocation(ExtendedMushrooms.MOD_ID, name));
        }

    }

    public static class OtherModItems {

        public static final Tag<Item> CORAIL_WOODCUTTER_ALLOWED_ITEMS = tag("corail_woodcutter", "allowed_items");
        public static final Tag<Item> WOOLPLATES_WOOLPLATES = tag("woolplates", "woolplates");

        private static Tag<Item> tag(@Nonnull String mod, @Nonnull String name) {
            return new ItemTags.Wrapper(new ResourceLocation(mod, name));
        }
    }

}
