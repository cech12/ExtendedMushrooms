package cech12.extendedmushrooms.api.recipe;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.item.crafting.IRecipeType;

public class FairyRingRecipeType implements IRecipeType<FairyRingRecipe> {

    @Override
    public String toString () {
        return ExtendedMushrooms.MOD_ID + ":fairy_ring_recipe";
    }

}
