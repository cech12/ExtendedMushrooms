package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.block.mushrooms.BrownMushroom;
import cech12.extendedmushrooms.block.mushrooms.Glowshroom;
import cech12.extendedmushrooms.block.mushrooms.PoisonousMushroom;
import cech12.extendedmushrooms.block.mushrooms.RedMushroom;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.world.gen.feature.BigGlowshroomFeature;
import cech12.extendedmushrooms.world.gen.feature.BigMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.BigPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaBrownMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaGlowshroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaRedMushroomFeature;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures {

    public static final class NotConfigured {
        public static BigMushroomFeature MEGA_BROWN_MUSHROOM = new MegaBrownMushroomFeature(BigMushroomFeatureConfig.CODEC);
        public static BigMushroomFeature MEGA_RED_MUSHROOM= new MegaRedMushroomFeature(BigMushroomFeatureConfig.CODEC);

        public static BigMushroomFeature BIG_GLOWSHROOM = new BigGlowshroomFeature(BigMushroomFeatureConfig.CODEC);
        public static BigMushroomFeature MEGA_GLOWSHROOM = new MegaGlowshroomFeature(BigMushroomFeatureConfig.CODEC);

        public static BigMushroomFeature BIG_POISONOUS_MUSHROOM = new BigPoisonousMushroomFeature(BigMushroomFeatureConfig.CODEC);
        public static BigMushroomFeature MEGA_POISONOUS_MUSHROOM = new MegaPoisonousMushroomFeature(BigMushroomFeatureConfig.CODEC);
    }

    public static final class Configured {
        public static ConfiguredFeature<?, ?> INFESTED_FLOWER = Feature.FLOWER.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ExtendedMushroomsBlocks.INFESTED_FLOWER.getDefaultState()), new SimpleBlockPlacer())).tries(32).build()).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).count(4);
        public static ConfiguredFeature<?, ?> INFESTED_GRASS = Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ExtendedMushroomsBlocks.INFESTED_GRASS.getDefaultState()), new SimpleBlockPlacer())).tries(32).build()).withPlacement(Features.Placements.PATCH_PLACEMENT).count(2);

        public static ConfiguredFeature<BigMushroomFeatureConfig, ?> MEGA_BROWN_MUSHROOM = NotConfigured.MEGA_BROWN_MUSHROOM.withConfiguration(BrownMushroom.getConfig());
        public static ConfiguredFeature<BigMushroomFeatureConfig, ?> MEGA_RED_MUSHROOM= NotConfigured.MEGA_RED_MUSHROOM.withConfiguration(RedMushroom.getConfig());

        public static ConfiguredFeature<BigMushroomFeatureConfig, ?> BIG_GLOWSHROOM = NotConfigured.BIG_GLOWSHROOM.withConfiguration(Glowshroom.getConfig());
        public static ConfiguredFeature<BigMushroomFeatureConfig, ?> MEGA_GLOWSHROOM = NotConfigured.MEGA_GLOWSHROOM.withConfiguration(Glowshroom.getConfig());

        public static ConfiguredFeature<BigMushroomFeatureConfig, ?> BIG_POISONOUS_MUSHROOM = NotConfigured.BIG_POISONOUS_MUSHROOM.withConfiguration(PoisonousMushroom.getConfig());
        public static ConfiguredFeature<BigMushroomFeatureConfig, ?> MEGA_POISONOUS_MUSHROOM = NotConfigured.MEGA_POISONOUS_MUSHROOM.withConfiguration(PoisonousMushroom.getConfig());
    }

    private static List<Mushroom> mushrooms = null;
    private static List<BigMushroom> bigMushrooms = null;
    private static List<BigMushroom> megaMushrooms = null;

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        register("infested_grass", Configured.INFESTED_FLOWER);
        register("infested_flower", Configured.INFESTED_GRASS);

        register("mega_brown_mushroom", NotConfigured.MEGA_BROWN_MUSHROOM, Configured.MEGA_BROWN_MUSHROOM);
        register("mega_red_mushroom", NotConfigured.MEGA_RED_MUSHROOM, Configured.MEGA_RED_MUSHROOM);

        register("big_glowshroom", NotConfigured.BIG_GLOWSHROOM, Configured.BIG_GLOWSHROOM);
        register("mega_glowshroom", NotConfigured.MEGA_GLOWSHROOM, Configured.MEGA_GLOWSHROOM);

        register("big_poisonous_mushroom", NotConfigured.BIG_POISONOUS_MUSHROOM, Configured.BIG_POISONOUS_MUSHROOM);
        register("mega_poisonous_mushroom", NotConfigured.MEGA_POISONOUS_MUSHROOM, Configured.MEGA_POISONOUS_MUSHROOM);

        //register world generation features
        if (mushrooms == null || bigMushrooms == null || megaMushrooms == null) {
            mushrooms = new LinkedList<>();
            bigMushrooms = new LinkedList<>();
            megaMushrooms = new LinkedList<>();

            megaMushrooms.add(new BigMushroom("mega_red_mushroom", Configured.MEGA_RED_MUSHROOM, 0.2F, Config.VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED));
            megaMushrooms.add(new BigMushroom("mega_brown_mushroom", Configured.MEGA_BROWN_MUSHROOM, 0.2F, Config.VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED));
            mushrooms.add(new Mushroom("glowshroom", ExtendedMushroomsBlocks.GLOWSHROOM, 0.4F, 32, Config.GLOWSHROOM_GENERATION_ENABLED));
            bigMushrooms.add(new BigMushroom("big_glowshroom", Configured.BIG_GLOWSHROOM, 0.15F, Config.BIG_GLOWSHROOM_GENERATION_ENABLED));
            megaMushrooms.add(new BigMushroom("mega_glowshroom", Configured.MEGA_GLOWSHROOM, 0.02F, Config.MEGA_GLOWSHROOM_GENERATION_ENABLED));
            mushrooms.add(new Mushroom("poisonous_mushroom", ExtendedMushroomsBlocks.POISONOUS_MUSHROOM, 0.5F, 32,  Config.POISONOUS_MUSHROOM_GENERATION_ENABLED));
            bigMushrooms.add(new BigMushroom("big_poisonous_mushroom", Configured.BIG_POISONOUS_MUSHROOM, 0.1F, Config.BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED));
            megaMushrooms.add(new BigMushroom("mega_poisonous_mushroom", Configured.MEGA_POISONOUS_MUSHROOM, 0.02F, Config.MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED));

            //register small mushroom features
            for (Mushroom mushroom : mushrooms) {
                //calculate chance
                int chance = Math.max(1, (int) (4.0 / mushroom.spawnFactor));
                register(mushroom.name + "_normal", Feature.RANDOM_PATCH.withConfiguration(mushroom.config).withPlacement(Features.Placements.PATCH_PLACEMENT).chance(chance));
                register(mushroom.name + "_taiga", Feature.RANDOM_PATCH.withConfiguration(mushroom.config).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(chance));
                register(mushroom.name + "_swamp", Feature.RANDOM_PATCH.withConfiguration(mushroom.config).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(chance).count(8));
                register(mushroom.name + "_nether", Feature.RANDOM_PATCH.withConfiguration(mushroom.config).range(128).chance(Math.max(1, chance / 2)));
            }

            //register feature for big mushrooms random patch
            for (BigMushroom bigMushroom : bigMushrooms) {
                register(bigMushroom.name + "_field", bigMushroom.config.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, bigMushroom.spawnChance, 1))));
            }

            //register feature for mega mushrooms random patch
            for (BigMushroom megaMushroom : megaMushrooms) {
                register(megaMushroom.name + "_field", megaMushroom.config.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, megaMushroom.spawnChance, 1))));
            }
        }

    }

    private static <FC extends IFeatureConfig> void register(String key, Feature<?> feature, ConfiguredFeature<FC, ?> configuredFeature) {
        feature.setRegistryName(new ResourceLocation(ExtendedMushrooms.MOD_ID, key));
        ForgeRegistries.FEATURES.register(feature);
        register(key, configuredFeature);
    }

    private static <FC extends IFeatureConfig> void register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(ExtendedMushrooms.MOD_ID, key), configuredFeature);
    }

    public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.Category.MUSHROOM)) {
            BiomeGenerationSettingsBuilder generation = event.getGeneration();
            //add infested grass to mushroom biomes
            if (Config.INFESTED_GRASS_ENABLED.get()) {
                generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Configured.INFESTED_GRASS);
            }
            //add infested flower to mushroom biomes
            if (Config.INFESTED_FLOWER_ENABLED.get()) {
                generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Configured.INFESTED_FLOWER);
            }
        }
        //add mushrooms
        addMushrooms(event);
        addBigMushrooms(event);
        addMegaMushrooms(event);

    }

    private static boolean biomeHasNoMushrooms(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        Biome.TemperatureModifier temperatureModifier = event.getClimate().temperatureModifier;
        return category == Biome.Category.THEEND
                || (category == Biome.Category.OCEAN && temperatureModifier != Biome.TemperatureModifier.FROZEN);
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
            WorldGenRegistries.CONFIGURED_FEATURE.getOptional(new ResourceLocation(ExtendedMushrooms.MOD_ID, mushroom.name + "_normal"))
                    .ifPresent(feature -> generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature));
            //add more mushrooms to some specific biomes
            String suffix = null;
            switch (event.getCategory()) {
                case MUSHROOM: //same as taiga
                case TAIGA:
                    suffix = "_taiga";
                    //giant taiga? - same with an additional .func_242731_b(3)
                    break;
                case SWAMP:
                    suffix = "_swamp";
                    break;
                case NETHER:
                    suffix = "_nether";
                    break;
            }
            if (suffix != null) {
                WorldGenRegistries.CONFIGURED_FEATURE.getOptional(new ResourceLocation(ExtendedMushrooms.MOD_ID, mushroom.name + suffix))
                        .ifPresent(feature -> generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature));
            }
        }
    }

    private static void addBigMushrooms(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        if (category == Biome.Category.MUSHROOM) {
            BiomeGenerationSettingsBuilder generation = event.getGeneration();
            for (BigMushroom bigMushroom : bigMushrooms) {
                if (bigMushroom.enableConfig.get()) {
                    WorldGenRegistries.CONFIGURED_FEATURE.getOptional(new ResourceLocation(ExtendedMushrooms.MOD_ID, bigMushroom.name + "_field"))
                            .ifPresent(feature -> generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature));
                }
            }
        }
    }

    private static void addMegaMushrooms(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        if (category == Biome.Category.MUSHROOM) {
            BiomeGenerationSettingsBuilder generation = event.getGeneration();
            for (BigMushroom megaMushroom : megaMushrooms) {
                if (megaMushroom.enableConfig.get()) {
                    WorldGenRegistries.CONFIGURED_FEATURE.getOptional(new ResourceLocation(ExtendedMushrooms.MOD_ID, megaMushroom.name + "_field"))
                            .ifPresent(feature -> generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature));
                }
            }
        }
    }

    private static class Mushroom {
        String name;
        Block block;
        double spawnFactor;
        ForgeConfigSpec.BooleanValue enableConfig;
        BlockClusterFeatureConfig config;

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
            this.config = new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(mushroomBlock.getDefaultState()),
                    new SimpleBlockPlacer()).tries(spawnTryCount).preventProjection().build();
        }
    }

    private static class BigMushroom {
        String name;
        ConfiguredFeature<BigMushroomFeatureConfig, ?> config;
        float spawnChance;
        ForgeConfigSpec.BooleanValue enableConfig;

        private BigMushroom(String name, ConfiguredFeature<BigMushroomFeatureConfig, ?> config, float spawnChance, ForgeConfigSpec.BooleanValue enableConfig) {
            this.name = name;
            this.config = config;
            this.spawnChance = spawnChance;
            this.enableConfig = enableConfig;
        }
    }


}
