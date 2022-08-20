package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.item.MushroomBoatItem;
import cech12.extendedmushrooms.item.MushroomSporesItem;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.SignItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExtendedMushrooms.MOD_ID);

    public static final RegistryObject<Item> MUSHROOM_BOAT = ITEMS.register("mushroom_boat", () -> new MushroomBoatItem(MushroomWoodType.MUSHROOM, false));
    public static final RegistryObject<Item> MUSHROOM_CHEST_BOAT = ITEMS.register("mushroom_chest_boat", () -> new MushroomBoatItem(MushroomWoodType.MUSHROOM, true));
    public static final RegistryObject<Item> GLOWSHROOM_BOAT = ITEMS.register("glowshroom_boat", () -> new MushroomBoatItem(MushroomWoodType.GLOWSHROOM, false));
    public static final RegistryObject<Item> GLOWSHROOM_CHEST_BOAT = ITEMS.register("glowshroom_chest_boat", () -> new MushroomBoatItem(MushroomWoodType.GLOWSHROOM, true));
    public static final RegistryObject<Item> POISONOUS_MUSHROOM_BOAT = ITEMS.register("poisonous_mushroom_boat", () -> new MushroomBoatItem(MushroomWoodType.POISONOUS_MUSHROOM, false));
    public static final RegistryObject<Item> POISONOUS_MUSHROOM_CHEST_BOAT = ITEMS.register("poisonous_mushroom_chest_boat", () -> new MushroomBoatItem(MushroomWoodType.POISONOUS_MUSHROOM, true));
    public static final RegistryObject<Item> HONEY_FUNGUS_BOAT = ITEMS.register("honey_fungus_boat", () -> new MushroomBoatItem(MushroomWoodType.HONEY_FUNGUS, false));
    public static final RegistryObject<Item> HONEY_FUNGUS_CHEST_BOAT = ITEMS.register("honey_fungus_chest_boat", () -> new MushroomBoatItem(MushroomWoodType.HONEY_FUNGUS, true));

    public static final RegistryObject<Item> MUSHROOM_SIGN = ITEMS.register("mushroom_sign", () -> new SignItem((new Item.Properties()).stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), ModBlocks.MUSHROOM_STANDING_SIGN.get(), ModBlocks.MUSHROOM_WALL_SIGN.get()));
    public static final RegistryObject<Item> GLOWSHROOM_SIGN = ITEMS.register("glowshroom_sign", () -> new SignItem((new Item.Properties()).stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), ModBlocks.GLOWSHROOM_STANDING_SIGN.get(), ModBlocks.GLOWSHROOM_WALL_SIGN.get()));
    public static final RegistryObject<Item> POISONOUS_MUSHROOM_SIGN = ITEMS.register("poisonous_mushroom_sign", () -> new SignItem((new Item.Properties()).stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), ModBlocks.POISONOUS_MUSHROOM_STANDING_SIGN.get(), ModBlocks.POISONOUS_MUSHROOM_WALL_SIGN.get()));
    public static final RegistryObject<Item> HONEY_FUNGUS_SIGN = ITEMS.register("honey_fungus_sign", () -> new SignItem((new Item.Properties()).stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), ModBlocks.HONEY_FUNGUS_STANDING_SIGN.get(), ModBlocks.HONEY_FUNGUS_WALL_SIGN.get()));

    public static final RegistryObject<Item> GRILLED_MUSHROOM = ITEMS.register("grilled_mushroom", () -> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_FOOD).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).build())));
    public static final RegistryObject<Item> MUSHROOM_BREAD = ITEMS.register("mushroom_bread", () -> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_FOOD).food((new FoodProperties.Builder()).nutrition(5).saturationMod(0.3F).build())));

    public static final RegistryObject<Item> MUSHROOM_SPORES = ITEMS.register("mushroom_spores", () -> new MushroomSporesItem((new Item.Properties()).tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> GLOWSTONE_CRUMBS = ITEMS.register("glowstone_crumbs", () -> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> SLIME_BLOB = ITEMS.register("slime_blob", () -> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> HONEY_BLOB = ITEMS.register("honey_blob", () -> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> HONEYCOMB_SHRED = ITEMS.register("honeycomb_shred", () -> new Item((new Item.Properties()).tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> MUSHROOM_SHEEP_SPAWN_EGG = ITEMS.register("mushroom_sheep_spawn_egg", () -> new ForgeSpawnEggItem(() -> ModEntityTypes.MUSHROOM_SHEEP.get(), 10489616, 10051392, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));

}
