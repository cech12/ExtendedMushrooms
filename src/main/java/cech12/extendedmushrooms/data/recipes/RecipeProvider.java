package cech12.extendedmushrooms.data.recipes;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModItems;
import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.item.MushroomType;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {

    public RecipeProvider(PackOutput packOutput) {
        super(packOutput);
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
    protected void buildRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {

        //grilled mushroom
        String name = ModItems.GRILLED_MUSHROOM.getId().getPath();
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(ModTags.ForgeItems.MUSHROOMS_EDIBLE), RecipeCategory.FOOD, ModItems.GRILLED_MUSHROOM.get(), 0.15F, 150)
                .unlockedBy("has_mushroom", has(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).save(consumer);
        SimpleCookingRecipeBuilder
                .campfireCooking(Ingredient.of(ModTags.ForgeItems.MUSHROOMS_EDIBLE), RecipeCategory.FOOD, ModItems.GRILLED_MUSHROOM.get(), 0.15F, 450)
                .unlockedBy("has_mushroom", has(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).save(consumer, getResourceLocation(name + "_from_campfire_cooking"));
        SimpleCookingRecipeBuilder
                .smoking(Ingredient.of(ModTags.ForgeItems.MUSHROOMS_EDIBLE), RecipeCategory.FOOD, ModItems.GRILLED_MUSHROOM.get(), 0.15F, 75)
                .unlockedBy("has_mushroom", has(ModTags.ForgeItems.MUSHROOMS_EDIBLE)).save(consumer, getResourceLocation(name + "_from_smoking"));

        //mushroom bread
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.MUSHROOM_BREAD.get())
                .define('#', ModTags.ForgeItems.MUSHROOMS_EDIBLE)
                .pattern("###")
                .group("bread")
                .unlockedBy("has_mushroom", has(ModTags.ForgeItems.MUSHROOMS_EDIBLE))
                .save(consumer);

        //rabbit stew
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.RABBIT_STEW)
                .requires(Items.BAKED_POTATO)
                .requires(Items.COOKED_RABBIT)
                .requires(Items.BOWL)
                .requires(Items.CARROT)
                .requires(ModTags.Items.MUSHROOMS_EDIBLE) //only mod intern edible mushrooms
                .group("rabbit_stew")
                .unlockedBy("has_cooked_rabbit", has(Items.COOKED_RABBIT))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.RABBIT_STEW).getPath() + "_from_edible_mushroom"));

        //brown dye from infested flower
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BROWN_DYE)
                .requires(ModBlocks.INFESTED_FLOWER.get())
                .unlockedBy("has_flower", has(ModBlocks.INFESTED_FLOWER.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.BROWN_DYE).getPath() + "_from_infested_flower"));

        //mycyclopedia
        Item mycyclopedia = ModItems.MYCYCLOPEDIA.get();
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, mycyclopedia)
                .requires(Items.BOOK)
                .requires(Tags.Items.MUSHROOMS)
                .unlockedBy("has_mushroom", has(Tags.Items.MUSHROOMS))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(mycyclopedia).getPath()));
        FairyRingRecipeBuilder.normal(mycyclopedia, 1)
                .requires(Items.BOOK)
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(mycyclopedia).getPath()));

        //overworld fairy ring recipes
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
        FairyRingRecipeBuilder.normal(Items.POISONOUS_POTATO, 1)
                .requires(Items.POTATO)
                .save(consumer, getResourceLocation("poisonous_potato_from_potato"));

        //nether fairy ring recipes
        FairyRingRecipeBuilder.normal(Items.CRIMSON_FUNGUS, 1)
                .requires(Items.CRIMSON_ROOTS)
                .requires(Items.WEEPING_VINES)
                .requires(Items.NETHER_WART)
                .save(consumer, getResourceLocation("crimson_fungus"));
        FairyRingRecipeBuilder.normal(Items.CRIMSON_NYLIUM, 1)
                .requires(Tags.Items.NETHERRACK)
                .requires(Tags.Items.DYES_RED)
                .save(consumer, getResourceLocation("crimson_nylium_from_netherrack"));
        FairyRingRecipeBuilder.normal(Items.CRIMSON_ROOTS, 2)
                .requires(Items.MANGROVE_ROOTS)
                .requires(Tags.Items.DYES_RED)
                .save(consumer, getResourceLocation("crimson_roots_from_mangrove_roots"));
        FairyRingRecipeBuilder.normal(Items.NETHER_WART, 1)
                .requires(Items.SWEET_BERRIES)
                .save(consumer, getResourceLocation("nether_wart_from_sweet_berry"));
        FairyRingRecipeBuilder.normal(Items.SHROOMLIGHT, 1)
                .requires(Items.GLOWSTONE)
                .save(consumer, getResourceLocation("shroomlight_from_glowstone"));
        FairyRingRecipeBuilder.normal(Items.SHROOMLIGHT, 1)
                .requires(Tags.Items.DUSTS_GLOWSTONE, 4)
                .save(consumer, getResourceLocation("shroomlight_from_glowstone_dust"));
        FairyRingRecipeBuilder.normal(Items.TWISTING_VINES, 1)
                .requires(Items.VINE)
                .requires(Tags.Items.DYES_CYAN)
                .save(consumer, getResourceLocation("twisting_vine_from_vine"));
        FairyRingRecipeBuilder.normal(Items.WARPED_NYLIUM, 1)
                .requires(Tags.Items.NETHERRACK)
                .requires(Tags.Items.DYES_CYAN)
                .save(consumer, getResourceLocation("warped_nylium_from_netherrack"));
        FairyRingRecipeBuilder.normal(Items.WARPED_FUNGUS, 1)
                .requires(Items.WARPED_ROOTS)
                .requires(Items.TWISTING_VINES)
                .requires(Items.NETHER_WART)
                .save(consumer, getResourceLocation("warped_fungus"));
        FairyRingRecipeBuilder.normal(Items.WARPED_ROOTS, 2)
                .requires(Items.MANGROVE_ROOTS)
                .requires(Tags.Items.DYES_CYAN)
                .save(consumer, getResourceLocation("warped_roots_from_mangrove_roots"));
        FairyRingRecipeBuilder.normal(Items.WEEPING_VINES, 1)
                .requires(Items.VINE)
                .requires(Tags.Items.DYES_RED)
                .save(consumer, getResourceLocation("weeping_vine_from_vine"));

        //mushroom spores
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MUSHROOM_SPORES.get(), 2)
                .requires(Tags.Items.MUSHROOMS)
                .unlockedBy("has_mushroom", has(Tags.Items.MUSHROOMS))
                .save(consumer);

        mushroomWoodRecipes(consumer, "colorless",
                ModTags.ForgeItems.MUSHROOM_STEMS_COLORLESS,
                ModItems.MUSHROOM_BOAT.get(),
                ModItems.MUSHROOM_CHEST_BOAT.get(),
                ModBlocks.MUSHROOM_BUTTON.get().asItem(),
                ModBlocks.MUSHROOM_DOOR.get().asItem(),
                ModBlocks.MUSHROOM_FENCE.get().asItem(),
                ModBlocks.MUSHROOM_FENCE_GATE.get().asItem(),
                ModItems.MUSHROOM_HANGING_SIGN.get(),
                ModBlocks.MUSHROOM_PLANKS.get().asItem(),
                ModBlocks.MUSHROOM_PRESSURE_PLATE.get().asItem(),
                ModItems.MUSHROOM_SIGN.get(),
                ModBlocks.MUSHROOM_SLAB.get().asItem(),
                ModBlocks.MUSHROOM_STAIRS.get().asItem(),
                ModBlocks.STRIPPED_MUSHROOM_STEM.get().asItem(),
                ModBlocks.MUSHROOM_TRAPDOOR.get().asItem());
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
                .requires(Items.SHROOMLIGHT)
                .requires(Items.GOLDEN_CARROT)
                .requires(Items.LAPIS_LAZULI)
                .save(consumer);
        mushroomWoodRecipes(consumer, "glowshroom",
                ModTags.ForgeItems.MUSHROOM_STEMS_GLOWSHROOM,
                ModItems.GLOWSHROOM_BOAT.get(),
                ModItems.GLOWSHROOM_CHEST_BOAT.get(),
                ModBlocks.GLOWSHROOM_BUTTON.get().asItem(),
                ModBlocks.GLOWSHROOM_DOOR.get().asItem(),
                ModBlocks.GLOWSHROOM_FENCE.get().asItem(),
                ModBlocks.GLOWSHROOM_FENCE_GATE.get().asItem(),
                ModItems.GLOWSHROOM_HANGING_SIGN.get(),
                ModBlocks.GLOWSHROOM_PLANKS.get().asItem(),
                ModBlocks.GLOWSHROOM_PRESSURE_PLATE.get().asItem(),
                ModItems.GLOWSHROOM_SIGN.get(),
                ModBlocks.GLOWSHROOM_SLAB.get().asItem(),
                ModBlocks.GLOWSHROOM_STAIRS.get().asItem(),
                ModBlocks.GLOWSHROOM_STEM_STRIPPED.get().asItem(),
                ModBlocks.GLOWSHROOM_TRAPDOOR.get().asItem());
        mushroomCapRecipes(consumer, "glowshroom",
                ModTags.ForgeItems.MUSHROOM_CAPS_GLOWSHROOM,
                Items.BLUE_BANNER,
                Items.BLUE_BED,
                ModBlocks.GLOWSHROOM_CAP_BUTTON.get().asItem(),
                ModBlocks.GLOWSHROOM_CAP_CARPET.get().asItem(),
                ModBlocks.GLOWSHROOM_CAP_PRESSURE_PLATE.get().asItem());
        //glowstone crumbs recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.GLOWSTONE_DUST)
                .define('#', ModItems.GLOWSTONE_CRUMBS.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_crumbs", has(ModItems.GLOWSTONE_CRUMBS.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.GLOWSTONE_DUST).getPath()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GLOWSTONE_CRUMBS.get(), 4)
                .requires(Items.GLOWSTONE_DUST)
                .unlockedBy("has_dust", has(Items.GLOWSTONE_DUST))
                .save(consumer);

        //deadly fibrecap
        FairyRingRecipeBuilder.normal(ModBlocks.DEADLY_FIBRECAP.get(), 1)
                .requires(Items.POISONOUS_POTATO)
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.SPIDER_EYE)
                .requires(Items.WHITE_DYE)
                .save(consumer);
        mushroomCapRecipes(consumer, "poisonous_mushroom",
                ModTags.ForgeItems.MUSHROOM_CAPS_WHITE,
                Items.WHITE_BANNER,
                Items.WHITE_BED,
                ModBlocks.DEADLY_FIBRECAP_CAP_BUTTON.get().asItem(),
                ModBlocks.DEADLY_FIBRECAP_CAP_CARPET.get().asItem(),
                ModBlocks.DEADLY_FIBRECAP_CAP_PRESSURE_PLATE.get().asItem());
        //no wood for deadly fibrecap

        //parrot waxcap
        FairyRingRecipeBuilder.normal(ModBlocks.PARROT_WAXCAP.get(), 1)
                .requires(Items.SLIME_BLOCK)
                .requires(Items.RABBIT_FOOT)
                .requires(Items.LIME_DYE)
                .save(consumer);
        mushroomWoodRecipes(consumer, "poisonous_mushroom",
                ModTags.ForgeItems.MUSHROOM_STEMS_GREEN,
                ModItems.PARROT_WAXCAP_BOAT.get(),
                ModItems.PARROT_WAXCAP_CHEST_BOAT.get(),
                ModBlocks.PARROT_WAXCAP_BUTTON.get().asItem(),
                ModBlocks.PARROT_WAXCAP_DOOR.get().asItem(),
                ModBlocks.PARROT_WAXCAP_FENCE.get().asItem(),
                ModBlocks.PARROT_WAXCAP_FENCE_GATE.get().asItem(),
                ModItems.PARROT_WAXCAP_HANGING_SIGN.get(),
                ModBlocks.PARROT_WAXCAP_PLANKS.get().asItem(),
                ModBlocks.PARROT_WAXCAP_PRESSURE_PLATE.get().asItem(),
                ModItems.PARROT_WAXCAP_SIGN.get(),
                ModBlocks.PARROT_WAXCAP_SLAB.get().asItem(),
                ModBlocks.PARROT_WAXCAP_STAIRS.get().asItem(),
                ModBlocks.PARROT_WAXCAP_STEM_STRIPPED.get().asItem(),
                ModBlocks.PARROT_WAXCAP_TRAPDOOR.get().asItem());
        mushroomCapRecipes(consumer, "slime_fungus",
                ModTags.ForgeItems.MUSHROOM_CAPS_LIME,
                Items.ORANGE_BANNER,
                Items.ORANGE_BED,
                ModBlocks.PARROT_WAXCAP_CAP_BUTTON.get().asItem(),
                ModBlocks.PARROT_WAXCAP_CAP_CARPET.get().asItem(),
                ModBlocks.PARROT_WAXCAP_CAP_PRESSURE_PLATE.get().asItem());
        //slime blob recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.SLIME_BALL)
                .define('#', ModItems.SLIME_BLOB.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_blobs", has(ModItems.SLIME_BLOB.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.SLIME_BALL).getPath()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SLIME_BLOB.get(), 4)
                .requires(Items.SLIME_BALL)
                .unlockedBy("has_slime", has(Items.SLIME_BALL))
                .save(consumer);

        //honey waxcap
        FairyRingRecipeBuilder.normal(ModBlocks.HONEY_WAXCAP.get(), 1)
                .requires(Items.HONEYCOMB)
                .requires(Items.HONEY_BOTTLE)
                .requires(Items.ORANGE_DYE)
                .save(consumer);
        mushroomWoodRecipes(consumer, "honey_fungus",
                ModTags.ForgeItems.MUSHROOM_STEMS_ORANGE,
                ModItems.HONEY_WAXCAP_BOAT.get(),
                ModItems.HONEY_WAXCAP_CHEST_BOAT.get(),
                ModBlocks.HONEY_WAXCAP_BUTTON.get().asItem(),
                ModBlocks.HONEY_WAXCAP_DOOR.get().asItem(),
                ModBlocks.HONEY_WAXCAP_FENCE.get().asItem(),
                ModBlocks.HONEY_WAXCAP_FENCE_GATE.get().asItem(),
                ModItems.HONEY_WAXCAP_HANGING_SIGN.get(),
                ModBlocks.HONEY_WAXCAP_PLANKS.get().asItem(),
                ModBlocks.HONEY_WAXCAP_PRESSURE_PLATE.get().asItem(),
                ModItems.HONEY_WAXCAP_SIGN.get(),
                ModBlocks.HONEY_WAXCAP_SLAB.get().asItem(),
                ModBlocks.HONEY_WAXCAP_STAIRS.get().asItem(),
                ModBlocks.HONEY_WAXCAP_STEM_STRIPPED.get().asItem(),
                ModBlocks.HONEY_WAXCAP_TRAPDOOR.get().asItem());
        mushroomCapRecipes(consumer, "honey_fungus",
                ModTags.ForgeItems.MUSHROOM_CAPS_ORANGE,
                Items.ORANGE_BANNER,
                Items.ORANGE_BED,
                ModBlocks.HONEY_WAXCAP_CAP_BUTTON.get().asItem(),
                ModBlocks.HONEY_WAXCAP_CAP_CARPET.get().asItem(),
                ModBlocks.HONEY_WAXCAP_CAP_PRESSURE_PLATE.get().asItem());
        //honey blob recipes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.HONEY_BOTTLE, 1)
                .requires(Items.GLASS_BOTTLE)
                .requires(ModItems.HONEY_BLOB.get(), 3)
                .unlockedBy("has_honey_blob", has(ModItems.HONEY_BLOB.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.HONEY_BOTTLE).getPath()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SUGAR)
                .requires(ModItems.HONEY_BLOB.get())
                .unlockedBy("has_honey_blob", has(ModItems.HONEY_BLOB.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.SUGAR).getPath()));
        //honeycomb shred recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.HONEYCOMB)
                .define('#', ModItems.HONEYCOMB_SHRED.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_shreds", has(ModItems.HONEYCOMB_SHRED.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.HONEYCOMB).getPath()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HONEYCOMB_SHRED.get(), 4)
                .requires(Items.HONEYCOMB)
                .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                .save(consumer);
        //fibre recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING)
                .define('#', ModItems.FIBRE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_fibre", has(ModItems.FIBRE.get()))
                .save(consumer, getResourceLocation(ForgeRegistries.ITEMS.getKey(Items.STRING).getPath()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FIBRE.get(), 4)
                .requires(Items.STRING)
                .unlockedBy("has_string", has(Items.STRING))
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


    private void mushroomWoodRecipes(Consumer<FinishedRecipe> consumer, String name, TagKey<Item> stems, Item boat,
                                     Item chestBoat, Item button, Item door, Item fence, Item fenceGate, Item hangingSign,
                                     Item planks, Item pressurePlate, Item sign, Item slab, Item stairs, Item strippedStem,
                                     Item trapdoor) {
        String directory = "mushroom_wood/" + name + "/";
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, boat)
                .define('#', planks)
                .pattern("# #")
                .pattern("###")
                .group("boat")
                .unlockedBy("in_water", insideOf(Blocks.WATER))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(boat)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, chestBoat)
                .requires(Tags.Items.CHESTS_WOODEN)
                .requires(boat)
                .group("chest_boat")
                .unlockedBy("has_boat", has(ItemTags.BOATS))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(chestBoat)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, button)
                .requires(planks)
                .group("wooden_button")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(button)));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, door, 3)
                .define('#', planks)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .group("wooden_door")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(door)));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, fence, 3)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', planks)
                .pattern("W#W")
                .pattern("W#W")
                .group("wooden_fence")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(fence)));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, fenceGate)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', planks)
                .pattern("#W#")
                .pattern("#W#")
                .group("wooden_fence_gate")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(fenceGate)));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, hangingSign, 6)
                .define('#', strippedStem)
                .define('X', Items.CHAIN)
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_planks", has(strippedStem))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(hangingSign)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                .requires(stems)
                .group("planks")
                .unlockedBy("has_logs", has(stems))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(planks)));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, pressurePlate)
                .define('#', planks)
                .pattern("##")
                .group("wooden_pressure_plate")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(pressurePlate)));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 3)
                .define('#', planks)
                .define('|', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("###")
                .pattern(" | ")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(sign)));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .define('#', planks)
                .pattern("###")
                .group("wooden_slab")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(slab)));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .define('#', planks)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .group("wooden_stairs")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(stairs)));
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, trapdoor, 2)
                .define('#', planks)
                .pattern("###")
                .pattern("###")
                .group("wooden_trapdoor")
                .unlockedBy("has_planks", has(planks))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(trapdoor)));

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
    }

    private void mushroomCapRecipes(Consumer<FinishedRecipe> consumer, String name, TagKey<Item> caps, Item banner,
                                    Item bed, Item button, Item carpet, Item pressure_plate) {
        String directory = "mushroom_cap/" + name + "/";
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, banner)
                .define('#', caps)
                .define('|', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("###")
                .pattern(" | ")
                .group("banner")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(banner).getPath()));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, bed)
                .define('#', caps)
                .define('W', ItemTags.PLANKS)
                .pattern("###")
                .pattern("WWW")
                .group("bed")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(bed).getPath()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, button)
                .requires(caps)
                .group("wool_buttons")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(button)));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, carpet, 3)
                .define('#', caps)
                .pattern("##")
                .group("carpet")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(carpet)));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, pressure_plate)
                .requires(ItemTags.WOODEN_PRESSURE_PLATES)
                .requires(caps)
                .group("wool_plates")
                .unlockedBy("has_cap", has(caps))
                .save(consumer, getResourceLocation(directory, ForgeRegistries.ITEMS.getKey(pressure_plate)));
    }

}
