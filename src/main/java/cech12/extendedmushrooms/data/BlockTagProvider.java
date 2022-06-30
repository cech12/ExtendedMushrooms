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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.data.tags.BlockTagsProvider;
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
    protected void addTags() {
        Predicate<Block> extendedMushrooms = block -> ExtendedMushrooms.MOD_ID.equals(block.getRegistryName().getNamespace());

        //generate mod intern tags
        tag(ModTags.Blocks.MUSHROOM_BOOKSHELVES).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof BookshelfBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_BUTTONS_WOOD).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomWoodButtonBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_BUTTONS_WOOL).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomCapButtonBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_BUTTONS)
                .addTag(ModTags.Blocks.MUSHROOM_BUTTONS_WOOD)
                .addTag(ModTags.Blocks.MUSHROOM_BUTTONS_WOOL);
        tag(ModTags.Blocks.MUSHROOM_CARPETS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof WoolCarpetBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_CHESTS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof VariantChestBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_CHESTS_TRAPPED).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof VariantTrappedChestBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_DOORS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof DoorBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_FENCE_GATES).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof FenceGateBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_FENCES).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof FenceBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_LADDERS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof LadderBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_PLANKS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block.getRegistryName().getPath().contains("_planks"))
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOD).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomWoodPressurePlateBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomCapPressurePlateBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES)
                .addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOD)
                .addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL);
        tag(ModTags.Blocks.MUSHROOM_SIGNS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof MushroomStandingSignBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_SLABS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof SlabBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_STAIRS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof StairBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_TRAPDOORS).add(registry.stream().filter(extendedMushrooms)
                .filter(block -> block instanceof TrapDoorBlock)
                .sorted(Comparator.comparing(Block::getRegistryName))
                .toArray(Block[]::new));
        //generate block categories
        tag(ModTags.Blocks.MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL).add(Blocks.DIRT).add(Blocks.GRASS_BLOCK);

        //generate forge tags
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_BROWN).add(Blocks.BROWN_MUSHROOM_BLOCK);
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_RED).add(Blocks.RED_MUSHROOM_BLOCK);
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_GLOWSHROOM).add(ExtendedMushroomsBlocks.GLOWSHROOM_CAP);
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_LIME).add(ExtendedMushroomsBlocks.SLIME_FUNGUS_CAP);
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_ORANGE).add(ExtendedMushroomsBlocks.HONEY_FUNGUS_CAP);
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_PURPLE).add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CAP);
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_BROWN)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_RED)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_GLOWSHROOM)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_LIME)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_ORANGE)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_PURPLE);

        tag(ModTags.ForgeBlocks.MUSHROOM_STEMS_COLORLESS)
                .add(Blocks.MUSHROOM_STEM)
                .add(ExtendedMushroomsBlocks.STRIPPED_MUSHROOM_STEM);
        tag(ModTags.ForgeBlocks.MUSHROOM_STEMS_GLOWSHROOM)
                .add(ExtendedMushroomsBlocks.GLOWSHROOM_STEM)
                .add(ExtendedMushroomsBlocks.GLOWSHROOM_STEM_STRIPPED);
        tag(ModTags.ForgeBlocks.MUSHROOM_STEMS_GREEN)
                .add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_STEM)
                .add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_STEM_STRIPPED);
        tag(ModTags.ForgeBlocks.MUSHROOM_STEMS_ORANGE)
                .add(ExtendedMushroomsBlocks.HONEY_FUNGUS_STEM)
                .add(ExtendedMushroomsBlocks.HONEY_FUNGUS_STEM_STRIPPED);
        tag(ModTags.ForgeBlocks.MUSHROOM_STEMS)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS_COLORLESS)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS_GLOWSHROOM)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS_GREEN)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS_ORANGE);

        tag(ModTags.ForgeBlocks.MUSHROOMS_BROWN).add(Blocks.BROWN_MUSHROOM);
        tag(ModTags.ForgeBlocks.MUSHROOMS_RED).add(Blocks.RED_MUSHROOM);
        tag(ModTags.ForgeBlocks.MUSHROOMS_GLOWSHROOM).add(ExtendedMushroomsBlocks.GLOWSHROOM);
        tag(ModTags.ForgeBlocks.MUSHROOMS_LIME).add(ExtendedMushroomsBlocks.SLIME_FUNGUS);
        tag(ModTags.ForgeBlocks.MUSHROOMS_ORANGE).add(ExtendedMushroomsBlocks.HONEY_FUNGUS);
        tag(ModTags.ForgeBlocks.MUSHROOMS_PURPLE).add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM);
        tag(ModTags.ForgeBlocks.MUSHROOMS)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_BROWN)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_RED)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_GLOWSHROOM)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_LIME)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_ORANGE)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_PURPLE);
        tag(ModTags.Blocks.MUSHROOMS_EDIBLE) // add mod intern edible mushrooms
                .add(ExtendedMushroomsBlocks.GLOWSHROOM)
                .add(ExtendedMushroomsBlocks.HONEY_FUNGUS)
                .add(ExtendedMushroomsBlocks.SLIME_FUNGUS);
        tag(ModTags.ForgeBlocks.MUSHROOMS_EDIBLE)
                .add(Blocks.BROWN_MUSHROOM)
                .add(Blocks.RED_MUSHROOM)
                .addTag(ModTags.Blocks.MUSHROOMS_EDIBLE);
        tag(ModTags.ForgeBlocks.MUSHROOMS_JUMP_BOOSTING)
                .add(ExtendedMushroomsBlocks.SLIME_FUNGUS);
        tag(ModTags.ForgeBlocks.MUSHROOMS_POISONOUS)
                .add(ExtendedMushroomsBlocks.POISONOUS_MUSHROOM);
        tag(ModTags.ForgeBlocks.MUSHROOMS_SLOWING_DOWN)
                .add(ExtendedMushroomsBlocks.HONEY_FUNGUS);

        //generate standard forge tags
        tag(Tags.Blocks.CHESTS).addTag(ModTags.Blocks.MUSHROOM_CHESTS);
        tag(Tags.Blocks.CHESTS_TRAPPED).addTag(ModTags.Blocks.MUSHROOM_CHESTS_TRAPPED);
        tag(Tags.Blocks.CHESTS_WOODEN).addTags(ModTags.Blocks.MUSHROOM_CHESTS, ModTags.Blocks.MUSHROOM_CHESTS_TRAPPED);
        tag(Tags.Blocks.FENCE_GATES_WOODEN).addTag(ModTags.Blocks.MUSHROOM_FENCE_GATES);
        tag(Tags.Blocks.FENCE_GATES).addTag(ModTags.Blocks.MUSHROOM_FENCE_GATES);

        //generate minecraft tags
        tag(BlockTags.BUTTONS).addTag(ModTags.Blocks.MUSHROOM_BUTTONS);
        tag(BlockTags.CARPETS).addTag(ModTags.Blocks.MUSHROOM_CARPETS);
        tag(BlockTags.CLIMBABLE).addTag(ModTags.Blocks.MUSHROOM_LADDERS);
        tag(BlockTags.DOORS).addTag(ModTags.Blocks.MUSHROOM_DOORS);
        tag(BlockTags.FENCES).addTag(ModTags.Blocks.MUSHROOM_FENCES);
        tag(BlockTags.LOGS).addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS);
        tag(BlockTags.PLANKS).addTag(ModTags.Blocks.MUSHROOM_PLANKS);
        tag(BlockTags.SIGNS).addTag(ModTags.Blocks.MUSHROOM_SIGNS);
        tag(BlockTags.SLABS).addTag(ModTags.Blocks.MUSHROOM_SLABS);
        tag(BlockTags.SMALL_FLOWERS).add(ExtendedMushroomsBlocks.INFESTED_FLOWER);
        tag(BlockTags.STAIRS).addTag(ModTags.Blocks.MUSHROOM_STAIRS);
        tag(BlockTags.TRAPDOORS).addTag(ModTags.Blocks.MUSHROOM_TRAPDOORS);
        tag(BlockTags.WOODEN_BUTTONS).addTag(ModTags.Blocks.MUSHROOM_BUTTONS);
        tag(BlockTags.WOODEN_DOORS).addTag(ModTags.Blocks.MUSHROOM_DOORS);
        tag(BlockTags.WOODEN_FENCES).addTag(ModTags.Blocks.MUSHROOM_FENCES);
        tag(BlockTags.WOODEN_PRESSURE_PLATES).addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES);
        tag(BlockTags.WOODEN_SLABS).addTag(ModTags.Blocks.MUSHROOM_SLABS);
        tag(BlockTags.WOODEN_STAIRS).addTag(ModTags.Blocks.MUSHROOM_STAIRS);
        tag(BlockTags.WOODEN_TRAPDOORS).addTag(ModTags.Blocks.MUSHROOM_TRAPDOORS);
        tag(BlockTags.WOOL).addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS);

        //generate tags for mod compatibility
        tag(ModTags.OtherModBlocks.QUARK_LADDERS).addTag(ModTags.Blocks.MUSHROOM_LADDERS);
        tag(ModTags.OtherModBlocks.WOOLPLATES_WOOLPLATES).addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL);

    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms Block Tags";
    }

}
