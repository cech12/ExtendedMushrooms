package cech12.extendedmushrooms.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BotanyPotsCropBuilder {

    private final List<JsonObject> conditions = new ArrayList<>();
    private final Item mushroom;
    private final int lightLevel;

    private BotanyPotsCropBuilder(Item mushroom, int lightLevel) {
        this.mushroom = mushroom;
        this.lightLevel = lightLevel;

        JsonObject object = new JsonObject();
        object.addProperty("type", ModLoadedCondition.Serializer.INSTANCE.getID().toString());
        ModLoadedCondition.Serializer.INSTANCE.write(object, new ModLoadedCondition("botanypots"));
        conditions.add(object);
    }

    public static BotanyPotsCropBuilder create(Item mushroom, int lightLevel) {
        return new BotanyPotsCropBuilder(mushroom, lightLevel);
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        consumer.accept(new Crop(this.conditions, this.mushroom, this.lightLevel));
    }

    public static class Crop implements FinishedRecipe {
        private final ResourceLocation resourceLocation;
        private final List<JsonObject> conditions;
        private final Item mushroom;
        private final int lightLevel;

        public Crop(List<JsonObject> conditions, Item mushroom, int lightLevel) {
            this.resourceLocation = new ResourceLocation("botanypots", "crops/" + ForgeRegistries.ITEMS.getKey(mushroom).getPath());
            this.conditions = conditions;
            this.mushroom = mushroom;
            this.lightLevel = lightLevel;
        }

        public void serializeRecipeData(@Nonnull JsonObject json) {
            json.addProperty("type", "botanypots:crop");

            JsonArray conditionArray = new JsonArray();
            for (JsonObject jsonObject : this.conditions) {
                conditionArray.add(jsonObject);
            }
            json.add("conditions", conditionArray);

            JsonObject jsonSeed = new JsonObject();
            jsonSeed.addProperty("item", ForgeRegistries.ITEMS.getKey(this.mushroom).toString());
            json.add("seed", jsonSeed);

            JsonArray jsonCategories = new JsonArray();
            jsonCategories.add("stone");
            jsonCategories.add("mushroom");
            jsonCategories.add("mulch");
            json.add("categories", jsonCategories);

            json.addProperty("growthTicks", 1200);
            if (this.lightLevel > 0) {
                json.addProperty("lightLevel", this.lightLevel);
            }

            JsonObject jsonDisplay = new JsonObject();
            jsonDisplay.addProperty("block", ForgeRegistries.ITEMS.getKey(this.mushroom).toString());
            json.add("display", jsonDisplay);

            JsonObject jsonDrop = new JsonObject();
            jsonDrop.addProperty("chance", 1.00);
            JsonObject jsonOutput = new JsonObject();
            jsonOutput.addProperty("item", ForgeRegistries.ITEMS.getKey(this.mushroom).toString());
            jsonDrop.add("output", jsonOutput);
            JsonArray jsonDrops = new JsonArray();
            jsonDrops.add(jsonDrop);
            json.add("drops", jsonDrops);
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
