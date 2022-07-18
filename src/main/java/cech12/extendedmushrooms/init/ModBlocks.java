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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModBlocks {


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExtendedMushrooms.MOD_ID);

    public static final RegistryObject<Block> FAIRY_RING = registerBlock("fairy_ring", FairyRingBlock::new);

    public static final RegistryObject<Block> INFESTED_GRASS = registerBlock("infested_grass", CreativeModeTab.TAB_DECORATIONS, () -> new InfestedGrassBlock(Block.Properties.of(Material.REPLACEABLE_PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> INFESTED_FLOWER = registerBlock("infested_flower", CreativeModeTab.TAB_DECORATIONS, () -> new InfestedFlowerBlock(MobEffects.MOVEMENT_SLOWDOWN, 9, Block.Properties.of(Material.PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> INFESTED_FLOWER_POTTED = registerBlock("infested_flower_potted", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, INFESTED_FLOWER, Block.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion()));

    public static final RegistryObject<Block> MUSHROOM_BOOKSHELF = registerCompatBlock("mushroom_bookshelf", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVariantBookshelvesModLoaded(), () -> new BookshelfBlock(Block.Properties.copy(Blocks.BOOKSHELF)));
    public static final RegistryObject<Block> MUSHROOM_BUTTON = registerBlock("mushroom_button", CreativeModeTab.TAB_REDSTONE, MushroomWoodButtonBlock::new);
    public static final RegistryObject<Block> MUSHROOM_CHEST = registerCompatBlock("mushroom_chest", CreativeModeTab.TAB_DECORATIONS, ModCompat.isVariantChestsModLoaded(), () -> new VariantChestBlock(MushroomWoodType.MUSHROOM, Block.Properties.copy(Blocks.CHEST)));
    public static final RegistryObject<Block> MUSHROOM_CHEST_TRAPPED = registerCompatBlock("mushroom_chest_trapped", CreativeModeTab.TAB_REDSTONE, ModCompat.isVariantTrappedChestsModLoaded(), () -> new VariantTrappedChestBlock(MushroomWoodType.MUSHROOM, Block.Properties.copy(Blocks.TRAPPED_CHEST)));
    public static final RegistryObject<Block> MUSHROOM_DOOR = registerBlock("mushroom_door", CreativeModeTab.TAB_REDSTONE, () -> new DoorBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MUSHROOM_FENCE = registerBlock("mushroom_fence", CreativeModeTab.TAB_DECORATIONS, () -> new MushroomFenceBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MUSHROOM_FENCE_GATE = registerBlock("mushroom_fence_gate", CreativeModeTab.TAB_REDSTONE, () -> new MushroomFenceGateBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MUSHROOM_LADDER = registerCompatBlock("mushroom_ladder", CreativeModeTab.TAB_DECORATIONS, ModCompat.isVariantLaddersModLoaded(), () -> new LadderBlock(Block.Properties.copy(Blocks.LADDER)));
    public static final RegistryObject<Block> MUSHROOM_PLANKS = registerBlock("mushroom_planks", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomPlanksBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MUSHROOM_PRESSURE_PLATE = registerBlock("mushroom_pressure_plate", CreativeModeTab.TAB_REDSTONE, MushroomWoodPressurePlateBlock::new);
    public static final RegistryObject<Block> MUSHROOM_SLAB = registerBlock("mushroom_slab", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomSlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MUSHROOM_STAIRS = registerBlock("mushroom_stairs", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomStairsBlock(() -> MUSHROOM_PLANKS.get().defaultBlockState(), Block.Properties.copy(MUSHROOM_PLANKS.get())));
    public static final RegistryObject<Block> MUSHROOM_STANDING_SIGN = registerBlock("mushroom_sign", () -> new MushroomStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.MUSHROOM.getWoodType()));
    public static final RegistryObject<Block> MUSHROOM_TRAPDOOR = registerBlock("mushroom_trapdoor", CreativeModeTab.TAB_REDSTONE, () -> new MushroomTrapdoorBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MUSHROOM_WALL_SIGN = registerBlock("mushroom_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.MUSHROOM.getWoodType()));
    public static final RegistryObject<Block> STRIPPED_MUSHROOM_STEM = registerBlock("stripped_mushroom_stem", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomStemBlock(MushroomWoodType.MUSHROOM, Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(0.2F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> MUSHROOM_VERTICAL_PLANKS = registerCompatBlock("mushroom_vertical_planks", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVerticalPlanksModLoaded(), () -> new VerticalPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MUSHROOM_VERTICAL_SLAB = registerCompatBlock("mushroom_vertical_slab", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVerticalSlabsModLoaded(), () -> new VerticalSlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> BROWN_MUSHROOM_BUTTON = registerBlock("brown_mushroom_button", CreativeModeTab.TAB_REDSTONE, MushroomCapButtonBlock::new);
    public static final RegistryObject<Block> BROWN_MUSHROOM_CARPET = registerBlock("brown_mushroom_carpet", CreativeModeTab.TAB_DECORATIONS, () -> new MushroomCarpetBlock(DyeColor.BROWN, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BROWN).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> BROWN_MUSHROOM_PRESSURE_PLATE = registerBlock("brown_mushroom_pressure_plate", CreativeModeTab.TAB_REDSTONE, MushroomCapPressurePlateBlock::new);
    public static final RegistryObject<Block> RED_MUSHROOM_BUTTON = registerBlock("red_mushroom_button", CreativeModeTab.TAB_REDSTONE, MushroomCapButtonBlock::new);
    public static final RegistryObject<Block> RED_MUSHROOM_CARPET = registerBlock("red_mushroom_carpet", CreativeModeTab.TAB_DECORATIONS, () -> new MushroomCarpetBlock(DyeColor.RED, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_RED).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> RED_MUSHROOM_PRESSURE_PLATE = registerBlock("red_mushroom_pressure_plate", CreativeModeTab.TAB_REDSTONE, MushroomCapPressurePlateBlock::new);

    public static final RegistryObject<Block> GLOWSHROOM = registerBlock("glowshroom", CreativeModeTab.TAB_DECORATIONS, () -> new EMMushroomBlock(new Glowshroom(), Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS).lightLevel((state) -> 8).hasPostProcess((a,b,c)->true)));
    public static final RegistryObject<Block> GLOWSHROOM_POTTED = registerBlock("glowshroom_potted", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GLOWSHROOM, Block.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion().lightLevel((state) -> 8)));

    public static final RegistryObject<Block> GLOWSHROOM_CAP = registerBlock("glowshroom_cap", CreativeModeTab.TAB_DECORATIONS, () -> new GlowshroomCap(MushroomType.GLOWSHROOM, Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BLUE).strength(0.2F).sound(SoundType.WOOL).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_STEM = registerBlock("glowshroom_stem", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomStemBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_STEM_STRIPPED = registerBlock("glowshroom_stem_stripped", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomStemBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 8)));

    public static final RegistryObject<Block> GLOWSHROOM_BOOKSHELF = registerCompatBlock("glowshroom_bookshelf", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVariantBookshelvesModLoaded(), () -> new BookshelfBlock(Block.Properties.copy(Blocks.BOOKSHELF).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_BUTTON = registerBlock("glowshroom_button", CreativeModeTab.TAB_REDSTONE, () -> new MushroomWoodButtonBlock(8));
    public static final RegistryObject<Block> GLOWSHROOM_CHEST = registerCompatBlock("glowshroom_chest", CreativeModeTab.TAB_DECORATIONS, ModCompat.isVariantChestsModLoaded(), () -> new VariantChestBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.copy(Blocks.CHEST).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_CHEST_TRAPPED = registerCompatBlock("glowshroom_chest_trapped", CreativeModeTab.TAB_REDSTONE, ModCompat.isVariantTrappedChestsModLoaded(), () -> new VariantTrappedChestBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.copy(Blocks.TRAPPED_CHEST).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_DOOR = registerBlock("glowshroom_door", CreativeModeTab.TAB_REDSTONE, () -> new DoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_FENCE = registerBlock("glowshroom_fence", CreativeModeTab.TAB_DECORATIONS, () -> new MushroomFenceBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_FENCE_GATE = registerBlock("glowshroom_fence_gate", CreativeModeTab.TAB_REDSTONE, () -> new MushroomFenceGateBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_LADDER = registerCompatBlock("glowshroom_ladder", CreativeModeTab.TAB_DECORATIONS, ModCompat.isVariantLaddersModLoaded(), () -> new LadderBlock(Block.Properties.copy(Blocks.LADDER).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_PLANKS = registerBlock("glowshroom_planks", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_PRESSURE_PLATE = registerBlock("glowshroom_pressure_plate", CreativeModeTab.TAB_REDSTONE, () -> new MushroomWoodPressurePlateBlock(8));
    public static final RegistryObject<Block> GLOWSHROOM_SLAB = registerBlock("glowshroom_slab", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomSlabBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_STAIRS = registerBlock("glowshroom_stairs", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomStairsBlock(() -> GLOWSHROOM_PLANKS.get().defaultBlockState(), Block.Properties.copy(GLOWSHROOM_PLANKS.get())));
    public static final RegistryObject<Block> GLOWSHROOM_STANDING_SIGN = registerBlock("glowshroom_sign", () -> new MushroomStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).lightLevel((state) -> 8), MushroomWoodType.GLOWSHROOM.getWoodType()));
    public static final RegistryObject<Block> GLOWSHROOM_TRAPDOOR = registerBlock("glowshroom_trapdoor", CreativeModeTab.TAB_REDSTONE, () -> new MushroomTrapdoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_VERTICAL_PLANKS = registerCompatBlock("glowshroom_vertical_planks", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVerticalPlanksModLoaded(), () -> new VerticalPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_VERTICAL_SLAB = registerCompatBlock("glowshroom_vertical_slab", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVerticalSlabsModLoaded(), () -> new VerticalSlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_WALL_SIGN = registerBlock("glowshroom_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).lightLevel((state) -> 8), MushroomWoodType.GLOWSHROOM.getWoodType()));
    public static final RegistryObject<Block> GLOWSHROOM_CAP_BUTTON = registerBlock("glowshroom_cap_button", CreativeModeTab.TAB_REDSTONE, () -> new MushroomCapButtonBlock(8));
    public static final RegistryObject<Block> GLOWSHROOM_CAP_CARPET = registerBlock("glowshroom_cap_carpet", CreativeModeTab.TAB_DECORATIONS, () -> new MushroomCarpetBlock(DyeColor.BLUE, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BLUE).strength(0.1F).sound(SoundType.WOOL).lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GLOWSHROOM_CAP_PRESSURE_PLATE = registerBlock("glowshroom_cap_pressure_plate", CreativeModeTab.TAB_REDSTONE, () -> new MushroomCapPressurePlateBlock(8));

    public static final RegistryObject<Block> POISONOUS_MUSHROOM = registerBlock("poisonous_mushroom", CreativeModeTab.TAB_DECORATIONS, () -> new PoisonousMushroomBlock(Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS).hasPostProcess((a,b,c)->true)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_POTTED = registerBlock("poisonous_mushroom_potted", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, POISONOUS_MUSHROOM, Block.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion()));

    public static final RegistryObject<Block> POISONOUS_MUSHROOM_CAP = registerBlock("poisonous_mushroom_cap", CreativeModeTab.TAB_DECORATIONS, () -> new PoisonousMushroomCap(MushroomType.POISONOUS_MUSHROOM, Block.Properties.of(Material.WOOD, MaterialColor.COLOR_PURPLE).strength(0.2F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_STEM = registerBlock("poisonous_mushroom_stem", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomStemBlock(MushroomWoodType.POISONOUS_MUSHROOM, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_STEM_STRIPPED = registerBlock("poisonous_mushroom_stem_stripped", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomStemBlock(MushroomWoodType.POISONOUS_MUSHROOM, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> POISONOUS_MUSHROOM_BOOKSHELF = registerCompatBlock("poisonous_mushroom_bookshelf", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVariantBookshelvesModLoaded(), () -> new BookshelfBlock(Block.Properties.copy(Blocks.BOOKSHELF)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_BUTTON = registerBlock("poisonous_mushroom_button", CreativeModeTab.TAB_REDSTONE, MushroomWoodButtonBlock::new);
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_CHEST = registerCompatBlock("poisonous_mushroom_chest", CreativeModeTab.TAB_DECORATIONS, ModCompat.isVariantChestsModLoaded(), () -> new VariantChestBlock(MushroomWoodType.POISONOUS_MUSHROOM, Block.Properties.copy(Blocks.CHEST)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_CHEST_TRAPPED = registerCompatBlock("poisonous_mushroom_chest_trapped", CreativeModeTab.TAB_REDSTONE, ModCompat.isVariantTrappedChestsModLoaded(), () -> new VariantTrappedChestBlock(MushroomWoodType.POISONOUS_MUSHROOM, Block.Properties.copy(Blocks.TRAPPED_CHEST)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_DOOR = registerBlock("poisonous_mushroom_door", CreativeModeTab.TAB_REDSTONE, () -> new DoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_FENCE = registerBlock("poisonous_mushroom_fence", CreativeModeTab.TAB_DECORATIONS, () -> new MushroomFenceBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_FENCE_GATE = registerBlock("poisonous_mushroom_fence_gate", CreativeModeTab.TAB_REDSTONE, () -> new MushroomFenceGateBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_LADDER = registerCompatBlock("poisonous_mushroom_ladder", CreativeModeTab.TAB_DECORATIONS, ModCompat.isVariantLaddersModLoaded(), () -> new LadderBlock(Block.Properties.copy(Blocks.LADDER)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_PLANKS = registerBlock("poisonous_mushroom_planks", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_PRESSURE_PLATE = registerBlock("poisonous_mushroom_pressure_plate", CreativeModeTab.TAB_REDSTONE, MushroomWoodPressurePlateBlock::new);
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_SLAB = registerBlock("poisonous_mushroom_slab", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomSlabBlock(Block.Properties.copy(POISONOUS_MUSHROOM_PLANKS.get())));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_STAIRS = registerBlock("poisonous_mushroom_stairs", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomStairsBlock(() -> POISONOUS_MUSHROOM_PLANKS.get().defaultBlockState(), Block.Properties.copy(POISONOUS_MUSHROOM_PLANKS.get())));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_STANDING_SIGN = registerBlock("poisonous_mushroom_sign", () -> new MushroomStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.POISONOUS_MUSHROOM.getWoodType()));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_TRAPDOOR = registerBlock("poisonous_mushroom_trapdoor", CreativeModeTab.TAB_REDSTONE, () -> new MushroomTrapdoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_VERTICAL_PLANKS = registerCompatBlock("poisonous_mushroom_vertical_planks", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVerticalPlanksModLoaded(), () -> new VerticalPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_VERTICAL_SLAB = registerCompatBlock("poisonous_mushroom_vertical_slab", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVerticalSlabsModLoaded(), () -> new VerticalSlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_WALL_SIGN = registerBlock("poisonous_mushroom_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.POISONOUS_MUSHROOM.getWoodType()));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_CAP_BUTTON = registerBlock("poisonous_mushroom_cap_button", CreativeModeTab.TAB_REDSTONE, MushroomCapButtonBlock::new);
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_CAP_CARPET = registerBlock("poisonous_mushroom_cap_carpet", CreativeModeTab.TAB_DECORATIONS, () -> new MushroomCarpetBlock(DyeColor.PURPLE, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_PURPLE).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> POISONOUS_MUSHROOM_CAP_PRESSURE_PLATE = registerBlock("poisonous_mushroom_cap_pressure_plate", CreativeModeTab.TAB_REDSTONE, MushroomCapPressurePlateBlock::new);

    public static final RegistryObject<Block> SLIME_FUNGUS = registerBlock("slime_fungus", CreativeModeTab.TAB_DECORATIONS, () -> new EMMushroomBlock(new SlimeFungus(), Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.SLIME_BLOCK).hasPostProcess((a, b, c)->true)));
    public static final RegistryObject<Block> SLIME_FUNGUS_POTTED = registerBlock("slime_fungus_potted", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SLIME_FUNGUS, Block.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion()));

    public static final RegistryObject<Block> SLIME_FUNGUS_CAP = registerBlock("slime_fungus_cap", CreativeModeTab.TAB_DECORATIONS, () -> new SlimeFungusCap(MushroomType.SLIME_FUNGUS, Block.Properties.of(Material.CLAY, MaterialColor.GRASS).strength(0.2F).noOcclusion().jumpFactor(1.5F).friction(0.8F).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryObject<Block> SLIME_FUNGUS_CAP_BUTTON = registerBlock("slime_fungus_cap_button", CreativeModeTab.TAB_REDSTONE, MushroomCapButtonBlock::new);
    public static final RegistryObject<Block> SLIME_FUNGUS_CAP_CARPET = registerBlock("slime_fungus_cap_carpet", CreativeModeTab.TAB_DECORATIONS, () -> new MushroomCarpetBlock(DyeColor.LIME, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_LIGHT_GREEN).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> SLIME_FUNGUS_CAP_PRESSURE_PLATE = registerBlock("slime_fungus_cap_pressure_plate", CreativeModeTab.TAB_REDSTONE, MushroomCapPressurePlateBlock::new);

    public static final RegistryObject<Block> HONEY_FUNGUS = registerBlock("honey_fungus", CreativeModeTab.TAB_DECORATIONS, () -> new EMMushroomBlock(new HoneyFungus(), Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0F).sound(SoundType.HONEY_BLOCK).hasPostProcess((a, b, c)->true)));
    public static final RegistryObject<Block> HONEY_FUNGUS_POTTED = registerBlock("honey_fungus_potted", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HONEY_FUNGUS, Block.Properties.of(Material.DECORATION).strength(0.0F).noOcclusion()));

    public static final RegistryObject<Block> HONEY_FUNGUS_CAP = registerBlock("honey_fungus_cap", CreativeModeTab.TAB_DECORATIONS, () -> new HoneyFungusCap(MushroomType.HONEY_FUNGUS, Block.Properties.of(Material.CLAY, MaterialColor.COLOR_ORANGE).strength(0.2F).noOcclusion().speedFactor(0.4F).jumpFactor(0.5F).sound(SoundType.HONEY_BLOCK)));
    public static final RegistryObject<Block> HONEY_FUNGUS_STEM = registerBlock("honey_fungus_stem", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomStemBlock(MushroomWoodType.HONEY_FUNGUS, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_FUNGUS_STEM_STRIPPED = registerBlock("honey_fungus_stem_stripped", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomStemBlock(MushroomWoodType.HONEY_FUNGUS, Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> HONEY_FUNGUS_BOOKSHELF = registerCompatBlock("honey_fungus_bookshelf", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVariantBookshelvesModLoaded(), () -> new BookshelfBlock(Block.Properties.copy(Blocks.BOOKSHELF)));
    public static final RegistryObject<Block> HONEY_FUNGUS_BUTTON = registerBlock("honey_fungus_button", CreativeModeTab.TAB_REDSTONE, MushroomWoodButtonBlock::new);
    public static final RegistryObject<Block> HONEY_FUNGUS_CHEST = registerCompatBlock("honey_fungus_chest", CreativeModeTab.TAB_DECORATIONS, ModCompat.isVariantChestsModLoaded(), () -> new VariantChestBlock(MushroomWoodType.HONEY_FUNGUS, Block.Properties.copy(Blocks.CHEST)));
    public static final RegistryObject<Block> HONEY_FUNGUS_CHEST_TRAPPED = registerCompatBlock("honey_fungus_chest_trapped", CreativeModeTab.TAB_REDSTONE, ModCompat.isVariantTrappedChestsModLoaded(), () -> new VariantTrappedChestBlock(MushroomWoodType.HONEY_FUNGUS, Block.Properties.copy(Blocks.TRAPPED_CHEST)));
    public static final RegistryObject<Block> HONEY_FUNGUS_DOOR = registerBlock("honey_fungus_door", CreativeModeTab.TAB_REDSTONE, () -> new DoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_FUNGUS_FENCE = registerBlock("honey_fungus_fence", CreativeModeTab.TAB_DECORATIONS, () -> new MushroomFenceBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_FUNGUS_FENCE_GATE = registerBlock("honey_fungus_fence_gate", CreativeModeTab.TAB_REDSTONE, () -> new MushroomFenceGateBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_FUNGUS_LADDER = registerCompatBlock("honey_fungus_ladder", CreativeModeTab.TAB_DECORATIONS, ModCompat.isVariantLaddersModLoaded(), () -> new LadderBlock(Block.Properties.copy(Blocks.LADDER)));
    public static final RegistryObject<Block> HONEY_FUNGUS_PLANKS = registerBlock("honey_fungus_planks", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_FUNGUS_PRESSURE_PLATE = registerBlock("honey_fungus_pressure_plate", CreativeModeTab.TAB_REDSTONE, MushroomWoodPressurePlateBlock::new);
    public static final RegistryObject<Block> HONEY_FUNGUS_SLAB = registerBlock("honey_fungus_slab", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomSlabBlock(Block.Properties.copy(HONEY_FUNGUS_PLANKS.get())));
    public static final RegistryObject<Block> HONEY_FUNGUS_STAIRS = registerBlock("honey_fungus_stairs", CreativeModeTab.TAB_BUILDING_BLOCKS, () -> new MushroomStairsBlock(() -> HONEY_FUNGUS_PLANKS.get().defaultBlockState(), Block.Properties.copy(HONEY_FUNGUS_PLANKS.get())));
    public static final RegistryObject<Block> HONEY_FUNGUS_STANDING_SIGN = registerBlock("honey_fungus_sign", () -> new MushroomStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.HONEY_FUNGUS.getWoodType()));
    public static final RegistryObject<Block> HONEY_FUNGUS_TRAPDOOR = registerBlock("honey_fungus_trapdoor", CreativeModeTab.TAB_REDSTONE, () -> new MushroomTrapdoorBlock(Block.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_FUNGUS_VERTICAL_PLANKS = registerCompatBlock("honey_fungus_vertical_planks", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVerticalPlanksModLoaded(), () -> new VerticalPlanksBlock(Block.Properties.of(Material.WOOD).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_FUNGUS_VERTICAL_SLAB = registerCompatBlock("honey_fungus_vertical_slab", CreativeModeTab.TAB_BUILDING_BLOCKS, ModCompat.isVerticalSlabsModLoaded(), () -> new VerticalSlabBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HONEY_FUNGUS_WALL_SIGN = registerBlock("honey_fungus_wall_sign", () -> new MushroomWallSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), MushroomWoodType.HONEY_FUNGUS.getWoodType()));
    public static final RegistryObject<Block> HONEY_FUNGUS_CAP_BUTTON = registerBlock("honey_fungus_cap_button", CreativeModeTab.TAB_REDSTONE, MushroomCapButtonBlock::new);
    public static final RegistryObject<Block> HONEY_FUNGUS_CAP_CARPET = registerBlock("honey_fungus_cap_carpet", CreativeModeTab.TAB_DECORATIONS, () -> new MushroomCarpetBlock(DyeColor.ORANGE, Block.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_ORANGE).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> HONEY_FUNGUS_CAP_PRESSURE_PLATE = registerBlock("honey_fungus_cap_pressure_plate", CreativeModeTab.TAB_REDSTONE, MushroomCapPressurePlateBlock::new);

    private static final Map<RegistryObject<Block>, RegistryObject<Block>> BLOCK_STRIPPING_MAP = new HashMap<>();

    static {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(INFESTED_FLOWER.getId()), INFESTED_FLOWER_POTTED);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(GLOWSHROOM.getId()), GLOWSHROOM_POTTED);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(POISONOUS_MUSHROOM.getId()), POISONOUS_MUSHROOM_POTTED);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(SLIME_FUNGUS.getId()), SLIME_FUNGUS_POTTED);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(HONEY_FUNGUS.getId()), HONEY_FUNGUS_POTTED);
        BLOCK_STRIPPING_MAP.put(RegistryObject.create(Blocks.MUSHROOM_STEM.getRegistryName(), ForgeRegistries.BLOCKS), STRIPPED_MUSHROOM_STEM);
        BLOCK_STRIPPING_MAP.put(GLOWSHROOM_STEM, GLOWSHROOM_STEM_STRIPPED);
        BLOCK_STRIPPING_MAP.put(POISONOUS_MUSHROOM_STEM, POISONOUS_MUSHROOM_STEM_STRIPPED);
        BLOCK_STRIPPING_MAP.put(HONEY_FUNGUS_STEM, HONEY_FUNGUS_STEM_STRIPPED);
    }

    public static Block getStrippedBlock(Block block) {
        return BLOCK_STRIPPING_MAP.entrySet().stream().filter(entry -> entry.getKey().get().equals(block)).findFirst().map(entry -> entry.getValue().get()).orElse(null);
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<? extends Block> block) {
        return BLOCKS.register(name, block);
    }

    private static RegistryObject<Block> registerCompatBlock(String name, CreativeModeTab itemGroup, boolean isActive, Supplier<? extends Block> block) {
        CreativeModeTab determinedGroup = (isActive) ? itemGroup : null;
        return registerBlock(name, determinedGroup, block);
    }

    private static RegistryObject<Block> registerBlock(String name, CreativeModeTab itemGroup, Supplier<? extends Block> block) {
        Item.Properties itemProperties = new Item.Properties().tab(itemGroup);
        try {
            if (block instanceof VariantChestBlock) {
                ((VariantChestBlock)block).setISTER(itemProperties);
            } else if (block instanceof VariantTrappedChestBlock) {
                ((VariantTrappedChestBlock)block).setISTER(itemProperties);
            }
        } catch (NoSuchMethodError ignore) {}
        RegistryObject<Block> registeredBlock = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), itemProperties));
        return registeredBlock;
    }

    /**
     * Setup render layers of blocks which are not solid blocks.
     * Method is called at mod initialisation (client side).
     */
    @OnlyIn(Dist.CLIENT)
    public static void setupRenderLayers() {
        //RenderTypeLookup.setRenderLayer(MUSHROOM_DOOR, RenderType.getCutout()); //unfortunately buggy - so, texture without transparency
        //RenderTypeLookup.setRenderLayer(MUSHROOM_TRAPDOOR, RenderType.getCutout()); //unfortunately buggy - so, texture without transparency

        ItemBlockRenderTypes.setRenderLayer(FAIRY_RING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(INFESTED_GRASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(INFESTED_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(INFESTED_FLOWER_POTTED.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(MUSHROOM_LADDER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GLOWSHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GLOWSHROOM_LADDER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GLOWSHROOM_POTTED.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(POISONOUS_MUSHROOM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(POISONOUS_MUSHROOM_LADDER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(POISONOUS_MUSHROOM_POTTED.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SLIME_FUNGUS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SLIME_FUNGUS_POTTED.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HONEY_FUNGUS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HONEY_FUNGUS_LADDER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(HONEY_FUNGUS_POTTED.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(SLIME_FUNGUS_CAP.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(HONEY_FUNGUS_CAP.get(), RenderType.translucent());
    }

}
