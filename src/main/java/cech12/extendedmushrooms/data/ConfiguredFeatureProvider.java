package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.block.EMMushroomBlock;
import cech12.extendedmushrooms.block.mushrooms.BigMushroom;
import cech12.extendedmushrooms.block.mushrooms.BrownMushroom;
import cech12.extendedmushrooms.block.mushrooms.MegaMushroom;
import cech12.extendedmushrooms.block.mushrooms.RedMushroom;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.item.MushroomType;
import cech12.extendedmushrooms.item.MushroomWoodType;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ConfiguredFeatureProvider implements DataProvider {

    private final PackOutput packOutput;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;

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

            Map<ResourceLocation, Tuple<ResourceLocation, ResourceLocation>> configuredFeatures = new HashMap<>();

            configuredFeatures.put(new BrownMushroom().getMegaMushroomFeature().location(), new Tuple<>(MushroomType.byItem(Items.BROWN_MUSHROOM).getCapBlockId(), MushroomWoodType.byName(ForgeRegistries.BLOCKS.getKey(Blocks.BROWN_MUSHROOM_BLOCK).getPath()).getStemBlockId()));
            configuredFeatures.put(new RedMushroom().getMegaMushroomFeature().location(), new Tuple<>(MushroomType.byItem(Items.RED_MUSHROOM).getCapBlockId(), MushroomWoodType.byName(ForgeRegistries.BLOCKS.getKey(Blocks.RED_MUSHROOM_BLOCK).getPath()).getStemBlockId()));

            ModBlocks.BLOCKS.getEntries().stream()
                    .filter(blockRegistryObject -> blockRegistryObject.get() instanceof EMMushroomBlock)
                    .forEach(blockRegistryObject -> {
                        EMMushroomBlock mushroomBlock = (EMMushroomBlock) blockRegistryObject.get();
                        BigMushroom bigMushroom = mushroomBlock.getBigMushroom();
                        configuredFeatures.put(bigMushroom.getBigMushroomFeature().location(), new Tuple<>(getCap(blockRegistryObject), getStem(blockRegistryObject)));
                        if (bigMushroom instanceof MegaMushroom megaMushroom) {
                            configuredFeatures.put(megaMushroom.getMegaMushroomFeature().location(), new Tuple<>(getCap(blockRegistryObject), getStem(blockRegistryObject)));
                        }
                    });

            return CompletableFuture.allOf(configuredFeatures.entrySet().stream().map(entry -> {
                Path path = getPath(this.packOutput.getOutputFolder(), entry.getKey());
                return DataProvider.saveStable(cache, new MushroomConfiguredFeature(entry.getKey(), entry.getValue().getA(), entry.getValue().getB()).toJson(), path);
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    @Nonnull
    public String getName() {
        return "Extended Mushrooms configured feature provider";
    }

    private record MushroomConfiguredFeature(
            ResourceLocation feature,
            ResourceLocation capBlock,
            ResourceLocation stemBlock
    ) {
        JsonElement toJson() {
            JsonObject config = new JsonObject();
            config.add("cap_provider", new SimpleBlockStateProvider(capBlock).toJson());
            config.add("stem_provider", new SimpleBlockStateProvider(stemBlock).toJson());
            JsonObject json = new JsonObject();
            json.addProperty("type", feature.toString());
            json.add("config", config);
            return json;
        }
    }

    private record SimpleBlockStateProvider(
            ResourceLocation location
    ) {
        JsonElement toJson() {
            JsonObject json = new JsonObject();
            JsonObject state = new JsonObject();
            state.addProperty("Name", location.toString());
            json.addProperty("type", Objects.requireNonNull(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES.getKey(BlockStateProviderType.SIMPLE_STATE_PROVIDER)).toString());
            json.add("state", state);
            return json;
        }
    }

}
