package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.api.item.ExtendedMushroomsItems;
import cech12.extendedmushrooms.block.BookshelfBlock;
import cech12.extendedmushrooms.block.FairyRingBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStemBlock;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.icu.impl.Pair;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.AlternativesLootEntry;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.IntClamper;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.Inverted;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.conditions.TableBonus;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.LimitCount;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BlockLootProvider implements IDataProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;
    private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();

    public BlockLootProvider(final DataGenerator generator) {
        this.generator = generator;

        for (Block block : ForgeRegistries.BLOCKS) {
            if (!ExtendedMushrooms.MOD_ID.equals(block.getRegistryName().getNamespace())) {
                continue;
            }

            if (block instanceof BookshelfBlock) {
                this.functionTable.put(block, BlockLootProvider::dropBookshelf);
            } else if (block instanceof SlabBlock) {
                this.functionTable.put(block, BlockLootProvider::dropSlab);
            } else if (block instanceof DoorBlock) {
                this.functionTable.put(block, BlockLootProvider::dropDoor);
            } else if (block instanceof MushroomStemBlock) {
                this.functionTable.put(block, BlockLootProvider::dropStem);
            } else if (block instanceof FairyRingBlock) {
                this.functionTable.put(block, BlockLootProvider::dropNothing);
            }
        }

        //caps have other loot
        this.functionTable.put(ExtendedMushroomsBlocks.GLOWSHROOM_CAP, block -> dropCap(block, ExtendedMushroomsBlocks.GLOWSHROOM,
                Pair.of(ExtendedMushroomsItems.GLOWSTONE_CRUMBS, new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F})));
        this.functionTable.put(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CAP, block -> dropCap(block, ExtendedMushroomsBlocks.POISONOUS_MUSHROOM));
        this.functionTable.put(ExtendedMushroomsBlocks.SLIME_FUNGUS_CAP, block -> dropCap(block, ExtendedMushroomsBlocks.SLIME_FUNGUS,
                Pair.of(ExtendedMushroomsItems.SLIME_BLOB, new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F})));
        this.functionTable.put(ExtendedMushroomsBlocks.HONEY_FUNGUS_CAP, block -> dropCap(block, ExtendedMushroomsBlocks.HONEY_FUNGUS,
                Pair.of(ExtendedMushroomsItems.HONEY_BLOB, new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F}),
                Pair.of(ExtendedMushroomsItems.HONEYCOMB_SHRED, new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F})));

        //only with shears
        this.functionTable.put(ExtendedMushroomsBlocks.INFESTED_GRASS, BlockLootProvider::dropOnlyWithShears);
    }

    private static Path getPath(Path root, ResourceLocation id) {
        return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
    }

    private static LootTable.Builder dropNothing(Block b) {
        return LootTable.lootTable();
    }

    private static LootTable.Builder dropItself(Block block) {
        LootEntry.Builder<?> entry = ItemLootEntry.lootTableItem(block);
        LootPool.Builder pool = LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry)
                .when(SurvivesExplosion.survivesExplosion());
        return LootTable.lootTable().withPool(pool);
    }

    private static LootTable.Builder dropOnlyWithShears(Block block) {
        LootEntry.Builder<?> entry = AlternativesLootEntry.alternatives(ItemLootEntry.lootTableItem(block)
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))));
        return LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry));
    }

    private static LootTable.Builder dropSlab(Block block) {
        LootEntry.Builder<?> entry = ItemLootEntry.lootTableItem(block)
                .apply(SetCount.setCount(ConstantRange.exactly(2))
                        .when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))
                .apply(ExplosionDecay.explosionDecay());
        return LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry));
    }

    private static LootTable.Builder dropDoor(Block block) {
        LootEntry.Builder<?> entry = ItemLootEntry.lootTableItem(block)
                .when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoorBlock.HALF, DoubleBlockHalf.LOWER)));
        return LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry).when(SurvivesExplosion.survivesExplosion()));
    }

    private static LootTable.Builder dropStem(Block block) {
        ItemPredicate.Builder silkPredicate = ItemPredicate.Builder.item()
                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)));
        LootEntry.Builder<?> entry = ItemLootEntry.lootTableItem(block);
        LootPool.Builder lootPool = LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry)
                .when(MatchTool.toolMatches(silkPredicate));
        return LootTable.lootTable().withPool(lootPool);
    }

    @SafeVarargs
    private static LootTable.Builder dropCap(Block block, IItemProvider mushroom, @Nullable Pair<IItemProvider, float[]>... additionalLoot) {
        ItemPredicate.Builder silkPredicate = ItemPredicate.Builder.item()
                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)));
        LootEntry.Builder<?> silkTouchAlternative = ItemLootEntry.lootTableItem(block)
                .when(MatchTool.toolMatches(silkPredicate));
        LootEntry.Builder<?> decayAlternative = ItemLootEntry.lootTableItem(mushroom)
                .apply(SetCount.setCount(RandomValueRange.between(-6, 2)))
                .apply(LimitCount.limitCount(IntClamper.lowerBound(0)))
                .apply(ExplosionDecay.explosionDecay());

        LootEntry.Builder<?> entry = AlternativesLootEntry.alternatives(silkTouchAlternative, decayAlternative);
        LootTable.Builder lootTable = LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry));

        //add additional loot if set
        if (additionalLoot != null) {
            for (Pair<IItemProvider, float[]> pair : additionalLoot) {
                IItemProvider lootItem = pair.first;
                float[] fortuneChances = pair.second;
                LootEntry.Builder<?> additionalEntry = ItemLootEntry.lootTableItem(lootItem)
                        .when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, fortuneChances))
                        .apply(ExplosionDecay.explosionDecay());
                LootPool.Builder additionalLootPool = LootPool.lootPool().name("additional").setRolls(ConstantRange.exactly(1)).add(additionalEntry)
                        .when(Inverted.invert(Alternative.alternative(MatchTool.toolMatches(silkPredicate))));
                lootTable.withPool(additionalLootPool);
            }
        }

        return lootTable;
    }

    private static LootTable.Builder dropBookshelf(Block block) {
        ItemPredicate.Builder silkPredicate = ItemPredicate.Builder.item()
                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)));
        LootEntry.Builder<?> silkTouchAlternative = ItemLootEntry.lootTableItem(block)
                .when(MatchTool.toolMatches(silkPredicate));
        LootEntry.Builder<?> dropBooksAlternative = ItemLootEntry.lootTableItem(Items.BOOK)
                .apply(SetCount.setCount(ConstantRange.exactly(3)))
                .apply(ExplosionDecay.explosionDecay());

        LootEntry.Builder<?> entry = AlternativesLootEntry.alternatives(silkTouchAlternative, dropBooksAlternative);
        return LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantRange.exactly(1)).add(entry));
    }

    @Override
    public void run(@Nonnull final DirectoryCache cache) throws IOException {
        Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

        for (Block block : ForgeRegistries.BLOCKS) {
            if (!ExtendedMushrooms.MOD_ID.equals(block.getRegistryName().getNamespace())) {
                continue;
            }
            if (block instanceof FlowerPotBlock) {
                //ignore potted flowers
                continue;
            }
            Function<Block, LootTable.Builder> func = functionTable.getOrDefault(block, BlockLootProvider::dropItself);
            tables.put(block.getRegistryName(), func.apply(block));
        }

        for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet()) {
            Path path = getPath(generator.getOutputFolder(), e.getKey());
            IDataProvider.save(GSON, cache, LootTableManager.serialize(e.getValue().setParamSet(LootParameterSets.BLOCK).build()), path);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms block loot tables";
    }

}
