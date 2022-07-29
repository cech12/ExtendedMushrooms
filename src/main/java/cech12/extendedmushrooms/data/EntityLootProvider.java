package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.item.MushroomType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EntityLootProvider implements DataProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;
    private final Map<MushroomType, Function<MushroomType, LootTable.Builder>> functionTable = new HashMap<>();

    public EntityLootProvider(final DataGenerator generator) {
        this.generator = generator;

        for (MushroomType mushroomType : MushroomType.values()) {
            functionTable.put(mushroomType, EntityLootProvider::createSheepTable);
        }
    }

    private static Path getSheepPath(Path root, ResourceLocation id) {
        return root.resolve("data/" + ExtendedMushrooms.MOD_ID + "/loot_tables/entities/sheep/" + id.getPath() + ".json");
    }

    private static LootTable.Builder createSheepTable(MushroomType mushroomType) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(mushroomType.getCapBlock()))).withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F)).add(LootTableReference.lootTableReference(EntityType.SHEEP.getDefaultLootTable())));
    }

    @Override
    public void run(@Nonnull final HashCache cache) throws IOException {
        Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

        for (MushroomType mushroomType : MushroomType.values()) {
            Function<MushroomType, LootTable.Builder> func = functionTable.get(mushroomType);
            tables.put(mushroomType.getItem().getRegistryName(), func.apply(mushroomType));
        }

        for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet()) {
            Path path = getSheepPath(generator.getOutputFolder(), e.getKey());
            DataProvider.save(GSON, cache, LootTables.serialize(e.getValue().setParamSet(LootContextParamSets.ENTITY).build()), path);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms entity loot tables";
    }

}
