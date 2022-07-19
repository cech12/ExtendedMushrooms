package cech12.extendedmushrooms.client.jei;

import cech12.extendedmushrooms.init.ModRecipeTypes;
import cech12.extendedmushrooms.api.recipe.FairyRingRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FairyRingCategory implements IRecipeCategory<FairyRingRecipe> {

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;
    private final int regularCookTime;
    private final LoadingCache<Integer, IDrawableAnimated> cachedBubbles;

    public FairyRingCategory(IGuiHelper guiHelper) {
        this.regularCookTime = 200;
        this.localizedName = new TranslatableComponent("jei.extendedmushrooms.fairy_ring");
        this.background = guiHelper.createDrawable(ExtendedMushroomsJEIPlugin.RECIPE_GUI_FAIRY_RING, 0, 0, 112, 61);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(Items.RED_MUSHROOM));
        this.cachedBubbles = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Nonnull
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
        return ModRecipeTypes.FAIRY_RING_ID;
    }

    @Override
    @Nonnull
    public Class<? extends FairyRingRecipe> getRecipeClass() {
        return FairyRingRecipe.class;
    }

    @Override
    @Nonnull
    public Component getTitle() {
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
    public void draw(@Nonnull FairyRingRecipe recipe, @Nonnull IRecipeSlotsView recipeSlotsView, @Nonnull PoseStack matrixStack, double mouseX, double mouseY) {
        IDrawableAnimated arrow = getBubbles(recipe);
        arrow.draw(matrixStack, 69, 24);
        drawRecipeTime(recipe, matrixStack, 52);
    }

    protected void drawRecipeTime(FairyRingRecipe recipe, PoseStack matrixStack, int y) {
        int cookTime = recipe.getRecipeTime();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            TranslatableComponent timeString = new TranslatableComponent("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Font fontRenderer = Minecraft.getInstance().font;
            int stringWidth = fontRenderer.width(timeString);
            fontRenderer.draw(matrixStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
        }
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull FairyRingRecipe recipe, @Nonnull IFocusGroup focuses) {
        List<List<ItemStack>> ingredients = new ArrayList<>();
        for (Ingredient ingr : recipe.getIngredients()) {
            ingredients.add(Arrays.asList(ingr.getItems()));
        }

        int index = 0;
        Vec2 center = new Vec2(23, 19);
        if (ingredients.size() > 1) {
            double angleBetweenEach = 360.0 / recipe.getIngredients().size();
            Vec2 point = new Vec2(center.x, 4);
            for (List<ItemStack> o : ingredients) {
                builder.addSlot(RecipeIngredientRole.INPUT, (int) point.x, (int) point.y);
                //recipeLayout.getItemStacks().init(index, true, (int) point.x, (int) point.y);
                //recipeLayout.getItemStacks().set(index, o);
                point = rotatePointAbout(point, center, angleBetweenEach);
                index++;
            }
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, (int) center.x, (int) center.y);
            //recipeLayout.getItemStacks().init(index, true, (int) center.x, (int) center.y);
            //recipeLayout.getItemStacks().set(index, ingredients.get(0));
            index++;
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 25);
        //recipeLayout.getItemStacks().init(index, false, 90, 25);
        //recipeLayout.getItemStacks().set(index, ingredients.get(0));
    }

    public static Vec2 rotatePointAbout(Vec2 in, Vec2 about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vec2((float) newX, (float) newY);
    }
}
