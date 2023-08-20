package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.world.level.levelgen.feature.BigGlowshroomFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.BigHoneyWaxcapFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.BigDeadlyFibrecapFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.BigParrotWaxcapFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.MegaBrownMushroomFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.MegaGlowshroomFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.MegaDeadlyFibrecapFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.MegaRedMushroomFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.configurations.ExtendedMushroomFeatureConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ExtendedMushrooms.MOD_ID);
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> MEGA_BROWN_MUSHROOM = FEATURES.register("mega_brown_mushroom", () -> new MegaBrownMushroomFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> MEGA_RED_MUSHROOM = FEATURES.register("mega_red_mushroom", () -> new MegaRedMushroomFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> BIG_GLOWSHROOM = FEATURES.register("big_glowshroom", () -> new BigGlowshroomFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> MEGA_GLOWSHROOM = FEATURES.register("mega_glowshroom", () -> new MegaGlowshroomFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> BIG_DEADLY_FIBRECAP = FEATURES.register("big_deadly_fibrecap", () -> new BigDeadlyFibrecapFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> MEGA_DEADLY_FIBRECAP = FEATURES.register("mega_deadly_fibrecap", () -> new MegaDeadlyFibrecapFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> BIG_PARROT_WAXCAP = FEATURES.register("big_parrot_waxcap", () -> new BigParrotWaxcapFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> BIG_HONEY_WAXCAP = FEATURES.register("big_honey_waxcap", () -> new BigHoneyWaxcapFeature(ExtendedMushroomFeatureConfiguration.CODEC));

    public static final ResourceKey<ConfiguredFeature<?, ?>> INFESTED_FLOWER_CONFIGURED = configuredKey("infested_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> INFESTED_GRASS_CONFIGURED = configuredKey("infested_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_BROWN_MUSHROOM_CONFIGURED = configuredKey("minecraft", "huge_brown_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_BROWN_MUSHROOM_CONFIGURED = configuredKey("mega_brown_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_RED_MUSHROOM_CONFIGURED = configuredKey("minecraft", "huge_red_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_RED_MUSHROOM_CONFIGURED = configuredKey("mega_red_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GLOWSHROOM_CONFIGURED = configuredKey("patch_glowshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_GLOWSHROOM_CONFIGURED = configuredKey("big_glowshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_GLOWSHROOM_CONFIGURED = configuredKey("mega_glowshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DEADLY_FIBRECAP_CONFIGURED = configuredKey("patch_deadly_fibrecap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_DEADLY_FIBRECAP_CONFIGURED = configuredKey("big_deadly_fibrecap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_DEADLY_FIBRECAP_CONFIGURED = configuredKey("mega_deadly_fibrecap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_PARROT_WAXCAP_CONFIGURED = configuredKey("big_parrot_waxcap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_HONEY_WAXCAP_CONFIGURED = configuredKey("big_honey_waxcap");

    public static final ResourceKey<PlacedFeature> INFESTED_FLOWER_PLACED = placedKey("patch_infested_flower");
    public static final ResourceKey<PlacedFeature> INFESTED_GRASS_PLACED = placedKey("patch_infested_grass");

    public static final Map<String, Map<String, ResourceKey<PlacedFeature>>> MUSHROOMS_PLACED = new HashMap<>();
    public static final Map<String, Map<String, ResourceKey<PlacedFeature>>> BIG_MUSHROOMS_PLACED = new HashMap<>();
    public static final Map<String, Map<String, ResourceKey<PlacedFeature>>> MEGA_MUSHROOMS_PLACED = new HashMap<>();

    static {
        List<String> mushroomExtensions = Arrays.asList("normal", "taiga", "mushroom_island", "swamp", "nether");
        for (String mushroom : Arrays.asList("glowshroom", "deadly_fibrecap")) {
            Map<String, ResourceKey<PlacedFeature>> extensionMap = new HashMap<>();
            mushroomExtensions.forEach(extension -> extensionMap.put(extension, placedKey(mushroom + "_" + extension)));
            MUSHROOMS_PLACED.put(mushroom, extensionMap);
        }
        for (String bigMushroom : Arrays.asList("big_glowshroom", "big_deadly_fibrecap")) {
            Map<String, ResourceKey<PlacedFeature>> extensionMap = new HashMap<>();
            extensionMap.put("mushroom_island", placedKey("mushroom_island_" + bigMushroom));
            BIG_MUSHROOMS_PLACED.put(bigMushroom, extensionMap);
        }
        for (String megaMushroom : Arrays.asList("mega_red_mushroom", "mega_brown_mushroom", "mega_glowshroom", "mega_deadly_fibrecap")) {
            Map<String, ResourceKey<PlacedFeature>> extensionMap = new HashMap<>();
            extensionMap.put("mushroom_island", placedKey("mushroom_island_" + megaMushroom));
            MEGA_MUSHROOMS_PLACED.put(megaMushroom, extensionMap);
        }
    }


    private static ResourceKey<ConfiguredFeature<?, ?>> configuredKey(String name) {
        return configuredKey(ExtendedMushrooms.MOD_ID, name);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredKey(String mod, String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(mod, name));
    }

    private static ResourceKey<PlacedFeature> placedKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ExtendedMushrooms.MOD_ID, name));
    }

}
