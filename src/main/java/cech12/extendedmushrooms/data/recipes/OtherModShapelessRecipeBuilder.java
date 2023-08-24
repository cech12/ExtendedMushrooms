package cech12.extendedmushrooms.data.recipes;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.CraftingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class OtherModShapelessRecipeBuilder extends CraftingRecipeBuilder implements RecipeBuilder {
   private final RecipeCategory category;
   private final ResourceLocation result;
   private final int count;
   private final List<Ingredient> ingredients = Lists.newArrayList();
   @Nullable
   private String group;

   public OtherModShapelessRecipeBuilder(RecipeCategory p_250837_, ResourceLocation p_251897_, int p_252227_) {
      this.category = p_250837_;
      this.result = p_251897_;
      this.count = p_252227_;
   }

   public static OtherModShapelessRecipeBuilder shapeless(RecipeCategory p_250714_, ResourceLocation p_249659_) {
      return new OtherModShapelessRecipeBuilder(p_250714_, p_249659_, 1);
   }

   public static OtherModShapelessRecipeBuilder shapeless(RecipeCategory p_252339_, ResourceLocation p_250836_, int p_249928_) {
      return new OtherModShapelessRecipeBuilder(p_252339_, p_250836_, p_249928_);
   }

   public OtherModShapelessRecipeBuilder requires(TagKey<Item> p_206420_) {
      return this.requires(Ingredient.of(p_206420_));
   }

   public OtherModShapelessRecipeBuilder requires(ItemLike p_126210_) {
      return this.requires(p_126210_, 1);
   }

   public OtherModShapelessRecipeBuilder requires(ItemLike p_126212_, int p_126213_) {
      for(int i = 0; i < p_126213_; ++i) {
         this.requires(Ingredient.of(p_126212_));
      }

      return this;
   }

   public OtherModShapelessRecipeBuilder requires(Ingredient p_126185_) {
      return this.requires(p_126185_, 1);
   }

   public OtherModShapelessRecipeBuilder requires(Ingredient p_126187_, int p_126188_) {
      for(int i = 0; i < p_126188_; ++i) {
         this.ingredients.add(p_126187_);
      }

      return this;
   }

   public OtherModShapelessRecipeBuilder unlockedBy(String p_126197_, CriterionTriggerInstance p_126198_) {
      //not implemented
      return this;
   }

   public OtherModShapelessRecipeBuilder group(@Nullable String p_126195_) {
      this.group = p_126195_;
      return this;
   }

   public Item getResult() {
      return ForgeRegistries.ITEMS.getValue(this.result);
   }

   public void save(Consumer<FinishedRecipe> p_126205_, ResourceLocation p_126206_) {
      p_126205_.accept(new OtherModShapelessRecipeBuilder.Result(p_126206_, this.result, this.count, this.group == null ? "" : this.group, determineBookCategory(this.category), this.ingredients));
   }


   public static class Result extends CraftingResult {
      private final ResourceLocation id;
      private final ResourceLocation result;
      private final int count;
      private final String group;
      private final List<Ingredient> ingredients;

      public Result(ResourceLocation p_249007_, ResourceLocation p_248667_, int p_249014_, String p_248592_, CraftingBookCategory p_249485_, List<Ingredient> p_252312_) {
         super(p_249485_);
         this.id = p_249007_;
         this.result = p_248667_;
         this.count = p_249014_;
         this.group = p_248592_;
         this.ingredients = p_252312_;
      }

      public void serializeRecipeData(JsonObject p_126230_) {
         super.serializeRecipeData(p_126230_);
         if (!this.group.isEmpty()) {
            p_126230_.addProperty("group", this.group);
         }

         JsonArray conditionArray = new JsonArray();
         JsonObject condition = new JsonObject();
         condition.addProperty("type", ModLoadedCondition.Serializer.INSTANCE.getID().toString());
         ModLoadedCondition.Serializer.INSTANCE.write(condition, new ModLoadedCondition(this.result.getNamespace()));
         conditionArray.add(condition);
         p_126230_.add("conditions", conditionArray);

         JsonArray jsonarray = new JsonArray();

         for(Ingredient ingredient : this.ingredients) {
            jsonarray.add(ingredient.toJson());
         }

         p_126230_.add("ingredients", jsonarray);
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("item", this.result.toString());
         if (this.count > 1) {
            jsonobject.addProperty("count", this.count);
         }

         p_126230_.add("result", jsonobject);
      }

      public RecipeSerializer<?> getType() {
         return RecipeSerializer.SHAPELESS_RECIPE;
      }

      public ResourceLocation getId() {
         return this.id;
      }

      @Nullable
      public JsonObject serializeAdvancement() {
         return null;
      }

      @Nullable
      public ResourceLocation getAdvancementId() {
         return null;
      }
   }
}