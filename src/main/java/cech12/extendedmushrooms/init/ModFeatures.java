package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.world.level.levelgen.feature.BigGlowshroomFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.BigHoneyFungusFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.BigPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.BigSlimeFungusFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.MegaBrownMushroomFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.MegaGlowshroomFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.MegaPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.MegaRedMushroomFeature;
import cech12.extendedmushrooms.world.level.levelgen.feature.configurations.ExtendedMushroomFeatureConfiguration;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ExtendedMushrooms.MOD_ID);
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> MEGA_BROWN_MUSHROOM = FEATURES.register("mega_brown_mushroom", () -> new MegaBrownMushroomFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> MEGA_RED_MUSHROOM = FEATURES.register("mega_red_mushroom", () -> new MegaRedMushroomFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> BIG_GLOWSHROOM = FEATURES.register("big_glowshroom", () -> new BigGlowshroomFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> MEGA_GLOWSHROOM = FEATURES.register("mega_glowshroom", () -> new MegaGlowshroomFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> BIG_POISONOUS_MUSHROOM = FEATURES.register("big_poisonous_mushroom", () -> new BigPoisonousMushroomFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> MEGA_POISONOUS_MUSHROOM = FEATURES.register("mega_poisonous_mushroom", () -> new MegaPoisonousMushroomFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> BIG_SLIME_FUNGUS = FEATURES.register("big_slime_fungus", () -> new BigSlimeFungusFeature(ExtendedMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ExtendedMushroomFeatureConfiguration>> BIG_HONEY_FUNGUS = FEATURES.register("big_honey_fungus", () -> new BigHoneyFungusFeature(ExtendedMushroomFeatureConfiguration.CODEC));

    //TODO
    //public static final ResourceKey<ConfiguredFeature<?, ?>> INFESTED_FLOWER_CONFIGURED = configuredKey("infested_flower");
    //public static final ResourceKey<ConfiguredFeature<?, ?>> INFESTED_GRASS_CONFIGURED = configuredKey("infested_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_BROWN_MUSHROOM_CONFIGURED = configuredKey("minecraft", "huge_brown_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_BROWN_MUSHROOM_CONFIGURED = configuredKey("mega_brown_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_RED_MUSHROOM_CONFIGURED = configuredKey("minecraft", "huge_red_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_RED_MUSHROOM_CONFIGURED = configuredKey("mega_red_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_GLOWSHROOM_CONFIGURED = configuredKey("big_glowshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_GLOWSHROOM_CONFIGURED = configuredKey("mega_glowshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_POISONOUS_MUSHROOM_CONFIGURED = configuredKey("big_poisonous_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_POISONOUS_MUSHROOM_CONFIGURED = configuredKey("mega_poisonous_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_SLIME_FUNGUS_CONFIGURED = configuredKey("big_slime_fungus");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_HONEY_FUNGUS_CONFIGURED = configuredKey("big_honey_fungus");

    /* TODO
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registries.PLACED_FEATURE, ExtendedMushrooms.MOD_ID); //TODO registring does not work for now
    public static final RegistryObject<PlacedFeature> INFESTED_FLOWER_PLACED = PLACED_FEATURES.register("patch_infested_flower", () -> new PlacedFeature(Holder.direct(INFESTED_FLOWER_CONFIGURED.get()), List.copyOf(VegetationPlacements.worldSurfaceSquaredWithCount(4))));
    public static final RegistryObject<PlacedFeature> INFESTED_GRASS_PLACED = PLACED_FEATURES.register("patch_infested_grass", () -> new PlacedFeature(Holder.direct(INFESTED_GRASS_CONFIGURED.get()), List.copyOf(VegetationPlacements.worldSurfaceSquaredWithCount(2))));
    public static final Map<String, RegistryObject<PlacedFeature>> MUSHROOM_PLACED_FEATURES = new HashMap<>();
    public static final Map<String, RegistryObject<PlacedFeature>> BIG_MUSHROOM_PLACED_FEATURES = new HashMap<>();
    public static final Map<String, RegistryObject<PlacedFeature>> MEGA_MUSHROOM_PLACED_FEATURES = new HashMap<>();
    */

    private static final List<Mushroom> mushrooms = new LinkedList<>();
    private static final List<BigMushroom> bigMushrooms = new LinkedList<>();
    private static final List<BigMushroom> megaMushrooms = new LinkedList<>();

    static {
        mushrooms.add(new Mushroom("glowshroom", ModBlocks.GLOWSHROOM, 0.4F));
        mushrooms.add(new Mushroom("poisonous_mushroom", ModBlocks.POISONOUS_MUSHROOM, 0.5F));
        /* TODO
        bigMushrooms.add(new BigMushroom("big_glowshroom", BIG_GLOWSHROOM_CONFIGURED, 0.125F));
        bigMushrooms.add(new BigMushroom("big_poisonous_mushroom", BIG_POISONOUS_MUSHROOM_CONFIGURED, 0.1F));
        megaMushrooms.add(new BigMushroom("mega_red_mushroom", MEGA_RED_MUSHROOM_CONFIGURED, 0.2F));
        megaMushrooms.add(new BigMushroom("mega_brown_mushroom", MEGA_BROWN_MUSHROOM_CONFIGURED, 0.2F));
        megaMushrooms.add(new BigMushroom("mega_glowshroom", MEGA_GLOWSHROOM_CONFIGURED, 0.02F));
        megaMushrooms.add(new BigMushroom("mega_poisonous_mushroom", MEGA_POISONOUS_MUSHROOM_CONFIGURED, 0.02F));
        //register small mushroom features
        for (Mushroom mushroom : mushrooms) {
            Supplier<ConfiguredFeature<?, ?>> patchFeature = () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(mushroom.block.get()))));
            MUSHROOM_PLACED_FEATURES.put(mushroom.name + "_normal", PLACED_FEATURES.register(mushroom.name + "_normal", () -> new PlacedFeature(Holder.direct(patchFeature.get()), List.copyOf(getMushroomPlacement((int) (512 * mushroom.spawnFactor))))));
            MUSHROOM_PLACED_FEATURES.put(mushroom.name + "_taiga", PLACED_FEATURES.register(mushroom.name + "_taiga", () -> new PlacedFeature(Holder.direct(patchFeature.get()), List.copyOf(getMushroomPlacement((int) (256 * mushroom.spawnFactor))))));
            MUSHROOM_PLACED_FEATURES.put(mushroom.name + "_mushroom_island", PLACED_FEATURES.register(mushroom.name + "_mushroom_island", () -> new PlacedFeature(Holder.direct(patchFeature.get()), List.copyOf(getMushroomPlacement((int) (256 * mushroom.spawnFactor))))));
            MUSHROOM_PLACED_FEATURES.put(mushroom.name + "_swamp", PLACED_FEATURES.register(mushroom.name + "_swamp", () -> new PlacedFeature(Holder.direct(patchFeature.get()), List.copyOf(getMushroomPlacement((int) (64 * mushroom.spawnFactor))))));
            MUSHROOM_PLACED_FEATURES.put(mushroom.name + "_nether", PLACED_FEATURES.register(mushroom.name + "_nether", () -> new PlacedFeature(Holder.direct(patchFeature.get()), List.of(RarityFilter.onAverageOnceEvery(Math.max(1, (int) (2.0 * mushroom.spawnFactor))), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()))));
        }
        //register feature for big mushrooms random patch
        for (BigMushroom bigMushroom : bigMushrooms) {
            BIG_MUSHROOM_PLACED_FEATURES.put("mushroom_island_" + bigMushroom.name, PLACED_FEATURES.register("mushroom_island_" + bigMushroom.name, () -> new PlacedFeature(Holder.direct(bigMushroom.feature.get()), List.copyOf(VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, bigMushroom.spawnChance, 1))))));
        }
        //register feature for mega mushrooms random patch
        for (BigMushroom megaMushroom : megaMushrooms) {
            MEGA_MUSHROOM_PLACED_FEATURES.put("mushroom_island_" + megaMushroom.name, PLACED_FEATURES.register("mushroom_island_" + megaMushroom.name, () -> new PlacedFeature(Holder.direct(megaMushroom.feature.get()), List.copyOf(VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, megaMushroom.spawnChance, 1))))));
            //register(megaMushroom.name + "_field", megaMushroom.config.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, megaMushroom.spawnChance, 1))));
        }
         */
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredKey(String name) {
        return configuredKey(ExtendedMushrooms.MOD_ID, name);
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredKey(String mod, String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(mod, name));
    }

    private static List<PlacementModifier> getMushroomPlacement(int chance) {
        ImmutableList.Builder<PlacementModifier> builder = ImmutableList.builder();

        if (chance != 0) {
            builder.add(RarityFilter.onAverageOnceEvery(chance));
        }

        builder.add(InSquarePlacement.spread());
        builder.add(PlacementUtils.HEIGHTMAP);
        builder.add(BiomeFilter.biome());
        return builder.build();
    }

    private static class Mushroom {
        String name;
        RegistryObject<Block> block;
        double spawnFactor;

        /**
         * @param name name of this mushroom
         * @param mushroomBlock block of this mushrooms
         * @param spawnFactor spawn factor relative to the spawn of small brown mushrooms
         */
        private Mushroom(String name, RegistryObject<Block> mushroomBlock, double spawnFactor) {
            this.name = name;
            this.block = mushroomBlock;
            this.spawnFactor = spawnFactor;
        }
    }

    private static class BigMushroom {
        String name;
        RegistryObject<ConfiguredFeature<?, ?>> feature;
        float spawnChance;

        private BigMushroom(String name, RegistryObject<ConfiguredFeature<?, ?>> feature, float spawnChance) {
            this.name = name;
            this.feature = feature;
            this.spawnChance = spawnChance;
        }
    }

}
