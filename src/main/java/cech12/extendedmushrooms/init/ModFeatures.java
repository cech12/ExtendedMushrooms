package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.block.mushrooms.BrownMushroom;
import cech12.extendedmushrooms.block.mushrooms.Glowshroom;
import cech12.extendedmushrooms.block.mushrooms.PoisonousMushroom;
import cech12.extendedmushrooms.block.mushrooms.RedMushroom;
import cech12.extendedmushrooms.compat.ModCompat;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.world.gen.feature.BigGlowshroomFeature;
import cech12.extendedmushrooms.world.gen.feature.BigPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaBrownMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaGlowshroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaRedMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.WeightedFeature;
import cech12.extendedmushrooms.world.gen.feature.WeightedRandomFeature;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
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

    public static Feature<WeightedRandomFeature> WEIGHT_RANDOM_SELECTOR;

    public static Feature<BigMushroomFeatureConfig> MEGA_BROWN_MUSHROOM;
    public static Feature<BigMushroomFeatureConfig> MEGA_RED_MUSHROOM;

    public static Feature<BigMushroomFeatureConfig> BIG_GLOWSHROOM;
    public static Feature<BigMushroomFeatureConfig> MEGA_GLOWSHROOM;

    public static Feature<BigMushroomFeatureConfig> BIG_POISONOUS_MUSHROOM;
    public static Feature<BigMushroomFeatureConfig> MEGA_POISONOUS_MUSHROOM;

    private static List<Mushroom> mushrooms = null;
    private static List<WeightedFeature<?>> bigMushrooms = null;
    private static List<WeightedFeature<?>> megaMushrooms = null;

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        //TODO
        //WEIGHT_RANDOM_SELECTOR = register("weight_random_selector", new WeightedRandomFeatureConfig(WeightedRandomFeatureConfig.INSTANCE));

        MEGA_BROWN_MUSHROOM = register("mega_brown_mushroom", new MegaBrownMushroomFeature(BigMushroomFeatureConfig.field_236528_a_));
        MEGA_RED_MUSHROOM = register("mega_red_mushroom", new MegaRedMushroomFeature(BigMushroomFeatureConfig.field_236528_a_));

        BIG_GLOWSHROOM = register("big_glowshroom", new BigGlowshroomFeature(BigMushroomFeatureConfig.field_236528_a_));
        MEGA_GLOWSHROOM = register("mega_glowshroom", new MegaGlowshroomFeature(BigMushroomFeatureConfig.field_236528_a_));

        BIG_POISONOUS_MUSHROOM = register("big_poisonous_mushroom", new BigPoisonousMushroomFeature(BigMushroomFeatureConfig.field_236528_a_));
        MEGA_POISONOUS_MUSHROOM = register("mega_poisonous_mushroom", new MegaPoisonousMushroomFeature(BigMushroomFeatureConfig.field_236528_a_));
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F feature) {
        feature.setRegistryName(ExtendedMushrooms.MOD_ID, key);
        ForgeRegistries.FEATURES.register(feature);
        return feature;
    }

    public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
        if (mushrooms == null || bigMushrooms == null || megaMushrooms == null) {
            mushrooms = new LinkedList<>();
            bigMushrooms = new LinkedList<>();
            megaMushrooms = new LinkedList<>();

            //add vanilla mega mushrooms to mushroom biomes
            if (Config.VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED.getValue()) {
                megaMushrooms.add(new WeightedFeature<>(MEGA_RED_MUSHROOM.withConfiguration(RedMushroom.getConfig()), (float) Config.MEGA_RED_MUSHROOM_GENERATION_WEIGHT.getValue()));
                megaMushrooms.add(new WeightedFeature<>(MEGA_BROWN_MUSHROOM.withConfiguration(BrownMushroom.getConfig()), (float) Config.MEGA_BROWN_MUSHROOM_GENERATION_WEIGHT.getValue()));
            }

            if (Config.GLOWSHROOM_GENERATION_ENABLED.getValue()) {
                mushrooms.add(new Mushroom(ExtendedMushroomsBlocks.GLOWSHROOM, (float) Config.GLOWSHROOM_GENERATION_FACTOR.getValue()));
            }
            if (Config.BIG_GLOWSHROOM_GENERATION_ENABLED.getValue()) {
                bigMushrooms.add(new WeightedFeature<>(BIG_GLOWSHROOM.withConfiguration(Glowshroom.getConfig()), (float) Config.BIG_GLOWSHROOM_GENERATION_WEIGHT.getValue()));
            }
            if (Config.MEGA_GLOWSHROOM_GENERATION_ENABLED.getValue()) {
                megaMushrooms.add(new WeightedFeature<>(MEGA_GLOWSHROOM.withConfiguration(Glowshroom.getConfig()), (float) Config.MEGA_GLOWSHROOM_GENERATION_WEIGHT.getValue()));
            }

            if (Config.POISONOUS_MUSHROOM_GENERATION_ENABLED.getValue()) {
                mushrooms.add(new Mushroom(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM, (float) Config.POISONOUS_MUSHROOM_GENERATION_FACTOR.getValue()));
            }
            if (Config.BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED.getValue()) {
                bigMushrooms.add(new WeightedFeature<>(BIG_POISONOUS_MUSHROOM.withConfiguration(PoisonousMushroom.getConfig()), (float) Config.BIG_POISONOUS_MUSHROOM_GENERATION_WEIGHT.getValue()));
            }
            if (Config.MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED.getValue()) {
                megaMushrooms.add(new WeightedFeature<>(MEGA_POISONOUS_MUSHROOM.withConfiguration(PoisonousMushroom.getConfig()), (float) Config.MEGA_POISONOUS_MUSHROOM_GENERATION_WEIGHT.getValue()));
            }
        }

        //add mushrooms
        addMushrooms(event);

        /* TODO
        //add all collected big mushrooms to biomes
        if (bigMushrooms.size() > 0) {
            Biome[] biomesWithBigMushrooms = {Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS};
            ConfiguredFeature<?, ?> spawnBigMushroomsFeature = WEIGHT_RANDOM_SELECTOR
                    .withConfiguration(new WeightedRandomFeature(bigMushrooms))
                    .withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(Config.BIG_MUSHROOM_GENERATION_CHANCE.getValue())));
            for (Biome biome : biomesWithBigMushrooms) {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, spawnBigMushroomsFeature);
            }
            addFeatureToMushroomBiomes(WEIGHT_RANDOM_SELECTOR
                    .withConfiguration(new WeightedRandomFeature(bigMushrooms))
                    .withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(5))));

            //mod biomes
            List<ModCompat.BiomeConfig> modBiomeConfigs = ModCompat.getBiomesWithHugeMushrooms();
            for (ModCompat.BiomeConfig biomeConfig : modBiomeConfigs) {
                if (biomeConfig.biomeExist()) {
                    biomeConfig.getBiome().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, WEIGHT_RANDOM_SELECTOR
                            .withConfiguration(new WeightedRandomFeature(bigMushrooms))
                            .withPlacement(biomeConfig.getPlacement(Config.BIG_MUSHROOM_GENERATION_CHANCE.getValue(), 0)));
                }
            }
        }

        //add all collected mega mushrooms to mushroom biomes
        if (megaMushrooms.size() > 0) {
            addFeatureToMushroomBiomes(WEIGHT_RANDOM_SELECTOR
                    .withConfiguration(new WeightedRandomFeature(megaMushrooms))
                    .withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(Config.MEGA_MUSHROOM_GENERATION_CHANCE.getValue()))));
        }
         */

    }

    private static boolean biomeHasNoMushrooms(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        Biome.TemperatureModifier temperatureModifier = event.getClimate().field_242462_d;
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
            //calculate chance
            int chance = Math.max(1, (int) (4.0 / mushroom.spawnFactor));
            //add mushrooms to all biomes
            generation.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(mushroom.config).withPlacement(Features.Placements.field_244002_m).func_242729_a(chance));
            //add more mushrooms to some specific biomes
            ConfiguredFeature<?, ?> configuredFeature = null;
            switch (event.getCategory()) {
                case MUSHROOM: //same as taiga
                case TAIGA:
                    configuredFeature = Feature.RANDOM_PATCH.withConfiguration(mushroom.config).withPlacement(Features.Placements.field_244001_l).func_242729_a(chance);
                    //TODO giant taiga? - same with an additional .func_242731_b(3)
                    break;
                case SWAMP:
                    configuredFeature = Feature.RANDOM_PATCH.withConfiguration(mushroom.config).withPlacement(Features.Placements.field_244001_l).func_242729_a(chance).func_242731_b(8);
                    break;
                case NETHER:
                    configuredFeature = Feature.RANDOM_PATCH.withConfiguration(mushroom.config).func_242733_d(128).func_242729_a(Math.max(1, chance / 2));
                    break;
            }
            if (configuredFeature != null) {
                generation.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, configuredFeature);
            }

            // TODO check mod biomes
            /*
            //mod biomes
            List<ModCompat.BiomeConfig> modBiomeConfigs = ModCompat.getBiomesWithMushrooms();
            for (ModCompat.BiomeConfig biomeConfig : modBiomeConfigs) {
                if (biomeConfig.biomeExist()) {
                    biomeConfig.getBiome().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(mushroom.config).withPlacement(biomeConfig.getPlacement(mushroom.countFactor, mushroom.chanceFactor)));
                }
            }
             */
        }
    }

    /*
    private static void addFeatureToMushroomBiomes(ConfiguredFeature<?, ?> feature) {
        Biome[] biomes = {Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE};
        for (Biome biome : biomes) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature);
        }
    }

    private static void addMushroomToAllBiomes(BlockClusterFeatureConfig mushroomConfig, float chanceFactor, float countFactor) {
        //Taiga
        Biome[] taigaBiomes = {Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS};
        for (Biome biome : taigaBiomes) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(mushroomConfig).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(Math.max(1, (int) countFactor), 0.25F * chanceFactor))));
        }
        //Giant Taiga
        Biome[] giantTaigaBiomes = {Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS};
        for (Biome biome : giantTaigaBiomes) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(mushroomConfig).withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(Math.max(1, (int) (3 * countFactor)), 0.25F * chanceFactor))));
        }
        //normal biomes //TODO NETHER!!!
        Biome[] biomes = {
                Biomes.BADLANDS, Biomes.BADLANDS_PLATEAU, Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS,
                Biomes.COLD_OCEAN, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_OCEAN,
                Biomes.DEEP_WARM_OCEAN, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.DESERT_LAKES, Biomes.ERODED_BADLANDS, Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.FLOWER_FOREST,
                Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS,
                Biomes.GRAVELLY_MOUNTAINS, Biomes.ICE_SPIKES, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.LUKEWARM_OCEAN, Biomes.MODIFIED_BADLANDS_PLATEAU,
                Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MOUNTAIN_EDGE,
                Biomes.MOUNTAINS, Biomes.MUSHROOM_FIELD_SHORE, Biomes.MUSHROOM_FIELDS, Biomes.OCEAN, Biomes.PLAINS, Biomes.RIVER, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU,
                Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.SNOWY_BEACH, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS,
                Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TUNDRA, Biomes.STONE_SHORE, Biomes.SUNFLOWER_PLAINS, Biomes.SWAMP, Biomes.SWAMP_HILLS, Biomes.TAIGA, Biomes.TAIGA_HILLS,
                Biomes.TAIGA_MOUNTAINS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.WARM_OCEAN, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.WOODED_HILLS, Biomes.WOODED_MOUNTAINS
        };
        for (Biome biome : biomes) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(mushroomConfig).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(Math.max(1, (int) (4 / chanceFactor))))));
        }
    }
     */

    private static class Mushroom {

        Block block;
        float spawnFactor;
        BlockClusterFeatureConfig config;

        private Mushroom(Block mushroomBlock, float chanceFactor) {
            this.block = mushroomBlock;
            this.spawnFactor = chanceFactor;
            this.config = new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(mushroomBlock.getDefaultState()),
                    new SimpleBlockPlacer()).tries(64).func_227317_b_().build();
        }

    }


}
