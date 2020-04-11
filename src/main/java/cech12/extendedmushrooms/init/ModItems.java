package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.entity.ExtendedMushroomsEntityTypes;
import cech12.extendedmushrooms.item.MushroomBoatItem;
import cech12.extendedmushrooms.item.MushroomSporesItem;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import static cech12.extendedmushrooms.api.item.ExtendedMushroomsItems.*;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        MUSHROOM_BOAT = registerItem("mushroom_boat", new MushroomBoatItem(MushroomWoodType.MUSHROOM));
        GLOWSHROOM_BOAT = registerItem("glowshroom_boat", new MushroomBoatItem(MushroomWoodType.GLOWSHROOM));
        POISONOUS_MUSHROOM_BOAT = registerItem("poisonous_mushroom_boat", new MushroomBoatItem(MushroomWoodType.POISONOUS_MUSHROOM));

        GRILLED_MUSHROOM = registerItem("grilled_mushroom", new Item((new Item.Properties()).group(ItemGroup.FOOD).food((new Food.Builder()).hunger(2).saturation(0.3F).build())));
        MUSHROOM_BREAD = registerItem("mushroom_bread", new Item((new Item.Properties()).group(ItemGroup.FOOD).food((new Food.Builder()).hunger(5).saturation(0.3F).build())));

        MUSHROOM_SPORES = registerItem("mushroom_spores", new MushroomSporesItem((new Item.Properties()).group(ItemGroup.MATERIALS)));
        GLOWSTONE_CRUMBS = registerItem("glowstone_crumbs", new Item((new Item.Properties()).group(ItemGroup.MATERIALS)));

        MUSHROOM_SHEEP_SPAWN_EGG = registerItem("mushroom_sheep_spawn_egg", new SpawnEggItem(ExtendedMushroomsEntityTypes.MUSHROOM_SHEEP, 10489616, 10051392, (new Item.Properties()).group(ItemGroup.MISC)));
    }

    public static Item registerItem(String name, Item item) {
        item.setRegistryName(name);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }

}
