package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.api.item.ExtendedMushroomsItems;
import cech12.extendedmushrooms.block.BookshelfBlock;
import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStemBlock;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.AlternativesLootEntry;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.IntClamper;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.Alternative;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import net.minecraft.world.storage.loot.conditions.Inverted;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;
import net.minecraft.world.storage.loot.conditions.TableBonus;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraft.world.storage.loot.functions.LimitCount;
import net.minecraft.world.storage.loot.functions.SetCount;
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
            }
        }

        //caps have other loot
        this.functionTable.put(ExtendedMushroomsBlocks.GLOWSHROOM_CAP, block -> dropCap(block, ExtendedMushroomsBlocks.GLOWSHROOM, ExtendedMushroomsItems.GLOWSTONE_CRUMBS, 0.5F, 0.6F, 0.7F, 0.8F, 0.9F));
        this.functionTable.put(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CAP, block -> dropCap(block, ExtendedMushroomsBlocks.POISONOUS_MUSHROOM));

        //only with shears
        this.functionTable.put(ExtendedMushroomsBlocks.INFESTED_GRASS, BlockLootProvider::dropOnlyWithShears);
    }

    private static Path getPath(Path root, ResourceLocation id) {
        return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
    }

    private static LootTable.Builder dropNothing(Block b) {
        return LootTable.builder();
    }

    private static LootTable.Builder dropItself(Block block) {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(block);
        LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(pool);
    }

    private static LootTable.Builder dropOnlyWithShears(Block block) {
        LootEntry.Builder<?> entry = AlternativesLootEntry.builder(ItemLootEntry.builder(block)
                .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().tag(ModTags.ForgeItems.SHEARS))));
        return LootTable.builder().addLootPool(LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry));
    }

    private static LootTable.Builder dropSlab(Block block) {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(block)
                .acceptFunction(SetCount.builder(ConstantRange.of(2))
                        .acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE))))
                .acceptFunction(ExplosionDecay.builder());
        return LootTable.builder().addLootPool(LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry));
    }

    private static LootTable.Builder dropDoor(Block block) {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(block)
                .acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(DoorBlock.HALF, DoubleBlockHalf.LOWER)));
        return LootTable.builder().addLootPool(LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry).acceptCondition(SurvivesExplosion.builder()));
    }

    private static LootTable.Builder dropStem(Block block) {
        ItemPredicate.Builder silkPredicate = ItemPredicate.Builder.create()
                .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)));
        LootEntry.Builder<?> entry = ItemLootEntry.builder(block);
        LootPool.Builder lootPool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
                .acceptCondition(MatchTool.builder(silkPredicate));
        return LootTable.builder().addLootPool(lootPool);
    }

    private static LootTable.Builder dropCap(Block block, IItemProvider mushroom) {
        return dropCap(block, mushroom, null, 0);
    }

    private static LootTable.Builder dropCap(Block block, IItemProvider mushroom, @Nullable IItemProvider additionalLoot, float... fortuneChances) {
        ItemPredicate.Builder silkPredicate = ItemPredicate.Builder.create()
                .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)));
        LootEntry.Builder<?> silkTouchAlternative = ItemLootEntry.builder(block)
                .acceptCondition(MatchTool.builder(silkPredicate));
        LootEntry.Builder<?> decayAlternative = ItemLootEntry.builder(mushroom)
                .acceptFunction(SetCount.builder(RandomValueRange.of(-6, 2)))
                .acceptFunction(LimitCount.func_215911_a(IntClamper.func_215848_a(0)))
                .acceptFunction(ExplosionDecay.builder());

        LootEntry.Builder<?> entry = AlternativesLootEntry.builder(silkTouchAlternative, decayAlternative);
        LootTable.Builder lootTable = LootTable.builder().addLootPool(LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry));

        //add additional loot if set
        if (additionalLoot != null) {
            LootEntry.Builder<?> additionalEntry = ItemLootEntry.builder(additionalLoot)
                    .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, fortuneChances))
                    .acceptFunction(ExplosionDecay.builder());
            LootPool.Builder additionalLootPool = LootPool.builder().name("additional").rolls(ConstantRange.of(1)).addEntry(additionalEntry)
                    .acceptCondition(Inverted.builder(Alternative.builder(MatchTool.builder(silkPredicate))));
            lootTable.addLootPool(additionalLootPool);
        }

        return lootTable;
    }

    private static LootTable.Builder dropBookshelf(Block block) {
        ItemPredicate.Builder silkPredicate = ItemPredicate.Builder.create()
                .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)));
        LootEntry.Builder<?> silkTouchAlternative = ItemLootEntry.builder(block)
                .acceptCondition(MatchTool.builder(silkPredicate));
        LootEntry.Builder<?> dropBooksAlternative = ItemLootEntry.builder(Items.BOOK)
                .acceptFunction(SetCount.builder(ConstantRange.of(3)))
                .acceptFunction(ExplosionDecay.builder());

        LootEntry.Builder<?> entry = AlternativesLootEntry.builder(silkTouchAlternative, dropBooksAlternative);
        return LootTable.builder().addLootPool(LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry));
    }

    @Override
    public void act(@Nonnull final DirectoryCache cache) throws IOException {
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
            IDataProvider.save(GSON, cache, LootTableManager.toJson(e.getValue().setParameterSet(LootParameterSets.BLOCK).build()), path);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms block loot tables";
    }

}
