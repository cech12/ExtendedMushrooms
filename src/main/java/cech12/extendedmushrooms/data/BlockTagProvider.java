package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.block.BookshelfBlock;
import cech12.extendedmushrooms.block.MushroomCapButtonBlock;
import cech12.extendedmushrooms.block.MushroomCapPressurePlateBlock;
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

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.function.Predicate;

import static cech12.extendedmushrooms.init.ModTags.Blocks.MUSHROOM_CHESTS_TRAPPED;

public class BlockTagProvider extends BlockTagsProvider {

    public BlockTagProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
        Predicate<Block> extendedMushrooms = block -> ExtendedMushrooms.MOD_ID.equals(block.getRegistryName().getNamespace());

        //generate mod intern tags
        getBuilder(ModTags.Blocks.MUSHROOM_BOOKSHELVES).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof BookshelfBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_BUTTONS_WOOD).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomWoodButtonBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_BUTTONS_WOOL).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomCapButtonBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_BUTTONS)
                .add(ModTags.Blocks.MUSHROOM_BUTTONS_WOOD)
                .add(ModTags.Blocks.MUSHROOM_BUTTONS_WOOL);
        getBuilder(ModTags.Blocks.MUSHROOM_CARPETS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof CarpetBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_CHESTS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof VariantChestBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(MUSHROOM_CHESTS_TRAPPED).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof VariantTrappedChestBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_DOORS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof DoorBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_FENCE_GATES).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof FenceGateBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_FENCES).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof FenceBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_LADDERS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof LadderBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_PLANKS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block.getRegistryName().getPath().contains("_planks"))
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOD).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomWoodPressurePlateBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomCapPressurePlateBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES)
                .add(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOD)
                .add(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL);
        getBuilder(ModTags.Blocks.MUSHROOM_SLABS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof SlabBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_STAIRS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof StairsBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        getBuilder(ModTags.Blocks.MUSHROOM_TRAPDOORS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof TrapDoorBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        //generate block categories
        getBuilder(ModTags.Blocks.MUSHROOM_GROWING_BLOCKS).add(Blocks.MYCELIUM).add(Blocks.PODZOL);
        getBuilder(ModTags.Blocks.MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL).add(Blocks.DIRT).add(Blocks.GRASS_BLOCK);

        //generate forge tags
        getBuilder(ModTags.ForgeBlocks.MUSHROOM_CAPS_BROWN).add(Blocks.BROWN_MUSHROOM_BLOCK);
        getBuilder(ModTags.ForgeBlocks.MUSHROOM_CAPS_RED).add(Blocks.RED_MUSHROOM_BLOCK);
        getBuilder(ModTags.ForgeBlocks.MUSHROOM_CAPS_GLOWSHROOM).add(ExtendedMushroomsBlocks.GLOWSHROOM_CAP);
        getBuilder(ModTags.ForgeBlocks.MUSHROOM_CAPS_PURPLE).add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CAP);
        getBuilder(ModTags.ForgeBlocks.MUSHROOM_CAPS)
                .add(ModTags.ForgeBlocks.MUSHROOM_CAPS_BROWN)
                .add(ModTags.ForgeBlocks.MUSHROOM_CAPS_RED)
                .add(ModTags.ForgeBlocks.MUSHROOM_CAPS_GLOWSHROOM)
                .add(ModTags.ForgeBlocks.MUSHROOM_CAPS_PURPLE);

        getBuilder(ModTags.ForgeBlocks.MUSHROOM_STEMS_COLORLESS)
                .add(Blocks.MUSHROOM_STEM)
                .add(ExtendedMushroomsBlocks.STRIPPED_MUSHROOM_STEM);
        getBuilder(ModTags.ForgeBlocks.MUSHROOM_STEMS_GLOWSHROOM)
                .add(ExtendedMushroomsBlocks.GLOWSHROOM_STEM)
                .add(ExtendedMushroomsBlocks.GLOWSHROOM_STEM_STRIPPED);
        getBuilder(ModTags.ForgeBlocks.MUSHROOM_STEMS_GREEN)
                .add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_STEM)
                .add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_STEM_STRIPPED);
        getBuilder(ModTags.ForgeBlocks.MUSHROOM_STEMS)
                .add(ModTags.ForgeBlocks.MUSHROOM_STEMS_COLORLESS)
                .add(ModTags.ForgeBlocks.MUSHROOM_STEMS_GLOWSHROOM)
                .add(ModTags.ForgeBlocks.MUSHROOM_STEMS_GREEN);

        getBuilder(ModTags.ForgeBlocks.MUSHROOMS_BROWN).add(Blocks.BROWN_MUSHROOM);
        getBuilder(ModTags.ForgeBlocks.MUSHROOMS_RED).add(Blocks.RED_MUSHROOM);
        getBuilder(ModTags.ForgeBlocks.MUSHROOMS_GLOWSHROOM).add(ExtendedMushroomsBlocks.GLOWSHROOM);
        getBuilder(ModTags.ForgeBlocks.MUSHROOMS_PURPLE).add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM);
        getBuilder(ModTags.ForgeBlocks.MUSHROOMS)
                .add(ModTags.ForgeBlocks.MUSHROOMS_BROWN)
                .add(ModTags.ForgeBlocks.MUSHROOMS_RED)
                .add(ModTags.ForgeBlocks.MUSHROOMS_GLOWSHROOM)
                .add(ModTags.ForgeBlocks.MUSHROOMS_PURPLE);
        getBuilder(ModTags.Blocks.MUSHROOMS_EDIBLE) // add mod intern edible mushrooms
                .add(ExtendedMushroomsBlocks.GLOWSHROOM);
        getBuilder(ModTags.ForgeBlocks.MUSHROOMS_EDIBLE)
                .add(Blocks.BROWN_MUSHROOM)
                .add(Blocks.RED_MUSHROOM)
                .add(ModTags.Blocks.MUSHROOMS_EDIBLE);
        getBuilder(ModTags.ForgeBlocks.MUSHROOMS_POISONOUS)
                .add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM);

        getBuilder(Tags.Blocks.CHESTS).add(ModTags.Blocks.MUSHROOM_CHESTS);
        getBuilder(Tags.Blocks.CHESTS_TRAPPED).add(MUSHROOM_CHESTS_TRAPPED);
        getBuilder(Tags.Blocks.CHESTS_WOODEN).add(ModTags.Blocks.MUSHROOM_CHESTS, MUSHROOM_CHESTS_TRAPPED);
        getBuilder(Tags.Blocks.FENCE_GATES_WOODEN).add(ModTags.Blocks.MUSHROOM_FENCE_GATES);
        getBuilder(Tags.Blocks.FENCE_GATES).add(ModTags.Blocks.MUSHROOM_FENCE_GATES);

        //generate minecraft tags
        getBuilder(BlockTags.BUTTONS).add(ModTags.Blocks.MUSHROOM_BUTTONS);
        getBuilder(BlockTags.CARPETS).add(ModTags.Blocks.MUSHROOM_CARPETS);
        getBuilder(BlockTags.DOORS).add(ModTags.Blocks.MUSHROOM_DOORS);
        getBuilder(BlockTags.FENCES).add(ModTags.Blocks.MUSHROOM_FENCES);
        getBuilder(BlockTags.LOGS).add(ModTags.ForgeBlocks.MUSHROOM_STEMS);
        getBuilder(BlockTags.PLANKS).add(ModTags.Blocks.MUSHROOM_PLANKS);
        getBuilder(BlockTags.SLABS).add(ModTags.Blocks.MUSHROOM_SLABS);
        getBuilder(BlockTags.SMALL_FLOWERS).add(ExtendedMushroomsBlocks.INFESTED_FLOWER);
        getBuilder(BlockTags.STAIRS).add(ModTags.Blocks.MUSHROOM_STAIRS);
        getBuilder(BlockTags.TRAPDOORS).add(ModTags.Blocks.MUSHROOM_TRAPDOORS);
        getBuilder(BlockTags.WOODEN_BUTTONS).add(ModTags.Blocks.MUSHROOM_BUTTONS);
        getBuilder(BlockTags.WOODEN_DOORS).add(ModTags.Blocks.MUSHROOM_DOORS);
        getBuilder(BlockTags.WOODEN_FENCES).add(ModTags.Blocks.MUSHROOM_FENCES);
        getBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES);
        getBuilder(BlockTags.WOODEN_SLABS).add(ModTags.Blocks.MUSHROOM_SLABS);
        getBuilder(BlockTags.WOODEN_STAIRS).add(ModTags.Blocks.MUSHROOM_STAIRS);
        getBuilder(BlockTags.WOODEN_TRAPDOORS).add(ModTags.Blocks.MUSHROOM_TRAPDOORS);
        getBuilder(BlockTags.WOOL).add(ModTags.ForgeBlocks.MUSHROOM_CAPS);

        //generate tags for mod compatibility
        getBuilder(ModTags.OtherModBlocks.QUARK_LADDERS).add(ModTags.Blocks.MUSHROOM_LADDERS);
        getBuilder(ModTags.OtherModBlocks.WOOLPLATES_WOOLPLATES).add(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL);

    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms Block Tags";
    }

}
