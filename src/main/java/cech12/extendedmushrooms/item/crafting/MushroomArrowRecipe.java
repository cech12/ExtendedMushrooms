package cech12.extendedmushrooms.item.crafting;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class MushroomArrowRecipe extends CustomRecipe {

    public static final SimpleRecipeSerializer<MushroomArrowRecipe> SERIALIZER = new Serializer();

    private static final Map<TagKey<Item>, Potion> MUSHROOM_POTION_MAP = new HashMap<>();

    static {
        MUSHROOM_POTION_MAP.putIfAbsent(ModTags.ForgeItems.MUSHROOMS_GLOWSHROOM, Potions.NIGHT_VISION);
        MUSHROOM_POTION_MAP.putIfAbsent(ModTags.ForgeItems.MUSHROOMS_JUMP_BOOSTING, Potions.LEAPING);
        MUSHROOM_POTION_MAP.putIfAbsent(ModTags.ForgeItems.MUSHROOMS_POISONOUS, Potions.POISON);
        MUSHROOM_POTION_MAP.putIfAbsent(ModTags.ForgeItems.MUSHROOMS_SLOWING_DOWN, Potions.SLOWNESS);
    }

    public MushroomArrowRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    private Potion getPotionFromMushroom(Item mushroom) {
        for (Map.Entry<TagKey<Item>, Potion> entry : MUSHROOM_POTION_MAP.entrySet()) {
            if (mushroom.getDefaultInstance().is(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean matches(@Nonnull CraftingContainer inv, @Nonnull Level worldIn) {
        try {
            new RecipeIngredients(inv);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull CraftingContainer inv) {
        try {
            RecipeIngredients ingredients = new RecipeIngredients(inv);
            Potion potion = getPotionFromMushroom(ingredients.mushroom.getItem());
            if (potion != null) {
                ItemStack tippedArrow = new ItemStack(Items.TIPPED_ARROW, 1);
                PotionUtils.setPotion(tippedArrow, potion);
                return tippedArrow;
            } else {
                return ItemStack.EMPTY;
            }
        } catch (Exception e) {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    private static class Serializer extends SimpleRecipeSerializer<MushroomArrowRecipe> {
        public Serializer() {
            super(MushroomArrowRecipe::new);
            this.setRegistryName(ExtendedMushrooms.MOD_ID, "mushroom_arrow_recipe");
        }
    }

    private static class RecipeIngredients {

        ItemStack arrow = null;
        ItemStack mushroom = null;

        RecipeIngredients(@Nonnull CraftingContainer inv) throws Exception {
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack stack = inv.getItem(i);
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
