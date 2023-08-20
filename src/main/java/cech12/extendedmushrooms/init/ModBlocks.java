package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.*;
import cech12.extendedmushrooms.block.mushroomblocks.GlowshroomCap;
import cech12.extendedmushrooms.block.mushroomblocks.HoneyWaxcapCap;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomCapBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStemBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStrippedStemBlock;
import cech12.extendedmushrooms.block.mushroomblocks.DeadlyFibrecapBlock;
import cech12.extendedmushrooms.block.mushroomblocks.DeadlyFibrecapCap;
import cech12.extendedmushrooms.block.mushroomblocks.ParrotWaxcapCap;
import cech12.extendedmushrooms.block.mushrooms.Glowshroom;
import cech12.extendedmushrooms.block.mushrooms.HoneyWaxcap;
import cech12.extendedmushrooms.block.mushrooms.ParrotWaxcap;
import cech12.extendedmushrooms.item.MushroomType;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExtendedMushrooms.MOD_ID);

    public static final RegistryObject<Block> FAIRY_RING = registerBlock("fairy_ring", FairyRingBlock::new);

    public static final RegistryObject<Block> INFESTED_GRASS = registerBlockWithItem("infested_grass", () -> new InfestedGrassBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS).offsetType(Block.OffsetType.XYZ)));
    public static final RegistryObject<Block> INFESTED_FLOWER = registerBlockWithItem("infested_flower", () -> new InfestedFlowerBlock(() -> MobEffects.MOVEMENT_SLOWDOWN, 9, Block.Properties.of().mapColor(MapColor.PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> INFESTED_FLOWER_POTTED = registerBlock("infested_flower_potted", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, INFESTED_FLOWER, Block.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> MUSHROOM_BUTTON = registerBlockWithItem("mushroom_button", () -> new MushroomWoodButtonBlock(MushroomWoodType.MUSHROOM));
    public static final RegistryObject<Block> MUSHROOM_DOOR = registerBlockWithItem("mushroom_door", () -> new DoorBlock(Block.Properties.of().mapColor(MapColor.WOOL).strength(3.0F).sound(SoundType.WOOD), MushroomWoodType.MUSHROOM.getBlockSetType()));
    public static final RegistryObject<Block> MUSHROOM_FENCE = registerBlockWithItem("mushroom_fence", () -> new MushroomFenceBlock(Block.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MUSHROOM_FENCE_GATE = registerBlockWithItem("mushroom_fence_gate", () -> new MushroomFenceGateBlock(Block.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MUSHROOM_HANGING_SIGN = registerBlock("mushroom_hanging_sign", () -> new MushroomCeilingHangingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), MushroomWoodType.MUSHROOM.getWoodType()));
    public static final RegistryObject<Block> MUSHROOM_PLANKS = registerBlockWithItem("mushroom_planks", () -> new MushroomPlanksBlock(Block.Properties.of().mapColor(MapColor.WOOL).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MUSHROOM_PRESSURE_PLATE = registerBlockWithItem("mushroom_pressure_plate", () -> new MushroomWoodPressurePlateBlock(MushroomWoodType.MUSHROOM));
    public static final RegistryObject<Block> MUSHROOM_SLAB = registerBlockWithItem("mushroom_slab", () -> new MushroomSlabBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MUSHROOM_STAIRS = registerBlockWithItem("mushroom_stairs", () -> new MushroomStairsBlock(() -> MUSHROOM_PLANKS.get().defaultBlockState(), Block.Properties.copy(MUSHROOM_PLANKS.get())));
    public static final RegistryObject<Block> MUSHROOM_STANDING_SIGN = registerBlock("mushroom_sign", () -> new MushroomStandingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), MushroomWoodType.MUSHROOM.getWoodType()));
    public static final RegistryObject<Block> MUSHROOM_TRAPDOOR = registerBlockWithItem("mushroom_trapdoor", () -> new TrapDoorBlock(Block.Properties.of().mapColor(MapColor.WOOL).strength(3.0F).sound(SoundType.WOOD), MushroomWoodType.MUSHROOM.getBlockSetType()));
    public static final RegistryObject<Block> MUSHROOM_WALL_HANGING_SIGN = registerBlock("mushroom_wall_hanging_sign", () -> new MushroomWallHangingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lootFrom(MUSHROOM_HANGING_SIGN), MushroomWoodType.MUSHROOM.getWoodType()));
    public static final RegistryObject<Block> MUSHROOM_WALL_SIGN = registerBlock("mushroom_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lootFrom(MUSHROOM_STANDING_SIGN), MushroomWoodType.MUSHROOM.getWoodType()));
    public static final RegistryObject<Block> STRIPPED_MUSHROOM_STEM = registerBlockWithItem("stripped_mushroom_stem", () -> new MushroomStrippedStemBlock(MushroomWoodType.MUSHROOM, Block.Properties.of().mapColor(MapColor.WOOD).strength(0.2F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> BROWN_MUSHROOM_BUTTON = registerBlockWithItem("brown_mushroom_button", () -> new MushroomCapButtonBlock(MushroomWoodType.MUSHROOM));
    public static final RegistryObject<Block> BROWN_MUSHROOM_CARPET = registerBlockWithItem("brown_mushroom_carpet", () -> new MushroomCarpetBlock(DyeColor.BROWN, Block.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> BROWN_MUSHROOM_PRESSURE_PLATE = registerBlockWithItem("brown_mushroom_pressure_plate", () -> new MushroomCapPressurePlateBlock(MushroomWoodType.MUSHROOM));
    public static final RegistryObject<Block> RED_MUSHROOM_BUTTON = registerBlockWithItem("red_mushroom_button", () -> new MushroomCapButtonBlock(MushroomWoodType.MUSHROOM));
    public static final RegistryObject<Block> RED_MUSHROOM_CARPET = registerBlockWithItem("red_mushroom_carpet", () -> new MushroomCarpetBlock(DyeColor.RED, Block.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> RED_MUSHROOM_PRESSURE_PLATE = registerBlockWithItem("red_mushroom_pressure_plate", () -> new MushroomCapPressurePlateBlock(MushroomWoodType.MUSHROOM));

    public static final RegistryObject<Block> GLOWSHROOM = registerBlockWithItem("glowshroom", () -> new EMMushroomBlock(new Glowshroom(), Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS).lightLevel((state) -> 8).hasPostProcess((a, b, c)->true)));
    public static final RegistryObject<Block> GLOWSHROOM_POTTED = registerBlock("glowshroom_potted", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLOWSHROOM, Block.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).lightLevel((state) -> 8)));

    public static final RegistryObject<Block> GLOWSHROOM_CAP = registerBlockWithItem("glowshroom_cap", () -> new GlowshroomCap(MushroomType.GLOWSHROOM, Block.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.2F).sound(SoundType.WOOL).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_STEM = registerBlockWithItem("glowshroom_stem", () -> new MushroomStemBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.of().mapColor(MapColor.WOOD).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_STEM_STRIPPED = registerBlockWithItem("glowshroom_stem_stripped", () -> new MushroomStrippedStemBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.of().mapColor(MapColor.WOOD).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 8)));

    public static final RegistryObject<Block> GLOWSHROOM_BUTTON = registerBlockWithItem("glowshroom_button", () -> new MushroomWoodButtonBlock(MushroomWoodType.GLOWSHROOM, 8));
    public static final RegistryObject<Block> GLOWSHROOM_DOOR = registerBlockWithItem("glowshroom_door", () -> new DoorBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8), MushroomWoodType.GLOWSHROOM.getBlockSetType()));
    public static final RegistryObject<Block> GLOWSHROOM_FENCE = registerBlockWithItem("glowshroom_fence", () -> new MushroomFenceBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_FENCE_GATE = registerBlockWithItem("glowshroom_fence_gate", () -> new MushroomFenceGateBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_HANGING_SIGN = registerBlock("glowshroom_hanging_sign", () -> new MushroomCeilingHangingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lightLevel((state) -> 8), MushroomWoodType.GLOWSHROOM.getWoodType()));
    public static final RegistryObject<Block> GLOWSHROOM_PLANKS = registerBlockWithItem("glowshroom_planks", () -> new MushroomPlanksBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_PRESSURE_PLATE = registerBlockWithItem("glowshroom_pressure_plate", () -> new MushroomWoodPressurePlateBlock(MushroomWoodType.GLOWSHROOM, 8));
    public static final RegistryObject<Block> GLOWSHROOM_SLAB = registerBlockWithItem("glowshroom_slab", () -> new MushroomSlabBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_STAIRS = registerBlockWithItem("glowshroom_stairs", () -> new MushroomStairsBlock(() -> GLOWSHROOM_PLANKS.get().defaultBlockState(), Block.Properties.copy(GLOWSHROOM_PLANKS.get())));
    public static final RegistryObject<Block> GLOWSHROOM_STANDING_SIGN = registerBlock("glowshroom_sign", () -> new MushroomStandingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lightLevel((state) -> 8), MushroomWoodType.GLOWSHROOM.getWoodType()));
    public static final RegistryObject<Block> GLOWSHROOM_TRAPDOOR = registerBlockWithItem("glowshroom_trapdoor", () -> new TrapDoorBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8), MushroomWoodType.GLOWSHROOM.getBlockSetType()));
    public static final RegistryObject<Block> GLOWSHROOM_WALL_HANGING_SIGN = registerBlock("glowshroom_wall_hanging_sign", () -> new MushroomWallHangingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lootFrom(GLOWSHROOM_HANGING_SIGN).lightLevel((state) -> 8), MushroomWoodType.GLOWSHROOM.getWoodType()));
    public static final RegistryObject<Block> GLOWSHROOM_WALL_SIGN = registerBlock("glowshroom_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lootFrom(GLOWSHROOM_STANDING_SIGN).lightLevel((state) -> 8), MushroomWoodType.GLOWSHROOM.getWoodType()));
    public static final RegistryObject<Block> GLOWSHROOM_CAP_BUTTON = registerBlockWithItem("glowshroom_cap_button", () -> new MushroomCapButtonBlock(MushroomWoodType.GLOWSHROOM, 8));
    public static final RegistryObject<Block> GLOWSHROOM_CAP_CARPET = registerBlockWithItem("glowshroom_cap_carpet", () -> new MushroomCarpetBlock(DyeColor.BLUE, Block.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.1F).sound(SoundType.WOOL).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_CAP_PRESSURE_PLATE = registerBlockWithItem("glowshroom_cap_pressure_plate", () -> new MushroomCapPressurePlateBlock(MushroomWoodType.GLOWSHROOM, 8));

    public static final RegistryObject<Block> DEADLY_FIBRECAP = registerBlockWithItem("deadly_fibrecap", () -> new DeadlyFibrecapBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS).hasPostProcess((a, b, c)->true)));
    public static final RegistryObject<Block> DEADLY_FIBRECAP_POTTED = registerBlock("deadly_fibrecap_potted", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DEADLY_FIBRECAP, Block.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> DEADLY_FIBRECAP_CAP = registerBlockWithItem("deadly_fibrecap_cap", () -> new DeadlyFibrecapCap(MushroomType.DEADLY_FIBRECAP, Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.2F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> DEADLY_FIBRECAP_CAP_BUTTON = registerBlockWithItem("deadly_fibrecap_cap_button", () -> new MushroomCapButtonBlock(MushroomWoodType.PARROT_WAXCAP));
    public static final RegistryObject<Block> DEADLY_FIBRECAP_CAP_CARPET = registerBlockWithItem("deadly_fibrecap_cap_carpet", () -> new MushroomCarpetBlock(DyeColor.WHITE, Block.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> DEADLY_FIBRECAP_CAP_PRESSURE_PLATE = registerBlockWithItem("deadly_fibrecap_cap_pressure_plate", () -> new MushroomCapPressurePlateBlock(MushroomWoodType.PARROT_WAXCAP));

    public static final RegistryObject<Block> PARROT_WAXCAP = registerBlockWithItem("parrot_waxcap", () -> new EMMushroomBlock(new ParrotWaxcap(), Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.SLIME_BLOCK).hasPostProcess((a, b, c)->true)));
    public static final RegistryObject<Block> PARROT_WAXCAP_POTTED = registerBlock("parrot_waxcap_potted", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PARROT_WAXCAP, Block.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> PARROT_WAXCAP_CAP = registerBlockWithItem("parrot_waxcap_cap", () -> new ParrotWaxcapCap(MushroomType.PARROT_WAXCAP, Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(0.2F).noOcclusion().jumpFactor(1.5F).friction(0.8F).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> PARROT_WAXCAP_STEM = registerBlockWithItem("parrot_waxcap_stem", () -> new MushroomStemBlock(MushroomWoodType.PARROT_WAXCAP, Block.Properties.of().mapColor(MapColor.WOOD).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> PARROT_WAXCAP_STEM_STRIPPED = registerBlockWithItem("parrot_waxcap_stem_stripped", () -> new MushroomStrippedStemBlock(MushroomWoodType.PARROT_WAXCAP, Block.Properties.of().mapColor(MapColor.WOOD).strength(0.2F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> PARROT_WAXCAP_BUTTON = registerBlockWithItem("parrot_waxcap_button", () -> new MushroomWoodButtonBlock(MushroomWoodType.PARROT_WAXCAP));
    public static final RegistryObject<Block> PARROT_WAXCAP_DOOR = registerBlockWithItem("parrot_waxcap_door", () -> new DoorBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(3.0F).sound(SoundType.WOOD), MushroomWoodType.PARROT_WAXCAP.getBlockSetType()));
    public static final RegistryObject<Block> PARROT_WAXCAP_FENCE = registerBlockWithItem("parrot_waxcap_fence", () -> new MushroomFenceBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> PARROT_WAXCAP_FENCE_GATE = registerBlockWithItem("parrot_waxcap_fence_gate", () -> new MushroomFenceGateBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> PARROT_WAXCAP_HANGING_SIGN = registerBlock("parrot_waxcap_hanging_sign", () -> new MushroomCeilingHangingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), MushroomWoodType.PARROT_WAXCAP.getWoodType()));
    public static final RegistryObject<Block> PARROT_WAXCAP_PLANKS = registerBlockWithItem("parrot_waxcap_planks", () -> new MushroomPlanksBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> PARROT_WAXCAP_PRESSURE_PLATE = registerBlockWithItem("parrot_waxcap_pressure_plate", () -> new MushroomWoodPressurePlateBlock(MushroomWoodType.PARROT_WAXCAP));
    public static final RegistryObject<Block> PARROT_WAXCAP_SLAB = registerBlockWithItem("parrot_waxcap_slab", () -> new MushroomSlabBlock(Block.Properties.copy(PARROT_WAXCAP_PLANKS.get())));
    public static final RegistryObject<Block> PARROT_WAXCAP_STAIRS = registerBlockWithItem("parrot_waxcap_stairs", () -> new MushroomStairsBlock(() -> PARROT_WAXCAP_PLANKS.get().defaultBlockState(), Block.Properties.copy(PARROT_WAXCAP_PLANKS.get())));
    public static final RegistryObject<Block> PARROT_WAXCAP_STANDING_SIGN = registerBlock("parrot_waxcap_sign", () -> new MushroomStandingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), MushroomWoodType.PARROT_WAXCAP.getWoodType()));
    public static final RegistryObject<Block> PARROT_WAXCAP_TRAPDOOR = registerBlockWithItem("parrot_waxcap_trapdoor", () -> new TrapDoorBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(3.0F).sound(SoundType.WOOD), MushroomWoodType.PARROT_WAXCAP.getBlockSetType()));
    public static final RegistryObject<Block> PARROT_WAXCAP_WALL_HANGING_SIGN = registerBlock("parrot_waxcap_wall_hanging_sign", () -> new MushroomWallHangingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lootFrom(PARROT_WAXCAP_HANGING_SIGN), MushroomWoodType.PARROT_WAXCAP.getWoodType()));
    public static final RegistryObject<Block> PARROT_WAXCAP_WALL_SIGN = registerBlock("parrot_waxcap_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lootFrom(PARROT_WAXCAP_STANDING_SIGN), MushroomWoodType.PARROT_WAXCAP.getWoodType()));
    public static final RegistryObject<Block> PARROT_WAXCAP_CAP_BUTTON = registerBlockWithItem("parrot_waxcap_cap_button", () -> new MushroomCapButtonBlock(MushroomWoodType.PARROT_WAXCAP));
    public static final RegistryObject<Block> PARROT_WAXCAP_CAP_CARPET = registerBlockWithItem("parrot_waxcap_cap_carpet", () -> new MushroomCarpetBlock(DyeColor.LIME, Block.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> PARROT_WAXCAP_CAP_PRESSURE_PLATE = registerBlockWithItem("parrot_waxcap_cap_pressure_plate", () -> new MushroomCapPressurePlateBlock(MushroomWoodType.PARROT_WAXCAP));

    public static final RegistryObject<Block> HONEY_WAXCAP = registerBlockWithItem("honey_waxcap", () -> new EMMushroomBlock(new HoneyWaxcap(), Block.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.HONEY_BLOCK).hasPostProcess((a, b, c)->true)));
    public static final RegistryObject<Block> HONEY_WAXCAP_POTTED = registerBlock("honey_waxcap_potted", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HONEY_WAXCAP, Block.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> HONEY_WAXCAP_CAP = registerBlockWithItem("honey_waxcap_cap", () -> new HoneyWaxcapCap(MushroomType.HONEY_WAXCAP, Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.2F).noOcclusion().speedFactor(0.4F).jumpFactor(0.5F).sound(SoundType.HONEY_BLOCK)));
    public static final RegistryObject<Block> HONEY_WAXCAP_STEM = registerBlockWithItem("honey_waxcap_stem", () -> new MushroomStemBlock(MushroomWoodType.HONEY_WAXCAP, Block.Properties.of().mapColor(MapColor.WOOD).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_WAXCAP_STEM_STRIPPED = registerBlockWithItem("honey_waxcap_stem_stripped", () -> new MushroomStrippedStemBlock(MushroomWoodType.HONEY_WAXCAP, Block.Properties.of().mapColor(MapColor.WOOD).strength(0.2F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> HONEY_WAXCAP_BUTTON = registerBlockWithItem("honey_waxcap_button", () -> new MushroomWoodButtonBlock(MushroomWoodType.HONEY_WAXCAP));
    public static final RegistryObject<Block> HONEY_WAXCAP_DOOR = registerBlockWithItem("honey_waxcap_door", () -> new DoorBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(3.0F).sound(SoundType.WOOD), MushroomWoodType.HONEY_WAXCAP.getBlockSetType()));
    public static final RegistryObject<Block> HONEY_WAXCAP_FENCE = registerBlockWithItem("honey_waxcap_fence", () -> new MushroomFenceBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_WAXCAP_FENCE_GATE = registerBlockWithItem("honey_waxcap_fence_gate", () -> new MushroomFenceGateBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_WAXCAP_HANGING_SIGN = registerBlock("honey_waxcap_hanging_sign", () -> new MushroomCeilingHangingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), MushroomWoodType.HONEY_WAXCAP.getWoodType()));
    public static final RegistryObject<Block> HONEY_WAXCAP_PLANKS = registerBlockWithItem("honey_waxcap_planks", () -> new MushroomPlanksBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_WAXCAP_PRESSURE_PLATE = registerBlockWithItem("honey_waxcap_pressure_plate", () -> new MushroomWoodPressurePlateBlock(MushroomWoodType.HONEY_WAXCAP));
    public static final RegistryObject<Block> HONEY_WAXCAP_SLAB = registerBlockWithItem("honey_waxcap_slab", () -> new MushroomSlabBlock(Block.Properties.copy(HONEY_WAXCAP_PLANKS.get())));
    public static final RegistryObject<Block> HONEY_WAXCAP_STAIRS = registerBlockWithItem("honey_waxcap_stairs", () -> new MushroomStairsBlock(() -> HONEY_WAXCAP_PLANKS.get().defaultBlockState(), Block.Properties.copy(HONEY_WAXCAP_PLANKS.get())));
    public static final RegistryObject<Block> HONEY_WAXCAP_STANDING_SIGN = registerBlock("honey_waxcap_sign", () -> new MushroomStandingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), MushroomWoodType.HONEY_WAXCAP.getWoodType()));
    public static final RegistryObject<Block> HONEY_WAXCAP_TRAPDOOR = registerBlockWithItem("honey_waxcap_trapdoor", () -> new TrapDoorBlock(Block.Properties.of().mapColor(MapColor.WOOD).strength(3.0F).sound(SoundType.WOOD), MushroomWoodType.HONEY_WAXCAP.getBlockSetType()));
    public static final RegistryObject<Block> HONEY_WAXCAP_WALL_HANGING_SIGN = registerBlock("honey_waxcap_wall_hanging_sign", () -> new MushroomWallHangingSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lootFrom(HONEY_WAXCAP_HANGING_SIGN), MushroomWoodType.HONEY_WAXCAP.getWoodType()));
    public static final RegistryObject<Block> HONEY_WAXCAP_WALL_SIGN = registerBlock("honey_waxcap_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lootFrom(HONEY_WAXCAP_STANDING_SIGN), MushroomWoodType.HONEY_WAXCAP.getWoodType()));
    public static final RegistryObject<Block> HONEY_WAXCAP_CAP_BUTTON = registerBlockWithItem("honey_waxcap_cap_button", () -> new MushroomCapButtonBlock(MushroomWoodType.HONEY_WAXCAP));
    public static final RegistryObject<Block> HONEY_WAXCAP_CAP_CARPET = registerBlockWithItem("honey_waxcap_cap_carpet", () -> new MushroomCarpetBlock(DyeColor.ORANGE, Block.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> HONEY_WAXCAP_CAP_PRESSURE_PLATE = registerBlockWithItem("honey_waxcap_cap_pressure_plate", () -> new MushroomCapPressurePlateBlock(MushroomWoodType.HONEY_WAXCAP));

    private static final Map<RegistryObject<Block>, RegistryObject<Block>> BLOCK_STRIPPING_MAP = new HashMap<>();

    static {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(INFESTED_FLOWER.getId()), INFESTED_FLOWER_POTTED);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(GLOWSHROOM.getId()), GLOWSHROOM_POTTED);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(DEADLY_FIBRECAP.getId()), DEADLY_FIBRECAP_POTTED);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(PARROT_WAXCAP.getId()), PARROT_WAXCAP_POTTED);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(HONEY_WAXCAP.getId()), HONEY_WAXCAP_POTTED);
        BLOCK_STRIPPING_MAP.put(RegistryObject.create(ForgeRegistries.BLOCKS.getKey(Blocks.MUSHROOM_STEM), ForgeRegistries.BLOCKS), STRIPPED_MUSHROOM_STEM);
        BLOCK_STRIPPING_MAP.put(GLOWSHROOM_STEM, GLOWSHROOM_STEM_STRIPPED);
        BLOCK_STRIPPING_MAP.put(PARROT_WAXCAP_STEM, PARROT_WAXCAP_STEM_STRIPPED);
        BLOCK_STRIPPING_MAP.put(HONEY_WAXCAP_STEM, HONEY_WAXCAP_STEM_STRIPPED);
    }

    public static Block getStrippedBlock(Block block) {
        return BLOCK_STRIPPING_MAP.entrySet().stream().filter(entry -> entry.getKey().get().equals(block)).findFirst().map(entry -> entry.getValue().get()).orElse(null);
    }

    public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
        for (RegistryObject<Block> registryObject : BLOCKS.getEntries()) {
            Block block = registryObject.get();
            if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
                if (block instanceof MushroomWoodButtonBlock
                        || block instanceof MushroomCapButtonBlock
                        || block instanceof DoorBlock
                        || block instanceof MushroomFenceBlock
                        || block instanceof MushroomFenceGateBlock
                        || block instanceof MushroomPlanksBlock
                        || block instanceof MushroomWoodPressurePlateBlock
                        || block instanceof MushroomCapPressurePlateBlock
                        || block instanceof MushroomSlabBlock
                        || block instanceof MushroomStairsBlock
                        || block instanceof TrapDoorBlock
                        || block instanceof MushroomStemBlock
                        || block instanceof MushroomStrippedStemBlock
                ) {
                    event.accept(block);
                }
            }
            if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
                if (block instanceof MushroomCarpetBlock) {
                    event.accept(block);
                }
            }
            if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
                if (block instanceof EMMushroomBlock
                        || block instanceof MushroomCapBlock
                        || block instanceof MushroomStemBlock
                ) {
                    event.accept(block);
                }
            }
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(INFESTED_GRASS);
            event.accept(INFESTED_FLOWER);
        }
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<? extends Block> block) {
        return BLOCKS.register(name, block);
    }

    private static RegistryObject<Block> registerBlockWithItem(String name, Supplier<? extends Block> block) {
        RegistryObject<Block> registeredBlock = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

}
