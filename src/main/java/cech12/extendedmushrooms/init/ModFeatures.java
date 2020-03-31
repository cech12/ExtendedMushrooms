package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.block.mushrooms.Glowshroom;
import cech12.extendedmushrooms.block.mushrooms.PoisonousMushroom;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.world.gen.feature.BigGlowshroomFeature;
import cech12.extendedmushrooms.world.gen.feature.BigPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaBrownMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaGlowshroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaPoisonousMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaRedMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.WeightedFeature;
import cech12.extendedmushrooms.world.gen.feature.WeightedRandomFeature;
import cech12.extendedmushrooms.world.gen.feature.WeightedRandomFeatureConfig;
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
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
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

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        WEIGHT_RANDOM_SELECTOR = register("weight_random_selector", new WeightedRandomFeatureConfig(WeightedRandomFeature::deserialize));

        MEGA_BROWN_MUSHROOM = register("mega_brown_mushroom", new MegaBrownMushroomFeature(BigMushroomFeatureConfig::deserialize));
        MEGA_RED_MUSHROOM = register("mega_red_mushroom", new MegaRedMushroomFeature(BigMushroomFeatureConfig::deserialize));

        BIG_GLOWSHROOM = register("big_glowshroom", new BigGlowshroomFeature(BigMushroomFeatureConfig::deserialize));
        MEGA_GLOWSHROOM = register("mega_glowshroom", new MegaGlowshroomFeature(BigMushroomFeatureConfig::deserialize));

        BIG_POISONOUS_MUSHROOM = register("big_poisonous_mushroom", new BigPoisonousMushroomFeature(BigMushroomFeatureConfig::deserialize));
        MEGA_POISONOUS_MUSHROOM = register("mega_poisonous_mushroom", new MegaPoisonousMushroomFeature(BigMushroomFeatureConfig::deserialize));
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F feature) {
        feature.setRegistryName(ExtendedMushrooms.MOD_ID, key);
        ForgeRegistries.FEATURES.register(feature);
        return feature;
    }

    public static void addFeaturesToBiomes() {
        List<Mushroom> mushrooms = new LinkedList<>();
        List<WeightedFeature<?>> bigMushrooms = new LinkedList<>();
        List<WeightedFeature<?>> megaMushrooms = new LinkedList<>();

        //add vanilla mega mushrooms to mushroom biomes
        if (Config.VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED.getValue()) {
            megaMushrooms.add(new WeightedFeature<>(MEGA_RED_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_RED_MUSHROOM), (float) Config.MEGA_RED_MUSHROOM_GENERATION_WEIGHT.getValue()));
            megaMushrooms.add(new WeightedFeature<>(MEGA_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM), (float) Config.MEGA_BROWN_MUSHROOM_GENERATION_WEIGHT.getValue()));
        }

        if (Config.GLOWSHROOM_GENERATION_ENABLED.getValue()) {
            mushrooms.add(new Mushroom(ExtendedMushroomsBlocks.GLOWSHROOM, (float) Config.GLOWSHROOM_GENERATION_CHANCE_FACTOR.getValue(), (float) Config.GLOWSHROOM_GENERATION_COUNT_FACTOR.getValue()));
        }
        if (Config.BIG_GLOWSHROOM_GENERATION_ENABLED.getValue()) {
            bigMushrooms.add(new WeightedFeature<>(BIG_GLOWSHROOM.withConfiguration(Glowshroom.getConfig()), (float) Config.BIG_GLOWSHROOM_GENERATION_WEIGHT.getValue()));
        }
        if (Config.MEGA_GLOWSHROOM_GENERATION_ENABLED.getValue()) {
            megaMushrooms.add(new WeightedFeature<>(MEGA_GLOWSHROOM.withConfiguration(Glowshroom.getConfig()), (float) Config.MEGA_GLOWSHROOM_GENERATION_WEIGHT.getValue()));
        }

        if (Config.POISONOUS_MUSHROOM_GENERATION_ENABLED.getValue()) {
            mushrooms.add(new Mushroom(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM, (float) Config.POISONOUS_MUSHROOM_GENERATION_CHANCE_FACTOR.getValue(), (float) Config.POISONOUS_MUSHROOM_GENERATION_COUNT_FACTOR.getValue()));
        }
        if (Config.BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED.getValue()) {
            bigMushrooms.add(new WeightedFeature<>(BIG_POISONOUS_MUSHROOM.withConfiguration(PoisonousMushroom.getConfig()), (float) Config.BIG_POISONOUS_MUSHROOM_GENERATION_WEIGHT.getValue()));
        }
        if (Config.MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED.getValue()) {
            megaMushrooms.add(new WeightedFeature<>(MEGA_POISONOUS_MUSHROOM.withConfiguration(PoisonousMushroom.getConfig()), (float) Config.MEGA_POISONOUS_MUSHROOM_GENERATION_WEIGHT.getValue()));
        }

        //add all collected mushrooms to biomes
        for (Mushroom mushroom : mushrooms) {
            //mushroom biomes
            addFeatureToMushroomBiomes(Feature.RANDOM_PATCH.withConfiguration(mushroom.config)
                    .withPlacement(Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(1, 0.25F * mushroom.chanceFactor))));
            //all other biomes with mushrooms
            addMushroomToAllBiomes(mushroom.config, mushroom.chanceFactor, mushroom.countFactor);
        }

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
        }

        //add all collected mega mushrooms to mushroom biomes
        if (megaMushrooms.size() > 0) {
            addFeatureToMushroomBiomes(WEIGHT_RANDOM_SELECTOR
                    .withConfiguration(new WeightedRandomFeature(megaMushrooms))
                    .withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(Config.MEGA_MUSHROOM_GENERATION_CHANCE.getValue()))));

        }

    }

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
        //normal biomes
        Biome[] biomes = {
                Biomes.BADLANDS, Biomes.BADLANDS_PLATEAU, Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS,
                Biomes.COLD_OCEAN, Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_OCEAN,
                Biomes.DEEP_WARM_OCEAN, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.DESERT_LAKES, Biomes.ERODED_BADLANDS, Biomes.FLOWER_FOREST, Biomes.FOREST, Biomes.FLOWER_FOREST,
                Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS,
                Biomes.GRAVELLY_MOUNTAINS, Biomes.ICE_SPIKES, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.LUKEWARM_OCEAN, Biomes.MODIFIED_BADLANDS_PLATEAU,
                Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MOUNTAIN_EDGE,
                Biomes.MOUNTAINS, Biomes.MUSHROOM_FIELD_SHORE, Biomes.MUSHROOM_FIELDS, Biomes.NETHER, Biomes.OCEAN, Biomes.PLAINS, Biomes.RIVER, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU,
                Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.SNOWY_BEACH, Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS,
                Biomes.SNOWY_MOUNTAINS, Biomes.SNOWY_TUNDRA, Biomes.STONE_SHORE, Biomes.SUNFLOWER_PLAINS, Biomes.SWAMP, Biomes.SWAMP_HILLS, Biomes.TAIGA, Biomes.TAIGA_HILLS,
                Biomes.TAIGA_MOUNTAINS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.WARM_OCEAN, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.WOODED_HILLS, Biomes.WOODED_MOUNTAINS
        };
        for (Biome biome : biomes) {
            biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(mushroomConfig).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(Math.max(1, (int) (4 / chanceFactor))))));
        }
    }

    private static class Mushroom {

        Block block;
        float chanceFactor;
        float countFactor;
        BlockClusterFeatureConfig config;

        private Mushroom(Block mushroomBlock, float chanceFactor, float countFactor) {
            this.block = mushroomBlock;
            this.chanceFactor = chanceFactor;
            this.countFactor = countFactor;
            this.config = new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(mushroomBlock.getDefaultState()),
                    new SimpleBlockPlacer()).tries(64).func_227317_b_().build();
        }

    }


}
