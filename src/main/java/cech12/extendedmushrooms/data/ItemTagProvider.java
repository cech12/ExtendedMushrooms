package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.item.ExtendedMushroomsItems;
import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.item.MushroomBoatItem;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.function.Predicate;

public class ItemTagProvider extends ItemTagsProvider {

    public ItemTagProvider(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, ExtendedMushrooms.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        Predicate<Item> extendedMushrooms = item -> ExtendedMushrooms.MOD_ID.equals(item.getRegistryName().getNamespace());

        //generate mod intern tags
        getOrCreateBuilder(ModTags.Items.MUSHROOM_BOATS).add(registry.stream().filter(extendedMushrooms)
                .filter(item -> item instanceof MushroomBoatItem)
                .sorted(Comparator.comparing(Item::getRegistryName))
                .toArray(Item[]::new));
        copy(ModTags.Blocks.MUSHROOM_BOOKSHELVES, ModTags.Items.MUSHROOM_BOOKSHELVES);
        copy(ModTags.Blocks.MUSHROOM_BUTTONS_WOOD, ModTags.Items.MUSHROOM_BUTTONS_WOOD);
        copy(ModTags.Blocks.MUSHROOM_BUTTONS_WOOL, ModTags.Items.MUSHROOM_BUTTONS_WOOL);
        copy(ModTags.Blocks.MUSHROOM_BUTTONS, ModTags.Items.MUSHROOM_BUTTONS);
        copy(ModTags.Blocks.MUSHROOM_CARPETS, ModTags.Items.MUSHROOM_CARPETS);
        copy(ModTags.Blocks.MUSHROOM_CHESTS, ModTags.Items.MUSHROOM_CHESTS);
        copy(ModTags.Blocks.MUSHROOM_CHESTS_TRAPPED, ModTags.Items.MUSHROOM_CHESTS_TRAPPED);
        copy(ModTags.Blocks.MUSHROOM_DOORS, ModTags.Items.MUSHROOM_DOORS);
        copy(ModTags.Blocks.MUSHROOM_FENCE_GATES, ModTags.Items.MUSHROOM_FENCE_GATES);
        copy(ModTags.Blocks.MUSHROOM_FENCES, ModTags.Items.MUSHROOM_FENCES);
        copy(ModTags.Blocks.MUSHROOM_LADDERS, ModTags.Items.MUSHROOM_LADDERS);
        copy(ModTags.Blocks.MUSHROOM_PLANKS, ModTags.Items.MUSHROOM_PLANKS);
        copy(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOD, ModTags.Items.MUSHROOM_PRESSURE_PLATES_WOOD);
        copy(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL, ModTags.Items.MUSHROOM_PRESSURE_PLATES_WOOL);
        copy(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES, ModTags.Items.MUSHROOM_PRESSURE_PLATES);
        copy(ModTags.Blocks.MUSHROOM_SIGNS, ModTags.Items.MUSHROOM_SIGNS);
        copy(ModTags.Blocks.MUSHROOM_SLABS, ModTags.Items.MUSHROOM_SLABS);
        copy(ModTags.Blocks.MUSHROOM_STAIRS, ModTags.Items.MUSHROOM_STAIRS);
        copy(ModTags.Blocks.MUSHROOM_TRAPDOORS, ModTags.Items.MUSHROOM_TRAPDOORS);

        //generate forge tags
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS_BROWN, ModTags.ForgeItems.MUSHROOM_CAPS_BROWN);
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS_RED, ModTags.ForgeItems.MUSHROOM_CAPS_RED);
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS_GLOWSHROOM, ModTags.ForgeItems.MUSHROOM_CAPS_GLOWSHROOM);
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS_PURPLE, ModTags.ForgeItems.MUSHROOM_CAPS_PURPLE);
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS, ModTags.ForgeItems.MUSHROOM_CAPS);
        copy(ModTags.ForgeBlocks.MUSHROOM_STEMS_COLORLESS, ModTags.ForgeItems.MUSHROOM_STEMS_COLORLESS);
        copy(ModTags.ForgeBlocks.MUSHROOM_STEMS_GLOWSHROOM, ModTags.ForgeItems.MUSHROOM_STEMS_GLOWSHROOM);
        copy(ModTags.ForgeBlocks.MUSHROOM_STEMS_GREEN, ModTags.ForgeItems.MUSHROOM_STEMS_GREEN);
        copy(ModTags.ForgeBlocks.MUSHROOM_STEMS, ModTags.ForgeItems.MUSHROOM_STEMS);
        copy(ModTags.ForgeBlocks.MUSHROOMS_BROWN, ModTags.ForgeItems.MUSHROOMS_BROWN);
        copy(ModTags.ForgeBlocks.MUSHROOMS_RED, ModTags.ForgeItems.MUSHROOMS_RED);
        copy(ModTags.ForgeBlocks.MUSHROOMS_GLOWSHROOM, ModTags.ForgeItems.MUSHROOMS_GLOWSHROOM);
        copy(ModTags.ForgeBlocks.MUSHROOMS_PURPLE, ModTags.ForgeItems.MUSHROOMS_PURPLE);
        copy(ModTags.ForgeBlocks.MUSHROOMS, Tags.Items.MUSHROOMS);
        copy(ModTags.Blocks.MUSHROOMS_EDIBLE, ModTags.Items.MUSHROOMS_EDIBLE); // add mod intern edible mushrooms
        copy(ModTags.ForgeBlocks.MUSHROOMS_EDIBLE, ModTags.ForgeItems.MUSHROOMS_EDIBLE);
        copy(ModTags.ForgeBlocks.MUSHROOMS_POISONOUS, ModTags.ForgeItems.MUSHROOMS_POISONOUS);

        getOrCreateBuilder(Tags.Items.BOOKSHELVES).addTag(ModTags.Items.MUSHROOM_BOOKSHELVES);
        copy(Tags.Blocks.CHESTS, Tags.Items.CHESTS);
        copy(Tags.Blocks.CHESTS_TRAPPED, Tags.Items.CHESTS_TRAPPED);
        copy(Tags.Blocks.CHESTS_WOODEN, Tags.Items.CHESTS_WOODEN);
        copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        copy(Tags.Blocks.FENCE_GATES, Tags.Items.FENCE_GATES);

        getOrCreateBuilder(ModTags.ForgeItems.BREAD).add(ExtendedMushroomsItems.MUSHROOM_BREAD);

        //generate minecraft tags
        getOrCreateBuilder(ItemTags.BOATS).addTag(ModTags.Items.MUSHROOM_BOATS);
        copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        copy(BlockTags.CARPETS, ItemTags.CARPETS);
        copy(BlockTags.DOORS, ItemTags.DOORS);
        copy(BlockTags.FENCES, ItemTags.FENCES);
        copy(BlockTags.LOGS, ItemTags.LOGS);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.SIGNS, ItemTags.SIGNS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.WOOL, ItemTags.WOOL);

        //generate tags for mod compatibility
        copy(ModTags.OtherModBlocks.QUARK_LADDERS, ModTags.OtherModItems.QUARK_LADDERS);
        copy(ModTags.OtherModBlocks.WOOLPLATES_WOOLPLATES, ModTags.OtherModItems.WOOLPLATES_WOOLPLATES);

    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms Block Tags";
    }

}
