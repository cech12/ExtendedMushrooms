package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.mushrooms.BrownMushroom;
import cech12.extendedmushrooms.block.mushrooms.Glowshroom;
import cech12.extendedmushrooms.block.mushrooms.HoneyFungus;
import cech12.extendedmushrooms.block.mushrooms.PoisonousMushroom;
import cech12.extendedmushrooms.block.mushrooms.RedMushroom;
import cech12.extendedmushrooms.block.mushrooms.SlimeFungus;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.world.gen.feature.BigGlowshroomFeature;
import cech12.extendedmushrooms.world.gen.feature.BigHoneyFungusFeature;
import cech12.extendedmushrooms.world.gen.feature.BigMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.BigPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.BigSlimeFungusFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaBrownMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaGlowshroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaRedMushroomFeature;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures {

    public static final class NotConfigured {
        public static BigMushroomFeature MEGA_BROWN_MUSHROOM = new MegaBrownMushroomFeature(HugeMushroomFeatureConfiguration.CODEC);
        public static BigMushroomFeature MEGA_RED_MUSHROOM= new MegaRedMushroomFeature(HugeMushroomFeatureConfiguration.CODEC);

        public static BigMushroomFeature BIG_GLOWSHROOM = new BigGlowshroomFeature(HugeMushroomFeatureConfiguration.CODEC);
        public static BigMushroomFeature MEGA_GLOWSHROOM = new MegaGlowshroomFeature(HugeMushroomFeatureConfiguration.CODEC);

        public static BigMushroomFeature BIG_POISONOUS_MUSHROOM = new BigPoisonousMushroomFeature(HugeMushroomFeatureConfiguration.CODEC);
        public static BigMushroomFeature MEGA_POISONOUS_MUSHROOM = new MegaPoisonousMushroomFeature(HugeMushroomFeatureConfiguration.CODEC);

        public static BigMushroomFeature BIG_SLIME_FUNGUS = new BigSlimeFungusFeature(HugeMushroomFeatureConfiguration.CODEC);

        public static BigMushroomFeature BIG_HONEY_FUNGUS = new BigHoneyFungusFeature(HugeMushroomFeatureConfiguration.CODEC);
    }

    public static final class Configured {
        public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> INFESTED_FLOWER = FeatureUtils.register("infested_flower", Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.INFESTED_FLOWER.get())))));
        public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> INFESTED_GRASS = FeatureUtils.register("infested_flower", Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.INFESTED_GRASS.get())))));

        public static Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_BROWN_MUSHROOM = register("mega_brown_mushroom", NotConfigured.MEGA_BROWN_MUSHROOM, BrownMushroom.getConfig());
        public static Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_RED_MUSHROOM= register("mega_red_mushroom", NotConfigured.MEGA_RED_MUSHROOM, RedMushroom.getConfig());

        public static Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_GLOWSHROOM = register("big_glowshroom", NotConfigured.BIG_GLOWSHROOM, Glowshroom.getConfig());
        public static Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_GLOWSHROOM = register("mega_glowshroom", NotConfigured.MEGA_GLOWSHROOM, Glowshroom.getConfig());

        public static Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_POISONOUS_MUSHROOM = register("big_poisonous_mushroom", NotConfigured.BIG_POISONOUS_MUSHROOM, PoisonousMushroom.getConfig());
        public static Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_POISONOUS_MUSHROOM = register("mega_poisonous_mushroom", NotConfigured.MEGA_POISONOUS_MUSHROOM, PoisonousMushroom.getConfig());

        public static Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_SLIME_FUNGUS = register("big_slime_fungus", NotConfigured.BIG_SLIME_FUNGUS, SlimeFungus.getConfig());

        public static Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_HONEY_FUNGUS = register("big_honey_fungus", NotConfigured.BIG_HONEY_FUNGUS, HoneyFungus.getConfig());
    }

    public static final class Placed {
        public static Holder<PlacedFeature> INFESTED_FLOWER = PlacementUtils.register("patch_infested_flower", Configured.INFESTED_FLOWER, VegetationPlacements.worldSurfaceSquaredWithCount(4));
        public static Holder<PlacedFeature> INFESTED_GRASS = PlacementUtils.register("patch_infested_grass", Configured.INFESTED_GRASS, VegetationPlacements.worldSurfaceSquaredWithCount(2));

        public static Map<String, Holder<PlacedFeature>> MUSHROOM_FEATURES = new HashMap<>();
        public static Map<String, Holder<PlacedFeature>> BIG_MUSHROOM_FEATURES = new HashMap<>();
        public static Map<String, Holder<PlacedFeature>> MEGA_MUSHROOM_FEATURES = new HashMap<>();
    }

    private static List<Mushroom> mushrooms = null;
    private static List<BigMushroom> bigMushrooms = null;
    private static List<BigMushroom> megaMushrooms = null;

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        //register world generation features
        if (mushrooms == null || bigMushrooms == null || megaMushrooms == null) {
            mushrooms = new LinkedList<>();
            bigMushrooms = new LinkedList<>();
            megaMushrooms = new LinkedList<>();

            megaMushrooms.add(new BigMushroom("mega_red_mushroom", Configured.MEGA_RED_MUSHROOM, 0.2F, Config.VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED));
            megaMushrooms.add(new BigMushroom("mega_brown_mushroom", Configured.MEGA_BROWN_MUSHROOM, 0.2F, Config.VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED));
            mushrooms.add(new Mushroom("glowshroom", ModBlocks.GLOWSHROOM.get(), 0.4F, 32, Config.GLOWSHROOM_GENERATION_ENABLED));
            bigMushrooms.add(new BigMushroom("big_glowshroom", Configured.BIG_GLOWSHROOM, 0.15F, Config.BIG_GLOWSHROOM_GENERATION_ENABLED));
            megaMushrooms.add(new BigMushroom("mega_glowshroom", Configured.MEGA_GLOWSHROOM, 0.02F, Config.MEGA_GLOWSHROOM_GENERATION_ENABLED));
            mushrooms.add(new Mushroom("poisonous_mushroom", ModBlocks.POISONOUS_MUSHROOM.get(), 0.5F, 32,  Config.POISONOUS_MUSHROOM_GENERATION_ENABLED));
            bigMushrooms.add(new BigMushroom("big_poisonous_mushroom", Configured.BIG_POISONOUS_MUSHROOM, 0.1F, Config.BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED));
            megaMushrooms.add(new BigMushroom("mega_poisonous_mushroom", Configured.MEGA_POISONOUS_MUSHROOM, 0.02F, Config.MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED));

            //register small mushroom features
            for (Mushroom mushroom : mushrooms) {
                Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> patchFeature = FeatureUtils.register("patch_" + mushroom.name, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(mushroom.block))));
                Placed.MUSHROOM_FEATURES.put(mushroom.name + "_normal", PlacementUtils.register(mushroom.name + "_normal", patchFeature, getMushroomPlacement((int) (512 * mushroom.spawnFactor), null)));
                Placed.MUSHROOM_FEATURES.put(mushroom.name + "_taiga", PlacementUtils.register(mushroom.name + "_taiga", patchFeature, getMushroomPlacement((int) (256 * mushroom.spawnFactor), null)));
                Placed.MUSHROOM_FEATURES.put(mushroom.name + "_swamp", PlacementUtils.register(mushroom.name + "_swamp", patchFeature, getMushroomPlacement((int) (64 * mushroom.spawnFactor), null)));
                Placed.MUSHROOM_FEATURES.put(mushroom.name + "_nether", PlacementUtils.register(mushroom.name + "_nether", patchFeature, RarityFilter.onAverageOnceEvery(Math.max(1, (int) (2.0 * mushroom.spawnFactor))), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
            }

            //register feature for big mushrooms random patch
            for (BigMushroom bigMushroom : bigMushrooms) {
                Placed.BIG_MUSHROOM_FEATURES.put("mushroom_island_" + bigMushroom.name, PlacementUtils.register("mushroom_island_" + bigMushroom.name, bigMushroom.config, VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery((int) (100 * bigMushroom.spawnChance)))));
            }

            //register feature for mega mushrooms random patch
            for (BigMushroom megaMushroom : megaMushrooms) {
                Placed.MEGA_MUSHROOM_FEATURES.put("mushroom_island_" + megaMushroom.name, PlacementUtils.register("mushroom_island_" + megaMushroom.name, megaMushroom.config, VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery((int) (10 * megaMushroom.spawnChance)))));
                //register(megaMushroom.name + "_field", megaMushroom.config.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, megaMushroom.spawnChance, 1))));
            }
        }

    }

    private static <FC extends FeatureConfiguration> Holder<ConfiguredFeature<FC, ?>> register(String key, Feature<FC> feature, FC featureConfiguration) {
        feature.setRegistryName(new ResourceLocation(ExtendedMushrooms.MOD_ID, key));
        ForgeRegistries.FEATURES.register(feature);
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, ExtendedMushrooms.MOD_ID + ":" + key, new ConfiguredFeature<>(feature, featureConfiguration));
    }

    public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.BiomeCategory.MUSHROOM)) {
            BiomeGenerationSettingsBuilder generation = event.getGeneration();
            //add infested grass to mushroom biomes
            if (Config.INFESTED_GRASS_ENABLED.get()) {
                generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.INFESTED_GRASS);
            }
            //add infested flower to mushroom biomes
            if (Config.INFESTED_FLOWER_ENABLED.get()) {
                generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.INFESTED_FLOWER);
            }
        }
        //add mushrooms
        addMushrooms(event);
        addBigMushrooms(event);
        addMegaMushrooms(event);

    }

    private static boolean biomeHasNoMushrooms(BiomeLoadingEvent event) {
        Biome.BiomeCategory category = event.getCategory();
        Biome.TemperatureModifier temperatureModifier = event.getClimate().temperatureModifier;
        return category == Biome.BiomeCategory.THEEND
                || (category == Biome.BiomeCategory.OCEAN && temperatureModifier != Biome.TemperatureModifier.FROZEN);
    }

    private static List<PlacementModifier> getMushroomPlacement(int chance, @Nullable PlacementModifier placementModifier) {
        ImmutableList.Builder<PlacementModifier> builder = ImmutableList.builder();
        if (placementModifier != null) {
            builder.add(placementModifier);
        }

        if (chance != 0) {
            builder.add(RarityFilter.onAverageOnceEvery(chance));
        }

        builder.add(InSquarePlacement.spread());
        builder.add(PlacementUtils.HEIGHTMAP);
        builder.add(BiomeFilter.biome());
        return builder.build();
    }

    private static void addMushrooms(BiomeLoadingEvent event) {
        //skip biomes with no mushrooms
        if (biomeHasNoMushrooms(event)) {
            return;
        }
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        for (Mushroom mushroom : mushrooms) {
            //check if mushroom spawning is disabled
            if (!mushroom.enableConfig.get()) {
                continue;
            }
            //add mushrooms to all biomes
            generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.MUSHROOM_FEATURES.get(mushroom.name + "_normal"));
            //add more mushrooms to some specific biomes
            String suffix = switch (event.getCategory()) { //same as taiga
                case MUSHROOM, TAIGA -> "_taiga";
                //giant taiga? - same with an additional .count(3)
                case SWAMP -> "_swamp";
                case NETHER -> "_nether";
                default -> null;
            };
            if (suffix != null) {
                generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.MUSHROOM_FEATURES.get(mushroom.name + suffix));
            }
        }
    }

    private static void addBigMushrooms(BiomeLoadingEvent event) {
        Biome.BiomeCategory category = event.getCategory();
        if (category == Biome.BiomeCategory.MUSHROOM) {
            BiomeGenerationSettingsBuilder generation = event.getGeneration();
            for (BigMushroom bigMushroom : bigMushrooms) {
                if (bigMushroom.enableConfig.get()) {
                    generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.BIG_MUSHROOM_FEATURES.get("mushroom_island_" + bigMushroom.name));
                }
            }
        }
    }

    private static void addMegaMushrooms(BiomeLoadingEvent event) {
        Biome.BiomeCategory category = event.getCategory();
        if (category == Biome.BiomeCategory.MUSHROOM) {
            BiomeGenerationSettingsBuilder generation = event.getGeneration();
            for (BigMushroom megaMushroom : megaMushrooms) {
                if (megaMushroom.enableConfig.get()) {
                    generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.BIG_MUSHROOM_FEATURES.get("mushroom_island_" + megaMushroom.name));
                }
            }
        }
    }

    private static class Mushroom {
        String name;
        Block block;
        double spawnFactor;
        ForgeConfigSpec.BooleanValue enableConfig;

        /**
         * @param name name of this mushroom
         * @param mushroomBlock block of this mushrooms
         * @param spawnFactor spawn factor relative to the spawn of small brown mushrooms
         * @param spawnTryCount count of tries to spawn this mushroom near a spot (64 try count of brown mushrooms)
         * @param enableConfig enable config object
         */
        private Mushroom(String name, Block mushroomBlock, double spawnFactor, int spawnTryCount, ForgeConfigSpec.BooleanValue enableConfig) {
            this.name = name;
            this.block = mushroomBlock;
            this.spawnFactor = spawnFactor;
            this.enableConfig = enableConfig;
        }
    }

    private static class BigMushroom {
        String name;
        Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> config;
        float spawnChance;
        ForgeConfigSpec.BooleanValue enableConfig;

        private BigMushroom(String name, Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> config, float spawnChance, ForgeConfigSpec.BooleanValue enableConfig) {
            this.name = name;
            this.config = config;
            this.spawnChance = spawnChance;
            this.enableConfig = enableConfig;
        }
    }


}
