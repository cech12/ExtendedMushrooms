package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.entity.ExtendedMushroomsEntityTypes;
import cech12.extendedmushrooms.item.MushroomBoatItem;
import cech12.extendedmushrooms.item.MushroomSporesItem;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
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
        HONEY_FUNGUS_BOAT = registerItem("honey_fungus_boat", new MushroomBoatItem(MushroomWoodType.HONEY_FUNGUS));

        MUSHROOM_SIGN = registerItem("mushroom_sign", new SignItem((new Item.Properties()).stacksTo(16).tab(ItemGroup.TAB_DECORATIONS), ModBlocks.MUSHROOM_STANDING_SIGN.get(), ModBlocks.MUSHROOM_WALL_SIGN.get()));
        GLOWSHROOM_SIGN = registerItem("glowshroom_sign", new SignItem((new Item.Properties()).stacksTo(16).tab(ItemGroup.TAB_DECORATIONS), ModBlocks.GLOWSHROOM_STANDING_SIGN.get(), ModBlocks.GLOWSHROOM_WALL_SIGN.get()));
        POISONOUS_MUSHROOM_SIGN = registerItem("poisonous_mushroom_sign", new SignItem((new Item.Properties()).stacksTo(16).tab(ItemGroup.TAB_DECORATIONS), ModBlocks.POISONOUS_MUSHROOM_STANDING_SIGN.get(), ModBlocks.POISONOUS_MUSHROOM_WALL_SIGN.get()));
        HONEY_FUNGUS_SIGN = registerItem("honey_fungus_sign", new SignItem((new Item.Properties()).stacksTo(16).tab(ItemGroup.TAB_DECORATIONS), ModBlocks.HONEY_FUNGUS_STANDING_SIGN.get(), ModBlocks.HONEY_FUNGUS_WALL_SIGN.get()));

        GRILLED_MUSHROOM = registerItem("grilled_mushroom", new Item((new Item.Properties()).tab(ItemGroup.TAB_FOOD).food((new Food.Builder()).nutrition(2).saturationMod(0.3F).build())));
        MUSHROOM_BREAD = registerItem("mushroom_bread", new Item((new Item.Properties()).tab(ItemGroup.TAB_FOOD).food((new Food.Builder()).nutrition(5).saturationMod(0.3F).build())));

        MUSHROOM_SPORES = registerItem("mushroom_spores", new MushroomSporesItem((new Item.Properties()).tab(ItemGroup.TAB_MATERIALS)));
        GLOWSTONE_CRUMBS = registerItem("glowstone_crumbs", new Item((new Item.Properties()).tab(ItemGroup.TAB_MATERIALS)));

        MUSHROOM_SHEEP_SPAWN_EGG = registerItem("mushroom_sheep_spawn_egg", new SpawnEggItem(ExtendedMushroomsEntityTypes.MUSHROOM_SHEEP, 10489616, 10051392, (new Item.Properties()).tab(ItemGroup.TAB_MISC)));
    }

    public static Item registerItem(String name, Item item) {
        item.setRegistryName(name);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }

}
