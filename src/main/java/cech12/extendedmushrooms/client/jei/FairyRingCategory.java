package cech12.extendedmushrooms.client.jei;

import cech12.extendedmushrooms.init.ModRecipeTypes;
import cech12.extendedmushrooms.api.recipe.FairyRingRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nonnull;
import java.util.List;

public class FairyRingCategory implements IRecipeCategory<FairyRingRecipe> {

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;
    private final int regularCookTime;
    private final LoadingCache<Integer, IDrawableAnimated> cachedBubbles;

    public FairyRingCategory(IGuiHelper guiHelper) {
        this.regularCookTime = 200;
        this.localizedName = Component.translatable("jei.extendedmushrooms.fairy_ring");
        this.background = guiHelper.createDrawable(ExtendedMushroomsJEIPlugin.RECIPE_GUI_FAIRY_RING, 0, 0, 112, 61);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.RED_MUSHROOM));
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

    @Nonnull
    @Override
    public RecipeType<FairyRingRecipe> getRecipeType() {
        return new RecipeType<>(ModRecipeTypes.FAIRY_RING.getId(), FairyRingRecipe.class);
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
    public void draw(@Nonnull FairyRingRecipe recipe, @Nonnull IRecipeSlotsView recipeSlotsView, @Nonnull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IDrawableAnimated arrow = getBubbles(recipe);
        arrow.draw(guiGraphics, 69, 24);
        drawRecipeTime(recipe, guiGraphics, 52);
    }

    protected void drawRecipeTime(FairyRingRecipe recipe, GuiGraphics guiGraphics, int y) {
        int cookTime = recipe.getRecipeTime();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Font fontRenderer = Minecraft.getInstance().font;
            int stringWidth = fontRenderer.width(timeString);
            guiGraphics.drawString(fontRenderer, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
        }
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull FairyRingRecipe recipe, @Nonnull IFocusGroup focuses) {
        Vec2 center = new Vec2(24, 20);
        List<Ingredient> ingredients = recipe.getIngredients();
        if (ingredients.size() > 1) {
            double angleBetweenEach = 360.0 / ingredients.size();
            Vec2 point = new Vec2(center.x, 4);
            for (Ingredient ingredient : ingredients) {
                builder.addSlot(RecipeIngredientRole.INPUT, (int) point.x, (int) point.y)
                        .addIngredients(ingredient);
                point = rotatePointAbout(point, center, angleBetweenEach);
            }
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, (int) center.x, (int) center.y)
                    .addIngredients(ingredients.get(0));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 26)
                .addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }

    public static Vec2 rotatePointAbout(Vec2 in, Vec2 about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vec2((float) newX, (float) newY);
    }
}
