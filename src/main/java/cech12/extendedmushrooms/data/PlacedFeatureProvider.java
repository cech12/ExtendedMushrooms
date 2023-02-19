package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.init.ModFeatures;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlacedFeatureProvider implements DataProvider {

    private final PackOutput packOutput;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public PlacedFeatureProvider(final PackOutput packOutput, final CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.packOutput = packOutput;
        this.lookupProvider = lookupProvider;
    }

    private static Path getPath(Path root, ResourceLocation id) {
        return root.resolve("data/" + id.getNamespace() + "/worldgen/placed_feature/" + id.getPath() + ".json");
    }

    @Override
    @Nonnull
    public CompletableFuture<?> run(@Nonnull CachedOutput cache) {
        return this.lookupProvider.thenCompose(provider -> {
            List<PlacedFeatureJson> placedFeatures = new ArrayList<>();

            placedFeatures.add(new PlacedFeatureJson(ModFeatures.INFESTED_FLOWER_PLACED.location(), ModFeatures.INFESTED_FLOWER_CONFIGURED, List.copyOf(VegetationPlacements.worldSurfaceSquaredWithCount(4))));
            placedFeatures.add(new PlacedFeatureJson(ModFeatures.INFESTED_GRASS_PLACED.location(), ModFeatures.INFESTED_GRASS_CONFIGURED, List.copyOf(VegetationPlacements.worldSurfaceSquaredWithCount(2))));

            List<BigMushroom> bigMushrooms = new LinkedList<>();
            List<BigMushroom> megaMushrooms = new LinkedList<>();
            bigMushrooms.add(new BigMushroom("big_glowshroom", ModFeatures.BIG_GLOWSHROOM_CONFIGURED, 0.125F));
            bigMushrooms.add(new BigMushroom("big_poisonous_mushroom", ModFeatures.BIG_POISONOUS_MUSHROOM_CONFIGURED, 0.1F));
            megaMushrooms.add(new BigMushroom("mega_red_mushroom", ModFeatures.MEGA_RED_MUSHROOM_CONFIGURED, 0.2F));
            megaMushrooms.add(new BigMushroom("mega_brown_mushroom", ModFeatures.MEGA_BROWN_MUSHROOM_CONFIGURED, 0.2F));
            megaMushrooms.add(new BigMushroom("mega_glowshroom", ModFeatures.MEGA_GLOWSHROOM_CONFIGURED, 0.02F));
            megaMushrooms.add(new BigMushroom("mega_poisonous_mushroom", ModFeatures.MEGA_POISONOUS_MUSHROOM_CONFIGURED, 0.02F));
            //register small mushroom features
            for (ConfiguredFeatureProvider.Mushroom mushroom : ConfiguredFeatureProvider.MUSHROOMS) {
                placedFeatures.add(new PlacedFeatureJson(ModFeatures.MUSHROOMS_PLACED.get(mushroom.name()).get("normal").location(), mushroom.configuredFeature(), List.copyOf(getMushroomPlacement((int) (512 * mushroom.spawnFactor())))));
                placedFeatures.add(new PlacedFeatureJson(ModFeatures.MUSHROOMS_PLACED.get(mushroom.name()).get("taiga").location(), mushroom.configuredFeature(), List.copyOf(getMushroomPlacement((int) (256 * mushroom.spawnFactor())))));
                placedFeatures.add(new PlacedFeatureJson(ModFeatures.MUSHROOMS_PLACED.get(mushroom.name()).get("mushroom_island").location(), mushroom.configuredFeature(), List.copyOf(getMushroomPlacement((int) (256 * mushroom.spawnFactor())))));
                placedFeatures.add(new PlacedFeatureJson(ModFeatures.MUSHROOMS_PLACED.get(mushroom.name()).get("swamp").location(), mushroom.configuredFeature(), List.copyOf(getMushroomPlacement((int) (64 * mushroom.spawnFactor())))));
                placedFeatures.add(new PlacedFeatureJson(ModFeatures.MUSHROOMS_PLACED.get(mushroom.name()).get("nether").location(), mushroom.configuredFeature(), List.of(RarityFilter.onAverageOnceEvery(Math.max(1, (int) (2.0 * mushroom.spawnFactor()))), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome())));
            }
            //register feature for big mushrooms random patch
            for (BigMushroom bigMushroom : bigMushrooms) {
                placedFeatures.add(new PlacedFeatureJson(ModFeatures.BIG_MUSHROOMS_PLACED.get(bigMushroom.name()).get("mushroom_island").location(), bigMushroom.feature(), List.copyOf(VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, bigMushroom.spawnChance, 1)))));
            }
            //register feature for mega mushrooms random patch
            for (BigMushroom megaMushroom : megaMushrooms) {
                placedFeatures.add(new PlacedFeatureJson(ModFeatures.MEGA_MUSHROOMS_PLACED.get(megaMushroom.name()).get("mushroom_island").location(), megaMushroom.feature(), List.copyOf(VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, megaMushroom.spawnChance, 1)))));
                //register(megaMushroom.name + "_field", megaMushroom.config.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, megaMushroom.spawnChance, 1))));
            }

            return CompletableFuture.allOf(placedFeatures.stream().map(entry -> {
                Path path = getPath(this.packOutput.getOutputFolder(), entry.name());
                return DataProvider.saveStable(cache, entry.toJson(), path);
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    @Nonnull
    public String getName() {
        return "Extended Mushrooms placed feature provider";
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

    private record BigMushroom(String name, ResourceKey<ConfiguredFeature<?, ?>> feature, float spawnChance) {}

    private record PlacedFeatureJson(
            ResourceLocation name,
            ResourceKey<ConfiguredFeature<?, ?>> feature,
            List<PlacementModifier> placementModifiers
    ) {
        JsonElement toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("feature", feature.location().toString());
            JsonArray placement = new JsonArray();
            placementModifiers.forEach(placementModifier -> {
                placement.add(PlacementModifier.CODEC.encodeStart(JsonOps.INSTANCE, placementModifier).getOrThrow(false, msg -> LOGGER.error("Failed to encode {}: {}", name, msg)));
            });
            json.add("placement", placement);
            return json;
        }
    }

}
