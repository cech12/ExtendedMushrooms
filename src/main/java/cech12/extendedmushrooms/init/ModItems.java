package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.item.MushroomBoatItem;
import cech12.extendedmushrooms.item.MushroomSporesItem;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.item.MycyclopediaItem;
import net.minecraft.resources.ResourceLocation;
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

    public static final RegistryObject<Item> GRILLED_MUSHROOM = ITEMS.register("grilled_mushroom", () -> new Item((new Item.Properties()).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).build())));
    public static final RegistryObject<Item> MUSHROOM_BREAD = ITEMS.register("mushroom_bread", () -> new Item((new Item.Properties()).food((new FoodProperties.Builder()).nutrition(5).saturationMod(0.3F).build())));

    public static final RegistryObject<Item> MYCYCLOPEDIA = ITEMS.register("mycyclopedia", MycyclopediaItem::new);
    public static final RegistryObject<Item> MUSHROOM_SPORES = ITEMS.register("mushroom_spores", () -> new MushroomSporesItem(new Item.Properties()));
    public static final RegistryObject<Item> GLOWSTONE_CRUMBS = ITEMS.register("glowstone_crumbs", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SLIME_BLOB = ITEMS.register("slime_blob", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HONEY_BLOB = ITEMS.register("honey_blob", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HONEYCOMB_SHRED = ITEMS.register("honeycomb_shred", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIBRE = ITEMS.register("fibre", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AMETHYST_SPLINTER = ITEMS.register("amethyst_splinter", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MUSHROOM_SHEEP_SPAWN_EGG = ITEMS.register("mushroom_sheep_spawn_egg", () -> new ForgeSpawnEggItem(() -> ModEntityTypes.MUSHROOM_SHEEP.get(), 10489616, 10051392, new Item.Properties()));

    static {
        for (MushroomWoodType woodType : MushroomWoodType.values()) {
            registerWoodTypeItems(woodType.getName(), woodType);
        }
    }

    private static void registerWoodTypeItems(String name, MushroomWoodType mushroomType) {
        ITEMS.register(ItemType.BOAT.getName(name), () -> new MushroomBoatItem(mushroomType, false));
        ITEMS.register(ItemType.CHEST_BOAT.getName(name), () -> new MushroomBoatItem(mushroomType, true));
        ITEMS.register(ItemType.SIGN.getName(name), () -> new SignItem((new Item.Properties()).stacksTo(16), mushroomType.getStandingSignBlock(), mushroomType.getWallSignBlock()));
        ITEMS.register(ItemType.HANGING_SIGN.getName(name), () -> new HangingSignItem(mushroomType.getHangingSignBlock(), mushroomType.getWallHangingSignBlock(), (new Item.Properties()).stacksTo(16)));
    }

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
            event.accept(AMETHYST_SPLINTER);
        }
    }

    public static RegistryObject<Item> getMushroomItem(String mushroomName, ItemType blockType) {
        return RegistryObject.create(new ResourceLocation(ExtendedMushrooms.MOD_ID, blockType.getName(mushroomName)), ForgeRegistries.ITEMS);
    }

    public enum ItemType {

        BOAT("{0}_boat"),
        CHEST_BOAT("{0}_chest_boat"),
        SIGN("{0}_sign"),
        HANGING_SIGN("{0}_hanging_sign");

        private final String pattern;

        ItemType(String pattern) {
            this.pattern = pattern;
        }

        String getName(String mushroomName) {
            return this.pattern.replace("{0}", mushroomName);
        }

    }

}
