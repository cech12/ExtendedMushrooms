package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.block.mushrooms.BrownMushroom;
import cech12.extendedmushrooms.block.mushrooms.Glowshroom;
import cech12.extendedmushrooms.block.mushrooms.PoisonousMushroom;
import cech12.extendedmushrooms.block.mushrooms.RedMushroom;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.world.gen.feature.BigGlowshroomFeature;
import cech12.extendedmushrooms.world.gen.feature.BigPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaBrownMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaGlowshroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaRedMushroomFeature;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredRandomFeatureList;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
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

    public static Feature<BigMushroomFeatureConfig> MEGA_BROWN_MUSHROOM;
    public static Feature<BigMushroomFeatureConfig> MEGA_RED_MUSHROOM;

    public static Feature<BigMushroomFeatureConfig> BIG_GLOWSHROOM;
    public static Feature<BigMushroomFeatureConfig> MEGA_GLOWSHROOM;

    public static Feature<BigMushroomFeatureConfig> BIG_POISONOUS_MUSHROOM;
    public static Feature<BigMushroomFeatureConfig> MEGA_POISONOUS_MUSHROOM;

    private static List<Mushroom> mushrooms = null;
    private static List<BigMushroom> bigMushrooms = null;
    private static List<BigMushroom> megaMushrooms = null;

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
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
                megaMushrooms.add(new BigMushroom(MEGA_RED_MUSHROOM.withConfiguration(RedMushroom.getConfig()), (float) Config.MEGA_RED_MUSHROOM_GENERATION_WEIGHT.getValue()));
                megaMushrooms.add(new BigMushroom(MEGA_BROWN_MUSHROOM.withConfiguration(BrownMushroom.getConfig()), (float) Config.MEGA_BROWN_MUSHROOM_GENERATION_WEIGHT.getValue()));
            }

            if (Config.GLOWSHROOM_GENERATION_ENABLED.getValue()) {
                mushrooms.add(new Mushroom(ExtendedMushroomsBlocks.GLOWSHROOM, (float) Config.GLOWSHROOM_GENERATION_FACTOR.getValue()));
            }
            if (Config.BIG_GLOWSHROOM_GENERATION_ENABLED.getValue()) {
                bigMushrooms.add(new BigMushroom(BIG_GLOWSHROOM.withConfiguration(Glowshroom.getConfig()), (float) Config.BIG_GLOWSHROOM_GENERATION_WEIGHT.getValue()));
            }
            if (Config.MEGA_GLOWSHROOM_GENERATION_ENABLED.getValue()) {
                megaMushrooms.add(new BigMushroom(MEGA_GLOWSHROOM.withConfiguration(Glowshroom.getConfig()), (float) Config.MEGA_GLOWSHROOM_GENERATION_WEIGHT.getValue()));
            }

            if (Config.POISONOUS_MUSHROOM_GENERATION_ENABLED.getValue()) {
                mushrooms.add(new Mushroom(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM, (float) Config.POISONOUS_MUSHROOM_GENERATION_FACTOR.getValue()));
            }
            if (Config.BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED.getValue()) {
                bigMushrooms.add(new BigMushroom(BIG_POISONOUS_MUSHROOM.withConfiguration(PoisonousMushroom.getConfig()), (float) Config.BIG_POISONOUS_MUSHROOM_GENERATION_WEIGHT.getValue()));
            }
            if (Config.MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED.getValue()) {
                megaMushrooms.add(new BigMushroom(MEGA_POISONOUS_MUSHROOM.withConfiguration(PoisonousMushroom.getConfig()), (float) Config.MEGA_POISONOUS_MUSHROOM_GENERATION_WEIGHT.getValue()));
            }
        }

        //add mushrooms
        addMushrooms(event);
        addBigMushrooms(event);
        addMegaMushrooms(event);

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

    private static void addBigMushrooms(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        if (category == Biome.Category.MUSHROOM) {
            if (!bigMushrooms.isEmpty()) {
                BiomeGenerationSettingsBuilder generation = event.getGeneration();
                float fullSpawnFactor = 0F;
                for (BigMushroom bigMushroom : bigMushrooms) {
                    fullSpawnFactor += bigMushroom.spawnFactor;
                }
                List<ConfiguredRandomFeatureList> mushrooms = new LinkedList<>();
                for (BigMushroom bigMushroom : bigMushrooms) {
                    mushrooms.add(bigMushroom.config.withChance(bigMushroom.spawnFactor / fullSpawnFactor));
                }
                generation.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(mushrooms, bigMushrooms.get(0).config)).withPlacement(Features.Placements.field_244001_l).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.5F * fullSpawnFactor, 1))));
            }
        }

        //TODO filter DARK_FOREST & DARK_FOREST_HILLS and add big mushrooms there
        /*
        Biome[] biomesWithBigMushrooms = {Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS};
        ConfiguredFeature<?, ?> spawnBigMushroomsFeature = WEIGHT_RANDOM_SELECTOR
                .withConfiguration(new WeightedRandomFeature(bigMushrooms))
                .withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(Config.BIG_MUSHROOM_GENERATION_CHANCE.getValue())));
        for (Biome biome : biomesWithBigMushrooms) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, spawnBigMushroomsFeature);
        }
         */

        // TODO check mod biomes
        //mod biomes
        /*
        List<ModCompat.BiomeConfig> modBiomeConfigs = ModCompat.getBiomesWithHugeMushrooms();
        for (ModCompat.BiomeConfig biomeConfig : modBiomeConfigs) {
            if (biomeConfig.biomeExist()) {
                biomeConfig.getBiome().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, WEIGHT_RANDOM_SELECTOR
                        .withConfiguration(new WeightedRandomFeature(bigMushrooms))
                        .withPlacement(biomeConfig.getPlacement(Config.BIG_MUSHROOM_GENERATION_CHANCE.getValue(), 0)));
            }
        }
         */
    }

    private static void addMegaMushrooms(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        if (category == Biome.Category.MUSHROOM) {
            if (!megaMushrooms.isEmpty()) {
                BiomeGenerationSettingsBuilder generation = event.getGeneration();
                float fullSpawnFactor = 0F;
                for (BigMushroom megaMushroom : megaMushrooms) {
                    fullSpawnFactor += megaMushroom.spawnFactor;
                }
                List<ConfiguredRandomFeatureList> mushrooms = new LinkedList<>();
                for (BigMushroom megaMushroom : megaMushrooms) {
                    mushrooms.add(megaMushroom.config.withChance(megaMushroom.spawnFactor / fullSpawnFactor));
                }
                generation.func_242513_a(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(mushrooms, bigMushrooms.get(0).config)).withPlacement(Features.Placements.field_244001_l).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.3F, 1))));
            }
        }
    }

    private static class Mushroom {
        Block block;
        float spawnFactor;
        BlockClusterFeatureConfig config;

        private Mushroom(Block mushroomBlock, float spawnFactor) {
            this.block = mushroomBlock;
            this.spawnFactor = spawnFactor;
            this.config = new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(mushroomBlock.getDefaultState()),
                    new SimpleBlockPlacer()).tries(64).func_227317_b_().build();
        }
    }

    private static class BigMushroom {
        float spawnFactor;
        ConfiguredFeature<BigMushroomFeatureConfig, ?> config;

        private BigMushroom(ConfiguredFeature<BigMushroomFeatureConfig, ?> config, float spawnFactor) {
            this.spawnFactor = spawnFactor;
            this.config = config;
        }
    }


}
