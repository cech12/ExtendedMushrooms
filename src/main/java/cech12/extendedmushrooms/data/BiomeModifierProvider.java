package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModEntityTypes;
import cech12.extendedmushrooms.init.ModFeatures;
import cech12.extendedmushrooms.init.ModTags;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class BiomeModifierProvider {

    public static void generateBiomeModifiers(GatherDataEvent event) {
        final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
        Map<ResourceLocation, BiomeModifier> modifierMap = new HashMap<>();

        HolderSet<Biome> isTaigaTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).orElseThrow(), ModTags.Biomes.HAS_MUSHROOMS);
        HolderSet<Biome> isSwampTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).orElseThrow(), ModTags.Biomes.HAS_MUSHROOMS);
        HolderSet<Biome> isNetherTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).orElseThrow(), ModTags.Biomes.HAS_MUSHROOMS);
        HolderSet<Biome> isMushroomTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).orElseThrow(), Tags.Biomes.IS_MUSHROOM);
        HolderSet<Biome> hasMushroomsTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).orElseThrow(), ModTags.Biomes.HAS_MUSHROOMS);

        HolderSet<PlacedFeature> infestedFlowerFeature = HolderSet.direct(placedFeature(ops, ModFeatures.INFESTED_FLOWER_PLACED.getId()));
        HolderSet<PlacedFeature> infestedGrassFeature = HolderSet.direct(placedFeature(ops, ModFeatures.INFESTED_GRASS_PLACED.getId()));

        HolderSet<PlacedFeature> normalMushroomFeatures = HolderSet.direct(
                ModFeatures.MUSHROOM_PLACED_FEATURES.entrySet().stream().filter(mapEntry -> mapEntry.getKey().endsWith("normal"))
                        .map(mapEntry -> placedFeature(ops, mapEntry.getValue().getId())).toList());
        HolderSet<PlacedFeature> taigaMushroomFeatures = HolderSet.direct(
                ModFeatures.MUSHROOM_PLACED_FEATURES.entrySet().stream().filter(mapEntry -> mapEntry.getKey().endsWith("taiga"))
                        .map(mapEntry -> placedFeature(ops, mapEntry.getValue().getId())).toList());
        HolderSet<PlacedFeature> mushroomIslandMushroomFeatures = HolderSet.direct(
                ModFeatures.MUSHROOM_PLACED_FEATURES.entrySet().stream().filter(mapEntry -> mapEntry.getKey().endsWith("mushroom_island"))
                        .map(mapEntry -> placedFeature(ops, mapEntry.getValue().getId())).toList());
        HolderSet<PlacedFeature> swampMushroomFeatures = HolderSet.direct(
                ModFeatures.MUSHROOM_PLACED_FEATURES.entrySet().stream().filter(mapEntry -> mapEntry.getKey().endsWith("swamp"))
                        .map(mapEntry -> placedFeature(ops, mapEntry.getValue().getId())).toList());
        HolderSet<PlacedFeature> netherMushroomFeatures = HolderSet.direct(
                ModFeatures.MUSHROOM_PLACED_FEATURES.entrySet().stream().filter(mapEntry -> mapEntry.getKey().endsWith("nether"))
                        .map(mapEntry -> placedFeature(ops, mapEntry.getValue().getId())).toList());

        HolderSet<PlacedFeature> bigMushroomFeatures = HolderSet.direct(
                ModFeatures.BIG_MUSHROOM_PLACED_FEATURES.values().stream().map(regObj -> placedFeature(ops, regObj.getId())).toList());
        HolderSet<PlacedFeature> megaMushroomFeatures = HolderSet.direct(
                ModFeatures.MEGA_MUSHROOM_PLACED_FEATURES.values().stream().map(regObj -> placedFeature(ops, regObj.getId())).toList());

        //entities
        modifierMap.put(prefix("mushroom_sheep_spawn"),
                ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(isMushroomTag, new MobSpawnSettings.SpawnerData(ModEntityTypes.MUSHROOM_SHEEP.get(), 8, 4, 8)));

        //infested flower & infested grass
        modifierMap.put(prefix("infested_flower_addition"),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(isMushroomTag, infestedFlowerFeature, GenerationStep.Decoration.VEGETAL_DECORATION));
        modifierMap.put(prefix("infested_grass_addition"),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(isMushroomTag, infestedGrassFeature, GenerationStep.Decoration.VEGETAL_DECORATION));

        //small mushrooms
        modifierMap.put(prefix("mushroom_addition_normal"),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(hasMushroomsTag, normalMushroomFeatures, GenerationStep.Decoration.VEGETAL_DECORATION));
        modifierMap.put(prefix("mushroom_addition_taiga"),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(isTaigaTag, taigaMushroomFeatures, GenerationStep.Decoration.VEGETAL_DECORATION));
        modifierMap.put(prefix("mushroom_addition_mushroom_island"),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(isMushroomTag, mushroomIslandMushroomFeatures, GenerationStep.Decoration.VEGETAL_DECORATION));
        modifierMap.put(prefix("mushroom_addition_swamp"),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(isSwampTag, swampMushroomFeatures, GenerationStep.Decoration.VEGETAL_DECORATION));
        modifierMap.put(prefix("mushroom_addition_nether"),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(isNetherTag, netherMushroomFeatures, GenerationStep.Decoration.VEGETAL_DECORATION));

        //big & mega mushrooms
        modifierMap.put(prefix("big_mushroom_addition"),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(isMushroomTag, bigMushroomFeatures, GenerationStep.Decoration.VEGETAL_DECORATION));
        modifierMap.put(prefix("mega_mushroom_addition"),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(isMushroomTag, megaMushroomFeatures, GenerationStep.Decoration.VEGETAL_DECORATION));

        event.getGenerator().addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(event.getGenerator(), event.getExistingFileHelper(), ExtendedMushrooms.MOD_ID,
                ops, ForgeRegistries.Keys.BIOME_MODIFIERS, modifierMap));
    }

    private static ResourceLocation prefix(String name) {
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, name);
    }

    private static Holder<PlacedFeature> placedFeature(RegistryOps<JsonElement> ops, ResourceLocation id) {
        return ops.registry(Registry.PLACED_FEATURE_REGISTRY).get().getOrCreateHolderOrThrow(ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, id));
    }

}
