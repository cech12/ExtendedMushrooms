package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModItems;
import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.item.MushroomBoatItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider {

    public ItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> tagsProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, tagsProvider, ExtendedMushrooms.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider lookupProvider) {
        //generate mod intern tags
        tag(ModTags.Items.FAIRY_RING_MUSHROOMS).addTag(Tags.Items.MUSHROOMS).addTag(ModTags.ForgeItems.FUNGI);
        copy(ModTags.Blocks.MUSHROOMS_GLOWSHROOM, ModTags.Items.MUSHROOMS_GLOWSHROOM);
        copy(ModTags.Blocks.MUSHROOMS_LIME, ModTags.Items.MUSHROOMS_LIME);
        copy(ModTags.Blocks.MUSHROOMS_ORANGE, ModTags.Items.MUSHROOMS_ORANGE);
        copy(ModTags.Blocks.MUSHROOMS_PURPLE, ModTags.Items.MUSHROOMS_PURPLE);
        copy(ModTags.Blocks.MUSHROOMS, ModTags.Items.MUSHROOMS);
        copy(ModTags.Blocks.MUSHROOMS_EDIBLE, ModTags.Items.MUSHROOMS_EDIBLE);
        tag(ModTags.Items.MUSHROOM_HANGING_SIGNS).add(ModItems.ITEMS.getEntries().stream().map(RegistryObject::get)
                .filter(item -> item instanceof HangingSignItem)
                .sorted(Comparator.comparing(ForgeRegistries.ITEMS::getKey))
                .toArray(Item[]::new));
        copy(ModTags.Blocks.MUSHROOMS_JUMP_BOOSTING, ModTags.Items.MUSHROOMS_JUMP_BOOSTING);
        copy(ModTags.Blocks.MUSHROOMS_POISONOUS, ModTags.Items.MUSHROOMS_POISONOUS);
        copy(ModTags.Blocks.MUSHROOMS_SLOWING_DOWN, ModTags.Items.MUSHROOMS_SLOWING_DOWN);
        tag(ModTags.Items.MUSHROOM_BOATS).add(ModItems.ITEMS.getEntries().stream().map(RegistryObject::get)
                .filter(item -> item instanceof MushroomBoatItem mushroomBoatItem && !mushroomBoatItem.hasChest())
                .sorted(Comparator.comparing(ForgeRegistries.ITEMS::getKey))
                .toArray(Item[]::new));
        copy(ModTags.Blocks.MUSHROOM_BUTTONS_WOOD, ModTags.Items.MUSHROOM_BUTTONS_WOOD);
        copy(ModTags.Blocks.MUSHROOM_BUTTONS_WOOL, ModTags.Items.MUSHROOM_BUTTONS_WOOL);
        copy(ModTags.Blocks.MUSHROOM_BUTTONS, ModTags.Items.MUSHROOM_BUTTONS);
        copy(ModTags.Blocks.MUSHROOM_CARPETS, ModTags.Items.MUSHROOM_CARPETS);
        tag(ModTags.Items.MUSHROOM_CHEST_BOATS).add(ModItems.ITEMS.getEntries().stream().map(RegistryObject::get)
                .filter(item -> item instanceof MushroomBoatItem mushroomBoatItem && mushroomBoatItem.hasChest())
                .sorted(Comparator.comparing(ForgeRegistries.ITEMS::getKey))
                .toArray(Item[]::new));
        copy(ModTags.Blocks.MUSHROOM_DOORS, ModTags.Items.MUSHROOM_DOORS);
        copy(ModTags.Blocks.MUSHROOM_FENCE_GATES, ModTags.Items.MUSHROOM_FENCE_GATES);
        copy(ModTags.Blocks.MUSHROOM_FENCES, ModTags.Items.MUSHROOM_FENCES);
        copy(ModTags.Blocks.MUSHROOM_PLANKS, ModTags.Items.MUSHROOM_PLANKS);
        copy(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOD, ModTags.Items.MUSHROOM_PRESSURE_PLATES_WOOD);
        copy(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL, ModTags.Items.MUSHROOM_PRESSURE_PLATES_WOOL);
        copy(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES, ModTags.Items.MUSHROOM_PRESSURE_PLATES);
        tag(ModTags.Items.MUSHROOM_SIGNS).add(ModItems.ITEMS.getEntries().stream().map(RegistryObject::get)
                .filter(item -> item instanceof SignItem && !(item instanceof HangingSignItem))
                .sorted(Comparator.comparing(ForgeRegistries.ITEMS::getKey))
                .toArray(Item[]::new));
        copy(ModTags.Blocks.MUSHROOM_SLABS, ModTags.Items.MUSHROOM_SLABS);
        copy(ModTags.Blocks.MUSHROOM_STAIRS, ModTags.Items.MUSHROOM_STAIRS);
        copy(ModTags.Blocks.MUSHROOM_TRAPDOORS, ModTags.Items.MUSHROOM_TRAPDOORS);

        //generate forge tags
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS_BROWN, ModTags.ForgeItems.MUSHROOM_CAPS_BROWN);
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS_RED, ModTags.ForgeItems.MUSHROOM_CAPS_RED);
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS_GLOWSHROOM, ModTags.ForgeItems.MUSHROOM_CAPS_GLOWSHROOM);
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS_LIME, ModTags.ForgeItems.MUSHROOM_CAPS_LIME);
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS_ORANGE, ModTags.ForgeItems.MUSHROOM_CAPS_ORANGE);
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS_PURPLE, ModTags.ForgeItems.MUSHROOM_CAPS_PURPLE);
        copy(ModTags.ForgeBlocks.MUSHROOM_CAPS, ModTags.ForgeItems.MUSHROOM_CAPS);
        copy(ModTags.ForgeBlocks.MUSHROOM_STEMS_COLORLESS, ModTags.ForgeItems.MUSHROOM_STEMS_COLORLESS);
        copy(ModTags.ForgeBlocks.MUSHROOM_STEMS_GLOWSHROOM, ModTags.ForgeItems.MUSHROOM_STEMS_GLOWSHROOM);
        copy(ModTags.ForgeBlocks.MUSHROOM_STEMS_GREEN, ModTags.ForgeItems.MUSHROOM_STEMS_GREEN);
        copy(ModTags.ForgeBlocks.MUSHROOM_STEMS_ORANGE, ModTags.ForgeItems.MUSHROOM_STEMS_ORANGE);
        copy(ModTags.ForgeBlocks.MUSHROOM_STEMS, ModTags.ForgeItems.MUSHROOM_STEMS);
        copy(ModTags.ForgeBlocks.MUSHROOMS_BROWN, ModTags.ForgeItems.MUSHROOMS_BROWN);
        copy(ModTags.ForgeBlocks.MUSHROOMS_RED, ModTags.ForgeItems.MUSHROOMS_RED);
        copy(ModTags.ForgeBlocks.MUSHROOMS_GLOWSHROOM, ModTags.ForgeItems.MUSHROOMS_GLOWSHROOM);
        copy(ModTags.ForgeBlocks.MUSHROOMS_LIME, ModTags.ForgeItems.MUSHROOMS_LIME);
        copy(ModTags.ForgeBlocks.MUSHROOMS_ORANGE, ModTags.ForgeItems.MUSHROOMS_ORANGE);
        copy(ModTags.ForgeBlocks.MUSHROOMS_PURPLE, ModTags.ForgeItems.MUSHROOMS_PURPLE);
        copy(ModTags.ForgeBlocks.MUSHROOMS, Tags.Items.MUSHROOMS);
        copy(ModTags.ForgeBlocks.MUSHROOMS_EDIBLE, ModTags.ForgeItems.MUSHROOMS_EDIBLE);
        copy(ModTags.ForgeBlocks.MUSHROOMS_JUMP_BOOSTING, ModTags.ForgeItems.MUSHROOMS_JUMP_BOOSTING);
        copy(ModTags.ForgeBlocks.MUSHROOMS_POISONOUS, ModTags.ForgeItems.MUSHROOMS_POISONOUS);
        copy(ModTags.ForgeBlocks.MUSHROOMS_SLOWING_DOWN, ModTags.ForgeItems.MUSHROOMS_SLOWING_DOWN);

        copy(ModTags.ForgeBlocks.FUNGI, ModTags.ForgeItems.FUNGI);

        copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);

        tag(ModTags.ForgeItems.BREAD).add(ModItems.MUSHROOM_BREAD.get());
        tag(ModTags.ForgeItems.RAW_MEAT).add(Items.BEEF).add(Items.CHICKEN).add(Items.MUTTON).add(Items.PORKCHOP).add(Items.RABBIT);

        //generate minecraft tags
        tag(ItemTags.BOATS).addTag(ModTags.Items.MUSHROOM_BOATS);
        copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        tag(ItemTags.CHEST_BOATS).addTag(ModTags.Items.MUSHROOM_CHEST_BOATS);
        copy(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
        copy(BlockTags.DOORS, ItemTags.DOORS);
        copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        tag(ItemTags.HANGING_SIGNS).addTag(ModTags.Items.MUSHROOM_HANGING_SIGNS);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        tag(ItemTags.SIGNS).addTag(ModTags.Items.MUSHROOM_SIGNS);
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
        copy(ModTags.OtherModBlocks.WOOLPLATES_WOOLPLATES, ModTags.OtherModItems.WOOLPLATES_WOOLPLATES);

    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms Item Tags";
    }

}
