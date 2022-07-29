package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.item.MushroomType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EntityLootProvider implements IDataProvider {

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
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(mushroomType.getCapBlock()))).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                .add(TableLootEntry.lootTableReference(EntityType.SHEEP.getDefaultLootTable())));
    }

    @Override
    public void run(@Nonnull final DirectoryCache cache) throws IOException {
        Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

        for (MushroomType mushroomType : MushroomType.values()) {
            Function<MushroomType, LootTable.Builder> func = functionTable.get(mushroomType);
            tables.put(mushroomType.getItem().getRegistryName(), func.apply(mushroomType));
        }

        for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet()) {
            Path path = getSheepPath(generator.getOutputFolder(), e.getKey());
            IDataProvider.save(GSON, cache, LootTableManager.serialize(e.getValue().setParamSet(LootParameterSets.ENTITY).build()), path);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms entity loot tables";
    }

}
