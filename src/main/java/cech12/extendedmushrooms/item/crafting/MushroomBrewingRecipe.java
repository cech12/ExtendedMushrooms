package cech12.extendedmushrooms.item.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import javax.annotation.Nonnull;

public class MushroomBrewingRecipe implements IBrewingRecipe {

    ITag<Item> mushroomTag;
    Potion result;

    public MushroomBrewingRecipe(ITag<Item> mushroomTag, Potion result) {
        this.mushroomTag = mushroomTag;
        this.result = result;
    }

    @Override
    public boolean isInput(ItemStack input) {
        return input.getItem() == Items.POTION && PotionUtils.getPotionFromItem(input) == Potions.AWKWARD;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem().isIn(this.mushroomTag);
    }

    @Nonnull
    @Override
    public ItemStack getOutput(@Nonnull ItemStack input, @Nonnull ItemStack ingredient) {
        if (!input.isEmpty() && !ingredient.isEmpty() && isInput(input) && isIngredient(ingredient)) {
            ItemStack output = input.copy();
            PotionUtils.addPotionToItemStack(output, this.result);
            return output;
        }
        return ItemStack.EMPTY;
    }
}
