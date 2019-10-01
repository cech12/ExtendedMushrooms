package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class MushroomBoatItem extends BoatItem {

    private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_boat");

    public MushroomBoatItem() {
        super(BoatEntity.Type.OAK, (new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION));
        setRegistryName(REGISTRY_NAME);
    }
}
