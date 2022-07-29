package cech12.extendedmushrooms.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BotanyPotsSoilBuilder {

    private final List<JsonObject> conditions = new ArrayList<>();
    private final Block soil;
    private final int lightLevel;

    private BotanyPotsSoilBuilder(Block soil, int lightLevel) {
        this.soil = soil;
        this.lightLevel = lightLevel;

        JsonObject object = new JsonObject();
        object.addProperty("type", ModLoadedCondition.Serializer.INSTANCE.getID().toString());
        ModLoadedCondition.Serializer.INSTANCE.write(object, new ModLoadedCondition("botanypots"));
        conditions.add(object);
    }

    public static BotanyPotsSoilBuilder create(Block mushroom, int lightLevel) {
        return new BotanyPotsSoilBuilder(mushroom, lightLevel);
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        consumer.accept(new Soil(this.conditions, this.soil, this.lightLevel));
    }

    public static class Soil implements FinishedRecipe {
        private final ResourceLocation resourceLocation;
        private final List<JsonObject> conditions;
        private final Block soil;
        private final int lightLevel;

        public Soil(List<JsonObject> conditions, Block soil, int lightLevel) {
            this.resourceLocation = new ResourceLocation("botanypots", "soil/" + soil.getRegistryName().getPath());
            this.conditions = conditions;
            this.soil = soil;
            this.lightLevel = lightLevel;
        }

        public void serializeRecipeData(@Nonnull JsonObject json) {
            json.addProperty("type", "botanypots:soil");

            JsonArray conditionArray = new JsonArray();
            for (JsonObject jsonObject : this.conditions) {
                conditionArray.add(jsonObject);
            }
            json.add("conditions", conditionArray);

            JsonObject jsonSeed = new JsonObject();
            jsonSeed.addProperty("item", this.soil.asItem().getRegistryName().toString());
            json.add("input", jsonSeed);

            JsonObject jsonDisplay = new JsonObject();
            jsonDisplay.addProperty("block", this.soil.getRegistryName().toString());
            json.add("display", jsonDisplay);

            JsonArray jsonCategories = new JsonArray();
            jsonCategories.add("mushroom");
            json.add("categories", jsonCategories);

            json.addProperty("growthModifier", 0.95);
            if (this.lightLevel > 0) {
                json.addProperty("lightLevel", this.lightLevel);
            }
        }

        @Nonnull
        public RecipeSerializer<?> getType() {
            return RecipeSerializer.SHAPELESS_RECIPE;
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
