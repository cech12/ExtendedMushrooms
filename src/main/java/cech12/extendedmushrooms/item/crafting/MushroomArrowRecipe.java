package cech12.extendedmushrooms.item.crafting;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModTags;
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
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class MushroomArrowRecipe extends SpecialRecipe {

    public static final SpecialRecipeSerializer<MushroomArrowRecipe> SERIALIZER = new Serializer();

    private static final Map<Tag<Item>, Potion> MUSHROOM_POTION_MAP = new HashMap<>();

    static {
        MUSHROOM_POTION_MAP.putIfAbsent(ModTags.ForgeItems.MUSHROOMS_GLOWSHROOM, Potions.NIGHT_VISION);
        MUSHROOM_POTION_MAP.putIfAbsent(ModTags.ForgeItems.MUSHROOMS_POISONOUS, Potions.POISON);
    }

    public MushroomArrowRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    private Potion getPotionFromMushroom(Item mushroom) {
        for (Map.Entry<Tag<Item>, Potion> entry : MUSHROOM_POTION_MAP.entrySet()) {
            if (mushroom.isIn(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
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
            Potion potion = getPotionFromMushroom(ingredients.mushroom.getItem());
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
                    } else if (MUSHROOM_POTION_MAP.containsKey(ModTags.ForgeItems.MUSHROOMS_POISONOUS)) {
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
