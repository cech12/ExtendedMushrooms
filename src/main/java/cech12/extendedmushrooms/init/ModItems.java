package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.item.GrilledMushroomItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModItems {

    private static final Item[] items = {
            new GrilledMushroomItem()
    };

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : ModItems.items) {
            event.getRegistry().register(item);
        }
    }

}
