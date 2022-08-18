package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.mushrooms.BrownMushroom;
import cech12.extendedmushrooms.block.mushrooms.Glowshroom;
import cech12.extendedmushrooms.block.mushrooms.HoneyFungus;
import cech12.extendedmushrooms.block.mushrooms.PoisonousMushroom;
import cech12.extendedmushrooms.block.mushrooms.RedMushroom;
import cech12.extendedmushrooms.block.mushrooms.SlimeFungus;
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
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ExtendedMushrooms.MOD_ID);
    public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> MEGA_BROWN_MUSHROOM = FEATURES.register("mega_brown_mushroom", () -> new MegaBrownMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> MEGA_RED_MUSHROOM = FEATURES.register("mega_red_mushroom", () -> new MegaRedMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> BIG_GLOWSHROOM = FEATURES.register("big_glowshroom", () -> new BigGlowshroomFeature(HugeMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> MEGA_GLOWSHROOM = FEATURES.register("mega_glowshroom", () -> new MegaGlowshroomFeature(HugeMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> BIG_POISONOUS_MUSHROOM = FEATURES.register("big_poisonous_mushroom", () -> new BigPoisonousMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> MEGA_POISONOUS_MUSHROOM = FEATURES.register("mega_poisonous_mushroom", () -> new MegaPoisonousMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> BIG_SLIME_FUNGUS = FEATURES.register("big_slime_fungus", () -> new BigSlimeFungusFeature(HugeMushroomFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<HugeMushroomFeatureConfiguration>> BIG_HONEY_FUNGUS = FEATURES.register("big_honey_fungus", () -> new BigHoneyFungusFeature(HugeMushroomFeatureConfiguration.CODEC));

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ExtendedMushrooms.MOD_ID);
    public static final RegistryObject<ConfiguredFeature<?, ?>> INFESTED_FLOWER_CONFIGURED = CONFIGURED_FEATURES.register("infested_flower", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(32,PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.INFESTED_FLOWER.get()))))));
    public static final RegistryObject<ConfiguredFeature<?, ?>> INFESTED_GRASS_CONFIGURED = CONFIGURED_FEATURES.register("infested_grass", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.INFESTED_GRASS.get()))))));
    public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_BROWN_MUSHROOM_CONFIGURED = CONFIGURED_FEATURES.register("mega_brown_mushroom", () -> new ConfiguredFeature<>(MEGA_BROWN_MUSHROOM.get(), BrownMushroom.getConfig()));
    public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_RED_MUSHROOM_CONFIGURED = CONFIGURED_FEATURES.register("mega_red_mushroom", () -> new ConfiguredFeature<>(MEGA_RED_MUSHROOM.get(), RedMushroom.getConfig()));
    public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_GLOWSHROOM_CONFIGURED = CONFIGURED_FEATURES.register("big_glowshroom", () -> new ConfiguredFeature<>(BIG_GLOWSHROOM.get(), Glowshroom.getConfig()));
    public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_GLOWSHROOM_CONFIGURED = CONFIGURED_FEATURES.register("mega_glowshroom", () -> new ConfiguredFeature<>(MEGA_GLOWSHROOM.get(), Glowshroom.getConfig()));
    public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_POISONOUS_MUSHROOM_CONFIGURED = CONFIGURED_FEATURES.register("big_poisonous_mushroom", () -> new ConfiguredFeature<>(BIG_POISONOUS_MUSHROOM.get(), PoisonousMushroom.getConfig()));
    public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> MEGA_POISONOUS_MUSHROOM_CONFIGURED = CONFIGURED_FEATURES.register("mega_poisonous_mushroom", () -> new ConfiguredFeature<>(MEGA_POISONOUS_MUSHROOM.get(), PoisonousMushroom.getConfig()));
    public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_SLIME_FUNGUS_CONFIGURED = CONFIGURED_FEATURES.register("big_slime_fungus", () -> new ConfiguredFeature<>(BIG_SLIME_FUNGUS.get(), SlimeFungus.getConfig()));
    public static final RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> BIG_HONEY_FUNGUS_CONFIGURED = CONFIGURED_FEATURES.register("big_honey_fungus", () -> new ConfiguredFeature<>(BIG_HONEY_FUNGUS.get(), HoneyFungus.getConfig()));

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ExtendedMushrooms.MOD_ID);
    public static final RegistryObject<PlacedFeature> INFESTED_FLOWER_PLACED = PLACED_FEATURES.register("patch_infested_flower", () -> new PlacedFeature(Holder.direct(INFESTED_FLOWER_CONFIGURED.get()), List.copyOf(VegetationPlacements.worldSurfaceSquaredWithCount(4))));
    public static final RegistryObject<PlacedFeature> INFESTED_GRASS_PLACED = PLACED_FEATURES.register("patch_infested_grass", () -> new PlacedFeature(Holder.direct(INFESTED_GRASS_CONFIGURED.get()), List.copyOf(VegetationPlacements.worldSurfaceSquaredWithCount(2))));
    public static final Map<String, RegistryObject<PlacedFeature>> MUSHROOM_PLACED_FEATURES = new HashMap<>();
    public static final Map<String, RegistryObject<PlacedFeature>> BIG_MUSHROOM_PLACED_FEATURES = new HashMap<>();
    public static final Map<String, RegistryObject<PlacedFeature>> MEGA_MUSHROOM_PLACED_FEATURES = new HashMap<>();

    private static final List<Mushroom> mushrooms = new LinkedList<>();
    private static final List<BigMushroom> bigMushrooms = new LinkedList<>();
    private static final List<BigMushroom> megaMushrooms = new LinkedList<>();

    static {
        mushrooms.add(new Mushroom("glowshroom", ModBlocks.GLOWSHROOM, 0.4F));
        mushrooms.add(new Mushroom("poisonous_mushroom", ModBlocks.POISONOUS_MUSHROOM, 0.5F));
        bigMushrooms.add(new BigMushroom("big_glowshroom", BIG_GLOWSHROOM_CONFIGURED, 0.15F));
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
            BIG_MUSHROOM_PLACED_FEATURES.put("mushroom_island_" + bigMushroom.name, PLACED_FEATURES.register("mushroom_island_" + bigMushroom.name, () -> new PlacedFeature(Holder.direct(bigMushroom.feature.get()), List.copyOf(VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery((int) (100 * bigMushroom.spawnChance)))))));
        }
        //register feature for mega mushrooms random patch
        for (BigMushroom megaMushroom : megaMushrooms) {
            MEGA_MUSHROOM_PLACED_FEATURES.put("mushroom_island_" + megaMushroom.name, PLACED_FEATURES.register("mushroom_island_" + megaMushroom.name, () -> new PlacedFeature(Holder.direct(megaMushroom.feature.get()), List.copyOf(VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery((int) ((1 - megaMushroom.spawnChance) * 100)))))));
            //register(megaMushroom.name + "_field", megaMushroom.config.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, megaMushroom.spawnChance, 1))));
        }
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
        RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> feature;
        float spawnChance;

        private BigMushroom(String name, RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> feature, float spawnChance) {
            this.name = name;
            this.feature = feature;
            this.spawnChance = spawnChance;
        }
    }

}
