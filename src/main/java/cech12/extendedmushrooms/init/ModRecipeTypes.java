package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.recipe.FairyRingRecipe;
import cech12.extendedmushrooms.item.crafting.MushroomArrowRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes {

    public static DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ExtendedMushrooms.MOD_ID);
    public static RegistryObject<RecipeType<FairyRingRecipe>> FAIRY_RING = RECIPE_TYPES.register("fairy_ring", () -> new RecipeType<>() {});

    public static DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ExtendedMushrooms.MOD_ID);
    public static RegistryObject<RecipeSerializer<?>> FAIRY_RING_SERIALIZER = RECIPE_SERIALIZERS.register("fairy_ring_recipe", () -> FairyRingRecipe.SERIALIZER);
    public static RegistryObject<RecipeSerializer<?>> MUSHROOM_ARROW_SERIALIZER = RECIPE_SERIALIZERS.register("mushroom_arrow_recipe", () -> MushroomArrowRecipe.SERIALIZER);

}
