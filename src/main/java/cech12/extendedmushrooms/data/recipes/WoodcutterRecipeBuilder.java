package cech12.extendedmushrooms.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
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

    public WoodcutterRecipeBuilder(IItemProvider resultIn, Ingredient ingredientIn, int countIn) {
        this.result = resultIn.asItem();
        this.ingredient = ingredientIn;
        this.count = countIn;

        JsonObject object = new JsonObject();
        object.addProperty("type", ModLoadedCondition.Serializer.INSTANCE.getID().toString());
        ModLoadedCondition.Serializer.INSTANCE.write(object, new ModLoadedCondition("woodcutter"));
        this.addCondition(object);
    }

    public static WoodcutterRecipeBuilder woodcutterRecipe(IItemProvider resultIn, Ingredient ingredientIn, int countIn) {
        return new WoodcutterRecipeBuilder(resultIn, ingredientIn, countIn);
    }

    public static WoodcutterRecipeBuilder woodcutterRecipe(IItemProvider resultIn, Ingredient ingredientIn) {
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
     * Builds this recipe into an {@link IFinishedRecipe}.
     */
    public void build(Consumer<IFinishedRecipe> consumerIn) {
        this.build(consumerIn, ForgeRegistries.ITEMS.getKey(this.result));
    }

    /**
     * Builds this recipe into an {@link IFinishedRecipe}. Use {@link #build(Consumer)} if save is the same as the ID for
     * the result.
     */
    public void build(Consumer<IFinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.result);
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Shapeless Recipe " + save + " should remove its 'save' argument");
        } else {
            this.build(consumerIn, new ResourceLocation(save));
        }
    }

    /**
     * Builds this recipe into an {@link IFinishedRecipe}.
     */
    public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id, this.result, this.ingredient, this.count, this.conditions));
    }

    public static class Result implements IFinishedRecipe {
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

        public void serialize(JsonObject json) {
            json.addProperty("type", "woodcutter:woodcutting");

            JsonArray conditionArray = new JsonArray();
            for (JsonObject jsonObject : this.conditions) {
                conditionArray.add(jsonObject);
            }
            json.add("conditions", conditionArray);

            json.add("ingredient", ingredient.serialize());

            json.addProperty("result", ForgeRegistries.ITEMS.getKey(this.result).toString());
            json.addProperty("count", this.count);
        }

        @Nonnull
        public IRecipeSerializer<?> getSerializer() {
            //maybe another serializer?
            return IRecipeSerializer.CRAFTING_SHAPELESS;
        }

        /**
         * Gets the ID for the recipe.
         */
        @Nonnull
        public ResourceLocation getID() {
            return this.id;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @Nullable
        public JsonObject getAdvancementJson() {
            return null;
        }

        /**
         * Gets the ID for the advancement associated with this recipe. Should not be null if {@link #getAdvancementJson}
         * is non-null.
         */
        @Nullable
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }
}
