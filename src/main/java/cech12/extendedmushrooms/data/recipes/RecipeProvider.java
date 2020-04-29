package cech12.extendedmushrooms.data.recipes;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.api.item.ExtendedMushroomsItems;
import cech12.extendedmushrooms.compat.ModFeatureEnabledCondition;
import cech12.extendedmushrooms.init.ModTags;
import com.google.gson.JsonArray;
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
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
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
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {

        //grilled mushroom
        String name = ExtendedMushroomsItems.GRILLED_MUSHROOM.getRegistryName().getPath();
        CookingRecipeBuilder
                .smeltingRecipe(Ingredient.fromTag(ModTags.ForgeItems.MUSHROOMS_EDIBLE), ExtendedMushroomsItems.GRILLED_MUSHROOM, 0.15F, 150)
                .addCriterion("has_mushroom", this.hasItem(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).build(consumer);
        CookingRecipeBuilder
                .cookingRecipe(Ingredient.fromTag(ModTags.ForgeItems.MUSHROOMS_EDIBLE), ExtendedMushroomsItems.GRILLED_MUSHROOM, 0.15F, 450, IRecipeSerializer.CAMPFIRE_COOKING)
                .addCriterion("has_mushroom", this.hasItem(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).build(consumer, getResourceLocation(name + "_from_campfire_cooking"));
        CookingRecipeBuilder
                .cookingRecipe(Ingredient.fromTag(ModTags.ForgeItems.MUSHROOMS_EDIBLE), ExtendedMushroomsItems.GRILLED_MUSHROOM, 0.15F, 75, IRecipeSerializer.SMOKING)
                .addCriterion("has_mushroom", this.hasItem(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).build(consumer, getResourceLocation(name + "_from_smoking"));

        //mushroom bread
        ShapedRecipeBuilder.shapedRecipe(ExtendedMushroomsItems.MUSHROOM_BREAD)
                .key('#', ModTags.ForgeItems.MUSHROOMS_EDIBLE)
                .patternLine("###")
                .setGroup("bread")
                .addCriterion("has_mushroom", this.hasItem(ModTags.ForgeItems.MUSHROOMS_EDIBLE))
                .build(consumer);

        //rabbit stew
        ShapelessRecipeBuilder.shapelessRecipe(Items.RABBIT_STEW)
                .addIngredient(Items.BAKED_POTATO)
                .addIngredient(Items.COOKED_RABBIT)
                .addIngredient(Items.BOWL)
                .addIngredient(Items.CARROT)
                .addIngredient(ModTags.Items.MUSHROOMS_EDIBLE) //only mod intern edible mushrooms
                .setGroup("rabbit_stew")
                .addCriterion("has_cooked_rabbit", hasItem(Items.COOKED_RABBIT))
                .build(consumer, getResourceLocation(Items.RABBIT_STEW.getRegistryName().getPath() + "_from_edible_mushroom"));

        //brown dye from infested flower
        ShapelessRecipeBuilder.shapelessRecipe(Items.BROWN_DYE)
                .addIngredient(ExtendedMushroomsBlocks.INFESTED_FLOWER)
                .addCriterion("has_flower", hasItem(ExtendedMushroomsBlocks.INFESTED_FLOWER))
                .build(consumer, getResourceLocation(Items.BROWN_DYE.getRegistryName().getPath() + "_from_infested_flower"));

        //mushroom spores
        ShapelessRecipeBuilder.shapelessRecipe(ExtendedMushroomsItems.MUSHROOM_SPORES, 2)
                .addIngredient(Tags.Items.MUSHROOMS)
                .addCriterion("has_mushroom", hasItem(Tags.Items.MUSHROOMS))
                .build(consumer);

        mushroomWoodRecipes(consumer, "colorless",
                ModTags.ForgeItems.MUSHROOM_STEMS_COLORLESS,
                ExtendedMushroomsItems.MUSHROOM_BOAT.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_BOOKSHELF.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_BUTTON.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_DOOR.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_FENCE.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_FENCE_GATE.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_LADDER.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_PLANKS.asItem(),
                ExtendedMushroomsBlocks.MUSHROOM_PRESSURE_PLATE.asItem(),
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

        mushroomWoodRecipes(consumer, "glowshroom",
                ModTags.ForgeItems.MUSHROOM_STEMS_GLOWSHROOM,
                ExtendedMushroomsItems.GLOWSHROOM_BOAT.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_BOOKSHELF.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_BUTTON.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_DOOR.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_FENCE.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_FENCE_GATE.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_LADDER.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_PLANKS.asItem(),
                ExtendedMushroomsBlocks.GLOWSHROOM_PRESSURE_PLATE.asItem(),
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
        ShapedRecipeBuilder.shapedRecipe(Items.GLOWSTONE_DUST)
                .key('#', ExtendedMushroomsItems.GLOWSTONE_CRUMBS)
                .patternLine("##")
                .patternLine("##")
                .addCriterion("has_crumbs", this.hasItem(ExtendedMushroomsItems.GLOWSTONE_CRUMBS))
                .build(consumer, getResourceLocation(Items.GLOWSTONE_DUST.getRegistryName().getPath()));
        ShapelessRecipeBuilder.shapelessRecipe(ExtendedMushroomsItems.GLOWSTONE_CRUMBS, 4)
                .addIngredient(Items.GLOWSTONE_DUST)
                .addCriterion("has_dust", hasItem(Items.GLOWSTONE_DUST))
                .build(consumer);

        mushroomWoodRecipes(consumer, "poisonous_mushroom",
                ModTags.ForgeItems.MUSHROOM_STEMS_GREEN,
                ExtendedMushroomsItems.POISONOUS_MUSHROOM_BOAT.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_BOOKSHELF.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_BUTTON.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_DOOR.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_FENCE.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_FENCE_GATE.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_LADDER.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_PLANKS.asItem(),
                ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_PRESSURE_PLATE.asItem(),
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

        //oak sign
        ShapedRecipeBuilder.shapedRecipe(Items.OAK_SIGN, 3)
                .key('#', ModTags.Items.MUSHROOM_PLANKS)
                .key('|', Tags.Items.RODS_WOODEN)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .addCriterion("has_planks", hasItem(ModTags.Items.MUSHROOM_PLANKS))
                .build(consumer, getResourceLocation(Items.OAK_SIGN.getRegistryName().getPath()));
        //TODO woodcutting

    }


    private void mushroomWoodRecipes(Consumer<IFinishedRecipe> consumer, String name, Tag<Item> stems, Item boat,
                                     Item bookshelf, Item button, Item door, Item fence, Item fence_gate, Item ladder,
                                     Item planks, Item pressure_plate, Item slab, Item stairs, Item trapdoor,
                                     Item verticalPlanks, Item verticalSlabs) {
        String directory = "mushroom_wood/" + name + "/";
        ShapedRecipeBuilder.shapedRecipe(boat)
                .key('#', planks)
                .patternLine("# #")
                .patternLine("###")
                .setGroup("boat")
                .addCriterion("in_water", enteredBlock(Blocks.WATER))
                .build(consumer, getResourceLocation(directory, boat.getRegistryName()));
        ShapelessRecipeBuilder.shapelessRecipe(button)
                .addIngredient(planks)
                .setGroup("wooden_button")
                .addCriterion("has_planks", hasItem(planks))
                .build(consumer, getResourceLocation(directory, button.getRegistryName()));
        ShapedRecipeBuilder.shapedRecipe(door, 3)
                .key('#', planks)
                .patternLine("##")
                .patternLine("##")
                .patternLine("##")
                .setGroup("wooden_door")
                .addCriterion("has_planks", hasItem(planks))
                .build(consumer, getResourceLocation(directory, door.getRegistryName()));
        ShapedRecipeBuilder.shapedRecipe(fence, 3)
                .key('#', Tags.Items.RODS_WOODEN)
                .key('W', planks)
                .patternLine("W#W")
                .patternLine("W#W")
                .setGroup("wooden_fence")
                .addCriterion("has_planks", hasItem(planks))
                .build(consumer, getResourceLocation(directory, fence.getRegistryName()));
        ShapedRecipeBuilder.shapedRecipe(fence_gate)
                .key('#', Tags.Items.RODS_WOODEN)
                .key('W', planks)
                .patternLine("#W#")
                .patternLine("#W#")
                .setGroup("wooden_fence_gate")
                .addCriterion("has_planks", hasItem(planks))
                .build(consumer, getResourceLocation(directory, fence_gate.getRegistryName()));
        ShapelessRecipeBuilder.shapelessRecipe(planks, 4)
                .addIngredient(stems)
                .setGroup("planks")
                .addCriterion("has_logs", hasItem(stems))
                .build(consumer, getResourceLocation(directory, planks.getRegistryName()));
        ShapedRecipeBuilder.shapedRecipe(pressure_plate)
                .key('#', planks)
                .patternLine("##")
                .setGroup("wooden_pressure_plate")
                .addCriterion("has_planks", hasItem(planks))
                .build(consumer, getResourceLocation(directory, pressure_plate.getRegistryName()));
        ShapedRecipeBuilder.shapedRecipe(slab, 6)
                .key('#', planks)
                .patternLine("###")
                .setGroup("wooden_slab")
                .addCriterion("has_planks", hasItem(planks))
                .build(consumer, getResourceLocation(directory, slab.getRegistryName()));
        ShapedRecipeBuilder.shapedRecipe(stairs, 4)
                .key('#', planks)
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .setGroup("wooden_stairs")
                .addCriterion("has_planks", hasItem(planks))
                .build(consumer, getResourceLocation(directory, stairs.getRegistryName()));
        ShapedRecipeBuilder.shapedRecipe(trapdoor, 2)
                .key('#', planks)
                .patternLine("###")
                .patternLine("###")
                .setGroup("wooden_trapdoor")
                .addCriterion("has_planks", hasItem(planks))
                .build(consumer, getResourceLocation(directory, trapdoor.getRegistryName()));

        //recipes that are only active when other mods are installed
        ShapedRecipeBuilder.shapedRecipe(bookshelf)
                .key('#', planks)
                .key('X', Items.BOOK)
                .patternLine("###")
                .patternLine("XXX")
                .patternLine("###")
                .addCriterion("has_book", hasItem(Items.BOOK))
                .build(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("variantBookshelf")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, bookshelf.getRegistryName()));
        ShapedRecipeBuilder.shapedRecipe(ladder, 4)
                .key('#', planks)
                .key('|', Tags.Items.RODS_WOODEN)
                .patternLine("| |")
                .patternLine("|#|")
                .patternLine("| |")
                .addCriterion("has_stick", hasItem(Tags.Items.RODS_WOODEN))
                .build(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("variantLadder")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, ladder.getRegistryName()));
        ShapedRecipeBuilder.shapedRecipe(verticalPlanks, 3)
                .key('#', planks)
                .patternLine("#")
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_planks", hasItem(planks))
                .build(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalPlanks")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, verticalPlanks.getRegistryName()));
        ShapelessRecipeBuilder.shapelessRecipe(planks)
                .addIngredient(verticalPlanks)
                .addCriterion("has_vertical_planks", hasItem(verticalPlanks))
                .build(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalPlanks")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, verticalPlanks.getRegistryName().getPath() + "_revert"));
        ShapedRecipeBuilder.shapedRecipe(verticalSlabs, 3)
                .key('#', slab)
                .patternLine("#")
                .patternLine("#")
                .patternLine("#")
                .addCriterion("has_slab", hasItem(slab))
                .build(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalSlab")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, verticalSlabs.getRegistryName()));
        ShapelessRecipeBuilder.shapelessRecipe(slab)
                .addIngredient(verticalSlabs)
                .addCriterion("has_vertical_slab", hasItem(verticalSlabs))
                .build(ResultWrapper.transformJson(consumer, json -> {
                    JsonArray array = new JsonArray();
                    array.add(ModFeatureEnabledCondition.Serializer.INSTANCE.getJson(new ModFeatureEnabledCondition("verticalSlab")));
                    json.add("conditions", array);
                }), getResourceLocation(directory, verticalSlabs.getRegistryName().getPath() + "_revert"));

        //TODO wood cutting
    }

    private void mushroomCapRecipes(Consumer<IFinishedRecipe> consumer, String name, Tag<Item> caps, Item banner,
                                    Item bed, Item button, Item carpet, Item pressure_plate) {
        String directory = "mushroom_cap/" + name + "/";
        ShapedRecipeBuilder.shapedRecipe(banner)
                .key('#', caps)
                .key('|', Tags.Items.RODS_WOODEN)
                .patternLine("###")
                .patternLine("###")
                .patternLine(" | ")
                .setGroup("banner")
                .addCriterion("has_cap", hasItem(caps))
                .build(consumer, getResourceLocation(directory, banner.getRegistryName().getPath()));
        ShapedRecipeBuilder.shapedRecipe(bed)
                .key('#', caps)
                .key('W', ItemTags.PLANKS)
                .patternLine("###")
                .patternLine("WWW")
                .setGroup("bed")
                .addCriterion("has_cap", hasItem(caps))
                .build(consumer, getResourceLocation(directory, bed.getRegistryName().getPath()));
        ShapelessRecipeBuilder.shapelessRecipe(button)
                .addIngredient(caps)
                .setGroup("wool_buttons")
                .addCriterion("has_cap", hasItem(caps))
                .build(consumer, getResourceLocation(directory, button.getRegistryName()));
        ShapedRecipeBuilder.shapedRecipe(carpet, 3)
                .key('#', caps)
                .patternLine("##")
                .setGroup("carpet")
                .addCriterion("has_cap", hasItem(caps))
                .build(consumer, getResourceLocation(directory, carpet.getRegistryName()));
        ShapelessRecipeBuilder.shapelessRecipe(pressure_plate)
                .addIngredient(ItemTags.WOODEN_PRESSURE_PLATES)
                .addIngredient(caps)
                .setGroup("wool_plates")
                .addCriterion("has_cap", hasItem(caps))
                .build(consumer, getResourceLocation(directory, pressure_plate.getRegistryName()));
    }

}
