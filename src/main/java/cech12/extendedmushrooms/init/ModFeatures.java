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
            if (Config.VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED.get()) {
                megaMushrooms.add(new BigMushroom(MEGA_RED_MUSHROOM.withConfiguration(RedMushroom.getConfig()), Config.MEGA_RED_MUSHROOM_GENERATION_WEIGHT.get()));
                megaMushrooms.add(new BigMushroom(MEGA_BROWN_MUSHROOM.withConfiguration(BrownMushroom.getConfig()), Config.MEGA_BROWN_MUSHROOM_GENERATION_WEIGHT.get()));
            }

            if (Config.GLOWSHROOM_GENERATION_ENABLED.get()) {
                mushrooms.add(new Mushroom(ExtendedMushroomsBlocks.GLOWSHROOM, Config.GLOWSHROOM_GENERATION_FACTOR.get()));
            }
            if (Config.BIG_GLOWSHROOM_GENERATION_ENABLED.get()) {
                bigMushrooms.add(new BigMushroom(BIG_GLOWSHROOM.withConfiguration(Glowshroom.getConfig()), Config.BIG_GLOWSHROOM_GENERATION_WEIGHT.get()));
            }
            if (Config.MEGA_GLOWSHROOM_GENERATION_ENABLED.get()) {
                megaMushrooms.add(new BigMushroom(MEGA_GLOWSHROOM.withConfiguration(Glowshroom.getConfig()), Config.MEGA_GLOWSHROOM_GENERATION_WEIGHT.get()));
            }

            if (Config.POISONOUS_MUSHROOM_GENERATION_ENABLED.get()) {
                mushrooms.add(new Mushroom(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM, Config.POISONOUS_MUSHROOM_GENERATION_FACTOR.get()));
            }
            if (Config.BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED.get()) {
                bigMushrooms.add(new BigMushroom(BIG_POISONOUS_MUSHROOM.withConfiguration(PoisonousMushroom.getConfig()), Config.BIG_POISONOUS_MUSHROOM_GENERATION_WEIGHT.get()));
            }
            if (Config.MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED.get()) {
                megaMushrooms.add(new BigMushroom(MEGA_POISONOUS_MUSHROOM.withConfiguration(PoisonousMushroom.getConfig()), Config.MEGA_POISONOUS_MUSHROOM_GENERATION_WEIGHT.get()));
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
            //calculate chance
            int chance = Math.max(1, (int) (4.0 / mushroom.spawnFactor));
            //add mushrooms to all biomes
            generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(mushroom.config).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242729_a(chance));
            //add more mushrooms to some specific biomes
            ConfiguredFeature<?, ?> configuredFeature = null;
            switch (event.getCategory()) {
                case MUSHROOM: //same as taiga
                case TAIGA:
                    configuredFeature = Feature.RANDOM_PATCH.withConfiguration(mushroom.config).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242729_a(chance);
                    //giant taiga? - same with an additional .func_242731_b(3)
                    break;
                case SWAMP:
                    configuredFeature = Feature.RANDOM_PATCH.withConfiguration(mushroom.config).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242729_a(chance).func_242731_b(8);
                    break;
                case NETHER:
                    configuredFeature = Feature.RANDOM_PATCH.withConfiguration(mushroom.config).func_242733_d(128).func_242729_a(Math.max(1, chance / 2));
                    break;
            }
            if (configuredFeature != null) {
                generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, configuredFeature);
            }
        }
    }

    private static void addBigMushrooms(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        if (category == Biome.Category.MUSHROOM) {
            if (!bigMushrooms.isEmpty()) {
                BiomeGenerationSettingsBuilder generation = event.getGeneration();
                double fullSpawnFactor = 0D;
                for (BigMushroom bigMushroom : bigMushrooms) {
                    fullSpawnFactor += bigMushroom.spawnFactor;
                }
                List<ConfiguredRandomFeatureList> mushrooms = new LinkedList<>();
                for (BigMushroom bigMushroom : bigMushrooms) {
                    mushrooms.add(bigMushroom.config.withChance((float) (bigMushroom.spawnFactor / fullSpawnFactor)));
                }
                generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(mushrooms, bigMushrooms.get(0).config)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, (float) (0.5D * fullSpawnFactor), 1))));
            }
        }
    }

    private static void addMegaMushrooms(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        if (category == Biome.Category.MUSHROOM) {
            if (!megaMushrooms.isEmpty()) {
                BiomeGenerationSettingsBuilder generation = event.getGeneration();
                double fullSpawnFactor = 0D;
                for (BigMushroom megaMushroom : megaMushrooms) {
                    fullSpawnFactor += megaMushroom.spawnFactor;
                }
                List<ConfiguredRandomFeatureList> mushrooms = new LinkedList<>();
                for (BigMushroom megaMushroom : megaMushrooms) {
                    mushrooms.add(megaMushroom.config.withChance((float) (megaMushroom.spawnFactor / fullSpawnFactor)));
                }
                generation.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(mushrooms, megaMushrooms.get(0).config)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.3F, 1))));
            }
        }
    }

    private static class Mushroom {
        Block block;
        double spawnFactor;
        BlockClusterFeatureConfig config;

        private Mushroom(Block mushroomBlock, double spawnFactor) {
            this.block = mushroomBlock;
            this.spawnFactor = spawnFactor;
            this.config = new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(mushroomBlock.getDefaultState()),
                    new SimpleBlockPlacer()).tries(64).func_227317_b_().build();
        }
    }

    private static class BigMushroom {
        double spawnFactor;
        ConfiguredFeature<BigMushroomFeatureConfig, ?> config;

        private BigMushroom(ConfiguredFeature<BigMushroomFeatureConfig, ?> config, double spawnFactor) {
            this.spawnFactor = spawnFactor;
            this.config = config;
        }
    }


}
