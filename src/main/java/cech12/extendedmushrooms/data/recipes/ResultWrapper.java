package cech12.extendedmushrooms.data.recipes;

import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ResultWrapper implements IFinishedRecipe {
    private final IFinishedRecipe delegate;
    @Nullable
    private final IRecipeSerializer<?> type;
    @Nullable
    private final Consumer<JsonObject> transform;

    /**
     * Wraps recipe consumer with one that swaps the recipe type to a different one.
     */
    public static Consumer<IFinishedRecipe> ofType(IRecipeSerializer<?> type, Consumer<IFinishedRecipe> parent) {
        return recipe -> parent.accept(new ResultWrapper(recipe, type, null));
    }

    /**
     * Transforms the resulting recipe json with the specified action, eg. adding NBT to an item result.
     */
    public static Consumer<IFinishedRecipe> transformJson(Consumer<IFinishedRecipe> parent, Consumer<JsonObject> transform) {
        return recipe -> parent.accept(new ResultWrapper(recipe, null, transform));
    }

    private ResultWrapper(IFinishedRecipe delegate, @Nullable IRecipeSerializer<?> type, @Nullable Consumer<JsonObject> transform) {
        this.delegate = delegate;
        this.type = type;
        this.transform = transform;
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
        delegate.serializeRecipeData(json);
        if (transform != null) {
            transform.accept(json);
        }
    }

    @Override
    public JsonObject serializeRecipe() {
        if (type == null) {
            return IFinishedRecipe.super.serializeRecipe();
        }
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("type", this.type.getRegistryName().toString());
        this.serializeRecipeData(jsonobject);
        return jsonobject;
    }

    @Override
    public ResourceLocation getId() {
        return delegate.getId();
    }

    @Override
    public IRecipeSerializer<?> getType() {
        return type != null ? type : delegate.getType();
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return delegate.serializeAdvancement();
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return delegate.getAdvancementId();
    }
}
