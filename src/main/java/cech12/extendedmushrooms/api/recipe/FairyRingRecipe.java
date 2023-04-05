package cech12.extendedmushrooms.api.recipe;

import cech12.extendedmushrooms.blockentity.FairyRingBlockEntity;
import cech12.extendedmushrooms.init.ModRecipeTypes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FairyRingRecipe implements IFairyRingRecipe, Recipe<Container> {

    public static final Serializer SERIALIZER = new Serializer();

    protected ResourceLocation resourceLocation;
    protected NonNullList<Ingredient> ingredients;
    protected FairyRingMode requiredMode;
    protected int recipeTime;
    protected ItemStack resultStack;
    protected FairyRingMode resultMode;

    public FairyRingRecipe(ResourceLocation resourceLocation, FairyRingMode requiredMode, NonNullList<Ingredient> ingredients, int recipeTime, FairyRingMode resultMode, ItemStack resultStack) {
        this.resourceLocation = resourceLocation;
        this.requiredMode = requiredMode;
        this.ingredients = ingredients;
        this.recipeTime = recipeTime;
        this.resultMode = resultMode;
        this.resultStack = resultStack;
    }

    @Nonnull
    @Override
    public ResourceLocation getId () {
        return this.resourceLocation;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.FAIRY_RING.get();
    }

    @Nonnull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public int getRecipeTime() {
        return this.recipeTime;
    }

    @Nonnull
    @Override
    public FairyRingMode getRequiredMode() {
        return this.requiredMode;
    }

    @Nullable
    @Override
    public ItemStack getResultItemStack() {
        return resultStack.copy();
    }

    @Nonnull
    @Override
    public FairyRingMode getResultMode() {
        return this.resultMode;
    }


    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    @Nonnull
    @Override
    public ItemStack getResultItem(@Nonnull RegistryAccess registryAccess) {
        return this.resultStack.copy();
    }

    @Nonnull
    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(Items.RED_MUSHROOM);
    }

    public boolean isValid(FairyRingMode mode, Container inv) {
        // inventory must have a stack limit of 1
        if (inv.getMaxStackSize() != 1) return false;
        // actual mode must fit to this recipe
        if (mode != this.getRequiredMode()) return false;

        //ingredients must be the same
        List<Ingredient> ingredientsMissing = new ArrayList<>(this.ingredients);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack input = inv.getItem(i);
            if (!input.isEmpty()) {
                int index = -1;
                for (int j = 0; j < ingredientsMissing.size(); j++) {
                    Ingredient ingredient = ingredientsMissing.get(j);
                    if (ingredient.test(input)) {
                        index = j;
                        break;
                    }
                }
                if (index == -1) {
                    return false;
                }
                ingredientsMissing.remove(index);
            }
        }
        return ingredientsMissing.isEmpty();
    }

    /**
     * @deprecated has no use
     */
    @Deprecated
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        // This method is ignored.
        return false;
    }

    /**
     * @deprecated use isValid method
     */
    @Deprecated
    @Override
    public boolean matches(@Nonnull Container inv, @Nonnull Level worldIn) {
        // This method is ignored. - isValid is used.
        return false;
    }

    /**
     * @deprecated use isValid method
     */
    @Nonnull
    @Deprecated
    @Override
    public ItemStack assemble(@Nonnull Container inv, @Nonnull RegistryAccess registryAccess) {
        // This method is ignored. - getResultItemStack is used.
        return this.resultStack.copy();
    }

    public static class Serializer implements RecipeSerializer<FairyRingRecipe> {

        Serializer() {
        }

        @Nonnull
        public FairyRingRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            //deserialize required mode
            FairyRingMode requiredMode = readMode(json, FairyRingMode.NORMAL);
            //deserialize ingredients
            NonNullList<Ingredient> ingredients = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for fairy ring recipe");
            } else if (ingredients.size() > FairyRingBlockEntity.INVENTORY_SIZE) {
                throw new JsonParseException("Too many ingredients for fairy ring recipe the max is " + FairyRingBlockEntity.INVENTORY_SIZE);
            }
            //deserialize recipe time
            int time = GsonHelper.getAsInt(json, "recipeTime");
            //deserialize result
            JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
            //deserialize result mode
            FairyRingMode resultMode = readMode(resultJson, requiredMode);
            //deserialize result item stack
            ItemStack resultStack = ItemStack.EMPTY;
            if (GsonHelper.isValidNode(resultJson, "item")) {
                resultStack = ShapedRecipe.itemStackFromJson(resultJson);
            }
            return new FairyRingRecipe(recipeId, requiredMode, ingredients, time, resultMode, resultStack);
        }

        public FairyRingRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
            FairyRingMode requiredMode = FairyRingMode.byName(buffer.readUtf(32767));
            int size = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buffer));
            int time = buffer.readVarInt();
            FairyRingMode resultMode = FairyRingMode.byName(buffer.readUtf(32767));
            ItemStack resultStack = buffer.readItem();
            return new FairyRingRecipe(recipeId, requiredMode, ingredients, time, resultMode, resultStack);
        }

        public void toNetwork(FriendlyByteBuf buffer, FairyRingRecipe recipe) {
            buffer.writeUtf(recipe.requiredMode.getName());
            buffer.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeVarInt(recipe.recipeTime);
            buffer.writeUtf(recipe.resultMode.getName());
            buffer.writeItem(recipe.resultStack);
        }

        private static FairyRingMode readMode(@Nonnull JsonObject jsonObject, @Nonnull FairyRingMode defaultMode) {
            FairyRingMode mode = defaultMode;
            try {
                String modeString = GsonHelper.getAsString(jsonObject, "mode");
                mode = FairyRingMode.byName(modeString);
                if (mode == null) {
                    throw new JsonParseException("Mode " + modeString + " does not exist");
                }
            } catch (Exception ignored) {
            }
            return mode;
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray jsonArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for (int i = 0; i < jsonArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(jsonArray.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }
    }
}
