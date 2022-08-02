package cech12.extendedmushrooms.data.recipes;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.compat.ModFeatureEnabledCondition;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModItems;
import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.item.MushroomType;
import cech12.extendedmushrooms.item.MushroomWoodType;
import com.google.gson.JsonArray;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {

    public RecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static ResourceLocation getResourceLocation(String directory, @Nullable ResourceLocation name) {
        return new ResourceLocation(name.getNamespace(), directory + name.getPath());
    }

    private static ResourceLocation getResourceLocation(String directory, String path) {
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, directory + path);
    }

    private static ResourceLocation getResourceLocation(String path) {
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, path);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {

        //grilled mushroom
        String name = ModItems.GRILLED_MUSHROOM.getId().getPath();
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(ModTags.ForgeItems.MUSHROOMS_EDIBLE), ModItems.GRILLED_MUSHROOM.get(), 0.15F, 150)
                .unlockedBy("has_mushroom", has(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).save(consumer);
        SimpleCookingRecipeBuilder
                .cooking(Ingredient.of(ModTags.ForgeItems.MUSHROOMS_EDIBLE), ModItems.GRILLED_MUSHROOM.get(), 0.15F, 450, RecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy("has_mushroom", has(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).save(consumer, getResourceLocation(name + "_from_campfire_cooking"));
        SimpleCookingRecipeBuilder
                .cooking(Ingredient.of(ModTags.ForgeItems.MUSHROOMS_EDIBLE), ModItems.GRILLED_MUSHROOM.get(), 0.15F, 75, RecipeSerializer.SMOKING_RECIPE)
                .unlockedBy("has_mushroom", has(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).save(consumer, getResourceLocation(name + "_from_smoking"));

        //mushroom bread
        ShapedRecipeBuilder.shaped(ModItems.MUSHROOM_BREAD.get())
                .define('#', ModTags.ForgeItems.MUSHROOMS_EDIBLE)
                .pattern("###")
                .group("bread")
                .unlockedBy("has_mushroom", has(ModTags.ForgeItems.MUSHROOMS_EDIBLE))
                .save(consumer);

        //rabbit stew
        ShapelessRecipeBuilder.shapeless(Items.RABBIT_STEW)
                .requires(Items.BAKED_POTATO)
                .requires(Items.COOKED_RABBIT)
                .requires(Items.BOWL)
                .requires(Items.CARROT)
                .requires(ModTags.Items.MUSHROOMS_EDIBLE) //only mod intern edible mushrooms
                .group("rabbit_stew")
                .unlockedBy("has_cooked_rabbit", has(Items.COOKED_RABBIT))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.RABBIT_STEW).getPath() + "_from_edible_mushroom"));

        //brown dye from infested flower
        ShapelessRecipeBuilder.shapeless(Items.BROWN_DYE)
                .requires(ModBlocks.INFESTED_FLOWER.get())
                .unlockedBy("has_flower", has(ModBlocks.INFESTED_FLOWER.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.BROWN_DYE).getPath() + "_from_infested_flower"));

        //some fairy ring recipes
        FairyRingRecipeBuilder.normal(Items.MYCELIUM, 1)
                .requires(Items.DIRT)
                .save(consumer, getResourceLocation("mycelium_from_dirt"));
        FairyRingRecipeBuilder.normal(Items.FERMENTED_SPIDER_EYE, 1)
                .requires(Items.SUGAR)
                .requires(Items.SPIDER_EYE)
                .save(consumer, getResourceLocation("fermented_spider_eye"));
        FairyRingRecipeBuilder.normal(Items.ROTTEN_FLESH, 1)
                .requires(ModTags.ForgeItems.RAW_MEAT)
                .save(consumer, getResourceLocation("rotten_flesh_from_raw_flesh"));
        FairyRingRecipeBuilder.normal(Items.DEAD_BUSH, 1)
                .requires(ItemTags.SAPLINGS)
                .save(consumer, getResourceLocation("dead_bush_from_sapling"));
        FairyRingRecipeBuilder.normal(ModBlocks.INFESTED_GRASS.get(), 1)
                .requires(Items.GRASS)
                .save(consumer, getResourceLocation("infested_grass_from_grass"));
        FairyRingRecipeBuilder.normal(ModBlocks.INFESTED_FLOWER.get(), 1)
                .requires(ItemTags.FLOWERS)
                .save(consumer, getResourceLocation("infested_flower_from_flower"));

        //mushroom spores
        ShapelessRecipeBuilder.shapeless(ModItems.MUSHROOM_SPORES.get(), 2)
                .requires(Tags.Items.MUSHROOMS)
                .unlockedBy("has_mushroom", has(Tags.Items.MUSHROOMS))
                .save(consumer);

        mushroomWoodRecipes(consumer, "colorless",
                ModTags.ForgeItems.MUSHROOM_STEMS_COLORLESS,
                Blocks.MUSHROOM_STEM.asItem(),
                ModBlocks.STRIPPED_MUSHROOM_STEM.get().asItem(),
                ModItems.MUSHROOM_BOAT.get().asItem(),
                ModBlocks.MUSHROOM_BOOKSHELF.get().asItem(),
                ModBlocks.MUSHROOM_BUTTON.get().asItem(),
                ModBlocks.MUSHROOM_CHEST.get().asItem(),
                ModBlocks.MUSHROOM_CHEST_TRAPPED.get().asItem(),
                ModBlocks.MUSHROOM_DOOR.get().asItem(),
                ModBlocks.MUSHROOM_FENCE.get().asItem(),
                ModBlocks.MUSHROOM_FENCE_GATE.get().asItem(),
                ModBlocks.MUSHROOM_LADDER.get().asItem(),
                ModBlocks.MUSHROOM_PLANKS.get().asItem(),
                ModBlocks.MUSHROOM_PRESSURE_PLATE.get().asItem(),
                ModItems.MUSHROOM_SIGN.get(),
                ModBlocks.MUSHROOM_SLAB.get().asItem(),
                ModBlocks.MUSHROOM_STAIRS.get().asItem(),
                ModBlocks.MUSHROOM_TRAPDOOR.get().asItem(),
                ModBlocks.MUSHROOM_VERTICAL_PLANKS.get().asItem(),
                ModBlocks.MUSHROOM_VERTICAL_SLAB.get().asItem());
        mushroomCapRecipes(consumer, "brown",
                ModTags.ForgeItems.MUSHROOM_CAPS_BROWN,
                Items.BROWN_BANNER,
                Items.BROWN_BED,
                ModBlocks.BROWN_MUSHROOM_BUTTON.get().asItem(),
                ModBlocks.BROWN_MUSHROOM_CARPET.get().asItem(),
                ModBlocks.BROWN_MUSHROOM_PRESSURE_PLATE.get().asItem());
        mushroomCapRecipes(consumer, "red",
                ModTags.ForgeItems.MUSHROOM_CAPS_RED,
                Items.RED_BANNER,
                Items.RED_BED,
                ModBlocks.RED_MUSHROOM_BUTTON.get().asItem(),
                ModBlocks.RED_MUSHROOM_CARPET.get().asItem(),
                ModBlocks.RED_MUSHROOM_PRESSURE_PLATE.get().asItem());

        //glowshroom
        FairyRingRecipeBuilder.normal(ModBlocks.GLOWSHROOM.get(), 1)
                .requires(Tags.Items.MUSHROOMS)
                .requires(Items.GLOWSTONE)
                .requires(Items.GOLDEN_CARROT)
                .requires(Items.LAPIS_LAZULI)
                .save(consumer);
        mushroomWoodRecipes(consumer, "glowshroom",
                ModTags.ForgeItems.MUSHROOM_STEMS_GLOWSHROOM,
                ModBlocks.GLOWSHROOM_STEM.get().asItem(),
                ModBlocks.GLOWSHROOM_STEM_STRIPPED.get().asItem(),
                ModItems.GLOWSHROOM_BOAT.get().asItem(),
                ModBlocks.GLOWSHROOM_BOOKSHELF.get().asItem(),
                ModBlocks.GLOWSHROOM_BUTTON.get().asItem(),
                ModBlocks.GLOWSHROOM_CHEST.get().asItem(),
                ModBlocks.GLOWSHROOM_CHEST_TRAPPED.get().asItem(),
                ModBlocks.GLOWSHROOM_DOOR.get().asItem(),
                ModBlocks.GLOWSHROOM_FENCE.get().asItem(),
                ModBlocks.GLOWSHROOM_FENCE_GATE.get().asItem(),
                ModBlocks.GLOWSHROOM_LADDER.get().asItem(),
                ModBlocks.GLOWSHROOM_PLANKS.get().asItem(),
                ModBlocks.GLOWSHROOM_PRESSURE_PLATE.get().asItem(),
                ModItems.GLOWSHROOM_SIGN.get(),
                ModBlocks.GLOWSHROOM_SLAB.get().asItem(),
                ModBlocks.GLOWSHROOM_STAIRS.get().asItem(),
                ModBlocks.GLOWSHROOM_TRAPDOOR.get().asItem(),
                ModBlocks.GLOWSHROOM_VERTICAL_PLANKS.get().asItem(),
                ModBlocks.GLOWSHROOM_VERTICAL_SLAB.get().asItem());
        mushroomCapRecipes(consumer, "glowshroom",
                ModTags.ForgeItems.MUSHROOM_CAPS_GLOWSHROOM,
                Items.BLUE_BANNER,
                Items.BLUE_BED,
                ModBlocks.GLOWSHROOM_CAP_BUTTON.get().asItem(),
                ModBlocks.GLOWSHROOM_CAP_CARPET.get().asItem(),
                ModBlocks.GLOWSHROOM_CAP_PRESSURE_PLATE.get().asItem());
        //glowstone crumbs recipes
        ShapedRecipeBuilder.shaped(Items.GLOWSTONE_DUST)
                .define('#', ModItems.GLOWSTONE_CRUMBS.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_crumbs", has(ModItems.GLOWSTONE_CRUMBS.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.GLOWSTONE_DUST).getPath()));
        ShapelessRecipeBuilder.shapeless(ModItems.GLOWSTONE_CRUMBS.get(), 4)
                .requires(Items.GLOWSTONE_DUST)
                .unlockedBy("has_dust", has(Items.GLOWSTONE_DUST))
                .save(consumer);

        //poisonous mushroom
        FairyRingRecipeBuilder.normal(ModBlocks.POISONOUS_MUSHROOM.get(), 1)
                .requires(Tags.Items.MUSHROOMS)
                .requires(Items.NETHER_WART)
                .requires(Items.SPIDER_EYE)
                .requires(Items.PURPLE_DYE)
                .save(consumer);
        mushroomWoodRecipes(consumer, "poisonous_mushroom",
                ModTags.ForgeItems.MUSHROOM_STEMS_GREEN,
                ModBlocks.POISONOUS_MUSHROOM_STEM.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_STEM_STRIPPED.get().asItem(),
                ModItems.POISONOUS_MUSHROOM_BOAT.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_BOOKSHELF.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_BUTTON.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_CHEST.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_CHEST_TRAPPED.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_DOOR.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_FENCE.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_FENCE_GATE.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_LADDER.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_PLANKS.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_PRESSURE_PLATE.get().asItem(),
                ModItems.POISONOUS_MUSHROOM_SIGN.get(),
                ModBlocks.POISONOUS_MUSHROOM_SLAB.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_STAIRS.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_TRAPDOOR.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_VERTICAL_PLANKS.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_VERTICAL_SLAB.get().asItem());
        mushroomCapRecipes(consumer, "poisonous_mushroom",
                ModTags.ForgeItems.MUSHROOM_CAPS_PURPLE,
                Items.PURPLE_BANNER,
                Items.PURPLE_BED,
                ModBlocks.POISONOUS_MUSHROOM_CAP_BUTTON.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_CAP_CARPET.get().asItem(),
                ModBlocks.POISONOUS_MUSHROOM_CAP_PRESSURE_PLATE.get().asItem());

        //slime fungus
        FairyRingRecipeBuilder.normal(ModBlocks.SLIME_FUNGUS.get(), 1)
                .requires(Tags.Items.MUSHROOMS)
                .requires(Items.SLIME_BLOCK)
                .requires(Items.RABBIT_FOOT)
                .requires(Items.LIME_DYE)
                .save(consumer);
        mushroomCapRecipes(consumer, "slime_fungus",
                ModTags.ForgeItems.MUSHROOM_CAPS_LIME,
                Items.ORANGE_BANNER,
                Items.ORANGE_BED,
                ModBlocks.SLIME_FUNGUS_CAP_BUTTON.get().asItem(),
                ModBlocks.SLIME_FUNGUS_CAP_CARPET.get().asItem(),
                ModBlocks.SLIME_FUNGUS_CAP_PRESSURE_PLATE.get().asItem());
        //no wood for slime fungus
        //slime blob recipes
        ShapedRecipeBuilder.shaped(Items.SLIME_BALL)
                .define('#', ModItems.SLIME_BLOB.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_blobs", has(ModItems.SLIME_BLOB.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.SLIME_BALL).getPath()));
        ShapelessRecipeBuilder.shapeless(ModItems.SLIME_BLOB.get(), 4)
                .requires(Items.SLIME_BALL)
                .unlockedBy("has_slime", has(Items.SLIME_BALL))
                .save(consumer);

        //honey fungus
        FairyRingRecipeBuilder.normal(ModBlocks.HONEY_FUNGUS.get(), 1)
                .requires(Tags.Items.MUSHROOMS)
                .requires(Items.HONEYCOMB)
                .requires(Items.HONEY_BOTTLE)
                .requires(Items.ORANGE_DYE)
                .save(consumer);
        mushroomWoodRecipes(consumer, "honey_fungus",
                ModTags.ForgeItems.MUSHROOM_STEMS_ORANGE,
                ModBlocks.HONEY_FUNGUS_STEM.get().asItem(),
                ModBlocks.HONEY_FUNGUS_STEM_STRIPPED.get().asItem(),
                ModItems.HONEY_FUNGUS_BOAT.get().asItem(),
                ModBlocks.HONEY_FUNGUS_BOOKSHELF.get().asItem(),
                ModBlocks.HONEY_FUNGUS_BUTTON.get().asItem(),
                ModBlocks.HONEY_FUNGUS_CHEST.get().asItem(),
                ModBlocks.HONEY_FUNGUS_CHEST_TRAPPED.get().asItem(),
                ModBlocks.HONEY_FUNGUS_DOOR.get().asItem(),
                ModBlocks.HONEY_FUNGUS_FENCE.get().asItem(),
                ModBlocks.HONEY_FUNGUS_FENCE_GATE.get().asItem(),
                ModBlocks.HONEY_FUNGUS_LADDER.get().asItem(),
                ModBlocks.HONEY_FUNGUS_PLANKS.get().asItem(),
                ModBlocks.HONEY_FUNGUS_PRESSURE_PLATE.get().asItem(),
                ModItems.HONEY_FUNGUS_SIGN.get(),
                ModBlocks.HONEY_FUNGUS_SLAB.get().asItem(),
                ModBlocks.HONEY_FUNGUS_STAIRS.get().asItem(),
                ModBlocks.HONEY_FUNGUS_TRAPDOOR.get().asItem(),
                ModBlocks.HONEY_FUNGUS_VERTICAL_PLANKS.get().asItem(),
                ModBlocks.HONEY_FUNGUS_VERTICAL_SLAB.get().asItem());
        mushroomCapRecipes(consumer, "honey_fungus",
                ModTags.ForgeItems.MUSHROOM_CAPS_ORANGE,
                Items.ORANGE_BANNER,
                Items.ORANGE_BED,
                ModBlocks.HONEY_FUNGUS_CAP_BUTTON.get().asItem(),
                ModBlocks.HONEY_FUNGUS_CAP_CARPET.get().asItem(),
                ModBlocks.HONEY_FUNGUS_CAP_PRESSURE_PLATE.get().asItem());
        //honey blob recipes
        ShapelessRecipeBuilder.shapeless(Items.HONEY_BOTTLE, 1)
                .requires(Items.GLASS_BOTTLE)
                .requires(ModItems.HONEY_BLOB.get(), 3)
                .unlockedBy("has_honey_blob", has(ModItems.HONEY_BLOB.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.HONEY_BOTTLE).getPath()));
        ShapelessRecipeBuilder.shapeless(Items.SUGAR)
                .requires(ModItems.HONEY_BLOB.get())
                .unlockedBy("has_honey_blob", has(ModItems.HONEY_BLOB.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.SUGAR).getPath()));
        //honeycomb shred recipes
        ShapedRecipeBuilder.shaped(Items.HONEYCOMB)
                .define('#', ModItems.HONEYCOMB_SHRED.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_shreds", has(ModItems.HONEYCOMB_SHRED.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.HONEYCOMB).getPath()));
        ShapelessRecipeBuilder.shapeless(ModItems.HONEYCOMB_SHRED.get(), 4)
                .requires(Items.HONEYCOMB)
                .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                .save(consumer);

        //botany pots
        for (MushroomType mushroomType : MushroomType.values()) {
            if (mushroomType == MushroomType.BROWN_MUSHROOM || mushroomType == MushroomType.RED_MUSHROOM) {
                continue;
            }
            BotanyPotsCropBuilder.create(mushroomType.getItem(), mushroomType.getLightValue()).save(consumer);
            BotanyPotsSoilBuilder.create(mushroomType.getCapBlock(), mushroomType.getLightValue()).save(consumer);
        }
        for (MushroomWoodType mushroomWoodType : MushroomWoodType.values()) {
            if (mushroomWoodType == MushroomWoodType.MUSHROOM) {
                continue;
            }
            BotanyPotsSoilBuilder.create(mushroomWoodType.getStemBlock(), mushroomWoodType.getLightValue()).save(consumer);
        }
    }


    private void mushroomWoodRecipes(Consumer<FinishedRecipe> consumer, String name, TagKey<Item> stems,
                                     Item stem, Item strippedStem, Item boat,
                                     Item bookshelf, Item button, Item chest, Item chestTrapped, Item door, Item fence,
                                     Item fenceGate, Item ladder, Item planks, Item pressurePlate, Item sign, Item slab,
                                     Item stairs, Item trapdoor, Item verticalPlanks, Item verticalSlab) {
        String directory = "mushroom_wood/" + name + "/";
        ShapedRecipeBuilder.shaped(boat)
                .define('#', planks)
                .pattern("# #")
                .pattern("###")
                .group("boat")
                .unlockedBy("in_water", insideOf(Blocks.WATER))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(boat)));
        ShapelessRecipeBuilder.shapeless(button)
                .requires(planks)
                .group("wooden_button")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(button)));
        ShapedRecipeBuilder.shaped(door, 3)
                .define('#', planks)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .group("wooden_door")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(door)));
        ShapedRecipeBuilder.shaped(fence, 3)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', planks)
                .pattern("W#W")
                .pattern("W#W")
                .group("wooden_fence")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(fence)));
        ShapedRecipeBuilder.shaped(fenceGate)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', planks)
                .pattern("#W#")
                .pattern("#W#")
                .group("wooden_fence_gate")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(fenceGate)));
        ShapelessRecipeBuilder.shapeless(planks, 4)
                .requires(stems)
                .group("planks")
                .unlockedBy("has_logs", has(stems))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(planks)));
        ShapedRecipeBuilder.shaped(pressurePlate)
                .define('#', planks)
                .pattern("##")
                .group("wooden_pressure_plate")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(pressurePlate)));
        ShapedRecipeBuilder.shaped(sign, 3)
                .define('#', planks)
                .define('|', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("###")
                .pattern(" | ")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(sign)));
        ShapedRecipeBuilder.shaped(slab, 6)
                .define('#', planks)
                .pattern("###")
                .group("wooden_slab")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(slab)));
        ShapedRecipeBuilder.shaped(stairs, 4)
                .define('#', planks)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .group("wooden_stairs")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(stairs)));
        ShapedRecipeBuilder.shaped(trapdoor, 2)
                .define('#', planks)
                .pattern("###")
                .pattern("###")
                .group("wooden_trapdoor")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(trapdoor)));

        //recipes that are only active when other mods are installed
        ShapedRecipeBuilder.shaped(bookshelf)
                .define('#', planks)
                .define('X', Items.BOOK)
                .pattern("###")
                .pattern("XXX")
                .pattern("###")
                .unlockedBy("has_book", has(Items.BOOK))
                .save(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("variantBookshelves")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(bookshelf)));
        ShapedRecipeBuilder.shaped(chest)
                .define('#', planks)
                .define('|', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .group("mushroom_chest")
                .unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0]))
                .save(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("variantChests")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(chest)));
        ShapedRecipeBuilder.shaped(chestTrapped)
                .define('#', planks)
                .define('|', Items.TRIPWIRE_HOOK)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .unlockedBy("has_tripwire_hook", has(Items.TRIPWIRE_HOOK))
                .group("mushroom_trapped_chest")
                .save(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("variantTrappedChests")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(chestTrapped)));
        ShapedRecipeBuilder.shaped(ladder, 4)
                .define('#', planks)
                .define('|', Tags.Items.RODS_WOODEN)
                .pattern("| |")
                .pattern("|#|")
                .pattern("| |")
                .unlockedBy("has_stick", has(Tags.Items.RODS_WOODEN))
                .save(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("variantLadders")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(ladder)));
        ShapedRecipeBuilder.shaped(verticalPlanks, 3)
                .define('#', planks)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_planks", has(planks))
                .save(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalPlanks")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(verticalPlanks)));
        ShapelessRecipeBuilder.shapeless(planks)
                .requires(verticalPlanks)
                .unlockedBy("has_vertical_planks", has(verticalPlanks))
                .save(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalPlanks")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(verticalPlanks).getPath() + "_revert"));
        ShapedRecipeBuilder.shaped(verticalSlab, 3)
                .define('#', slab)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_slab", has(slab))
                .save(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalSlabs")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(verticalSlab)));
        ShapelessRecipeBuilder.shapeless(slab)
                .requires(verticalSlab)
                .unlockedBy("has_vertical_slab", has(verticalSlab))
                .save(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalSlabs")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(verticalSlab).getPath() + "_revert"));

        //wood cutting
        String woodcuttingDirectory = directory + "woodcutting/";
        WoodcutterRecipeBuilder.woodcutterRecipe(boat, Ingredient.of(stems))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "boat_from_stem"));
        WoodcutterRecipeBuilder.woodcutterRecipe(button, Ingredient.of(stems), 4)
                .build(consumer, getResourceLocation(woodcuttingDirectory, "button_from_stem"));
        WoodcutterRecipeBuilder.woodcutterRecipe(door, Ingredient.of(stems), 4)
                .build(consumer, getResourceLocation(woodcuttingDirectory, "door_from_stem"));
        WoodcutterRecipeBuilder.woodcutterRecipe(fence, Ingredient.of(stems), 4)
                .build(consumer, getResourceLocation(woodcuttingDirectory, "fence_from_stem"));
        WoodcutterRecipeBuilder.woodcutterRecipe(fenceGate, Ingredient.of(stems))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "fence_gate_from_stem"));
        WoodcutterRecipeBuilder.woodcutterRecipe(planks, Ingredient.of(stems), 4)
                .build(consumer, getResourceLocation(woodcuttingDirectory, "planks_from_stem"));
        WoodcutterRecipeBuilder.woodcutterRecipe(pressurePlate, Ingredient.of(stems), 4)
                .build(consumer, getResourceLocation(woodcuttingDirectory, "pressure_plate_from_stem"));
        WoodcutterRecipeBuilder.woodcutterRecipe(sign, Ingredient.of(stems), 4)
                .build(consumer, getResourceLocation(woodcuttingDirectory, "sign_from_stem"));
        WoodcutterRecipeBuilder.woodcutterRecipe(slab, Ingredient.of(stems), 8)
                .build(consumer, getResourceLocation(woodcuttingDirectory, "slab_from_stem"));
        WoodcutterRecipeBuilder.woodcutterRecipe(stairs, Ingredient.of(stems), 4)
                .build(consumer, getResourceLocation(woodcuttingDirectory, "stairs_from_stem"));
        WoodcutterRecipeBuilder.woodcutterRecipe(trapdoor, Ingredient.of(stems), 4)
                .build(consumer, getResourceLocation(woodcuttingDirectory, "trapdoor_from_stem"));

        WoodcutterRecipeBuilder.woodcutterRecipe(button, Ingredient.of(planks))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "button_from_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(door, Ingredient.of(planks))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "door_from_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(fence, Ingredient.of(planks))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "fence_from_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(pressurePlate, Ingredient.of(planks))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "pressure_plate_from_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(sign, Ingredient.of(planks))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "sign_from_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(slab, Ingredient.of(planks), 2)
                .build(consumer, getResourceLocation(woodcuttingDirectory, "slab_from_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(stairs, Ingredient.of(planks))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "stairs_from_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(trapdoor, Ingredient.of(planks))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "trapdoor_from_planks"));

        //wood cutting recipes that are only active when other mods are installed
        WoodcutterRecipeBuilder.woodcutterRecipe(verticalPlanks, Ingredient.of(stems), 4)
                .addCondition(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalPlanks")))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "vertical_planks_from_stem"));

        WoodcutterRecipeBuilder.woodcutterRecipe(verticalPlanks, Ingredient.of(planks))
                .addCondition(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalPlanks")))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "vertical_planks_from_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(planks, Ingredient.of(verticalPlanks))
                .addCondition(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalPlanks")))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "planks_from_vertical_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(verticalSlab, Ingredient.of(planks), 2)
                .addCondition(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalSlabs")))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "vertical_slab_from_planks"));
    }

    private void mushroomCapRecipes(Consumer<FinishedRecipe> consumer, String name, TagKey<Item> caps, Item banner,
                                    Item bed, Item button, Item carpet, Item pressure_plate) {
        String directory = "mushroom_cap/" + name + "/";
        ShapedRecipeBuilder.shaped(banner)
                .define('#', caps)
                .define('|', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("###")
                .pattern(" | ")
                .group("banner")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(banner).getPath()));
        ShapedRecipeBuilder.shaped(bed)
                .define('#', caps)
                .define('W', ItemTags.PLANKS)
                .pattern("###")
                .pattern("WWW")
                .group("bed")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(bed).getPath()));
        ShapelessRecipeBuilder.shapeless(button)
                .requires(caps)
                .group("wool_buttons")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(button)));
        ShapedRecipeBuilder.shaped(carpet, 3)
                .define('#', caps)
                .pattern("##")
                .group("carpet")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(carpet)));
        ShapelessRecipeBuilder.shapeless(pressure_plate)
                .requires(ItemTags.WOODEN_PRESSURE_PLATES)
                .requires(caps)
                .group("wool_plates")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(pressure_plate)));
    }

}
