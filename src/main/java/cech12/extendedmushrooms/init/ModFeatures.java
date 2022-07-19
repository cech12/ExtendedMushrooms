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
import cech12.extendedmushrooms.world.gen.feature.BigPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.BigSlimeFungusFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaBrownMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaGlowshroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaRedMushroomFeature;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ExtendedMushrooms.MOD_ID);

    public static final class Configured {
        public static final RegistryObject<ConfiguredFeature<?, ?>> INFESTED_FLOWER = CONFIGURED_FEATURES.register("infested_flower", () -> new ConfiguredFeature<>(new RandomPatchFeature(RandomPatchConfiguration.CODEC), FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.INFESTED_FLOWER.get()))))));
        public static final RegistryObject<ConfiguredFeature<?, ?>> INFESTED_GRASS = CONFIGURED_FEATURES.register("infested_grass", () -> new ConfiguredFeature<>(new RandomPatchFeature(RandomPatchConfiguration.CODEC), FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.INFESTED_GRASS.get()))))));

        public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_BROWN_MUSHROOM = CONFIGURED_FEATURES.register("mega_brown_mushroom", () -> new ConfiguredFeature<>(new MegaBrownMushroomFeature(HugeMushroomFeatureConfiguration.CODEC), BrownMushroom.getConfig()));
        public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_RED_MUSHROOM = CONFIGURED_FEATURES.register("mega_red_mushroom", () -> new ConfiguredFeature<>(new MegaRedMushroomFeature(HugeMushroomFeatureConfiguration.CODEC), RedMushroom.getConfig()));

        public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_GLOWSHROOM = CONFIGURED_FEATURES.register("big_glowshroom", () -> new ConfiguredFeature<>(new BigGlowshroomFeature(HugeMushroomFeatureConfiguration.CODEC), Glowshroom.getConfig()));
        public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_GLOWSHROOM = CONFIGURED_FEATURES.register("mega_glowshroom", () -> new ConfiguredFeature<>(new MegaGlowshroomFeature(HugeMushroomFeatureConfiguration.CODEC), Glowshroom.getConfig()));

        public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_POISONOUS_MUSHROOM = CONFIGURED_FEATURES.register("big_poisonous_mushroom", () -> new ConfiguredFeature<>(new BigPoisonousMushroomFeature(HugeMushroomFeatureConfiguration.CODEC), PoisonousMushroom.getConfig()));
        public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_POISONOUS_MUSHROOM = CONFIGURED_FEATURES.register("mega_poisonous_mushroom", () -> new ConfiguredFeature<>(new MegaPoisonousMushroomFeature(HugeMushroomFeatureConfiguration.CODEC), PoisonousMushroom.getConfig()));

        public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_SLIME_FUNGUS = CONFIGURED_FEATURES.register("big_slime_fungus", () -> new ConfiguredFeature<>(new BigSlimeFungusFeature(HugeMushroomFeatureConfiguration.CODEC), SlimeFungus.getConfig()));

        public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_HONEY_FUNGUS = CONFIGURED_FEATURES.register("big_honey_fungus", () -> new ConfiguredFeature<>(new BigHoneyFungusFeature(HugeMushroomFeatureConfiguration.CODEC), HoneyFungus.getConfig()));

        public static final Map<String, RegistryObject<ConfiguredFeature<?, ?>>> MUSHROOM_PATCH_FEATURES = new HashMap<>();

        static {
            for (Mushroom mushroom : mushrooms) {
                Configured.MUSHROOM_PATCH_FEATURES.put(mushroom.name, CONFIGURED_FEATURES.register("patch_" + mushroom.name, () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(mushroom.block.get()))))));
            }
        }
    }

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ExtendedMushrooms.MOD_ID);

    public static final class Placed {
        public static final RegistryObject<PlacedFeature> INFESTED_FLOWER = PLACED_FEATURES.register("patch_infested_flower", () -> new PlacedFeature(Holder.hackyErase(Configured.INFESTED_FLOWER.getHolder().get()), List.copyOf(VegetationPlacements.worldSurfaceSquaredWithCount(4))));
        public static final RegistryObject<PlacedFeature> INFESTED_GRASS = PLACED_FEATURES.register("patch_infested_grass", () -> new PlacedFeature(Holder.hackyErase(Configured.INFESTED_GRASS.getHolder().get()), List.copyOf(VegetationPlacements.worldSurfaceSquaredWithCount(2))));

        public static final Map<String, RegistryObject<PlacedFeature>> MUSHROOM_FEATURES = new HashMap<>();
        public static final Map<String, RegistryObject<PlacedFeature>> BIG_MUSHROOM_FEATURES = new HashMap<>();
        public static final Map<String, RegistryObject<PlacedFeature>> MEGA_MUSHROOM_FEATURES = new HashMap<>();

        static {
            //register small mushroom features
            for (Mushroom mushroom : mushrooms) {
                RegistryObject<ConfiguredFeature<?, ?>> patchFeature = Configured.MUSHROOM_PATCH_FEATURES.get(mushroom.name);
                Placed.MUSHROOM_FEATURES.put(mushroom.name + "_normal", PLACED_FEATURES.register(mushroom.name + "_normal", () -> new PlacedFeature(Holder.hackyErase(patchFeature.getHolder().get()), List.copyOf(getMushroomPlacement((int) (512 * mushroom.spawnFactor), null)))));
                Placed.MUSHROOM_FEATURES.put(mushroom.name + "_taiga", PLACED_FEATURES.register(mushroom.name + "_taiga", () -> new PlacedFeature(Holder.hackyErase(patchFeature.getHolder().get()), List.copyOf(getMushroomPlacement((int) (256 * mushroom.spawnFactor), null)))));
                Placed.MUSHROOM_FEATURES.put(mushroom.name + "_swamp", PLACED_FEATURES.register(mushroom.name + "_swamp", () -> new PlacedFeature(Holder.hackyErase(patchFeature.getHolder().get()), List.copyOf(getMushroomPlacement((int) (64 * mushroom.spawnFactor), null)))));
                Placed.MUSHROOM_FEATURES.put(mushroom.name + "_nether", PLACED_FEATURES.register(mushroom.name + "_nether", () -> new PlacedFeature(Holder.hackyErase(patchFeature.getHolder().get()), List.of(RarityFilter.onAverageOnceEvery(Math.max(1, (int) (2.0 * mushroom.spawnFactor))), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()))));
            }
            //register feature for big mushrooms random patch
            for (BigMushroom bigMushroom : bigMushrooms) {
                Placed.BIG_MUSHROOM_FEATURES.put("mushroom_island_" + bigMushroom.name, PLACED_FEATURES.register("mushroom_island_" + bigMushroom.name, () -> new PlacedFeature(Holder.hackyErase(bigMushroom.feature.getHolder().get()), List.copyOf(VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery((int) (100 * bigMushroom.spawnChance)))))));
            }
            //register feature for mega mushrooms random patch
            for (BigMushroom megaMushroom : megaMushrooms) {
                Placed.MEGA_MUSHROOM_FEATURES.put("mushroom_island_" + megaMushroom.name, PLACED_FEATURES.register("mushroom_island_" + megaMushroom.name, () -> new PlacedFeature(Holder.hackyErase(megaMushroom.feature.getHolder().get()), List.copyOf(VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery((int) (10 * megaMushroom.spawnChance)))))));
                //register(megaMushroom.name + "_field", megaMushroom.config.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, megaMushroom.spawnChance, 1))));
            }
        }
    }

    private static final List<Mushroom> mushrooms = new LinkedList<>();
    private static final List<BigMushroom> bigMushrooms = new LinkedList<>();
    private static final List<BigMushroom> megaMushrooms = new LinkedList<>();

    static {
        mushrooms.add(new Mushroom("glowshroom", ModBlocks.GLOWSHROOM, 0.4F, 32, Config.GLOWSHROOM_GENERATION_ENABLED));
        mushrooms.add(new Mushroom("poisonous_mushroom", ModBlocks.POISONOUS_MUSHROOM, 0.5F, 32,  Config.POISONOUS_MUSHROOM_GENERATION_ENABLED));
        bigMushrooms.add(new BigMushroom("big_glowshroom", Configured.BIG_GLOWSHROOM, 0.15F, Config.BIG_GLOWSHROOM_GENERATION_ENABLED));
        bigMushrooms.add(new BigMushroom("big_poisonous_mushroom", Configured.BIG_POISONOUS_MUSHROOM, 0.1F, Config.BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED));
        megaMushrooms.add(new BigMushroom("mega_red_mushroom", Configured.MEGA_RED_MUSHROOM, 0.2F, Config.VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED));
        megaMushrooms.add(new BigMushroom("mega_brown_mushroom", Configured.MEGA_BROWN_MUSHROOM, 0.2F, Config.VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED));
        megaMushrooms.add(new BigMushroom("mega_glowshroom", Configured.MEGA_GLOWSHROOM, 0.02F, Config.MEGA_GLOWSHROOM_GENERATION_ENABLED));
        megaMushrooms.add(new BigMushroom("mega_poisonous_mushroom", Configured.MEGA_POISONOUS_MUSHROOM, 0.02F, Config.MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED));
    }

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        //register world generation features

    }

    public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.BiomeCategory.MUSHROOM)) {
            BiomeGenerationSettingsBuilder generation = event.getGeneration();
            //add infested grass to mushroom biomes
            if (Config.INFESTED_GRASS_ENABLED.get()) {
                generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.INFESTED_GRASS.getHolder().get());
            }
            //add infested flower to mushroom biomes
            if (Config.INFESTED_FLOWER_ENABLED.get()) {
                generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.INFESTED_FLOWER.getHolder().get());
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
            generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.MUSHROOM_FEATURES.get(mushroom.name + "_normal").getHolder().get());
            //add more mushrooms to some specific biomes
            String suffix = switch (event.getCategory()) {
                //mushroom same as taiga
                case MUSHROOM, TAIGA -> "_taiga";
                //giant taiga? - same with an additional .count(3)
                case SWAMP -> "_swamp";
                case NETHER -> "_nether";
                default -> null;
            };
            if (suffix != null) {
                generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.MUSHROOM_FEATURES.get(mushroom.name + suffix).getHolder().get());
            }
        }
    }

    private static void addBigMushrooms(BiomeLoadingEvent event) {
        Biome.BiomeCategory category = event.getCategory();
        if (category == Biome.BiomeCategory.MUSHROOM) {
            BiomeGenerationSettingsBuilder generation = event.getGeneration();
            for (BigMushroom bigMushroom : bigMushrooms) {
                if (bigMushroom.enableConfig.get()) {
                    generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.BIG_MUSHROOM_FEATURES.get("mushroom_island_" + bigMushroom.name).getHolder().get());
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
                    generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Placed.BIG_MUSHROOM_FEATURES.get("mushroom_island_" + megaMushroom.name).getHolder().get());
                }
            }
        }
    }

    private static class Mushroom {
        String name;
        RegistryObject<Block> block;
        double spawnFactor;
        ForgeConfigSpec.BooleanValue enableConfig;

        /**
         * @param name name of this mushroom
         * @param mushroomBlock block of this mushrooms
         * @param spawnFactor spawn factor relative to the spawn of small brown mushrooms
         * @param spawnTryCount count of tries to spawn this mushroom near a spot (64 try count of brown mushrooms)
         * @param enableConfig enable config object
         */
        private Mushroom(String name, RegistryObject<Block> mushroomBlock, double spawnFactor, int spawnTryCount, ForgeConfigSpec.BooleanValue enableConfig) {
            this.name = name;
            this.block = mushroomBlock;
            this.spawnFactor = spawnFactor;
            this.enableConfig = enableConfig;
        }
    }

    private static class BigMushroom {
        String name;
        RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> feature;
        float spawnChance;
        ForgeConfigSpec.BooleanValue enableConfig;

        private BigMushroom(String name, RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> feature, float spawnChance, ForgeConfigSpec.BooleanValue enableConfig) {
            this.name = name;
            this.feature = feature;
            this.spawnChance = spawnChance;
            this.enableConfig = enableConfig;
        }
    }


}
