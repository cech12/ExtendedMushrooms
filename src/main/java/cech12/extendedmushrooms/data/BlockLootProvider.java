package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.block.FairyRingBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStemBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStrippedStemBlock;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModItems;
import com.ibm.icu.impl.Pair;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class BlockLootProvider implements DataProvider {
    private final PackOutput packOutput;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public BlockLootProvider(final PackOutput packOutput, final CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.packOutput = packOutput;
        this.lookupProvider = lookupProvider;
    }

    private static Path getPath(Path root, ResourceLocation id) {
        return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
    }

    private static LootTable.Builder dropNothing(Block b) {
        return LootTable.lootTable();
    }

    private static LootTable.Builder dropItself(Block block) {
        LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(block);
        LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry)
                .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(pool);
    }

    private static LootTable.Builder dropOnlyWithShears(Block block) {
        LootPoolEntryContainer.Builder<?> entry = AlternativesEntry.alternatives(LootItem.lootTableItem(block)
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))));
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry));
    }

    private static LootTable.Builder dropSlab(Block block) {
        LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(block)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))
                .apply(ApplyExplosionDecay.explosionDecay());
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry));
    }

    private static LootTable.Builder dropDoor(Block block) {
        LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(block)
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoorBlock.HALF, DoubleBlockHalf.LOWER)));
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry).when(ExplosionCondition.survivesExplosion()));
    }

    private static LootTable.Builder dropStem(Block block) {
        ItemPredicate.Builder silkPredicate = ItemPredicate.Builder.item()
                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)));
        LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(block);
        LootPool.Builder lootPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry)
                .when(MatchTool.toolMatches(silkPredicate));
        return LootTable.lootTable().withPool(lootPool);
    }

    @SafeVarargs
    private static LootTable.Builder dropCap(Block block, ItemLike mushroom, @Nullable Pair<ItemLike, float[]>... additionalLoot) {
        ItemPredicate.Builder silkPredicate = ItemPredicate.Builder.item()
                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)));
        LootPoolEntryContainer.Builder<?> silkTouchAlternative = LootItem.lootTableItem(block)
                .when(MatchTool.toolMatches(silkPredicate));
        LootPoolEntryContainer.Builder<?> decayAlternative = LootItem.lootTableItem(mushroom)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(-6, 2)))
                .apply(LimitCount.limitCount(IntRange.lowerBound(0)))
                .apply(ApplyExplosionDecay.explosionDecay());

        LootPoolEntryContainer.Builder<?> entry = AlternativesEntry.alternatives(silkTouchAlternative, decayAlternative);
        LootTable.Builder lootTable = LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry));

        //add additional loot if set
        if (additionalLoot != null) {
            for (Pair<ItemLike, float[]> pair : additionalLoot) {
                ItemLike lootItem = pair.first;
                float[] fortuneChances = pair.second;
                LootPoolEntryContainer.Builder<?> additionalEntry = LootItem.lootTableItem(lootItem)
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, fortuneChances))
                        .apply(ApplyExplosionDecay.explosionDecay());
                LootPool.Builder additionalLootPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(additionalEntry)
                        .when(InvertedLootItemCondition.invert(MatchTool.toolMatches(silkPredicate)));
                lootTable.withPool(additionalLootPool);
            }
        }

        return lootTable;
    }

    private static LootTable.Builder dropBookshelf(Block block) {
        ItemPredicate.Builder silkPredicate = ItemPredicate.Builder.item()
                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)));
        LootPoolEntryContainer.Builder<?> silkTouchAlternative = LootItem.lootTableItem(block)
                .when(MatchTool.toolMatches(silkPredicate));
        LootPoolEntryContainer.Builder<?> dropBooksAlternative = LootItem.lootTableItem(Items.BOOK)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3)))
                .apply(ApplyExplosionDecay.explosionDecay());

        LootPoolEntryContainer.Builder<?> entry = AlternativesEntry.alternatives(silkTouchAlternative, dropBooksAlternative);
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry));
    }

    @Nonnull
    @Override
    public CompletableFuture<?> run(@Nonnull final CachedOutput cache) {
        return this.lookupProvider.thenCompose(provider -> {
            Map<RegistryObject<Block>, Function<Block, LootTable.Builder>> tables = new HashMap<>();

            for (RegistryObject<Block> block : ModBlocks.BLOCKS.getEntries()) {
                if (block.get() instanceof SlabBlock) {
                    tables.put(block, BlockLootProvider::dropSlab);
                } else if (block.get() instanceof DoorBlock) {
                    tables.put(block, BlockLootProvider::dropDoor);
                } else if (block.get() instanceof MushroomStemBlock) {
                    tables.put(block, BlockLootProvider::dropStem);
                } else if (block.get() instanceof MushroomStrippedStemBlock) {
                    tables.put(block, BlockLootProvider::dropStem);
                } else if (block.get() instanceof FairyRingBlock) {
                    tables.put(block, BlockLootProvider::dropNothing);
                } else if (!(block.get() instanceof FlowerPotBlock)) {
                    //ignore potted flowers
                    tables.put(block, BlockLootProvider::dropItself);
                }
            }

            //caps have other loot
            tables.put(ModBlocks.GLOWSHROOM_CAP, block -> dropCap(block, ModBlocks.GLOWSHROOM.get(),
                    Pair.of(ModItems.GLOWSTONE_CRUMBS.get(), new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F})));
            tables.put(ModBlocks.POISONOUS_MUSHROOM_CAP, block -> dropCap(block, ModBlocks.POISONOUS_MUSHROOM.get()));
            tables.put(ModBlocks.SLIME_FUNGUS_CAP, block -> dropCap(block, ModBlocks.SLIME_FUNGUS.get(),
                    Pair.of(ModItems.SLIME_BLOB.get(), new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F})));
            tables.put(ModBlocks.HONEY_FUNGUS_CAP, block -> dropCap(block, ModBlocks.HONEY_FUNGUS.get(),
                    Pair.of(ModItems.HONEY_BLOB.get(), new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F}),
                    Pair.of(ModItems.HONEYCOMB_SHRED.get(), new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F})));

            //only with shears
            tables.put(ModBlocks.INFESTED_GRASS, BlockLootProvider::dropOnlyWithShears);

            return CompletableFuture.allOf(tables.entrySet().stream().map(entry -> {
                Path path = getPath(this.packOutput.getOutputFolder(), entry.getKey().getId());
                return DataProvider.saveStable(cache, LootDataType.TABLE.parser().toJsonTree(entry.getValue().apply(entry.getKey().get()).setParamSet(LootContextParamSets.BLOCK).build()), path);
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms block loot tables";
    }

}
