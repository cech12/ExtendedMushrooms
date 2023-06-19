package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.item.MushroomType;
import com.google.gson.Gson;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class EntityLootProvider implements DataProvider {

    private static final Gson GSON = Deserializers.createLootTableSerializer().create();
    private final PackOutput packOutput;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final Map<MushroomType, Function<MushroomType, LootTable.Builder>> functionTable = new HashMap<>();

    public EntityLootProvider(final PackOutput packOutput, final CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.packOutput = packOutput;
        this.lookupProvider = lookupProvider;
    }

    private static Path getSheepPath(Path root, ResourceLocation id) {
        return root.resolve("data/" + ExtendedMushrooms.MOD_ID + "/loot_tables/entities/sheep/" + id.getPath() + ".json");
    }

    private static LootTable.Builder createSheepTable(MushroomType mushroomType) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(mushroomType.getCapBlock()))).withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F)).add(LootTableReference.lootTableReference(EntityType.SHEEP.getDefaultLootTable())));
    }

    @Nonnull
    @Override
    public CompletableFuture<?> run(@Nonnull final CachedOutput cache) {
        return this.lookupProvider.thenCompose(provider -> {
            Map<MushroomType, Function<MushroomType, LootTable.Builder>> tables = new HashMap<>();

            for (MushroomType mushroomType : MushroomType.values()) {
                tables.put(mushroomType, EntityLootProvider::createSheepTable);
            }

            return CompletableFuture.allOf(tables.entrySet().stream().map((entry) -> {
                Path path = getSheepPath(packOutput.getOutputFolder(), ForgeRegistries.ITEMS.getKey(entry.getKey().getItem()));
                return DataProvider.saveStable(cache, LootDataType.TABLE.parser().toJsonTree(entry.getValue().apply(entry.getKey()).setParamSet(LootContextParamSets.ENTITY).build()), path);
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms entity loot tables";
    }

}
