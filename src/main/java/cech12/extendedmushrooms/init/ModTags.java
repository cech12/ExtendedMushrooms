package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class ModTags {

    public static class Biomes {

        public static final TagKey<Biome> HAS_MUSHROOMS = tag("has_mushrooms");

        private static TagKey<Biome> tag(@Nonnull String name) {
            return TagKey.create(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation(ExtendedMushrooms.MOD_ID, name));
        }
    }

    public static class ForgeBlocks {

        public static final TagKey<Block> MUSHROOM_CAPS = tag("mushroom_caps");
        public static final TagKey<Block> MUSHROOM_CAPS_BROWN = tag("mushroom_caps/brown");
        public static final TagKey<Block> MUSHROOM_CAPS_RED = tag("mushroom_caps/red");
        public static final TagKey<Block> MUSHROOM_CAPS_GLOWSHROOM = tag("mushroom_caps/glowshroom");
        public static final TagKey<Block> MUSHROOM_CAPS_LIME = tag("mushroom_caps/lime");
        public static final TagKey<Block> MUSHROOM_CAPS_ORANGE = tag("mushroom_caps/orange");
        public static final TagKey<Block> MUSHROOM_CAPS_PURPLE = tag("mushroom_caps/purple");
        public static final TagKey<Block> MUSHROOM_CAPS_WHITE = tag("mushroom_caps/white");

        public static final TagKey<Block> MUSHROOM_STEMS = tag("mushroom_stems");
        public static final TagKey<Block> MUSHROOM_STEMS_COLORLESS = tag("mushroom_stems/colorless");
        public static final TagKey<Block> MUSHROOM_STEMS_GLOWSHROOM = tag("mushroom_stems/glowshroom");
        public static final TagKey<Block> MUSHROOM_STEMS_GREEN = tag("mushroom_stems/green");
        public static final TagKey<Block> MUSHROOM_STEMS_ORANGE = tag("mushroom_stems/orange");

        public static final TagKey<Block> MUSHROOMS = tag("mushrooms");
        public static final TagKey<Block> MUSHROOMS_BROWN = tag("mushrooms/brown");
        public static final TagKey<Block> MUSHROOMS_RED = tag("mushrooms/red");
        public static final TagKey<Block> MUSHROOMS_GLOWSHROOM = tag("mushrooms/glowshroom");
        public static final TagKey<Block> MUSHROOMS_LIME = tag("mushrooms/lime");
        public static final TagKey<Block> MUSHROOMS_ORANGE = tag("mushrooms/orange");
        public static final TagKey<Block> MUSHROOMS_PURPLE = tag("mushrooms/purple");
        public static final TagKey<Block> MUSHROOMS_WHITE = tag("mushrooms/white");
        public static final TagKey<Block> MUSHROOMS_EDIBLE = tag("mushrooms/edible");
        public static final TagKey<Block> MUSHROOMS_JUMP_BOOSTING = tag("mushrooms/jump_boosting");
        public static final TagKey<Block> MUSHROOMS_POISONOUS = tag("mushrooms/poisonous");
        public static final TagKey<Block> MUSHROOMS_SLOWING_DOWN = tag("mushrooms/slowing_down");

        public static final TagKey<Block> FUNGI = tag("fungi");

        private static TagKey<Block> tag(@Nonnull String name) {
            return TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation("forge", name));
        }
    }

    public static class Blocks {

        public static final TagKey<Block> MUSHROOM_BUTTONS_WOOD = tag("mushroom_buttons/wood");
        public static final TagKey<Block> MUSHROOM_BUTTONS_WOOL = tag("mushroom_buttons/wool");
        public static final TagKey<Block> MUSHROOM_BUTTONS = tag("mushroom_buttons");
        public static final TagKey<Block> MUSHROOM_CARPETS = tag("mushroom_carpets");
        public static final TagKey<Block> MUSHROOM_CEILING_HANGING_SIGNS = tag("mushroom_ceiling_hanging_signs");
        public static final TagKey<Block> MUSHROOM_DOORS = tag("mushroom_doors");
        public static final TagKey<Block> MUSHROOM_FENCE_GATES = tag("mushroom_fence_gates");
        public static final TagKey<Block> MUSHROOM_FENCES = tag("mushroom_fences");
        public static final TagKey<Block> MUSHROOM_PLANKS = tag("mushroom_planks");
        public static final TagKey<Block> MUSHROOM_PRESSURE_PLATES_WOOD = tag("mushroom_pressure_plates/wood");
        public static final TagKey<Block> MUSHROOM_PRESSURE_PLATES_WOOL = tag("mushroom_pressure_plates/wool");
        public static final TagKey<Block> MUSHROOM_PRESSURE_PLATES = tag("mushroom_pressure_plates");
        public static final TagKey<Block> MUSHROOM_SLABS = tag("mushroom_slabs");
        public static final TagKey<Block> MUSHROOM_STAIRS = tag("mushroom_stairs");
        public static final TagKey<Block> MUSHROOM_STANDING_SIGNS = tag("mushroom_standing_signs");
        public static final TagKey<Block> MUSHROOM_TRAPDOORS = tag("mushroom_trapdoors");
        public static final TagKey<Block> MUSHROOM_WALL_HANGING_SIGNS = tag("mushroom_wall_hanging_signs");
        public static final TagKey<Block> MUSHROOM_WALL_SIGNS = tag("mushroom_wall_signs");

        public static final TagKey<Block> MUSHROOMS = tag("mushrooms");
        public static final TagKey<Block> MUSHROOMS_GLOWSHROOM = tag("mushrooms/glowshroom");
        public static final TagKey<Block> MUSHROOMS_LIME = tag("mushrooms/lime");
        public static final TagKey<Block> MUSHROOMS_ORANGE = tag("mushrooms/orange");
        public static final TagKey<Block> MUSHROOMS_PURPLE = tag("mushrooms/purple");
        public static final TagKey<Block> MUSHROOMS_WHITE = tag("mushrooms/white");
        public static final TagKey<Block> MUSHROOMS_EDIBLE = tag("mushrooms/edible");
        public static final TagKey<Block> MUSHROOMS_JUMP_BOOSTING = tag("mushrooms/jump_boosting");
        public static final TagKey<Block> MUSHROOMS_POISONOUS = tag("mushrooms/poisonous");
        public static final TagKey<Block> MUSHROOMS_SLOWING_DOWN = tag("mushrooms/slowing_down");

        //use "minecraft:mushroom_grow_block" tag (Blocktags,MUSHROOM_GROW_BLOCK) for lightlevel ignoring blocks
        public static final TagKey<Block> MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL = tag("mushroom_growing_blocks_lightlevel");

        private static TagKey<Block> tag(@Nonnull String name) {
            return TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation(ExtendedMushrooms.MOD_ID, name));
        }
    }

    public static class OtherModBlocks {
        public static final TagKey<Block> WOOLPLATES_WOOLPLATES = tag("woolplates", "woolplates");

        private static TagKey<Block> tag(@Nonnull String mod, @Nonnull String name) {
            return TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation(mod, name));
        }
    }

    public static class ForgeItems {

        public static final TagKey<Item> MUSHROOM_CAPS = tag("mushroom_caps");
        public static final TagKey<Item> MUSHROOM_CAPS_BROWN = tag("mushroom_caps/brown");
        public static final TagKey<Item> MUSHROOM_CAPS_RED = tag("mushroom_caps/red");
        public static final TagKey<Item> MUSHROOM_CAPS_GLOWSHROOM = tag("mushroom_caps/glowshroom");
        public static final TagKey<Item> MUSHROOM_CAPS_LIME = tag("mushroom_caps/lime");
        public static final TagKey<Item> MUSHROOM_CAPS_ORANGE = tag("mushroom_caps/orange");
        public static final TagKey<Item> MUSHROOM_CAPS_PURPLE = tag("mushroom_caps/purple");
        public static final TagKey<Item> MUSHROOM_CAPS_WHITE = tag("mushroom_caps/white");

        public static final TagKey<Item> MUSHROOM_STEMS = tag("mushroom_stems");
        public static final TagKey<Item> MUSHROOM_STEMS_COLORLESS = tag("mushroom_stems/colorless");
        public static final TagKey<Item> MUSHROOM_STEMS_GLOWSHROOM = tag("mushroom_stems/glowshroom");
        public static final TagKey<Item> MUSHROOM_STEMS_GREEN = tag("mushroom_stems/green");
        public static final TagKey<Item> MUSHROOM_STEMS_ORANGE = tag("mushroom_stems/orange");

        // public static final ITag.INamedTag<Item> MUSHROOMS = tag("mushrooms"); // already there!
        public static final TagKey<Item> MUSHROOMS_BROWN = tag("mushrooms/brown");
        public static final TagKey<Item> MUSHROOMS_RED = tag("mushrooms/red");
        public static final TagKey<Item> MUSHROOMS_GLOWSHROOM = tag("mushrooms/glowshroom");
        public static final TagKey<Item> MUSHROOMS_LIME = tag("mushrooms/lime");
        public static final TagKey<Item> MUSHROOMS_ORANGE = tag("mushrooms/orange");
        public static final TagKey<Item> MUSHROOMS_PURPLE = tag("mushrooms/purple");
        public static final TagKey<Item> MUSHROOMS_WHITE = tag("mushrooms/white");
        public static final TagKey<Item> MUSHROOMS_EDIBLE = tag("mushrooms/edible");
        public static final TagKey<Item> MUSHROOMS_JUMP_BOOSTING = tag("mushrooms/jump_boosting");
        public static final TagKey<Item> MUSHROOMS_POISONOUS = tag("mushrooms/poisonous");
        public static final TagKey<Item> MUSHROOMS_SLOWING_DOWN = tag("mushrooms/slowing_down");

        public static final TagKey<Item> FUNGI = tag("fungi");

        public static final TagKey<Item> BREAD = tag("bread");
        public static final TagKey<Item> RAW_MEAT = tag("raw_meat");

        private static TagKey<Item> tag(@Nonnull String name) {
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", name));
        }
    }

    public static class Items {

        public static final TagKey<Item> FAIRY_RING_MUSHROOMS = tag("fairy_ring_mushrooms");

        public static final TagKey<Item> MUSHROOM_BOATS = tag("mushroom_boats");
        public static final TagKey<Item> MUSHROOM_BUTTONS_WOOD = tag("mushroom_buttons/wood");
        public static final TagKey<Item> MUSHROOM_BUTTONS_WOOL = tag("mushroom_buttons/wool");
        public static final TagKey<Item> MUSHROOM_BUTTONS = tag("mushroom_buttons");
        public static final TagKey<Item> MUSHROOM_CARPETS = tag("mushroom_carpets");
        public static final TagKey<Item> MUSHROOM_CHEST_BOATS = tag("mushroom_chest_boats");
        public static final TagKey<Item> MUSHROOM_DOORS = tag("mushroom_doors");
        public static final TagKey<Item> MUSHROOM_FENCE_GATES = tag("mushroom_fence_gates");
        public static final TagKey<Item> MUSHROOM_FENCES = tag("mushroom_fences");
        public static final TagKey<Item> MUSHROOM_HANGING_SIGNS = tag("mushroom_hanging_signs");
        public static final TagKey<Item> MUSHROOM_PLANKS = tag("mushroom_planks");
        public static final TagKey<Item> MUSHROOM_PRESSURE_PLATES_WOOD = tag("mushroom_pressure_plates/wood");
        public static final TagKey<Item> MUSHROOM_PRESSURE_PLATES_WOOL = tag("mushroom_pressure_plates/wool");
        public static final TagKey<Item> MUSHROOM_PRESSURE_PLATES = tag("mushroom_pressure_plates");
        public static final TagKey<Item> MUSHROOM_SIGNS = tag("mushroom_signs");
        public static final TagKey<Item> MUSHROOM_SLABS = tag("mushroom_slabs");
        public static final TagKey<Item> MUSHROOM_STAIRS = tag("mushroom_stairs");
        public static final TagKey<Item> MUSHROOM_TRAPDOORS = tag("mushroom_trapdoors");

        public static final TagKey<Item> MUSHROOMS = tag("mushrooms");
        public static final TagKey<Item> MUSHROOMS_GLOWSHROOM = tag("mushrooms/glowshroom");
        public static final TagKey<Item> MUSHROOMS_LIME = tag("mushrooms/lime");
        public static final TagKey<Item> MUSHROOMS_ORANGE = tag("mushrooms/orange");
        public static final TagKey<Item> MUSHROOMS_PURPLE = tag("mushrooms/purple");
        public static final TagKey<Item> MUSHROOMS_WHITE = tag("mushrooms/white");
        public static final TagKey<Item> MUSHROOMS_EDIBLE = tag("mushrooms/edible");
        public static final TagKey<Item> MUSHROOMS_JUMP_BOOSTING = tag("mushrooms/jump_boosting");
        public static final TagKey<Item> MUSHROOMS_POISONOUS = tag("mushrooms/poisonous");
        public static final TagKey<Item> MUSHROOMS_SLOWING_DOWN = tag("mushrooms/slowing_down");

        private static TagKey<Item> tag(@Nonnull String name) {
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation(ExtendedMushrooms.MOD_ID, name));
        }

    }

    public static class OtherModItems {

        public static final TagKey<Item> WOOLPLATES_WOOLPLATES = tag("woolplates", "woolplates");

        private static TagKey<Item> tag(@Nonnull String mod, @Nonnull String name) {
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation(mod, name));
        }
    }

}
