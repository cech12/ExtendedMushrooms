package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.block.BookshelfBlock;
import cech12.extendedmushrooms.block.MushroomCapButtonBlock;
import cech12.extendedmushrooms.block.MushroomCapPressurePlateBlock;
import cech12.extendedmushrooms.block.MushroomStandingSignBlock;
import cech12.extendedmushrooms.block.MushroomWoodButtonBlock;
import cech12.extendedmushrooms.block.MushroomWoodPressurePlateBlock;
import cech12.extendedmushrooms.block.VariantChestBlock;
import cech12.extendedmushrooms.block.VariantTrappedChestBlock;
import cech12.extendedmushrooms.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.function.Predicate;

public class BlockTagProvider extends BlockTagsProvider {

    public BlockTagProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, ExtendedMushrooms.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        Predicate<Block> extendedMushrooms = block -> ExtendedMushrooms.MOD_ID.equals(block.getRegistryName().getNamespace());

        //generate mod intern tags
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_BOOKSHELVES).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof BookshelfBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_BUTTONS_WOOD).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomWoodButtonBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_BUTTONS_WOOL).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomCapButtonBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_BUTTONS)
                .addTag(ModTags.Blocks.MUSHROOM_BUTTONS_WOOD)
                .addTag(ModTags.Blocks.MUSHROOM_BUTTONS_WOOL);
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_CARPETS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof CarpetBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_CHESTS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof VariantChestBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_CHESTS_TRAPPED).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof VariantTrappedChestBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_DOORS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof DoorBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_FENCE_GATES).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof FenceGateBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_FENCES).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof FenceBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_LADDERS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof LadderBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_PLANKS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block.getRegistryName().getPath().contains("_planks"))
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOD).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomWoodPressurePlateBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomCapPressurePlateBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES)
                .addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOD)
                .addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL);
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_SIGNS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomStandingSignBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_SLABS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof SlabBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_STAIRS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof StairsBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_TRAPDOORS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof TrapDoorBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        //generate block categories
        getOrCreateBuilder(ModTags.Blocks.MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL).add(Blocks.DIRT).add(Blocks.GRASS_BLOCK);

        //generate forge tags
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOM_CAPS_BROWN).add(Blocks.BROWN_MUSHROOM_BLOCK);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOM_CAPS_RED).add(Blocks.RED_MUSHROOM_BLOCK);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOM_CAPS_GLOWSHROOM).add(ExtendedMushroomsBlocks.GLOWSHROOM_CAP);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOM_CAPS_PURPLE).add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CAP);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOM_CAPS)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_BROWN)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_RED)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_GLOWSHROOM)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_PURPLE);

        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOM_STEMS_COLORLESS)
                .add(Blocks.MUSHROOM_STEM)
                .add(ExtendedMushroomsBlocks.STRIPPED_MUSHROOM_STEM);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOM_STEMS_GLOWSHROOM)
                .add(ExtendedMushroomsBlocks.GLOWSHROOM_STEM)
                .add(ExtendedMushroomsBlocks.GLOWSHROOM_STEM_STRIPPED);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOM_STEMS_GREEN)
                .add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_STEM)
                .add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_STEM_STRIPPED);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOM_STEMS)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS_COLORLESS)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS_GLOWSHROOM)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS_GREEN);

        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOMS_BROWN).add(Blocks.BROWN_MUSHROOM);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOMS_RED).add(Blocks.RED_MUSHROOM);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOMS_GLOWSHROOM).add(ExtendedMushroomsBlocks.GLOWSHROOM);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOMS_PURPLE).add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOMS)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_BROWN)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_RED)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_GLOWSHROOM)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_PURPLE);
        getOrCreateBuilder(ModTags.Blocks.MUSHROOMS_EDIBLE) // add mod intern edible mushrooms
                .add(ExtendedMushroomsBlocks.GLOWSHROOM);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOMS_EDIBLE)
                .add(Blocks.BROWN_MUSHROOM)
                .add(Blocks.RED_MUSHROOM)
                .addTag(ModTags.Blocks.MUSHROOMS_EDIBLE);
        getOrCreateBuilder(ModTags.ForgeBlocks.MUSHROOMS_POISONOUS)
                .add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM);

        //generate standard forge tags
        getOrCreateBuilder(Tags.Blocks.CHESTS).addTag(ModTags.Blocks.MUSHROOM_CHESTS);
        getOrCreateBuilder(Tags.Blocks.CHESTS_TRAPPED).addTag(ModTags.Blocks.MUSHROOM_CHESTS_TRAPPED);
        getOrCreateBuilder(Tags.Blocks.CHESTS_WOODEN).addTags(ModTags.Blocks.MUSHROOM_CHESTS, ModTags.Blocks.MUSHROOM_CHESTS_TRAPPED);
        getOrCreateBuilder(Tags.Blocks.FENCE_GATES_WOODEN).addTag(ModTags.Blocks.MUSHROOM_FENCE_GATES);
        getOrCreateBuilder(Tags.Blocks.FENCE_GATES).addTag(ModTags.Blocks.MUSHROOM_FENCE_GATES);

        //generate minecraft tags
        getOrCreateBuilder(BlockTags.BUTTONS).addTag(ModTags.Blocks.MUSHROOM_BUTTONS);
        getOrCreateBuilder(BlockTags.CARPETS).addTag(ModTags.Blocks.MUSHROOM_CARPETS);
        getOrCreateBuilder(BlockTags.CLIMBABLE).addTag(ModTags.Blocks.MUSHROOM_LADDERS);
        getOrCreateBuilder(BlockTags.DOORS).addTag(ModTags.Blocks.MUSHROOM_DOORS);
        getOrCreateBuilder(BlockTags.FENCES).addTag(ModTags.Blocks.MUSHROOM_FENCES);
        getOrCreateBuilder(BlockTags.LOGS).addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS);
        getOrCreateBuilder(BlockTags.PLANKS).addTag(ModTags.Blocks.MUSHROOM_PLANKS);
        getOrCreateBuilder(BlockTags.SIGNS).addTag(ModTags.Blocks.MUSHROOM_SIGNS);
        getOrCreateBuilder(BlockTags.SLABS).addTag(ModTags.Blocks.MUSHROOM_SLABS);
        getOrCreateBuilder(BlockTags.SMALL_FLOWERS).add(ExtendedMushroomsBlocks.INFESTED_FLOWER);
        getOrCreateBuilder(BlockTags.STAIRS).addTag(ModTags.Blocks.MUSHROOM_STAIRS);
        getOrCreateBuilder(BlockTags.TRAPDOORS).addTag(ModTags.Blocks.MUSHROOM_TRAPDOORS);
        getOrCreateBuilder(BlockTags.WOODEN_BUTTONS).addTag(ModTags.Blocks.MUSHROOM_BUTTONS);
        getOrCreateBuilder(BlockTags.WOODEN_DOORS).addTag(ModTags.Blocks.MUSHROOM_DOORS);
        getOrCreateBuilder(BlockTags.WOODEN_FENCES).addTag(ModTags.Blocks.MUSHROOM_FENCES);
        getOrCreateBuilder(BlockTags.WOODEN_PRESSURE_PLATES).addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES);
        getOrCreateBuilder(BlockTags.WOODEN_SLABS).addTag(ModTags.Blocks.MUSHROOM_SLABS);
        getOrCreateBuilder(BlockTags.WOODEN_STAIRS).addTag(ModTags.Blocks.MUSHROOM_STAIRS);
        getOrCreateBuilder(BlockTags.WOODEN_TRAPDOORS).addTag(ModTags.Blocks.MUSHROOM_TRAPDOORS);
        getOrCreateBuilder(BlockTags.WOOL).addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS);

        //generate tags for mod compatibility
        getOrCreateBuilder(ModTags.OtherModBlocks.QUARK_LADDERS).addTag(ModTags.Blocks.MUSHROOM_LADDERS);
        getOrCreateBuilder(ModTags.OtherModBlocks.WOOLPLATES_WOOLPLATES).addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL);

    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms Block Tags";
    }

}
