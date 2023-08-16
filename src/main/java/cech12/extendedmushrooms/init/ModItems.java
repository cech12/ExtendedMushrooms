package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.item.MushroomBoatItem;
import cech12.extendedmushrooms.item.MushroomSporesItem;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.item.MycyclopediaItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExtendedMushrooms.MOD_ID);

    public static final RegistryObject<Item> MUSHROOM_BOAT = ITEMS.register("mushroom_boat", () -> new MushroomBoatItem(MushroomWoodType.MUSHROOM, false));
    public static final RegistryObject<Item> MUSHROOM_CHEST_BOAT = ITEMS.register("mushroom_chest_boat", () -> new MushroomBoatItem(MushroomWoodType.MUSHROOM, true));
    public static final RegistryObject<Item> GLOWSHROOM_BOAT = ITEMS.register("glowshroom_boat", () -> new MushroomBoatItem(MushroomWoodType.GLOWSHROOM, false));
    public static final RegistryObject<Item> GLOWSHROOM_CHEST_BOAT = ITEMS.register("glowshroom_chest_boat", () -> new MushroomBoatItem(MushroomWoodType.GLOWSHROOM, true));
    public static final RegistryObject<Item> PARROT_WAXCAP_BOAT = ITEMS.register("poisonous_mushroom_boat", () -> new MushroomBoatItem(MushroomWoodType.PARROT_WAXCAP, false));
    public static final RegistryObject<Item> PARROT_WAXCAP_CHEST_BOAT = ITEMS.register("poisonous_mushroom_chest_boat", () -> new MushroomBoatItem(MushroomWoodType.PARROT_WAXCAP, true));
    public static final RegistryObject<Item> HONEY_WAXCAP_BOAT = ITEMS.register("honey_fungus_boat", () -> new MushroomBoatItem(MushroomWoodType.HONEY_WAXCAP, false));
    public static final RegistryObject<Item> HONEY_WAXCAP_CHEST_BOAT = ITEMS.register("honey_fungus_chest_boat", () -> new MushroomBoatItem(MushroomWoodType.HONEY_WAXCAP, true));

    public static final RegistryObject<Item> MUSHROOM_SIGN = ITEMS.register("mushroom_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), ModBlocks.MUSHROOM_STANDING_SIGN.get(), ModBlocks.MUSHROOM_WALL_SIGN.get()));
    public static final RegistryObject<Item> MUSHROOM_HANGING_SIGN = ITEMS.register("mushroom_hanging_sign", () -> new HangingSignItem(ModBlocks.MUSHROOM_HANGING_SIGN.get(), ModBlocks.MUSHROOM_WALL_HANGING_SIGN.get(), (new Item.Properties()).stacksTo(16)));
    public static final RegistryObject<Item> GLOWSHROOM_SIGN = ITEMS.register("glowshroom_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), ModBlocks.GLOWSHROOM_STANDING_SIGN.get(), ModBlocks.GLOWSHROOM_WALL_SIGN.get()));
    public static final RegistryObject<Item> GLOWSHROOM_HANGING_SIGN = ITEMS.register("glowshroom_hanging_sign", () -> new HangingSignItem(ModBlocks.GLOWSHROOM_HANGING_SIGN.get(), ModBlocks.GLOWSHROOM_WALL_HANGING_SIGN.get(), (new Item.Properties()).stacksTo(16)));
    public static final RegistryObject<Item> PARROT_WAXCAP_SIGN = ITEMS.register("poisonous_mushroom_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), ModBlocks.PARROT_WAXCAP_STANDING_SIGN.get(), ModBlocks.PARROT_WAXCAP_WALL_SIGN.get()));
    public static final RegistryObject<Item> PARROT_WAXCAP_HANGING_SIGN = ITEMS.register("poisonous_mushroom_hanging_sign", () -> new HangingSignItem(ModBlocks.PARROT_WAXCAP_HANGING_SIGN.get(), ModBlocks.PARROT_WAXCAP_WALL_HANGING_SIGN.get(), (new Item.Properties()).stacksTo(16)));
    public static final RegistryObject<Item> HONEY_WAXCAP_SIGN = ITEMS.register("honey_fungus_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), ModBlocks.HONEY_WAXCAP_STANDING_SIGN.get(), ModBlocks.HONEY_WAXCAP_WALL_SIGN.get()));
    public static final RegistryObject<Item> HONEY_WAXCAP_HANGING_SIGN = ITEMS.register("honey_fungus_hanging_sign", () -> new HangingSignItem(ModBlocks.HONEY_WAXCAP_HANGING_SIGN.get(), ModBlocks.HONEY_WAXCAP_WALL_HANGING_SIGN.get(), (new Item.Properties()).stacksTo(16)));

    public static final RegistryObject<Item> GRILLED_MUSHROOM = ITEMS.register("grilled_mushroom", () -> new Item((new Item.Properties()).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).build())));
    public static final RegistryObject<Item> MUSHROOM_BREAD = ITEMS.register("mushroom_bread", () -> new Item((new Item.Properties()).food((new FoodProperties.Builder()).nutrition(5).saturationMod(0.3F).build())));

    public static final RegistryObject<Item> MYCYCLOPEDIA = ITEMS.register("mycyclopedia", MycyclopediaItem::new);
    public static final RegistryObject<Item> MUSHROOM_SPORES = ITEMS.register("mushroom_spores", () -> new MushroomSporesItem(new Item.Properties()));
    public static final RegistryObject<Item> GLOWSTONE_CRUMBS = ITEMS.register("glowstone_crumbs", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SLIME_BLOB = ITEMS.register("slime_blob", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HONEY_BLOB = ITEMS.register("honey_blob", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HONEYCOMB_SHRED = ITEMS.register("honeycomb_shred", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIBRE = ITEMS.register("fibre", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MUSHROOM_SHEEP_SPAWN_EGG = ITEMS.register("mushroom_sheep_spawn_egg", () -> new ForgeSpawnEggItem(() -> ModEntityTypes.MUSHROOM_SHEEP.get(), 10489616, 10051392, new Item.Properties()));

    public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
        for (RegistryObject<Item> item : ITEMS.getEntries()) {
            if (item.get() instanceof MushroomBoatItem && event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                event.accept(item);
            }
            if (item.get() instanceof SignItem && event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
                event.accept(item);
            }
            if (item.get() instanceof ForgeSpawnEggItem && event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
                event.accept(item);
            }
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(GRILLED_MUSHROOM);
            event.accept(MUSHROOM_BREAD);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(MYCYCLOPEDIA);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(MUSHROOM_SPORES);
            event.accept(GLOWSTONE_CRUMBS);
            event.accept(SLIME_BLOB);
            event.accept(HONEY_BLOB);
            event.accept(HONEYCOMB_SHRED);
            event.accept(FIBRE);
        }
    }

}
