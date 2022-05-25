package cech12.extendedmushrooms.data.recipes;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.api.item.ExtendedMushroomsItems;
import cech12.extendedmushrooms.compat.ModFeatureEnabledCondition;
import cech12.extendedmushrooms.init.ModTags;
import com.google.gson.JsonArray;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.RecipeProvider {

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
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {

        //grilled mushroom
        String name = ExtendedMushroomsItems.GRILLED_MUSHROOM.getRegistryName().getPath();
        CookingRecipeBuilder
                .smelting(Ingredient.of(ModTags.ForgeItems.MUSHROOMS_EDIBLE), ExtendedMushroomsItems.GRILLED_MUSHROOM, 0.15F, 150)
                .unlockedBy("has_mushroom", has(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).save(consumer);
        CookingRecipeBuilder
                .cooking(Ingredient.of(ModTags.ForgeItems.MUSHROOMS_EDIBLE), ExtendedMushroomsItems.GRILLED_MUSHROOM, 0.15F, 450, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy("has_mushroom", has(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).save(consumer, getResourceLocation(name + "_from_campfire_cooking"));
        CookingRecipeBuilder
                .cooking(Ingredient.of(ModTags.ForgeItems.MUSHROOMS_EDIBLE), ExtendedMushroomsItems.GRILLED_MUSHROOM, 0.15F, 75, IRecipeSerializer.SMOKING_RECIPE)
                .unlockedBy("has_mushroom", has(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).save(consumer, getResourceLocation(name + "_from_smoking"));

        //mushroom bread
        ShapedRecipeBuilder.shaped(ExtendedMushroomsItems.MUSHROOM_BREAD)
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
                .save(consumer, getResourceLocation(Items.RABBIT_STEW.getRegistryName().getPath() + "_from_edible_mushroom"));

        //brown dye from infested flower
        ShapelessRecipeBuilder.shapeless(Items.BROWN_DYE)
                .requires(ExtendedMushroomsBlocks.INFESTED_FLOWER)
                .unlockedBy("has_flower", has(ExtendedMushroomsBlocks.INFESTED_FLOWER))
                .save(consumer, getResourceLocation(Items.BROWN_DYE.getRegistryName().getPath() + "_from_infested_flower"));

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
        FairyRingRecipeBuilder.normal(ExtendedMushroomsBlocks.INFESTED_GRASS, 1)
                .requires(Items.GRASS)
                .save(consumer, getResourceLocation("infested_grass_from_grass"));
        FairyRingRecipeBuilder.normal(ExtendedMushroomsBlocks.INFESTED_FLOWER, 1)
                .requires(ItemTags.FLOWERS)
                .save(consumer, getResourceLocation("infested_flower_from_flower"));

        //mushroom spores
        ShapelessRecipeBuilder.shapeless(ExtendedMushroomsItems.MUSHROOM_SPORES, 2)
                .requires(Tags.Items.MUSHROOMS)
                .unlockedBy("has_mushroom", has(Tags.Items.MUSHROOMS))
                .save(consumer);

        mushroomWoodRecipes(consumer, "colorless",
                ModTags.ForgeItems.MUSHROOM_STEMS_COLORLESS,
                Blocks.MUSHROOM_STEM.asItem(),
                ExtendedMushroomsBlocks.STRIPPED_MUSHROOM_STEM.asItem(),
                ExtendedMushroomsItems.MUSHROOM_BOAT.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_BOOKSHELF.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_BUTTON.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_CHEST.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_CHEST_TRAPPED.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_DOOR.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_FENCE.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_FENCE_GATE.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_LADDER.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_PLANKS.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_PRESSURE_PLATE.asItem(),
                ExtendedMushroomsItems.MUSHROOM_SIGN,
                ExtendedMushroomsBlocks.MUSHROOM_SLAB.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_STAIRS.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_TRAPDOOR.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_VERTICAL_PLANKS.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_VERTICAL_SLAB.asItem());
        mushroomCapRecipes(consumer, "brown",
                ModTags.ForgeItems.MUSHROOM_CAPS_BROWN,
                Items.BROWN_BANNER,
                Items.BROWN_BED,
                ExtendedMushroomsBlocks.BROWN_MUSHROOM_BUTTON.asItem(),
                ExtendedMushroomsBlocks.BROWN_MUSHROOM_CARPET.asItem(),
                ExtendedMushroomsBlocks.BROWN_MUSHROOM_PRESSURE_PLATE.asItem());
        mushroomCapRecipes(consumer, "red",
                ModTags.ForgeItems.MUSHROOM_CAPS_RED,
                Items.RED_BANNER,
                Items.RED_BED,
                ExtendedMushroomsBlocks.RED_MUSHROOM_BUTTON.asItem(),
                ExtendedMushroomsBlocks.RED_MUSHROOM_CARPET.asItem(),
                ExtendedMushroomsBlocks.RED_MUSHROOM_PRESSURE_PLATE.asItem());

        //glowshroom
        FairyRingRecipeBuilder.normal(ExtendedMushroomsBlocks.GLOWSHROOM, 1)
                .requires(Tags.Items.MUSHROOMS)
                .requires(Items.GLOWSTONE)
                .requires(Items.GOLDEN_CARROT)
                .requires(Items.LAPIS_LAZULI)
                .save(consumer);
        mushroomWoodRecipes(consumer, "glowshroom",
                ModTags.ForgeItems.MUSHROOM_STEMS_GLOWSHROOM,
                ExtendedMushroomsBlocks.GLOWSHROOM_STEM.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_STEM_STRIPPED.asItem(),
                ExtendedMushroomsItems.GLOWSHROOM_BOAT.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_BOOKSHELF.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_BUTTON.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_CHEST.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_CHEST_TRAPPED.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_DOOR.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_FENCE.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_FENCE_GATE.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_LADDER.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_PLANKS.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_PRESSURE_PLATE.asItem(),
                ExtendedMushroomsItems.GLOWSHROOM_SIGN,
                ExtendedMushroomsBlocks.GLOWSHROOM_SLAB.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_STAIRS.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_TRAPDOOR.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_VERTICAL_PLANKS.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_VERTICAL_SLAB.asItem());
        mushroomCapRecipes(consumer, "glowshroom",
                ModTags.ForgeItems.MUSHROOM_CAPS_GLOWSHROOM,
                Items.BLUE_BANNER,
                Items.BLUE_BED,
                ExtendedMushroomsBlocks.GLOWSHROOM_CAP_BUTTON.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_CAP_CARPET.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_CAP_PRESSURE_PLATE.asItem());
        //glowstone crumbs recipes
        ShapedRecipeBuilder.shaped(Items.GLOWSTONE_DUST)
                .define('#', ExtendedMushroomsItems.GLOWSTONE_CRUMBS)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_crumbs", has(ExtendedMushroomsItems.GLOWSTONE_CRUMBS))
                .save(consumer, getResourceLocation(Items.GLOWSTONE_DUST.getRegistryName().getPath()));
        ShapelessRecipeBuilder.shapeless(ExtendedMushroomsItems.GLOWSTONE_CRUMBS, 4)
                .requires(Items.GLOWSTONE_DUST)
                .unlockedBy("has_dust", has(Items.GLOWSTONE_DUST))
                .save(consumer);

        //poisonous mushroom
        FairyRingRecipeBuilder.normal(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM, 1)
                .requires(Tags.Items.MUSHROOMS)
                .requires(Items.NETHER_WART)
                .requires(Items.SPIDER_EYE)
                .requires(Items.PURPLE_DYE)
                .save(consumer);
        mushroomWoodRecipes(consumer, "poisonous_mushroom",
                ModTags.ForgeItems.MUSHROOM_STEMS_GREEN,
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_STEM.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_STEM_STRIPPED.asItem(),
                ExtendedMushroomsItems.POISONOUS_MUSHROOM_BOAT.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_BOOKSHELF.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_BUTTON.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CHEST.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CHEST_TRAPPED.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_DOOR.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_FENCE.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_FENCE_GATE.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_LADDER.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_PLANKS.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_PRESSURE_PLATE.asItem(),
                ExtendedMushroomsItems.POISONOUS_MUSHROOM_SIGN,
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_SLAB.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_STAIRS.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_TRAPDOOR.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_VERTICAL_PLANKS.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_VERTICAL_SLAB.asItem());
        mushroomCapRecipes(consumer, "poisonous_mushroom",
                ModTags.ForgeItems.MUSHROOM_CAPS_PURPLE,
                Items.PURPLE_BANNER,
                Items.PURPLE_BED,
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CAP_BUTTON.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CAP_CARPET.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CAP_PRESSURE_PLATE.asItem());

        //wood cutting
        //String woodcuttingDirectory = "mushroom_wood/woodcutting/";
        //WoodcutterRecipeBuilder.woodcutterRecipe(Items.STICK, Ingredient.fromTag(ModTags.Items.MUSHROOM_PLANKS), 2)
        //        .build(consumer, getResourceLocation(woodcuttingDirectory, "stick_from_planks"));
    }


    private void mushroomWoodRecipes(Consumer<IFinishedRecipe> consumer, String name, ITag.INamedTag<Item> stems,
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
                .save(consumer, getResourceLocation(directory, boat.getRegistryName()));
        ShapelessRecipeBuilder.shapeless(button)
                .requires(planks)
                .group("wooden_button")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, button.getRegistryName()));
        ShapedRecipeBuilder.shaped(door, 3)
                .define('#', planks)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .group("wooden_door")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, door.getRegistryName()));
        ShapedRecipeBuilder.shaped(fence, 3)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', planks)
                .pattern("W#W")
                .pattern("W#W")
                .group("wooden_fence")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, fence.getRegistryName()));
        ShapedRecipeBuilder.shaped(fenceGate)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', planks)
                .pattern("#W#")
                .pattern("#W#")
                .group("wooden_fence_gate")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, fenceGate.getRegistryName()));
        ShapelessRecipeBuilder.shapeless(planks, 4)
                .requires(stems)
                .group("planks")
                .unlockedBy("has_logs", has(stems))
                .save(consumer, getResourceLocation(directory, planks.getRegistryName()));
        ShapedRecipeBuilder.shaped(pressurePlate)
                .define('#', planks)
                .pattern("##")
                .group("wooden_pressure_plate")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, pressurePlate.getRegistryName()));
        ShapedRecipeBuilder.shaped(sign, 3)
                .define('#', planks)
                .define('|', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("###")
                .pattern(" | ")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, sign.getRegistryName()));
        ShapedRecipeBuilder.shaped(slab, 6)
                .define('#', planks)
                .pattern("###")
                .group("wooden_slab")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, slab.getRegistryName()));
        ShapedRecipeBuilder.shaped(stairs, 4)
                .define('#', planks)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .group("wooden_stairs")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, stairs.getRegistryName()));
        ShapedRecipeBuilder.shaped(trapdoor, 2)
                .define('#', planks)
                .pattern("###")
                .pattern("###")
                .group("wooden_trapdoor")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, trapdoor.getRegistryName()));

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
                }), getResourceLocation(directory, bookshelf.getRegistryName()));
        ShapedRecipeBuilder.shaped(chest)
                .define('#', planks)
                .define('|', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .group("mushroom_chest")
                .unlockedBy("has_lots_of_items", new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY, MinMaxBounds.IntBound.atLeast(10), MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, new ItemPredicate[0]))
                .save(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("variantChests")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, chest.getRegistryName()));
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
                }), getResourceLocation(directory, chestTrapped.getRegistryName()));
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
                }), getResourceLocation(directory, ladder.getRegistryName()));
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
                }), getResourceLocation(directory, verticalPlanks.getRegistryName()));
        ShapelessRecipeBuilder.shapeless(planks)
                .requires(verticalPlanks)
                .unlockedBy("has_vertical_planks", has(verticalPlanks))
                .save(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalPlanks")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, verticalPlanks.getRegistryName().getPath() + "_revert"));
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
                }), getResourceLocation(directory, verticalSlab.getRegistryName()));
        ShapelessRecipeBuilder.shapeless(slab)
                .requires(verticalSlab)
                .unlockedBy("has_vertical_slab", has(verticalSlab))
                .save(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalSlabs")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, verticalSlab.getRegistryName().getPath() + "_revert"));

        //wood cutting
        String woodcuttingDirectory = directory + "woodcutting/";
        WoodcutterRecipeBuilder.woodcutterRecipe(planks, Ingredient.of(stems), 6)
                .build(consumer, getResourceLocation(woodcuttingDirectory, "planks_from_stem"));
        WoodcutterRecipeBuilder.woodcutterRecipe(strippedStem, Ingredient.of(stem))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "stripped_stem_from_stem"));

        WoodcutterRecipeBuilder.woodcutterRecipe(button, Ingredient.of(planks))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "button_from_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(door, Ingredient.of(planks))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "door_from_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(fence, Ingredient.of(planks))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "fence_from_planks"));
        WoodcutterRecipeBuilder.woodcutterRecipe(fenceGate, Ingredient.of(planks))
                .build(consumer, getResourceLocation(woodcuttingDirectory, "fence_gate_from_planks"));
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
        WoodcutterRecipeBuilder.woodcutterRecipe(verticalPlanks, Ingredient.of(stems), 6)
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

    private void mushroomCapRecipes(Consumer<IFinishedRecipe> consumer, String name, ITag.INamedTag<Item> caps, Item banner,
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
                .save(consumer, getResourceLocation(directory, banner.getRegistryName().getPath()));
        ShapedRecipeBuilder.shaped(bed)
                .define('#', caps)
                .define('W', ItemTags.PLANKS)
                .pattern("###")
                .pattern("WWW")
                .group("bed")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, bed.getRegistryName().getPath()));
        ShapelessRecipeBuilder.shapeless(button)
                .requires(caps)
                .group("wool_buttons")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, button.getRegistryName()));
        ShapedRecipeBuilder.shaped(carpet, 3)
                .define('#', caps)
                .pattern("##")
                .group("carpet")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, carpet.getRegistryName()));
        ShapelessRecipeBuilder.shapeless(pressure_plate)
                .requires(ItemTags.WOODEN_PRESSURE_PLATES)
                .requires(caps)
                .group("wool_plates")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, pressure_plate.getRegistryName()));
    }

}
