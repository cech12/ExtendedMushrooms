package cech12.extendedmushrooms.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WoodcutterRecipeBuilder {

    private final Item result;
    private final Ingredient ingredient;
    private final int count;
    private final List<JsonObject> conditions = new ArrayList<>();

    public WoodcutterRecipeBuilder(ItemLike resultIn, Ingredient ingredientIn, int countIn) {
        this.result = resultIn.asItem();
        this.ingredient = ingredientIn;
        this.count = countIn;

        JsonObject object = new JsonObject();
        object.addProperty("type", ModLoadedCondition.Serializer.INSTANCE.getID().toString());
        ModLoadedCondition.Serializer.INSTANCE.write(object, new ModLoadedCondition("corail_woodcutter"));
        this.addCondition(object);
    }

    public static WoodcutterRecipeBuilder woodcutterRecipe(ItemLike resultIn, Ingredient ingredientIn, int countIn) {
        return new WoodcutterRecipeBuilder(resultIn, ingredientIn, countIn);
    }

    public static WoodcutterRecipeBuilder woodcutterRecipe(ItemLike resultIn, Ingredient ingredientIn) {
        return new WoodcutterRecipeBuilder(resultIn, ingredientIn, 1);
    }

    /**
     * Adds a json condition.
     */
    public WoodcutterRecipeBuilder addCondition(JsonObject json) {
        conditions.add(json);
        return this;
    }

    /**
     * Builds this recipe into an {@link FinishedRecipe}.
     */
    public void build(Consumer<FinishedRecipe> consumerIn) {
        this.build(consumerIn, ForgeRegistries.ITEMS.getKey(this.result));
    }

    /**
     * Builds this recipe into an {@link FinishedRecipe}. Use {@link #build(Consumer)} if save is the same as the ID for
     * the result.
     */
    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.result);
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Shapeless Recipe " + save + " should remove its 'save' argument");
        } else {
            this.build(consumerIn, new ResourceLocation(save));
        }
    }

    /**
     * Builds this recipe into an {@link FinishedRecipe}.
     */
    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id, this.result, this.ingredient, this.count, this.conditions));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient ingredient;
        private final int count;
        private final List<JsonObject> conditions;

        public Result(ResourceLocation idIn, Item resultIn, Ingredient ingredientIn, int countIn, List<JsonObject> conditionsIn) {
            this.id = idIn;
            this.result = resultIn;
            this.ingredient = ingredientIn;
            this.count = countIn;
            this.conditions = conditionsIn;
        }

        public void serializeRecipeData(JsonObject json) {
            json.addProperty("type", "corail_woodcutter:woodcutting");

            JsonArray conditionArray = new JsonArray();
            for (JsonObject jsonObject : this.conditions) {
                conditionArray.add(jsonObject);
            }
            json.add("conditions", conditionArray);

            json.add("ingredient", ingredient.toJson());

            json.addProperty("result", ForgeRegistries.ITEMS.getKey(this.result).toString());
            json.addProperty("count", this.count);
        }

        @Nonnull
        public RecipeSerializer<?> getType() {
            //maybe another serializer?
            return RecipeSerializer.SHAPELESS_RECIPE;
        }

        /**
         * Gets the ID for the recipe.
         */
        @Nonnull
        public ResourceLocation getId() {
            return this.id;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }

        /**
         * Gets the ID for the advancement associated with this recipe.
         */
        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
