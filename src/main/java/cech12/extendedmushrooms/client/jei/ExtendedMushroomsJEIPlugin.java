package cech12.extendedmushrooms.client.jei;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.recipe.ExtendedMushroomsRecipeTypes;
import cech12.extendedmushrooms.api.recipe.FairyRingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;

@JeiPlugin
public class ExtendedMushroomsJEIPlugin implements IModPlugin {


    public static final String TEXTURE_GUI_PATH = "textures/gui/";
    public static final String TEXTURE_GUI_FAIRY_RING = TEXTURE_GUI_PATH + "fairy_ring.png";

    public static final ResourceLocation RECIPE_GUI_FAIRY_RING = new ResourceLocation(ExtendedMushrooms.MOD_ID, TEXTURE_GUI_FAIRY_RING);

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, "plugin_" + ExtendedMushrooms.MOD_ID);
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            RecipeManager manager = player.connection.getRecipeManager();
            registration.addRecipes(manager.getAllRecipesFor((IRecipeType<FairyRingRecipe>) ExtendedMushroomsRecipeTypes.FAIRY_RING), ExtendedMushroomsRecipeTypes.FAIRY_RING_ID);
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FairyRingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        for (Item mushroom : Tags.Items.MUSHROOMS.getValues()) {
            registration.addRecipeCatalyst(new ItemStack(mushroom), ExtendedMushroomsRecipeTypes.FAIRY_RING_ID);
        }
    }

}
