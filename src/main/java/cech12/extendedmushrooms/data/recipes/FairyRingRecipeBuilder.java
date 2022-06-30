package cech12.extendedmushrooms.data.recipes;

import cech12.extendedmushrooms.api.recipe.FairyRingMode;
import cech12.extendedmushrooms.api.recipe.FairyRingRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FairyRingRecipeBuilder {
    private final Item result;
    private final int resultCount;
    private final List<Ingredient> ingredients;
    private final FairyRingMode requiredMode;

    private int recipeTime;
    private FairyRingMode resultMode;

    private FairyRingRecipeBuilder(ItemLike result, int resultCount, FairyRingMode requiredMode) {
        this.result = result.asItem();
        this.resultCount = resultCount;
        this.resultMode = requiredMode;
        this.ingredients = new ArrayList<>();
        this.requiredMode = requiredMode;
        this.recipeTime = 200;
    }

    public static FairyRingRecipeBuilder normal(ItemLike result, int resultCount) {
        return new FairyRingRecipeBuilder(result, resultCount, FairyRingMode.NORMAL);
    }

    public static FairyRingRecipeBuilder fairy(ItemLike result, int resultCount) {
        return new FairyRingRecipeBuilder(result, resultCount, FairyRingMode.FAIRY);
    }

    public static FairyRingRecipeBuilder witch(ItemLike result, int resultCount) {
        return new FairyRingRecipeBuilder(result, resultCount, FairyRingMode.WITCH);
    }



    public FairyRingRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public FairyRingRecipeBuilder requires(ItemLike iItemProvider) {
        return this.requires(iItemProvider, 1);
    }

    public FairyRingRecipeBuilder requires(ItemLike itemProvider, int count) {
        return this.requires(Ingredient.of(itemProvider), count);
    }

    public FairyRingRecipeBuilder requires(Ingredient ingredient, int count) {
        for (int i = 0; i < count; i++) {
            this.ingredients.add(ingredient);
        }
        return this;
    }

    public FairyRingRecipeBuilder requires(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public FairyRingRecipeBuilder recipeTime(int time) {
        this.recipeTime = time;
        return this;
    }

    public FairyRingRecipeBuilder resultMode(FairyRingMode mode) {
        this.resultMode = mode;
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        this.save(consumer, ForgeRegistries.ITEMS.getKey(this.result));
    }

    public void save(Consumer<FinishedRecipe> consumer, String location) {
        ResourceLocation resourceLocation = new ResourceLocation(location);
        if (resourceLocation.equals(ForgeRegistries.ITEMS.getKey(this.result))) {
            throw new IllegalStateException("Recipe " + resourceLocation + " should remove its 'save' argument");
        } else {
            this.save(consumer, resourceLocation);
        }
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation resourceLocation) {
        consumer.accept(new Result(resourceLocation, this.result, this.resultCount, this.resultMode, this.ingredients, this.requiredMode, this.recipeTime));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation resourceLocation;
        private final Item result;
        private final int resultCount;
        private final FairyRingMode resultMode;
        private final List<Ingredient> ingredients;
        private final FairyRingMode requiredMode;
        private final int recipeTime;

        public Result(ResourceLocation resourceLocation, Item result, int resultCount, FairyRingMode resultMode, List<Ingredient> ingredients, FairyRingMode requiredMode, int recipeTime) {
            this.resourceLocation = new ResourceLocation(resourceLocation.getNamespace(), "fairy_ring/" + resourceLocation.getPath());
            this.result = result.asItem();
            this.resultCount = resultCount;
            this.resultMode = resultMode;
            this.ingredients = ingredients;
            this.requiredMode = requiredMode;
            this.recipeTime = recipeTime;
        }

        public void serializeRecipeData(JsonObject json) {
            JsonArray jsonArray = new JsonArray();
            for (Ingredient ingredient : this.ingredients) {
                jsonArray.add(ingredient.toJson());
            }
            json.add("ingredients", jsonArray);

            json.addProperty("recipeTime", this.recipeTime);

            if (this.requiredMode != FairyRingMode.NORMAL) {
                json.addProperty("mode", this.requiredMode.getName());
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.resultCount > 1) {
                jsonObject.addProperty("count", this.resultCount);
            }
            if (this.resultMode != this.requiredMode) {
                jsonObject.addProperty("mode", this.resultMode.getName());
            }
            json.add("result", jsonObject);
        }

        @Nonnull
        public RecipeSerializer<?> getType() {
            return FairyRingRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }

        @Nonnull
        public ResourceLocation getId() {
            return this.resourceLocation;
        }
    }
}
