package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.BookshelfBlock;
import cech12.extendedmushrooms.block.FairyRingBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStemBlock;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModItems;
import com.ibm.icu.impl.Pair;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.CachedOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
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
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BlockLootProvider implements DataProvider {
    private final DataGenerator generator;
    private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();

    public BlockLootProvider(final DataGenerator generator) {
        this.generator = generator;

        for (Block block : ForgeRegistries.BLOCKS) {
            if (!ExtendedMushrooms.MOD_ID.equals(ForgeRegistries.BLOCKS.getKey(block).getNamespace())) {
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
        this.functionTable.put(ModBlocks.GLOWSHROOM_CAP.get(), block -> dropCap(block, ModBlocks.GLOWSHROOM.get(),
                Pair.of(ModItems.GLOWSTONE_CRUMBS.get(), new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F})));
        this.functionTable.put(ModBlocks.POISONOUS_MUSHROOM_CAP.get(), block -> dropCap(block, ModBlocks.POISONOUS_MUSHROOM.get()));
        this.functionTable.put(ModBlocks.SLIME_FUNGUS_CAP.get(), block -> dropCap(block, ModBlocks.SLIME_FUNGUS.get(),
                Pair.of(ModItems.SLIME_BLOB.get(), new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F})));
        this.functionTable.put(ModBlocks.HONEY_FUNGUS_CAP.get(), block -> dropCap(block, ModBlocks.HONEY_FUNGUS.get(),
                Pair.of(ModItems.HONEY_BLOB.get(), new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F}),
                Pair.of(ModItems.HONEYCOMB_SHRED.get(), new float[] {0.5F, 0.6F, 0.7F, 0.8F, 0.9F})));

        //only with shears
        this.functionTable.put(ModBlocks.INFESTED_GRASS.get(), BlockLootProvider::dropOnlyWithShears);
    }

    private static Path getPath(Path root, ResourceLocation id) {
        return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
    }

    private static LootTable.Builder dropNothing(Block b) {
        return LootTable.lootTable();
    }

    private static LootTable.Builder dropItself(Block block) {
        LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(block);
        LootPool.Builder pool = LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1)).add(entry)
                .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(pool);
    }

    private static LootTable.Builder dropOnlyWithShears(Block block) {
        LootPoolEntryContainer.Builder<?> entry = AlternativesEntry.alternatives(LootItem.lootTableItem(block)
                .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS))));
        return LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1)).add(entry));
    }

    private static LootTable.Builder dropSlab(Block block) {
        LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(block)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))
                .apply(ApplyExplosionDecay.explosionDecay());
        return LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1)).add(entry));
    }

    private static LootTable.Builder dropDoor(Block block) {
        LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(block)
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoorBlock.HALF, DoubleBlockHalf.LOWER)));
        return LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1)).add(entry).when(ExplosionCondition.survivesExplosion()));
    }

    private static LootTable.Builder dropStem(Block block) {
        ItemPredicate.Builder silkPredicate = ItemPredicate.Builder.item()
                .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)));
        LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(block);
        LootPool.Builder lootPool = LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1)).add(entry)
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
        LootTable.Builder lootTable = LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1)).add(entry));

        //add additional loot if set
        if (additionalLoot != null) {
            for (Pair<ItemLike, float[]> pair : additionalLoot) {
                ItemLike lootItem = pair.first;
                float[] fortuneChances = pair.second;
                LootPoolEntryContainer.Builder<?> additionalEntry = LootItem.lootTableItem(lootItem)
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, fortuneChances))
                        .apply(ApplyExplosionDecay.explosionDecay());
                LootPool.Builder additionalLootPool = LootPool.lootPool().name("additional").setRolls(ConstantValue.exactly(1)).add(additionalEntry)
                        .when(InvertedLootItemCondition.invert(AlternativeLootItemCondition.alternative(MatchTool.toolMatches(silkPredicate))));
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
        return LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1)).add(entry));
    }

    @Override
    public void run(@Nonnull final CachedOutput cache) throws IOException {
        Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

        for (Block block : ForgeRegistries.BLOCKS) {
            if (!ExtendedMushrooms.MOD_ID.equals(ForgeRegistries.BLOCKS.getKey(block).getNamespace())) {
                continue;
            }
            if (block instanceof FlowerPotBlock) {
                //ignore potted flowers
                continue;
            }
            Function<Block, LootTable.Builder> func = functionTable.getOrDefault(block, BlockLootProvider::dropItself);
            tables.put(ForgeRegistries.BLOCKS.getKey(block), func.apply(block));
        }

        for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet()) {
            Path path = getPath(generator.getOutputFolder(), e.getKey());
            DataProvider.saveStable(cache, LootTables.serialize(e.getValue().setParamSet(LootContextParamSets.BLOCK).build()), path);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms block loot tables";
    }

}
