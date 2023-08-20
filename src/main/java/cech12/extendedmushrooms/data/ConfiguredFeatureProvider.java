package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.block.EMMushroomBlock;
import cech12.extendedmushrooms.block.mushrooms.BigMushroom;
import cech12.extendedmushrooms.block.mushrooms.BrownMushroom;
import cech12.extendedmushrooms.block.mushrooms.MegaMushroom;
import cech12.extendedmushrooms.block.mushrooms.RedMushroom;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModFeatures;
import cech12.extendedmushrooms.item.MushroomType;
import cech12.extendedmushrooms.item.MushroomWoodType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ConfiguredFeatureProvider implements DataProvider {

    private final PackOutput packOutput;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public static final List<Mushroom> MUSHROOMS = new LinkedList<>();

    static {
        MUSHROOMS.add(new Mushroom("glowshroom", ModFeatures.PATCH_GLOWSHROOM_CONFIGURED, ModBlocks.GLOWSHROOM, 0.4F));
        MUSHROOMS.add(new Mushroom("deadly_fibrecap", ModFeatures.PATCH_DEADLY_FIBRECAP_CONFIGURED, ModBlocks.DEADLY_FIBRECAP, 0.5F));
    }

    public ConfiguredFeatureProvider(final PackOutput packOutput, final CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.packOutput = packOutput;
        this.lookupProvider = lookupProvider;
    }

    private static Path getPath(Path root, ResourceLocation id) {
        return root.resolve("data/" + id.getNamespace() + "/worldgen/configured_feature/" + id.getPath() + ".json");
    }


    private ResourceLocation getCap(RegistryObject<Block> blockRegistryObject) {
        return MushroomType.byItem(blockRegistryObject.get().asItem()).getCapBlockId();
    }

    private ResourceLocation getStem(RegistryObject<Block> blockRegistryObject) {
        return MushroomWoodType.byName(blockRegistryObject.getId().getPath()).getStemBlockId();
    }

    @Override
    @Nonnull
    public CompletableFuture<?> run(@Nonnull CachedOutput cache) {
        return this.lookupProvider.thenCompose(provider -> {
            List<JsonProvider> configuredFeatures = new ArrayList<>();

            configuredFeatures.add(new MushroomConfiguredFeature(new BrownMushroom().getMegaMushroomFeature().location(), MushroomType.byItem(Items.BROWN_MUSHROOM).getCapBlockId(), MushroomWoodType.byName(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(Blocks.BROWN_MUSHROOM_BLOCK)).getPath()).getStemBlockId()));
            configuredFeatures.add(new MushroomConfiguredFeature(new RedMushroom().getMegaMushroomFeature().location(), MushroomType.byItem(Items.RED_MUSHROOM).getCapBlockId(), MushroomWoodType.byName(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(Blocks.RED_MUSHROOM_BLOCK)).getPath()).getStemBlockId()));
            ModBlocks.BLOCKS.getEntries().stream()
                    .filter(blockRegistryObject -> blockRegistryObject.get() instanceof EMMushroomBlock)
                    .forEach(blockRegistryObject -> {
                        EMMushroomBlock mushroomBlock = (EMMushroomBlock) blockRegistryObject.get();
                        BigMushroom bigMushroom = mushroomBlock.getBigMushroom();
                        configuredFeatures.add(new MushroomConfiguredFeature(bigMushroom.getBigMushroomFeature().location(), getCap(blockRegistryObject), getStem(blockRegistryObject)));
                        if (bigMushroom instanceof MegaMushroom megaMushroom) {
                            configuredFeatures.add(new MushroomConfiguredFeature(megaMushroom.getMegaMushroomFeature().location(), getCap(blockRegistryObject), getStem(blockRegistryObject)));
                        }
                    });

            for (ConfiguredFeatureProvider.Mushroom mushroom : ConfiguredFeatureProvider.MUSHROOMS) {
                configuredFeatures.add(new SimpleRandomPatchFeature(mushroom.configuredFeature.location(), mushroom.block().getId(), (int) (96 * mushroom.spawnFactor()), 7, 3));
            }
            configuredFeatures.add(new SimpleRandomPatchFeature(ModFeatures.INFESTED_FLOWER_CONFIGURED.location(), ModBlocks.INFESTED_FLOWER.getId(), 16, 7, 3));
            configuredFeatures.add(new SimpleRandomPatchFeature(ModFeatures.INFESTED_GRASS_CONFIGURED.location(), ModBlocks.INFESTED_GRASS.getId(), 32, 7, 3));

            return CompletableFuture.allOf(configuredFeatures.stream().map(entry -> {
                Path path = getPath(this.packOutput.getOutputFolder(), entry.location());
                return DataProvider.saveStable(cache, entry.toJson(), path);
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    @Nonnull
    public String getName() {
        return "Extended Mushrooms configured feature provider";
    }

    /**
     * @param name name of this mushroom
     * @param block block of this mushrooms
     * @param spawnFactor spawn factor relative to the spawn of small brown mushrooms
     */
    public record Mushroom(String name, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, RegistryObject<Block> block, double spawnFactor) {}

    interface JsonProvider {
        ResourceLocation location();
        JsonElement toJson();
    }

    private record MushroomConfiguredFeature(
            ResourceLocation location,
            ResourceLocation capBlock,
            ResourceLocation stemBlock
    ) implements JsonProvider {
        @Override
        public JsonElement toJson() {
            JsonObject config = new JsonObject();
            config.add("cap_provider", new SimpleBlockStateProvider(capBlock).toJson());
            config.add("stem_provider", new SimpleBlockStateProvider(stemBlock).toJson());
            JsonObject json = new JsonObject();
            json.addProperty("type", location.toString());
            json.add("config", config);
            return json;
        }
    }

    private record SimpleBlockStateProvider(
            ResourceLocation location
    ) implements JsonProvider {
        @Override
        public JsonElement toJson() {
            JsonObject state = new JsonObject();
            state.addProperty("Name", location.toString());
            JsonObject json = new JsonObject();
            json.addProperty("type", Objects.requireNonNull(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES.getKey(BlockStateProviderType.SIMPLE_STATE_PROVIDER)).toString());
            json.add("state", state);
            return json;
        }
    }

    private record SimpleRandomPatchFeature(
            ResourceLocation location,
            ResourceLocation block,
            int tries,
            int xz_spread,
            int y_spread
    ) implements JsonProvider {
        @Override
        public JsonElement toJson() {
            JsonObject featureFeature = new JsonObject();
            featureFeature.addProperty("type", Objects.requireNonNull(ForgeRegistries.FEATURES.getKey(Feature.SIMPLE_BLOCK)).toString());
            JsonObject featureConfig = new JsonObject();
            featureConfig.add("to_place", new SimpleBlockStateProvider(block).toJson());
            featureFeature.add("config", featureConfig);

            JsonArray placements = new JsonArray();
            JsonObject placement = new JsonObject();
            placement.addProperty("type", Objects.requireNonNull(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE.getKey(PlacementModifierType.BLOCK_PREDICATE_FILTER)).toString());
            JsonObject placementPredicate = new JsonObject();
            placementPredicate.addProperty("type", Objects.requireNonNull(BuiltInRegistries.BLOCK_PREDICATE_TYPE.getKey(BlockPredicateType.MATCHING_BLOCKS)).toString());
            placementPredicate.addProperty("blocks", Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(Blocks.AIR)).toString());
            placement.add("predicate", placementPredicate);
            placements.add(placement);

            JsonObject feature = new JsonObject();
            feature.add("feature", featureFeature);
            feature.add("placement", placements);

            JsonObject config = new JsonObject();
            config.add("feature", feature);
            config.addProperty("tries", tries);
            config.addProperty("xz_spread", xz_spread);
            config.addProperty("y_spread", y_spread);

            JsonObject json = new JsonObject();
            json.addProperty("type", Objects.requireNonNull(ForgeRegistries.FEATURES.getKey(Feature.RANDOM_PATCH)).toString());
            json.add("config", config);
            return json;
        }
    }

}
