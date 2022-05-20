package cech12.extendedmushrooms.client.jei;

import cech12.extendedmushrooms.api.recipe.ExtendedMushroomsRecipeTypes;
import cech12.extendedmushrooms.api.recipe.FairyRingRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FairyRingCategory implements IRecipeCategory<FairyRingRecipe> {

    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;
    private final int regularCookTime;
    private final LoadingCache<Integer, IDrawableAnimated> cachedBubbles;

    public FairyRingCategory(IGuiHelper guiHelper) {
        this.regularCookTime = 200;
        this.localizedName = I18n.get("jei.extendedmushrooms.fairy_ring");
        this.background = guiHelper.createDrawable(ExtendedMushroomsJEIPlugin.RECIPE_GUI_FAIRY_RING, 0, 0, 112, 61);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(Items.RED_MUSHROOM));
        this.cachedBubbles = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<Integer, IDrawableAnimated>() {
                    @Override
                    public IDrawableAnimated load(@Nonnull Integer cookTime) {
                        return guiHelper.drawableBuilder(ExtendedMushroomsJEIPlugin.RECIPE_GUI_FAIRY_RING, 112, 0, 11, 20)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.BOTTOM, false);
                    }
                });
    }

    protected IDrawableAnimated getBubbles(FairyRingRecipe recipe) {
        int cookTime = recipe.getRecipeTime();
        if (cookTime <= 0) {
            cookTime = regularCookTime;
        }
        return this.cachedBubbles.getUnchecked(cookTime);
    }

    @Override
    @Nonnull
    public ResourceLocation getUid() {
        return ExtendedMushroomsRecipeTypes.FAIRY_RING_ID;
    }

    @Override
    @Nonnull
    public Class<? extends FairyRingRecipe> getRecipeClass() {
        return FairyRingRecipe.class;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return this.localizedName;
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    @Nonnull
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(FairyRingRecipe recipe, @Nonnull IIngredients ingredients) {
        List<List<ItemStack>> list = new ArrayList<>();
        for (Ingredient ingr : recipe.getIngredients()) {
            list.add(Arrays.asList(ingr.getItems()));
        }
        ingredients.setInputLists(VanillaTypes.ITEM, list);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void draw(@Nonnull FairyRingRecipe recipe, @Nonnull MatrixStack matrixStack, double mouseX, double mouseY) {
        IDrawableAnimated arrow = getBubbles(recipe);
        arrow.draw(matrixStack, 69, 24);
        drawRecipeTime(recipe, matrixStack, 52);
    }

    protected void drawRecipeTime(FairyRingRecipe recipe, MatrixStack matrixStack, int y) {
        int cookTime = recipe.getRecipeTime();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            TranslationTextComponent timeString = new TranslationTextComponent("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            FontRenderer fontRenderer = Minecraft.getInstance().font;
            int stringWidth = fontRenderer.width(timeString);
            fontRenderer.draw(matrixStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
        }
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull FairyRingRecipe recipe, @Nonnull IIngredients ingredients) {
        int index = 0;
        Vector2f center = new Vector2f(22, 19);
        if (ingredients.getInputs(VanillaTypes.ITEM).size() > 1) {
            double angleBetweenEach = 360.0 / ingredients.getInputs(VanillaTypes.ITEM).size();
            Vector2f point = new Vector2f(22, 4);
            for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
                recipeLayout.getItemStacks().init(index, true, (int) point.x, (int) point.y);
                recipeLayout.getItemStacks().set(index, o);
                point = rotatePointAbout(point, center, angleBetweenEach);
                index++;
            }
        } else {
            recipeLayout.getItemStacks().init(index, true, (int) center.x, (int) center.y);
            recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(0));
            index++;
        }
        recipeLayout.getItemStacks().init(index, false, 90, 25);
        recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }

    public static Vector2f rotatePointAbout(Vector2f in, Vector2f about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vector2f((float) newX, (float) newY);
    }
}
