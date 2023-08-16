package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.MushroomCapButtonBlock;
import cech12.extendedmushrooms.block.MushroomCapPressurePlateBlock;
import cech12.extendedmushrooms.block.MushroomCeilingHangingSignBlock;
import cech12.extendedmushrooms.block.MushroomStandingSignBlock;
import cech12.extendedmushrooms.block.MushroomWallHangingSignBlock;
import cech12.extendedmushrooms.block.MushroomWallSignBlock;
import cech12.extendedmushrooms.block.MushroomWoodButtonBlock;
import cech12.extendedmushrooms.block.MushroomWoodPressurePlateBlock;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends BlockTagsProvider {

    public BlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, ExtendedMushrooms.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider lookupProvider) {
        //generate mod intern tags
        tag(ModTags.Blocks.MUSHROOMS_GLOWSHROOM).add(ModBlocks.GLOWSHROOM.get());
        tag(ModTags.Blocks.MUSHROOMS_LIME).add(ModBlocks.PARROT_WAXCAP.get());
        tag(ModTags.Blocks.MUSHROOMS_ORANGE).add(ModBlocks.HONEY_WAXCAP.get());
        tag(ModTags.Blocks.MUSHROOMS_PURPLE); //TODO
        tag(ModTags.Blocks.MUSHROOMS_WHITE).add(ModBlocks.DEADLY_FIBRECAP.get());
        tag(ModTags.Blocks.MUSHROOMS)
                .addTag(ModTags.Blocks.MUSHROOMS_GLOWSHROOM)
                .addTag(ModTags.Blocks.MUSHROOMS_LIME)
                .addTag(ModTags.Blocks.MUSHROOMS_ORANGE)
                .addTag(ModTags.Blocks.MUSHROOMS_PURPLE)
                .addTag(ModTags.Blocks.MUSHROOMS_WHITE);
        tag(ModTags.Blocks.MUSHROOMS_EDIBLE)
                .add(ModBlocks.GLOWSHROOM.get())
                .add(ModBlocks.HONEY_WAXCAP.get())
                .add(ModBlocks.PARROT_WAXCAP.get());
        tag(ModTags.Blocks.MUSHROOMS_JUMP_BOOSTING)
                .add(ModBlocks.PARROT_WAXCAP.get());
        tag(ModTags.Blocks.MUSHROOMS_POISONOUS)
                .add(ModBlocks.DEADLY_FIBRECAP.get());
        tag(ModTags.Blocks.MUSHROOMS_SLOWING_DOWN)
                .add(ModBlocks.HONEY_WAXCAP.get());
        tag(ModTags.Blocks.MUSHROOM_BUTTONS_WOOD).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof MushroomWoodButtonBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_BUTTONS_WOOL).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof MushroomCapButtonBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_BUTTONS)
                .addTag(ModTags.Blocks.MUSHROOM_BUTTONS_WOOD)
                .addTag(ModTags.Blocks.MUSHROOM_BUTTONS_WOOL);
        tag(ModTags.Blocks.MUSHROOM_CARPETS).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof WoolCarpetBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_CEILING_HANGING_SIGNS).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof MushroomCeilingHangingSignBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_DOORS).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof DoorBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_FENCE_GATES).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof FenceGateBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_FENCES).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof FenceBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_PLANKS).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> ForgeRegistries.BLOCKS.getKey(block).getPath().contains("_planks"))
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOD).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof MushroomWoodPressurePlateBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof MushroomCapPressurePlateBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES)
                .addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOD)
                .addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL);
        tag(ModTags.Blocks.MUSHROOM_SLABS).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof SlabBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_STAIRS).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof StairBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_STANDING_SIGNS).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof MushroomStandingSignBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_TRAPDOORS).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof TrapDoorBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_WALL_HANGING_SIGNS).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof MushroomWallHangingSignBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        tag(ModTags.Blocks.MUSHROOM_WALL_SIGNS).add(ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)
                .filter(block -> block instanceof MushroomWallSignBlock)
                .sorted(Comparator.comparing(ForgeRegistries.BLOCKS::getKey))
                .toArray(Block[]::new));
        //generate block categories
        tag(ModTags.Blocks.MUSHROOM_GROWING_BLOCKS_LIGHTLEVEL).add(Blocks.DIRT).add(Blocks.GRASS_BLOCK);

        //generate forge tags
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_BROWN).add(Blocks.BROWN_MUSHROOM_BLOCK);
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_RED).add(Blocks.RED_MUSHROOM_BLOCK);
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_GLOWSHROOM).add(ModBlocks.GLOWSHROOM_CAP.get());
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_LIME).add(ModBlocks.PARROT_WAXCAP_CAP.get());
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_ORANGE).add(ModBlocks.HONEY_WAXCAP_CAP.get());
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_PURPLE); //TODO
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS_WHITE).add(ModBlocks.DEADLY_FIBRECAP_CAP.get());
        tag(ModTags.ForgeBlocks.MUSHROOM_CAPS)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_BROWN)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_RED)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_GLOWSHROOM)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_LIME)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_ORANGE)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_PURPLE)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS_WHITE);

        tag(ModTags.ForgeBlocks.MUSHROOM_STEMS_COLORLESS)
                .add(Blocks.MUSHROOM_STEM)
                .add(ModBlocks.STRIPPED_MUSHROOM_STEM.get());
        tag(ModTags.ForgeBlocks.MUSHROOM_STEMS_GLOWSHROOM)
                .add(ModBlocks.GLOWSHROOM_STEM.get())
                .add(ModBlocks.GLOWSHROOM_STEM_STRIPPED.get());
        tag(ModTags.ForgeBlocks.MUSHROOM_STEMS_GREEN)
                .add(ModBlocks.PARROT_WAXCAP_STEM.get())
                .add(ModBlocks.PARROT_WAXCAP_STEM_STRIPPED.get());
        tag(ModTags.ForgeBlocks.MUSHROOM_STEMS_ORANGE)
                .add(ModBlocks.HONEY_WAXCAP_STEM.get())
                .add(ModBlocks.HONEY_WAXCAP_STEM_STRIPPED.get());
        tag(ModTags.ForgeBlocks.MUSHROOM_STEMS)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS_COLORLESS)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS_GLOWSHROOM)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS_GREEN)
                .addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS_ORANGE);

        tag(ModTags.ForgeBlocks.MUSHROOMS_BROWN).add(Blocks.BROWN_MUSHROOM);
        tag(ModTags.ForgeBlocks.MUSHROOMS_RED).add(Blocks.RED_MUSHROOM);
        tag(ModTags.ForgeBlocks.MUSHROOMS_GLOWSHROOM).addTag(ModTags.Blocks.MUSHROOMS_GLOWSHROOM);
        tag(ModTags.ForgeBlocks.MUSHROOMS_LIME).addTag(ModTags.Blocks.MUSHROOMS_LIME);
        tag(ModTags.ForgeBlocks.MUSHROOMS_ORANGE).addTag(ModTags.Blocks.MUSHROOMS_ORANGE);
        tag(ModTags.ForgeBlocks.MUSHROOMS_PURPLE).addTag(ModTags.Blocks.MUSHROOMS_PURPLE);
        tag(ModTags.ForgeBlocks.MUSHROOMS_WHITE).addTag(ModTags.Blocks.MUSHROOMS_WHITE);
        tag(ModTags.ForgeBlocks.MUSHROOMS)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_BROWN)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_RED)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_GLOWSHROOM)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_LIME)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_ORANGE)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_PURPLE)
                .addTag(ModTags.ForgeBlocks.MUSHROOMS_WHITE);
        tag(ModTags.ForgeBlocks.MUSHROOMS_EDIBLE)
                .add(Blocks.BROWN_MUSHROOM)
                .add(Blocks.RED_MUSHROOM)
                .addTag(ModTags.Blocks.MUSHROOMS_EDIBLE);
        tag(ModTags.ForgeBlocks.MUSHROOMS_JUMP_BOOSTING).addTag(ModTags.Blocks.MUSHROOMS_JUMP_BOOSTING);
        tag(ModTags.ForgeBlocks.MUSHROOMS_POISONOUS).addTag(ModTags.Blocks.MUSHROOMS_POISONOUS);
        tag(ModTags.ForgeBlocks.MUSHROOMS_SLOWING_DOWN).addTag(ModTags.Blocks.MUSHROOMS_SLOWING_DOWN);

        tag(ModTags.ForgeBlocks.FUNGI)
                .add(Blocks.CRIMSON_FUNGUS)
                .add(Blocks.WARPED_FUNGUS)
                .addOptional(new ResourceLocation("byg", "death_cap"))
                .addOptional(new ResourceLocation("byg", "soul_shroom"))
                .addOptional(new ResourceLocation("byg", "sythian_fungus"));

        //generate standard forge tags
        tag(Tags.Blocks.FENCE_GATES_WOODEN).addTag(ModTags.Blocks.MUSHROOM_FENCE_GATES);

        //generate minecraft tags
        tag(BlockTags.BUTTONS).addTag(ModTags.Blocks.MUSHROOM_BUTTONS);
        tag(BlockTags.CEILING_HANGING_SIGNS).addTag(ModTags.Blocks.MUSHROOM_CEILING_HANGING_SIGNS);
        tag(BlockTags.DOORS).addTag(ModTags.Blocks.MUSHROOM_DOORS);
        tag(BlockTags.FENCE_GATES).addTag(ModTags.Blocks.MUSHROOM_FENCE_GATES);
        tag(BlockTags.LOGS_THAT_BURN).addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS);
        tag(BlockTags.OVERWORLD_NATURAL_LOGS).addTag(ModTags.ForgeBlocks.MUSHROOM_STEMS);
        tag(BlockTags.PLANKS).addTag(ModTags.Blocks.MUSHROOM_PLANKS);
        tag(BlockTags.REPLACEABLE).add(ModBlocks.INFESTED_GRASS.get());
        tag(BlockTags.REPLACEABLE_BY_TREES).add(ModBlocks.INFESTED_GRASS.get());
        tag(BlockTags.SLABS).addTag(ModTags.Blocks.MUSHROOM_SLABS);
        tag(BlockTags.SMALL_FLOWERS).add(ModBlocks.INFESTED_FLOWER.get());
        tag(BlockTags.STAIRS).addTag(ModTags.Blocks.MUSHROOM_STAIRS);
        tag(BlockTags.STANDING_SIGNS).addTag(ModTags.Blocks.MUSHROOM_STANDING_SIGNS);
        tag(BlockTags.SWORD_EFFICIENT).addTag(ModTags.Blocks.MUSHROOMS).add(ModBlocks.INFESTED_GRASS.get()); //small flowers already included
        tag(BlockTags.TRAPDOORS).addTag(ModTags.Blocks.MUSHROOM_TRAPDOORS);
        tag(BlockTags.WALL_HANGING_SIGNS).addTag(ModTags.Blocks.MUSHROOM_WALL_HANGING_SIGNS);
        tag(BlockTags.WALL_SIGNS).addTag(ModTags.Blocks.MUSHROOM_WALL_SIGNS);
        tag(BlockTags.WOODEN_BUTTONS).addTag(ModTags.Blocks.MUSHROOM_BUTTONS);
        tag(BlockTags.WOODEN_DOORS).addTag(ModTags.Blocks.MUSHROOM_DOORS);
        tag(BlockTags.WOODEN_FENCES).addTag(ModTags.Blocks.MUSHROOM_FENCES);
        tag(BlockTags.WOODEN_PRESSURE_PLATES).addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES);
        tag(BlockTags.WOODEN_SLABS).addTag(ModTags.Blocks.MUSHROOM_SLABS);
        tag(BlockTags.WOODEN_STAIRS).addTag(ModTags.Blocks.MUSHROOM_STAIRS);
        tag(BlockTags.WOODEN_TRAPDOORS).addTag(ModTags.Blocks.MUSHROOM_TRAPDOORS);
        tag(BlockTags.WOOL).addTag(ModTags.ForgeBlocks.MUSHROOM_CAPS);
        tag(BlockTags.WOOL_CARPETS).addTag(ModTags.Blocks.MUSHROOM_CARPETS);

        //generate tags for mod compatibility
        tag(ModTags.OtherModBlocks.WOOLPLATES_WOOLPLATES).addTag(ModTags.Blocks.MUSHROOM_PRESSURE_PLATES_WOOL);

    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms Block Tags";
    }

}
