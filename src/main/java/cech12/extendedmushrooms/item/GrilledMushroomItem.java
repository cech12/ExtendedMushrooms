package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class GrilledMushroomItem extends Item {

    private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(ExtendedMushrooms.MOD_ID, "grilled_mushroom");

    private static final Food FOOD = (new Food.Builder()).hunger(2).saturation(0.3F).build();

    public GrilledMushroomItem() {
        super((new Item.Properties()).group(ItemGroup.FOOD).food(FOOD));
        this.setRegistryName(REGISTRY_NAME);
    }

}
