package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.item.GrilledMushroomItem;
import cech12.extendedmushrooms.item.MushroomBreadItem;
import cech12.extendedmushrooms.item.MushroomSporesItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModItems {

    private static final Item[] items = {
            new GrilledMushroomItem(),
            new MushroomBreadItem(),
            new MushroomSporesItem()
    };

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : ModItems.items) {
            event.getRegistry().register(item);
        }
    }

}
