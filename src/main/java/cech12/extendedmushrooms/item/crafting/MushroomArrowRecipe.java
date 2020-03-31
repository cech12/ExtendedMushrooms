package cech12.extendedmushrooms.item.crafting;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class MushroomArrowRecipe extends SpecialRecipe {

    public static final SpecialRecipeSerializer<MushroomArrowRecipe> SERIALIZER = new Serializer();

    private static final Map<Item, Potion> MUSHROOM_POTION_MAP = new HashMap<>();

    static {
        MUSHROOM_POTION_MAP.putIfAbsent(ExtendedMushroomsBlocks.GLOWSHROOM.asItem(), Potions.NIGHT_VISION);
        MUSHROOM_POTION_MAP.putIfAbsent(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM.asItem(), Potions.POISON);
    }

    public MushroomArrowRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(@Nonnull CraftingInventory inv, @Nonnull World worldIn) {
        try {
            new RecipeIngredients(inv);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull CraftingInventory inv) {
        try {
            RecipeIngredients ingredients = new RecipeIngredients(inv);
            Potion potion = MUSHROOM_POTION_MAP.getOrDefault(ingredients.mushroom.getItem(), null);
            if (potion != null) {
                ItemStack tippedArrow = new ItemStack(Items.TIPPED_ARROW, 1);
                PotionUtils.addPotionToItemStack(tippedArrow, potion);
                return tippedArrow;
            } else {
                return ItemStack.EMPTY;
            }
        } catch (Exception e) {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    private static class Serializer extends SpecialRecipeSerializer<MushroomArrowRecipe> {
        public Serializer() {
            super(MushroomArrowRecipe::new);
            this.setRegistryName(ExtendedMushrooms.MOD_ID, "mushroom_arrow_recipe");
        }
    }

    private static class RecipeIngredients {

        ItemStack arrow = null;
        ItemStack mushroom = null;

        RecipeIngredients(@Nonnull CraftingInventory inv) throws Exception {
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    if (stack.getItem() == Items.ARROW) {
                        if (this.arrow == null) {
                            this.arrow = stack;
                        } else {
                            throw new Exception();
                        }
                    } else if (MUSHROOM_POTION_MAP.containsKey(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM.asItem())) {
                        if (this.mushroom == null) {
                            this.mushroom = stack;
                        } else {
                            throw new Exception();
                        }
                    } else {
                        throw new Exception();
                    }
                }
            }
            if (this.arrow == null || this.mushroom == null) {
                throw new Exception();
            }
        }
    }

}
