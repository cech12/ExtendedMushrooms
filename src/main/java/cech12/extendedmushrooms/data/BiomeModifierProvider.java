package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModEntityTypes;
import cech12.extendedmushrooms.init.ModFeatures;
import cech12.extendedmushrooms.init.ModTags;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class BiomeModifierProvider implements DataProvider {

    private final PackOutput packOutput;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public BiomeModifierProvider(final PackOutput packOutput, final CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.packOutput = packOutput;
        this.lookupProvider = lookupProvider;
    }

    private static ResourceLocation prefix(String name) {
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, name);
    }

    private static Path getPath(Path root, ResourceLocation id) {
        return root.resolve("data/" + id.getNamespace() + "/forge/biome_modifier/" + id.getPath() + ".json");
    }

    private static List<ResourceKey<PlacedFeature>> getPlacedFeatures(Map<String, Map<String, ResourceKey<PlacedFeature>>> mushrooms, String extension) {
        List<ResourceKey<PlacedFeature>> placedFeatures = new ArrayList<>();
        mushrooms.forEach((mushroom, extensionMap) -> placedFeatures.add(extensionMap.get(extension)));
        return placedFeatures;
    }

    @Override
    @Nonnull
    public CompletableFuture<?> run(@Nonnull CachedOutput cache) {
        return this.lookupProvider.thenCompose(provider -> {
            List<JsonProvider> modifierList = new ArrayList<>();

            TagKey<Biome> isTaigaTag = BiomeTags.IS_TAIGA;
            TagKey<Biome> isSwampTag = Tags.Biomes.IS_SWAMP;
            TagKey<Biome> isNetherTag = BiomeTags.IS_NETHER;
            TagKey<Biome> isMushroomTag = Tags.Biomes.IS_MUSHROOM;
            TagKey<Biome> hasMushroomsTag = ModTags.Biomes.HAS_MUSHROOMS;

            //entities
            modifierList.add(new AddSpawnsBiomeModifierJson(prefix("mushroom_sheep_spawn"), isMushroomTag, new MobSpawnSettings.SpawnerData(ModEntityTypes.MUSHROOM_SHEEP.get(), 8, 4, 8)));

            //infested flower & infested grass
            modifierList.add(new AddFeaturesBiomeModifierJson(prefix("infested_flower_addition"), isMushroomTag, Collections.singletonList(ModFeatures.INFESTED_FLOWER_PLACED), GenerationStep.Decoration.VEGETAL_DECORATION));
            modifierList.add(new AddFeaturesBiomeModifierJson(prefix("infested_grass_addition"), isMushroomTag, Collections.singletonList(ModFeatures.INFESTED_GRASS_PLACED), GenerationStep.Decoration.VEGETAL_DECORATION));

            //small mushrooms
            modifierList.add(new AddFeaturesBiomeModifierJson(prefix("mushroom_addition_normal"), hasMushroomsTag, getPlacedFeatures(ModFeatures.MUSHROOMS_PLACED, "normal"), GenerationStep.Decoration.VEGETAL_DECORATION));
            modifierList.add(new AddFeaturesBiomeModifierJson(prefix("mushroom_addition_taiga"), isTaigaTag, getPlacedFeatures(ModFeatures.MUSHROOMS_PLACED, "taiga"), GenerationStep.Decoration.VEGETAL_DECORATION));
            modifierList.add(new AddFeaturesBiomeModifierJson(prefix("mushroom_addition_mushroom_island"), isMushroomTag, getPlacedFeatures(ModFeatures.MUSHROOMS_PLACED, "mushroom_island"), GenerationStep.Decoration.VEGETAL_DECORATION));
            modifierList.add(new AddFeaturesBiomeModifierJson(prefix("mushroom_addition_swamp"), isSwampTag, getPlacedFeatures(ModFeatures.MUSHROOMS_PLACED, "swamp"), GenerationStep.Decoration.VEGETAL_DECORATION));
            modifierList.add(new AddFeaturesBiomeModifierJson(prefix("mushroom_addition_nether"), isNetherTag, getPlacedFeatures(ModFeatures.MUSHROOMS_PLACED, "nether"), GenerationStep.Decoration.VEGETAL_DECORATION));

            //big & mega mushrooms
            modifierList.add(new AddFeaturesBiomeModifierJson(prefix("big_mushroom_addition"), isMushroomTag, getPlacedFeatures(ModFeatures.BIG_MUSHROOMS_PLACED, "mushroom_island"), GenerationStep.Decoration.VEGETAL_DECORATION));
            modifierList.add(new AddFeaturesBiomeModifierJson(prefix("mega_mushroom_addition"), isMushroomTag, getPlacedFeatures(ModFeatures.MEGA_MUSHROOMS_PLACED, "mushroom_island"), GenerationStep.Decoration.VEGETAL_DECORATION));

            return CompletableFuture.allOf(modifierList.stream().map(entry -> {
                Path path = getPath(this.packOutput.getOutputFolder(), entry.location());
                return DataProvider.saveStable(cache, entry.toJson(), path);
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    @Nonnull
    public String getName() {
        return "Extended Mushrooms biome modifier provider";
    }

    private interface JsonProvider {
        ResourceLocation location();
        JsonElement toJson();
    }

    private record AddSpawnsBiomeModifierJson(
            ResourceLocation location,
            TagKey<Biome> tag,
            MobSpawnSettings.SpawnerData spawnerData
    ) implements JsonProvider {
        public JsonElement toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("type", ForgeMod.ADD_SPAWNS_BIOME_MODIFIER_TYPE.getId().toString());
            json.addProperty("biomes", "#" + tag.location());
            json.add("spawners", MobSpawnSettings.SpawnerData.CODEC.encodeStart(JsonOps.INSTANCE, spawnerData).getOrThrow(false, msg -> LOGGER.error("Failed to encode spawners of {}: {}", location, msg)));
            return json;
        }
    }

    private record AddFeaturesBiomeModifierJson(
            ResourceLocation location,
            TagKey<Biome> tag,
            List<ResourceKey<PlacedFeature>> features,
            GenerationStep.Decoration step
    ) implements JsonProvider {
        public JsonElement toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("type", ForgeMod.ADD_FEATURES_BIOME_MODIFIER_TYPE.getId().toString());
            json.addProperty("biomes", "#" + tag.location());
            JsonArray featureJson = new JsonArray();
            features.stream().sorted(Comparator.comparing(o -> o.location().toString()))
                    .forEach(placedFeatureResourceKey -> featureJson.add(placedFeatureResourceKey.location().toString()));
            json.add("features", featureJson);
            json.addProperty("step", step.getName());
            return json;
        }
    }

}
