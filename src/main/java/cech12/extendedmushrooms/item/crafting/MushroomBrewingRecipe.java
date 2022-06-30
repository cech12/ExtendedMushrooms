package cech12.extendedmushrooms.item.crafting;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import javax.annotation.Nonnull;

public class MushroomBrewingRecipe implements IBrewingRecipe {

    TagKey<Item> mushroomTag;
    Potion result;

    public MushroomBrewingRecipe(TagKey<Item> mushroomTag, Potion result) {
        this.mushroomTag = mushroomTag;
        this.result = result;
    }

    @Override
    public boolean isInput(ItemStack input) {
        return input.getItem() == Items.POTION && PotionUtils.getPotion(input) == Potions.AWKWARD;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.is(this.mushroomTag);
    }

    @Nonnull
    @Override
    public ItemStack getOutput(@Nonnull ItemStack input, @Nonnull ItemStack ingredient) {
        if (!input.isEmpty() && !ingredient.isEmpty() && isInput(input) && isIngredient(ingredient)) {
            ItemStack output = input.copy();
            PotionUtils.setPotion(output, this.result);
            return output;
        }
        return ItemStack.EMPTY;
    }
}
