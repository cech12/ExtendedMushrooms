package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

import javax.annotation.Nonnull;

public class ModTags {

    public static class ForgeBlocks {

        public static final ITag.INamedTag<Block> MUSHROOM_CAPS = tag("mushroom_caps");
        public static final ITag.INamedTag<Block> MUSHROOM_CAPS_BROWN = tag("mushroom_caps/brown");
        public static final ITag.INamedTag<Block> MUSHROOM_CAPS_RED = tag("mushroom_caps/red");
        public static final ITag.INamedTag<Block> MUSHROOM_CAPS_GLOWSHROOM = tag("mushroom_caps/glowshroom");
        public static final ITag.INamedTag<Block> MUSHROOM_CAPS_PURPLE = tag("mushroom_caps/purple");

        public static final ITag.INamedTag<Block> MUSHROOM_STEMS = tag("mushroom_stems");
        public static final ITag.INamedTag<Block> MUSHROOM_STEMS_COLORLESS = tag("mushroom_stems/colorless");
        public static final ITag.INamedTag<Block> MUSHROOM_STEMS_GLOWSHROOM = tag("mushroom_stems/glowshroom");
        public static final ITag.INamedTag<Block> MUSHROOM_STEMS_GREEN = tag("mushroom_stems/green");

        public static final ITag.INamedTag<Block> MUSHROOMS = tag("mushrooms");
        public static final ITag.INamedTag<Block> MUSHROOMS_BROWN = tag("mushrooms/brown");
        public static final ITag.INamedTag<Block> MUSHROOMS_RED = tag("mushrooms/red");
        public static final ITag.INamedTag<Block> MUSHROOMS_GLOWSHROOM = tag("mushrooms/glowshroom");
        public static final ITag.INamedTag<Block> MUSHROOMS_PURPLE = tag("mushrooms/purple");
        public static final ITag.INamedTag<Block> MUSHROOMS_EDIBLE = tag("mushrooms/edible");
        public static final ITag.INamedTag<Block> MUSHROOMS_POISONOUS = tag("mushrooms/poisonous");

        private static ITag.INamedTag<Block> tag(@Nonnull String name) {
            return BlockTags.makeWrapperTag("forge:" + name);
        }
    }

    public static class Blocks {

        public static final ITag.INamedTag<Block> MUSHROOM_BOOKSHELVES = tag("mushroom_bookshelves");
        public static final ITag.INamedTag<Block> MUSHROOM_BUTTONS_WOOD = tag("mushroom_buttons/wood");
        public static final ITag.INamedTag<Block> MUSHROOM_BUTTONS_WOOL = tag("mushroom_buttons/wool");
        public static final ITag.INamedTag<Block> MUSHROOM_BUTTONS = tag("mushroom_buttons");
        public static final ITag.INamedTag<Block> MUSHROOM_CARPETS = tag("mushroom_carpets");
        public static final ITag.INamedTag<Block> MUSHROOM_CHESTS = tag("mushroom_chests");
        public static final ITag.INamedTag<Block> MUSHROOM_CHESTS_TRAPPED = tag("mushroom_chests_trapped");
        public static final ITag.INamedTag<Block> MUSHROOM_DOORS = tag("mushroom_doors");
        public static final ITag.INamedTag<Block> MUSHROOM_FENCE_GATES = tag("mushroom_fence_gates");
        public static final ITag.INamedTag<Block> MUSHROOM_FENCES = tag("mushroom_fences");
        public static final ITag.INamedTag<Block> MUSHROOM_LADDERS = tag("mushroom_ladders");
        public static final ITag.INamedTag<Block> MUSHROOM_PLANKS = tag("mushroom_planks");
        public static final ITag.INamedTag<Block> MUSHROOM_PRESSURE_PLATES_WOOD = tag("mushroom_pressure_plates/wood");
        public static final ITag.INamedTag<Block> MUSHROOM_PRESSURE_PLATES_WOOL = tag("mushroom_pressure_plates/wool");
        public static final ITag.INamedTag<Block> MUSHROOM_PRESSURE_PLATES = tag("mushroom_pressure_plates");
        public static final ITag.INamedTag<Block> MUSHROOM_SIGNS = tag("mushroom_signs");
        public static final ITag.INamedTag<Block> MUSHROOM_SLABS = tag("mushroom_slabs");
        public static final ITag.INamedTag<Block> MUSHROOM_STAIRS = tag("mushroom_stairs");
        public static final ITag.INamedTag<Block> MUSHROOM_TRAPDOORS = tag("mushroom_trapdoors");

        public static final ITag.INamedTag<Block> MUSHROOMS_EDIBLE = tag("mushrooms/edible"); // only mod intern edible mushrooms

        //use "minecraft:mushroom_grow_block" tag (Blocktags,field_242171_aD) for lightlevel ignoring blocks
        public static final ITag.INamedTag<Block> MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL = tag("mushroom_growing_blocks_lightlevel");

        private static ITag.INamedTag<Block> tag(@Nonnull String name) {
            return BlockTags.makeWrapperTag(ExtendedMushrooms.MOD_ID + ":" + name);
        }
    }

    public static class OtherModBlocks {

        public static final ITag.INamedTag<Block> QUARK_LADDERS = tag("quark", "ladders");
        public static final ITag.INamedTag<Block> WOOLPLATES_WOOLPLATES = tag("woolplates", "woolplates");

        private static ITag.INamedTag<Block> tag(@Nonnull String mod, @Nonnull String name) {
            return BlockTags.makeWrapperTag(mod + ":" + name);
        }
    }

    public static class ForgeItems {

        public static final ITag.INamedTag<Item> MUSHROOM_CAPS = tag("mushroom_caps");
        public static final ITag.INamedTag<Item> MUSHROOM_CAPS_BROWN = tag("mushroom_caps/brown");
        public static final ITag.INamedTag<Item> MUSHROOM_CAPS_RED = tag("mushroom_caps/red");
        public static final ITag.INamedTag<Item> MUSHROOM_CAPS_GLOWSHROOM = tag("mushroom_caps/glowshroom");
        public static final ITag.INamedTag<Item> MUSHROOM_CAPS_PURPLE = tag("mushroom_caps/purple");

        public static final ITag.INamedTag<Item> MUSHROOM_STEMS = tag("mushroom_stems");
        public static final ITag.INamedTag<Item> MUSHROOM_STEMS_COLORLESS = tag("mushroom_stems/colorless");
        public static final ITag.INamedTag<Item> MUSHROOM_STEMS_GLOWSHROOM = tag("mushroom_stems/glowshroom");
        public static final ITag.INamedTag<Item> MUSHROOM_STEMS_GREEN = tag("mushroom_stems/green");

        // public static final ITag.INamedTag<Item> MUSHROOMS = tag("mushrooms"); // already there!
        public static final ITag.INamedTag<Item> MUSHROOMS_BROWN = tag("mushrooms/brown");
        public static final ITag.INamedTag<Item> MUSHROOMS_RED = tag("mushrooms/red");
        public static final ITag.INamedTag<Item> MUSHROOMS_GLOWSHROOM = tag("mushrooms/glowshroom");
        public static final ITag.INamedTag<Item> MUSHROOMS_PURPLE = tag("mushrooms/purple");
        public static final ITag.INamedTag<Item> MUSHROOMS_EDIBLE = tag("mushrooms/edible");
        public static final ITag.INamedTag<Item> MUSHROOMS_POISONOUS = tag("mushrooms/poisonous");

        public static final ITag.INamedTag<Item> BREAD = tag("bread");

        private static ITag.INamedTag<Item> tag(@Nonnull String name) {
            return ItemTags.makeWrapperTag("forge:" + name);
        }
    }

    public static class Items {

        public static final ITag.INamedTag<Item> MUSHROOM_BOATS = tag("mushroom_boats");
        public static final ITag.INamedTag<Item> MUSHROOM_BOOKSHELVES = tag("mushroom_bookshelves");
        public static final ITag.INamedTag<Item> MUSHROOM_BUTTONS_WOOD = tag("mushroom_buttons/wood");
        public static final ITag.INamedTag<Item> MUSHROOM_BUTTONS_WOOL = tag("mushroom_buttons/wool");
        public static final ITag.INamedTag<Item> MUSHROOM_BUTTONS = tag("mushroom_buttons");
        public static final ITag.INamedTag<Item> MUSHROOM_CARPETS = tag("mushroom_carpets");
        public static final ITag.INamedTag<Item> MUSHROOM_CHESTS = tag("mushroom_chests");
        public static final ITag.INamedTag<Item> MUSHROOM_CHESTS_TRAPPED = tag("mushroom_chests_trapped");
        public static final ITag.INamedTag<Item> MUSHROOM_DOORS = tag("mushroom_doors");
        public static final ITag.INamedTag<Item> MUSHROOM_FENCE_GATES = tag("mushroom_fence_gates");
        public static final ITag.INamedTag<Item> MUSHROOM_FENCES = tag("mushroom_fences");
        public static final ITag.INamedTag<Item> MUSHROOM_LADDERS = tag("mushroom_ladders");
        public static final ITag.INamedTag<Item> MUSHROOM_PLANKS = tag("mushroom_planks");
        public static final ITag.INamedTag<Item> MUSHROOM_PRESSURE_PLATES_WOOD = tag("mushroom_pressure_plates/wood");
        public static final ITag.INamedTag<Item> MUSHROOM_PRESSURE_PLATES_WOOL = tag("mushroom_pressure_plates/wool");
        public static final ITag.INamedTag<Item> MUSHROOM_PRESSURE_PLATES = tag("mushroom_pressure_plates");
        public static final ITag.INamedTag<Item> MUSHROOM_SIGNS = tag("mushroom_signs");
        public static final ITag.INamedTag<Item> MUSHROOM_SLABS = tag("mushroom_slabs");
        public static final ITag.INamedTag<Item> MUSHROOM_STAIRS = tag("mushroom_stairs");
        public static final ITag.INamedTag<Item> MUSHROOM_TRAPDOORS = tag("mushroom_trapdoors");

        public static final ITag.INamedTag<Item> MUSHROOMS_EDIBLE = tag("mushrooms/edible"); // only mod intern edible mushrooms

        private static ITag.INamedTag<Item> tag(@Nonnull String name) {
            return ItemTags.makeWrapperTag(ExtendedMushrooms.MOD_ID + ":" + name);
        }

    }

    public static class OtherModItems {

        public static final ITag.INamedTag<Item> QUARK_LADDERS = tag("quark", "ladders");
        public static final ITag.INamedTag<Item> WOOLPLATES_WOOLPLATES = tag("woolplates", "woolplates");

        private static ITag.INamedTag<Item> tag(@Nonnull String mod, @Nonnull String name) {
            return ItemTags.makeWrapperTag(mod + ":" + name);
        }
    }

}
