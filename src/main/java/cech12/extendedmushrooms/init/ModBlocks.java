package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.*;
import cech12.extendedmushrooms.block.mushroomblocks.GlowshroomCap;
import cech12.extendedmushrooms.block.mushroomblocks.HoneyFungusCap;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStemBlock;
import cech12.extendedmushrooms.block.mushroomblocks.PoisonousMushroomBlock;
import cech12.extendedmushrooms.block.mushroomblocks.PoisonousMushroomCap;
import cech12.extendedmushrooms.block.mushroomblocks.SlimeFungusCap;
import cech12.extendedmushrooms.block.mushrooms.Glowshroom;
import cech12.extendedmushrooms.block.mushrooms.HoneyFungus;
import cech12.extendedmushrooms.block.mushrooms.SlimeFungus;
import cech12.extendedmushrooms.compat.ModCompat;
import cech12.extendedmushrooms.item.MushroomType;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks.*;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModBlocks {

    public static Map<Block, Block> BLOCK_STRIPPING_MAP = new HashMap<>();

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExtendedMushrooms.MOD_ID);

    //TODO sign textures only work when the signs are registered like this. Change the other registrations to the same structure
    public static final RegistryObject<MushroomStandingSignBlock> MUSHROOM_STANDING_SIGN = BLOCKS.register("mushroom_sign",() -> new MushroomStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.MUSHROOM.getWoodType()));
    public static final RegistryObject<MushroomWallSignBlock> MUSHROOM_WALL_SIGN = BLOCKS.register("mushroom_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.MUSHROOM.getWoodType()));
    public static final RegistryObject<MushroomStandingSignBlock> GLOWSHROOM_STANDING_SIGN = BLOCKS.register("glowshroom_sign", () -> new MushroomStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).lightLevel((state) -> 8), MushroomWoodType.GLOWSHROOM.getWoodType()));
    public static final RegistryObject<MushroomWallSignBlock> GLOWSHROOM_WALL_SIGN = BLOCKS.register("glowshroom_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).lightLevel((state) -> 8), MushroomWoodType.GLOWSHROOM.getWoodType()));
    public static final RegistryObject<MushroomStandingSignBlock> POISONOUS_MUSHROOM_STANDING_SIGN = BLOCKS.register("poisonous_mushroom_sign", () -> new MushroomStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.POISONOUS_MUSHROOM.getWoodType()));
    public static final RegistryObject<MushroomWallSignBlock> POISONOUS_MUSHROOM_WALL_SIGN = BLOCKS.register("poisonous_mushroom_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.POISONOUS_MUSHROOM.getWoodType()));
    public static final RegistryObject<MushroomStandingSignBlock> HONEY_FUNGUS_STANDING_SIGN = BLOCKS.register("honey_fungus_sign", () -> new MushroomStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.HONEY_FUNGUS.getWoodType()));
    public static final RegistryObject<MushroomWallSignBlock> HONEY_FUNGUS_WALL_SIGN = BLOCKS.register("honey_fungus_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.HONEY_FUNGUS.getWoodType()));

    public static void registerBlocks(IEventBus bus) {
        BLOCKS.register(bus);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        FAIRY_RING = registerBlock("fairy_ring", new FairyRingBlock());

        INFESTED_GRASS = registerBlock("infested_grass", ItemGroup.TAB_DECORATIONS, new InfestedGrassBlock(Block.Properties.of(Material.REPLACEABLE_PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS)));
        INFESTED_FLOWER = registerBlock("infested_flower", ItemGroup.TAB_DECORATIONS, new InfestedFlowerBlock(Effects.MOVEMENT_SLOWDOWN, 9, Block.Properties.of(Material.PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS)));
        INFESTED_FLOWER_POTTED = registerBlock("infested_flower_potted", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> INFESTED_FLOWER, Block.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion()));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(INFESTED_FLOWER.getRegistryName()), () -> INFESTED_FLOWER_POTTED);

        MUSHROOM_BOOKSHELF = registerCompatBlock("mushroom_bookshelf", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVariantBookshelvesModLoaded(), new BookshelfBlock(Block.Properties.copy(Blocks.BOOKSHELF)));
        MUSHROOM_BUTTON = registerBlock("mushroom_button", ItemGroup.TAB_REDSTONE, new MushroomWoodButtonBlock());
        MUSHROOM_CHEST = registerCompatBlock("mushroom_chest", ItemGroup.TAB_DECORATIONS, ModCompat.isVariantChestsModLoaded(), new VariantChestBlock(MushroomWoodType.MUSHROOM, Block.Properties.copy(Blocks.CHEST)));
        MUSHROOM_CHEST_TRAPPED = registerCompatBlock("mushroom_chest_trapped", ItemGroup.TAB_REDSTONE, ModCompat.isVariantTrappedChestsModLoaded(), new VariantTrappedChestBlock(MushroomWoodType.MUSHROOM, Block.Properties.copy(Blocks.TRAPPED_CHEST)));
        MUSHROOM_DOOR = registerBlock("mushroom_door", ItemGroup.TAB_REDSTONE, new DoorBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(3.0F).sound(SoundType.WOOD)));
        MUSHROOM_FENCE = registerBlock("mushroom_fence", ItemGroup.TAB_DECORATIONS, new MushroomFenceBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_FENCE_GATE = registerBlock("mushroom_fence_gate", ItemGroup.TAB_REDSTONE, new MushroomFenceGateBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_LADDER = registerCompatBlock("mushroom_ladder", ItemGroup.TAB_DECORATIONS, ModCompat.isVariantLaddersModLoaded(), new LadderBlock(Block.Properties.copy(Blocks.LADDER)));
        MUSHROOM_PLANKS = registerBlock("mushroom_planks", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomPlanksBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(0.2F).sound(SoundType.WOOD)));
        MUSHROOM_PRESSURE_PLATE = registerBlock("mushroom_pressure_plate", ItemGroup.TAB_REDSTONE, new MushroomWoodPressurePlateBlock());
        MUSHROOM_SLAB = registerBlock("mushroom_slab", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomSlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_STAIRS = registerBlock("mushroom_stairs", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomStairsBlock(() -> MUSHROOM_PLANKS.defaultBlockState(), Block.Properties.copy(MUSHROOM_PLANKS)));
        MUSHROOM_TRAPDOOR = registerBlock("mushroom_trapdoor", ItemGroup.TAB_REDSTONE, new MushroomTrapdoorBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(3.0F).sound(SoundType.WOOD)));
        STRIPPED_MUSHROOM_STEM = registerBlock("stripped_mushroom_stem", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomStemBlock(MushroomWoodType.MUSHROOM, Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(0.2F).sound(SoundType.WOOD)));
        BLOCK_STRIPPING_MAP.put(Blocks.MUSHROOM_STEM, STRIPPED_MUSHROOM_STEM);
        MUSHROOM_VERTICAL_PLANKS = registerCompatBlock("mushroom_vertical_planks", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVerticalPlanksModLoaded(), new VerticalPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
        MUSHROOM_VERTICAL_SLAB = registerCompatBlock("mushroom_vertical_slab", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVerticalSlabsModLoaded(), new VerticalSlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

        BROWN_MUSHROOM_BUTTON = registerBlock("brown_mushroom_button", ItemGroup.TAB_REDSTONE, new MushroomCapButtonBlock());
        BROWN_MUSHROOM_CARPET = registerBlock("brown_mushroom_carpet", ItemGroup.TAB_DECORATIONS, new MushroomCarpetBlock(DyeColor.BROWN, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BROWN).strength(0.1F).sound(SoundType.WOOL)));
        BROWN_MUSHROOM_PRESSURE_PLATE = registerBlock("brown_mushroom_pressure_plate", ItemGroup.TAB_REDSTONE, new MushroomCapPressurePlateBlock());
        RED_MUSHROOM_BUTTON = registerBlock("red_mushroom_button", ItemGroup.TAB_REDSTONE, new MushroomCapButtonBlock());
        RED_MUSHROOM_CARPET = registerBlock("red_mushroom_carpet", ItemGroup.TAB_DECORATIONS, new MushroomCarpetBlock(DyeColor.RED, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_RED).strength(0.1F).sound(SoundType.WOOL)));
        RED_MUSHROOM_PRESSURE_PLATE = registerBlock("red_mushroom_pressure_plate", ItemGroup.TAB_REDSTONE, new MushroomCapPressurePlateBlock());

        GLOWSHROOM = registerBlock("glowshroom", ItemGroup.TAB_DECORATIONS, new EMMushroomBlock(new Glowshroom(), Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS).lightLevel((state) -> 8).hasPostProcess((a,b,c)->true)));
        GLOWSHROOM_POTTED = registerBlock("glowshroom_potted", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> GLOWSHROOM, Block.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion().lightLevel((state) -> 8)));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(GLOWSHROOM.getRegistryName()), () -> GLOWSHROOM_POTTED);
        GLOWSHROOM_CAP = registerBlock("glowshroom_cap", ItemGroup.TAB_DECORATIONS, new GlowshroomCap(MushroomType.GLOWSHROOM, Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BLUE).strength(0.2F).sound(SoundType.WOOL).lightLevel((state) -> 8)));
        GLOWSHROOM_STEM = registerBlock("glowshroom_stem", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomStemBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
        GLOWSHROOM_STEM_STRIPPED = registerBlock("glowshroom_stem_stripped", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomStemBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
        BLOCK_STRIPPING_MAP.put(GLOWSHROOM_STEM, GLOWSHROOM_STEM_STRIPPED);
        GLOWSHROOM_BOOKSHELF = registerCompatBlock("glowshroom_bookshelf", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVariantBookshelvesModLoaded(), new BookshelfBlock(Block.Properties.copy(Blocks.BOOKSHELF).lightLevel((state) -> 8)));
        GLOWSHROOM_BUTTON = registerBlock("glowshroom_button", ItemGroup.TAB_REDSTONE, new MushroomWoodButtonBlock(8));
        GLOWSHROOM_CHEST = registerCompatBlock("glowshroom_chest", ItemGroup.TAB_DECORATIONS, ModCompat.isVariantChestsModLoaded(), new VariantChestBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.copy(Blocks.CHEST).lightLevel((state) -> 8)));
        GLOWSHROOM_CHEST_TRAPPED = registerCompatBlock("glowshroom_chest_trapped", ItemGroup.TAB_REDSTONE, ModCompat.isVariantTrappedChestsModLoaded(), new VariantTrappedChestBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.copy(Blocks.TRAPPED_CHEST).lightLevel((state) -> 8)));
        GLOWSHROOM_DOOR = registerBlock("glowshroom_door", ItemGroup.TAB_REDSTONE, new DoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
        GLOWSHROOM_FENCE = registerBlock("glowshroom_fence", ItemGroup.TAB_DECORATIONS, new MushroomFenceBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
        GLOWSHROOM_FENCE_GATE = registerBlock("glowshroom_fence_gate", ItemGroup.TAB_REDSTONE, new MushroomFenceGateBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
        GLOWSHROOM_LADDER = registerCompatBlock("glowshroom_ladder", ItemGroup.TAB_DECORATIONS, ModCompat.isVariantLaddersModLoaded(), new LadderBlock(Block.Properties.copy(Blocks.LADDER).lightLevel((state) -> 8)));
        GLOWSHROOM_PLANKS = registerBlock("glowshroom_planks", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
        GLOWSHROOM_PRESSURE_PLATE = registerBlock("glowshroom_pressure_plate", ItemGroup.TAB_REDSTONE, new MushroomWoodPressurePlateBlock(8));
        GLOWSHROOM_SLAB = registerBlock("glowshroom_slab", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomSlabBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
        GLOWSHROOM_STAIRS = registerBlock("glowshroom_stairs", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomStairsBlock(() -> GLOWSHROOM_PLANKS.defaultBlockState(), Block.Properties.copy(GLOWSHROOM_PLANKS)));
        GLOWSHROOM_TRAPDOOR = registerBlock("glowshroom_trapdoor", ItemGroup.TAB_REDSTONE, new MushroomTrapdoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
        GLOWSHROOM_VERTICAL_PLANKS = registerCompatBlock("glowshroom_vertical_planks", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVerticalPlanksModLoaded(), new VerticalPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
        GLOWSHROOM_VERTICAL_SLAB = registerCompatBlock("glowshroom_vertical_slab", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVerticalSlabsModLoaded(), new VerticalSlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
        GLOWSHROOM_CAP_BUTTON = registerBlock("glowshroom_cap_button", ItemGroup.TAB_REDSTONE, new MushroomCapButtonBlock(8));
        GLOWSHROOM_CAP_CARPET = registerBlock("glowshroom_cap_carpet", ItemGroup.TAB_DECORATIONS, new MushroomCarpetBlock(DyeColor.BLUE, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLUE).strength(0.1F).sound(SoundType.WOOL).lightLevel((state) -> 8)));
        GLOWSHROOM_CAP_PRESSURE_PLATE = registerBlock("glowshroom_cap_pressure_plate", ItemGroup.TAB_REDSTONE, new MushroomCapPressurePlateBlock(8));

        POISONOUS_MUSHROOM = registerBlock("poisonous_mushroom", ItemGroup.TAB_DECORATIONS, new PoisonousMushroomBlock(Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS).hasPostProcess((a,b,c)->true)));
        POISONOUS_MUSHROOM_POTTED = registerBlock("poisonous_mushroom_potted", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> POISONOUS_MUSHROOM, Block.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion()));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(POISONOUS_MUSHROOM.getRegistryName()), () -> POISONOUS_MUSHROOM_POTTED);
        POISONOUS_MUSHROOM_CAP = registerBlock("poisonous_mushroom_cap", ItemGroup.TAB_DECORATIONS, new PoisonousMushroomCap(MushroomType.POISONOUS_MUSHROOM, Block.Properties.of(Material.WOOD, MaterialColor.COLOR_PURPLE).strength(0.2F).sound(SoundType.WOOL)));
        POISONOUS_MUSHROOM_STEM = registerBlock("poisonous_mushroom_stem", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomStemBlock(MushroomWoodType.POISONOUS_MUSHROOM, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_STEM_STRIPPED = registerBlock("poisonous_mushroom_stem_stripped", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomStemBlock(MushroomWoodType.POISONOUS_MUSHROOM, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
        BLOCK_STRIPPING_MAP.put(POISONOUS_MUSHROOM_STEM, POISONOUS_MUSHROOM_STEM_STRIPPED);
        POISONOUS_MUSHROOM_BOOKSHELF = registerCompatBlock("poisonous_mushroom_bookshelf", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVariantBookshelvesModLoaded(), new BookshelfBlock(Block.Properties.copy(Blocks.BOOKSHELF)));
        POISONOUS_MUSHROOM_BUTTON = registerBlock("poisonous_mushroom_button", ItemGroup.TAB_REDSTONE, new MushroomWoodButtonBlock());
        POISONOUS_MUSHROOM_CHEST = registerCompatBlock("poisonous_mushroom_chest", ItemGroup.TAB_DECORATIONS, ModCompat.isVariantChestsModLoaded(), new VariantChestBlock(MushroomWoodType.POISONOUS_MUSHROOM, Block.Properties.copy(Blocks.CHEST)));
        POISONOUS_MUSHROOM_CHEST_TRAPPED = registerCompatBlock("poisonous_mushroom_chest_trapped", ItemGroup.TAB_REDSTONE, ModCompat.isVariantTrappedChestsModLoaded(), new VariantTrappedChestBlock(MushroomWoodType.POISONOUS_MUSHROOM, Block.Properties.copy(Blocks.TRAPPED_CHEST)));
        POISONOUS_MUSHROOM_DOOR = registerBlock("poisonous_mushroom_door", ItemGroup.TAB_REDSTONE, new DoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_FENCE = registerBlock("poisonous_mushroom_fence", ItemGroup.TAB_DECORATIONS, new MushroomFenceBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_FENCE_GATE = registerBlock("poisonous_mushroom_fence_gate", ItemGroup.TAB_REDSTONE, new MushroomFenceGateBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_LADDER = registerCompatBlock("poisonous_mushroom_ladder", ItemGroup.TAB_DECORATIONS, ModCompat.isVariantLaddersModLoaded(), new LadderBlock(Block.Properties.copy(Blocks.LADDER)));
        POISONOUS_MUSHROOM_PLANKS = registerBlock("poisonous_mushroom_planks", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_PRESSURE_PLATE = registerBlock("poisonous_mushroom_pressure_plate", ItemGroup.TAB_REDSTONE, new MushroomWoodPressurePlateBlock());
        POISONOUS_MUSHROOM_SLAB = registerBlock("poisonous_mushroom_slab", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomSlabBlock(Block.Properties.copy(POISONOUS_MUSHROOM_PLANKS)));
        POISONOUS_MUSHROOM_STAIRS = registerBlock("poisonous_mushroom_stairs", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomStairsBlock(() -> POISONOUS_MUSHROOM_PLANKS.defaultBlockState(), Block.Properties.copy(POISONOUS_MUSHROOM_PLANKS)));
        POISONOUS_MUSHROOM_TRAPDOOR = registerBlock("poisonous_mushroom_trapdoor", ItemGroup.TAB_REDSTONE, new MushroomTrapdoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_VERTICAL_PLANKS = registerCompatBlock("poisonous_mushroom_vertical_planks", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVerticalPlanksModLoaded(), new VerticalPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_VERTICAL_SLAB = registerCompatBlock("poisonous_mushroom_vertical_slab", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVerticalSlabsModLoaded(), new VerticalSlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_CAP_BUTTON = registerBlock("poisonous_mushroom_cap_button", ItemGroup.TAB_REDSTONE, new MushroomCapButtonBlock());
        POISONOUS_MUSHROOM_CAP_CARPET = registerBlock("poisonous_mushroom_cap_carpet", ItemGroup.TAB_DECORATIONS, new MushroomCarpetBlock(DyeColor.PURPLE, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_PURPLE).strength(0.1F).sound(SoundType.WOOL)));
        POISONOUS_MUSHROOM_CAP_PRESSURE_PLATE = registerBlock("poisonous_mushroom_cap_pressure_plate", ItemGroup.TAB_REDSTONE, new MushroomCapPressurePlateBlock());

        SLIME_FUNGUS = registerBlock("slime_fungus", ItemGroup.TAB_DECORATIONS, new EMMushroomBlock(new SlimeFungus(), Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.SLIME_BLOCK).hasPostProcess((a, b, c)->true)));
        SLIME_FUNGUS_POTTED = registerBlock("slime_fungus_potted", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> SLIME_FUNGUS, Block.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion()));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(SLIME_FUNGUS.getRegistryName()), () -> SLIME_FUNGUS_POTTED);
        SLIME_FUNGUS_CAP = registerBlock("slime_fungus_cap", ItemGroup.TAB_DECORATIONS, new SlimeFungusCap(MushroomType.SLIME_FUNGUS, Block.Properties.of(Material.CLAY, MaterialColor.GRASS).friction(0.8F).jumpFactor(1.5F).strength(0.2F).sound(SoundType.SLIME_BLOCK)));
        SLIME_FUNGUS_CAP_BUTTON = registerBlock("slime_fungus_cap_button", ItemGroup.TAB_REDSTONE, new MushroomCapButtonBlock());
        SLIME_FUNGUS_CAP_CARPET = registerBlock("slime_fungus_cap_carpet", ItemGroup.TAB_DECORATIONS, new MushroomCarpetBlock(DyeColor.LIME, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_LIGHT_GREEN).strength(0.1F).sound(SoundType.WOOL)));
        SLIME_FUNGUS_CAP_PRESSURE_PLATE = registerBlock("slime_fungus_cap_pressure_plate", ItemGroup.TAB_REDSTONE, new MushroomCapPressurePlateBlock());

        HONEY_FUNGUS = registerBlock("honey_fungus", ItemGroup.TAB_DECORATIONS, new EMMushroomBlock(new HoneyFungus(), Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.HONEY_BLOCK).hasPostProcess((a, b, c)->true)));
        HONEY_FUNGUS_POTTED = registerBlock("honey_fungus_potted", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> HONEY_FUNGUS, Block.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion()));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(HONEY_FUNGUS.getRegistryName()), () -> HONEY_FUNGUS_POTTED);
        HONEY_FUNGUS_CAP = registerBlock("honey_fungus_cap", ItemGroup.TAB_DECORATIONS, new HoneyFungusCap(MushroomType.HONEY_FUNGUS, Block.Properties.of(Material.CLAY, MaterialColor.COLOR_ORANGE).strength(0.2F).speedFactor(0.4F).jumpFactor(0.5F).sound(SoundType.SLIME_BLOCK)));
        HONEY_FUNGUS_STEM = registerBlock("honey_fungus_stem", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomStemBlock(MushroomWoodType.HONEY_FUNGUS, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
        HONEY_FUNGUS_STEM_STRIPPED = registerBlock("honey_fungus_stem_stripped", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomStemBlock(MushroomWoodType.HONEY_FUNGUS, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
        BLOCK_STRIPPING_MAP.put(HONEY_FUNGUS_STEM, HONEY_FUNGUS_STEM_STRIPPED);
        HONEY_FUNGUS_BOOKSHELF = registerCompatBlock("honey_fungus_bookshelf", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVariantBookshelvesModLoaded(), new BookshelfBlock(Block.Properties.copy(Blocks.BOOKSHELF)));
        HONEY_FUNGUS_BUTTON = registerBlock("honey_fungus_button", ItemGroup.TAB_REDSTONE, new MushroomWoodButtonBlock());
        HONEY_FUNGUS_CHEST = registerCompatBlock("honey_fungus_chest", ItemGroup.TAB_DECORATIONS, ModCompat.isVariantChestsModLoaded(), new VariantChestBlock(MushroomWoodType.HONEY_FUNGUS, Block.Properties.copy(Blocks.CHEST)));
        HONEY_FUNGUS_CHEST_TRAPPED = registerCompatBlock("honey_fungus_chest_trapped", ItemGroup.TAB_REDSTONE, ModCompat.isVariantTrappedChestsModLoaded(), new VariantTrappedChestBlock(MushroomWoodType.HONEY_FUNGUS, Block.Properties.copy(Blocks.TRAPPED_CHEST)));
        HONEY_FUNGUS_DOOR = registerBlock("honey_fungus_door", ItemGroup.TAB_REDSTONE, new DoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD)));
        HONEY_FUNGUS_FENCE = registerBlock("honey_fungus_fence", ItemGroup.TAB_DECORATIONS, new MushroomFenceBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
        HONEY_FUNGUS_FENCE_GATE = registerBlock("honey_fungus_fence_gate", ItemGroup.TAB_REDSTONE, new MushroomFenceGateBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
        HONEY_FUNGUS_LADDER = registerCompatBlock("honey_fungus_ladder", ItemGroup.TAB_DECORATIONS, ModCompat.isVariantLaddersModLoaded(), new LadderBlock(Block.Properties.copy(Blocks.LADDER)));
        HONEY_FUNGUS_PLANKS = registerBlock("honey_fungus_planks", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
        HONEY_FUNGUS_PRESSURE_PLATE = registerBlock("honey_fungus_pressure_plate", ItemGroup.TAB_REDSTONE, new MushroomWoodPressurePlateBlock());
        HONEY_FUNGUS_SLAB = registerBlock("honey_fungus_slab", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomSlabBlock(Block.Properties.copy(HONEY_FUNGUS_PLANKS)));
        HONEY_FUNGUS_STAIRS = registerBlock("honey_fungus_stairs", ItemGroup.TAB_BUILDING_BLOCKS, new MushroomStairsBlock(() -> HONEY_FUNGUS_PLANKS.defaultBlockState(), Block.Properties.copy(HONEY_FUNGUS_PLANKS)));
        HONEY_FUNGUS_TRAPDOOR = registerBlock("honey_fungus_trapdoor", ItemGroup.TAB_REDSTONE, new MushroomTrapdoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD)));
        HONEY_FUNGUS_VERTICAL_PLANKS = registerCompatBlock("honey_fungus_vertical_planks", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVerticalPlanksModLoaded(), new VerticalPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
        HONEY_FUNGUS_VERTICAL_SLAB = registerCompatBlock("honey_fungus_vertical_slab", ItemGroup.TAB_BUILDING_BLOCKS, ModCompat.isVerticalSlabsModLoaded(), new VerticalSlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
        HONEY_FUNGUS_CAP_BUTTON = registerBlock("honey_fungus_cap_button", ItemGroup.TAB_REDSTONE, new MushroomCapButtonBlock());
        HONEY_FUNGUS_CAP_CARPET = registerBlock("honey_fungus_cap_carpet", ItemGroup.TAB_DECORATIONS, new MushroomCarpetBlock(DyeColor.ORANGE, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_ORANGE).strength(0.1F).sound(SoundType.WOOL)));
        HONEY_FUNGUS_CAP_PRESSURE_PLATE = registerBlock("honey_fungus_cap_pressure_plate", ItemGroup.TAB_REDSTONE, new MushroomCapPressurePlateBlock());

    }

    private static Block registerBlock(String name, Block block) {
        block.setRegistryName(name);
        ForgeRegistries.BLOCKS.register(block);
        return block;
    }

    private static Block registerCompatBlock(String name, ItemGroup itemGroup, boolean isActive, Block block) {
        ItemGroup determinedGroup = (isActive) ? itemGroup : null;
        return registerBlock(name, determinedGroup, block);
    }

    private static Block registerBlock(String name, ItemGroup itemGroup, Block block) {
        Item.Properties itemProperties = new Item.Properties().tab(itemGroup);
        try {
            if (block instanceof VariantChestBlock) {
                ((VariantChestBlock)block).setISTER(itemProperties);
            } else if (block instanceof VariantTrappedChestBlock) {
                ((VariantTrappedChestBlock)block).setISTER(itemProperties);
            }
        } catch (NoSuchMethodError ignore) {}
        BlockItem itemBlock = new BlockItem(block, itemProperties);
        block.setRegistryName(name);
        itemBlock.setRegistryName(name);
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(itemBlock);
        return block;
    }

    /**
     * Setup render layers of blocks which are not solid blocks.
     * Method is called at mod initialisation (client side).
     */
    @OnlyIn(Dist.CLIENT)
    public static void setupRenderLayers() {
        //RenderTypeLookup.setRenderLayer(MUSHROOM_DOOR, RenderType.getCutout()); //unfortunately buggy - so, texture without transparency
        //RenderTypeLookup.setRenderLayer(MUSHROOM_TRAPDOOR, RenderType.getCutout()); //unfortunately buggy - so, texture without transparency

        RenderTypeLookup.setRenderLayer(FAIRY_RING, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(INFESTED_GRASS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(INFESTED_FLOWER, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(INFESTED_FLOWER_POTTED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MUSHROOM_LADDER, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(GLOWSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(GLOWSHROOM_LADDER, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(GLOWSHROOM_POTTED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(POISONOUS_MUSHROOM, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(POISONOUS_MUSHROOM_LADDER, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(POISONOUS_MUSHROOM_POTTED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SLIME_FUNGUS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(SLIME_FUNGUS_POTTED, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(HONEY_FUNGUS, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(HONEY_FUNGUS_LADDER, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(HONEY_FUNGUS_POTTED, RenderType.cutout());

        RenderTypeLookup.setRenderLayer(SLIME_FUNGUS_CAP, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(HONEY_FUNGUS_CAP, RenderType.translucent());
    }

}
