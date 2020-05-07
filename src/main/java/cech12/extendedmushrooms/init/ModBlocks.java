package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.*;
import cech12.extendedmushrooms.block.mushroomblocks.GlowshroomCap;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStemBlock;
import cech12.extendedmushrooms.block.mushroomblocks.PoisonousMushroomBlock;
import cech12.extendedmushrooms.block.mushroomblocks.PoisonousMushroomCap;
import cech12.extendedmushrooms.block.mushrooms.Glowshroom;
import cech12.extendedmushrooms.compat.ModCompat;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.item.MushroomType;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effects;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks.*;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModBlocks {

    public static Map<Block, Block> BLOCK_STRIPPING_MAP = new HashMap<>();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        INFESTED_GRASS = registerBlock("infested_grass", ItemGroup.DECORATIONS, new InfestedGrassBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)));
        INFESTED_FLOWER = registerBlock("infested_flower", ItemGroup.DECORATIONS, new InfestedFlowerBlock(Effects.SLOWNESS, 9, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)));
        INFESTED_FLOWER_POTTED = registerBlock("infested_flower_potted", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> INFESTED_FLOWER, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F).notSolid()));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(INFESTED_FLOWER.getRegistryName()), () -> INFESTED_FLOWER_POTTED);

        MUSHROOM_BOOKSHELF = registerCompatBlock("mushroom_bookshelf", ItemGroup.BUILDING_BLOCKS, ModCompat.isVariantBookshelvesModLoaded(), new BookshelfBlock(Block.Properties.from(Blocks.BOOKSHELF)));
        MUSHROOM_BUTTON = registerBlock("mushroom_button", ItemGroup.REDSTONE, new MushroomWoodButtonBlock());
        MUSHROOM_CHEST = registerCompatBlock("mushroom_chest", ItemGroup.DECORATIONS, ModCompat.isVariantChestsModLoaded(), new VariantChestBlock(MushroomWoodType.MUSHROOM, Block.Properties.from(Blocks.CHEST)));
        MUSHROOM_CHEST_TRAPPED = registerCompatBlock("mushroom_chest_trapped", ItemGroup.REDSTONE, ModCompat.isVariantTrappedChestsModLoaded(), new VariantTrappedChestBlock(MushroomWoodType.MUSHROOM, Block.Properties.from(Blocks.TRAPPED_CHEST)));
        MUSHROOM_DOOR = registerBlock("mushroom_door", ItemGroup.REDSTONE, new MushroomDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(3.0F).sound(SoundType.WOOD)));
        MUSHROOM_FENCE = registerBlock("mushroom_fence", ItemGroup.DECORATIONS, new FenceBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_FENCE_GATE = registerBlock("mushroom_fence_gate", ItemGroup.REDSTONE, new FenceGateBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_LADDER = registerCompatBlock("mushroom_ladder", ItemGroup.DECORATIONS, ModCompat.isVariantLaddersModLoaded(), new MushroomLadderBlock(Block.Properties.from(Blocks.LADDER)));
        MUSHROOM_PLANKS = registerBlock("mushroom_planks", ItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
        MUSHROOM_PRESSURE_PLATE = registerBlock("mushroom_pressure_plate", ItemGroup.REDSTONE, new MushroomWoodPressurePlateBlock());
        MUSHROOM_SLAB = registerBlock("mushroom_slab", ItemGroup.BUILDING_BLOCKS, new SlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_STAIRS = registerBlock("mushroom_stairs", ItemGroup.BUILDING_BLOCKS, new StairsBlock(() -> MUSHROOM_PLANKS.getDefaultState(), Block.Properties.from(MUSHROOM_PLANKS)));
        MUSHROOM_TRAPDOOR = registerBlock("mushroom_trapdoor", ItemGroup.REDSTONE, new MushroomTrapdoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(3.0F).sound(SoundType.WOOD)));
        STRIPPED_MUSHROOM_STEM = registerBlock("stripped_mushroom_stem", ItemGroup.BUILDING_BLOCKS, new HugeMushroomBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
        BLOCK_STRIPPING_MAP.put(Blocks.MUSHROOM_STEM, STRIPPED_MUSHROOM_STEM);
        MUSHROOM_VERTICAL_PLANKS = registerCompatBlock("mushroom_vertical_planks", ItemGroup.BUILDING_BLOCKS, ModCompat.isVerticalPlanksModLoaded(), new VerticalPlanksBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
        MUSHROOM_VERTICAL_SLAB = registerCompatBlock("mushroom_vertical_slab", ItemGroup.BUILDING_BLOCKS, ModCompat.isVerticalSlabsModLoaded(), new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));

        BROWN_MUSHROOM_BUTTON = registerBlock("brown_mushroom_button", ItemGroup.REDSTONE, new MushroomCapButtonBlock());
        BROWN_MUSHROOM_CARPET = registerBlock("brown_mushroom_carpet", ItemGroup.DECORATIONS, new MushroomCarpetBlock(DyeColor.BROWN, Block.Properties.create(Material.CARPET, MaterialColor.BROWN).hardnessAndResistance(0.1F).sound(SoundType.CLOTH)));
        BROWN_MUSHROOM_PRESSURE_PLATE = registerBlock("brown_mushroom_pressure_plate", ItemGroup.REDSTONE, new MushroomCapPressurePlateBlock());
        RED_MUSHROOM_BUTTON = registerBlock("red_mushroom_button", ItemGroup.REDSTONE, new MushroomCapButtonBlock());
        RED_MUSHROOM_CARPET = registerBlock("red_mushroom_carpet", ItemGroup.DECORATIONS, new MushroomCarpetBlock(DyeColor.RED, Block.Properties.create(Material.CARPET, MaterialColor.RED).hardnessAndResistance(0.1F).sound(SoundType.CLOTH)));
        RED_MUSHROOM_PRESSURE_PLATE = registerBlock("red_mushroom_pressure_plate", ItemGroup.REDSTONE, new MushroomCapPressurePlateBlock());

        GLOWSHROOM = registerBlock("glowshroom", ItemGroup.DECORATIONS, new EMMushroomBlock(new Glowshroom(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT).lightValue(8)));
        GLOWSHROOM_POTTED = registerBlock("glowshroom_potted", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> GLOWSHROOM, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F).notSolid().lightValue(8)));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(GLOWSHROOM.getRegistryName()), () -> GLOWSHROOM_POTTED);
        GLOWSHROOM_CAP = registerBlock("glowshroom_cap", ItemGroup.DECORATIONS, new GlowshroomCap(MushroomType.GLOWSHROOM, Block.Properties.create(Material.WOOD, MaterialColor.BLUE).hardnessAndResistance(0.2F).sound(SoundType.CLOTH).lightValue(8)));
        GLOWSHROOM_STEM = registerBlock("glowshroom_stem", ItemGroup.BUILDING_BLOCKS, new MushroomStemBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_STEM_STRIPPED = registerBlock("glowshroom_stem_stripped", ItemGroup.BUILDING_BLOCKS, new HugeMushroomBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD).lightValue(8)));
        BLOCK_STRIPPING_MAP.put(GLOWSHROOM_STEM, GLOWSHROOM_STEM_STRIPPED);
        GLOWSHROOM_BOOKSHELF = registerCompatBlock("glowshroom_bookshelf", ItemGroup.BUILDING_BLOCKS, ModCompat.isVariantBookshelvesModLoaded(), new BookshelfBlock(Block.Properties.from(Blocks.BOOKSHELF).lightValue(8)));
        GLOWSHROOM_BUTTON = registerBlock("glowshroom_button", ItemGroup.REDSTONE, new MushroomWoodButtonBlock(8));
        GLOWSHROOM_CHEST = registerCompatBlock("glowshroom_chest", ItemGroup.DECORATIONS, ModCompat.isVariantChestsModLoaded(), new VariantChestBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.from(Blocks.CHEST).lightValue(8)));
        GLOWSHROOM_CHEST_TRAPPED = registerCompatBlock("glowshroom_chest_trapped", ItemGroup.REDSTONE, ModCompat.isVariantTrappedChestsModLoaded(), new VariantTrappedChestBlock(MushroomWoodType.GLOWSHROOM, Block.Properties.from(Blocks.TRAPPED_CHEST).lightValue(8)));
        GLOWSHROOM_DOOR = registerBlock("glowshroom_door", ItemGroup.REDSTONE, new MushroomDoorBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_FENCE = registerBlock("glowshroom_fence", ItemGroup.DECORATIONS, new FenceBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_FENCE_GATE = registerBlock("glowshroom_fence_gate", ItemGroup.REDSTONE, new FenceGateBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_LADDER = registerCompatBlock("glowshroom_ladder", ItemGroup.DECORATIONS, ModCompat.isVariantLaddersModLoaded(), new MushroomLadderBlock(Block.Properties.from(Blocks.LADDER).lightValue(8)));
        GLOWSHROOM_PLANKS = registerBlock("glowshroom_planks", ItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_PRESSURE_PLATE = registerBlock("glowshroom_pressure_plate", ItemGroup.REDSTONE, new MushroomWoodPressurePlateBlock(8));
        GLOWSHROOM_SLAB = registerBlock("glowshroom_slab", ItemGroup.BUILDING_BLOCKS, new SlabBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_STAIRS = registerBlock("glowshroom_stairs", ItemGroup.BUILDING_BLOCKS, new StairsBlock(() -> GLOWSHROOM_PLANKS.getDefaultState(), Block.Properties.from(GLOWSHROOM_PLANKS)));
        GLOWSHROOM_TRAPDOOR = registerBlock("glowshroom_trapdoor", ItemGroup.REDSTONE, new MushroomTrapdoorBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_VERTICAL_PLANKS = registerCompatBlock("glowshroom_vertical_planks", ItemGroup.BUILDING_BLOCKS, ModCompat.isVerticalPlanksModLoaded(), new VerticalPlanksBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_VERTICAL_SLAB = registerCompatBlock("glowshroom_vertical_slab", ItemGroup.BUILDING_BLOCKS, ModCompat.isVerticalSlabsModLoaded(), new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_CAP_BUTTON = registerBlock("glowshroom_cap_button", ItemGroup.REDSTONE, new MushroomCapButtonBlock(8));
        GLOWSHROOM_CAP_CARPET = registerBlock("glowshroom_cap_carpet", ItemGroup.DECORATIONS, new MushroomCarpetBlock(DyeColor.BLUE, Block.Properties.create(Material.CARPET, MaterialColor.BLUE).hardnessAndResistance(0.1F).sound(SoundType.CLOTH).lightValue(8)));
        GLOWSHROOM_CAP_PRESSURE_PLATE = registerBlock("glowshroom_cap_pressure_plate", ItemGroup.REDSTONE, new MushroomCapPressurePlateBlock(8));

        POISONOUS_MUSHROOM = registerBlock("poisonous_mushroom", ItemGroup.DECORATIONS, new PoisonousMushroomBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT)));
        POISONOUS_MUSHROOM_POTTED = registerBlock("poisonous_mushroom_potted", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> POISONOUS_MUSHROOM, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F).notSolid()));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(POISONOUS_MUSHROOM.getRegistryName()), () -> POISONOUS_MUSHROOM_POTTED);
        POISONOUS_MUSHROOM_CAP = registerBlock("poisonous_mushroom_cap", ItemGroup.DECORATIONS, new PoisonousMushroomCap(MushroomType.POISONOUS_MUSHROOM, Block.Properties.create(Material.WOOD, MaterialColor.PURPLE).hardnessAndResistance(0.2F).sound(SoundType.CLOTH)));
        POISONOUS_MUSHROOM_STEM = registerBlock("poisonous_mushroom_stem", ItemGroup.BUILDING_BLOCKS, new MushroomStemBlock(MushroomWoodType.POISONOUS_MUSHROOM, Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_STEM_STRIPPED = registerBlock("poisonous_mushroom_stem_stripped", ItemGroup.BUILDING_BLOCKS, new HugeMushroomBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
        BLOCK_STRIPPING_MAP.put(POISONOUS_MUSHROOM_STEM, POISONOUS_MUSHROOM_STEM_STRIPPED);
        POISONOUS_MUSHROOM_BOOKSHELF = registerCompatBlock("poisonous_mushroom_bookshelf", ItemGroup.BUILDING_BLOCKS, ModCompat.isVariantBookshelvesModLoaded(), new BookshelfBlock(Block.Properties.from(Blocks.BOOKSHELF)));
        POISONOUS_MUSHROOM_BUTTON = registerBlock("poisonous_mushroom_button", ItemGroup.REDSTONE, new MushroomWoodButtonBlock());
        POISONOUS_MUSHROOM_CHEST = registerCompatBlock("poisonous_mushroom_chest", ItemGroup.DECORATIONS, ModCompat.isVariantChestsModLoaded(), new VariantChestBlock(MushroomWoodType.POISONOUS_MUSHROOM, Block.Properties.from(Blocks.CHEST)));
        POISONOUS_MUSHROOM_CHEST_TRAPPED = registerCompatBlock("poisonous_mushroom_chest_trapped", ItemGroup.REDSTONE, ModCompat.isVariantTrappedChestsModLoaded(), new VariantTrappedChestBlock(MushroomWoodType.POISONOUS_MUSHROOM, Block.Properties.from(Blocks.TRAPPED_CHEST)));
        POISONOUS_MUSHROOM_DOOR = registerBlock("poisonous_mushroom_door", ItemGroup.REDSTONE, new MushroomDoorBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_FENCE = registerBlock("poisonous_mushroom_fence", ItemGroup.DECORATIONS, new FenceBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_FENCE_GATE = registerBlock("poisonous_mushroom_fence_gate", ItemGroup.REDSTONE, new FenceGateBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_LADDER = registerCompatBlock("poisonous_mushroom_ladder", ItemGroup.DECORATIONS, ModCompat.isVariantLaddersModLoaded(), new MushroomLadderBlock(Block.Properties.from(Blocks.LADDER)));
        POISONOUS_MUSHROOM_PLANKS = registerBlock("poisonous_mushroom_planks", ItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_PRESSURE_PLATE = registerBlock("poisonous_mushroom_pressure_plate", ItemGroup.REDSTONE, new MushroomWoodPressurePlateBlock());
        POISONOUS_MUSHROOM_SLAB = registerBlock("poisonous_mushroom_slab", ItemGroup.BUILDING_BLOCKS, new SlabBlock(Block.Properties.from(POISONOUS_MUSHROOM_PLANKS)));
        POISONOUS_MUSHROOM_STAIRS = registerBlock("poisonous_mushroom_stairs", ItemGroup.BUILDING_BLOCKS, new StairsBlock(() -> POISONOUS_MUSHROOM_PLANKS.getDefaultState(), Block.Properties.from(POISONOUS_MUSHROOM_PLANKS)));
        POISONOUS_MUSHROOM_TRAPDOOR = registerBlock("poisonous_mushroom_trapdoor", ItemGroup.REDSTONE, new MushroomTrapdoorBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_VERTICAL_PLANKS = registerCompatBlock("poisonous_mushroom_vertical_planks", ItemGroup.BUILDING_BLOCKS, ModCompat.isVerticalPlanksModLoaded(), new VerticalPlanksBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_VERTICAL_SLAB = registerCompatBlock("poisonous_mushroom_vertical_slab", ItemGroup.BUILDING_BLOCKS, ModCompat.isVerticalSlabsModLoaded(), new VerticalSlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        POISONOUS_MUSHROOM_CAP_BUTTON = registerBlock("poisonous_mushroom_cap_button", ItemGroup.REDSTONE, new MushroomCapButtonBlock());
        POISONOUS_MUSHROOM_CAP_CARPET = registerBlock("poisonous_mushroom_cap_carpet", ItemGroup.DECORATIONS, new MushroomCarpetBlock(DyeColor.PURPLE, Block.Properties.create(Material.CARPET, MaterialColor.PURPLE).hardnessAndResistance(0.1F).sound(SoundType.CLOTH)));
        POISONOUS_MUSHROOM_CAP_PRESSURE_PLATE = registerBlock("poisonous_mushroom_cap_pressure_plate", ItemGroup.REDSTONE, new MushroomCapPressurePlateBlock());

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
        Item.Properties itemProperties = new Item.Properties().group(itemGroup);
        if (block instanceof VariantChestBlock) {
            ((VariantChestBlock)block).setISTER(itemProperties);
        } else if (block instanceof VariantTrappedChestBlock) {
            ((VariantTrappedChestBlock)block).setISTER(itemProperties);
        }
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

        RenderTypeLookup.setRenderLayer(INFESTED_GRASS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(INFESTED_FLOWER, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(INFESTED_FLOWER_POTTED, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(MUSHROOM_LADDER, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(GLOWSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(GLOWSHROOM_LADDER, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(GLOWSHROOM_POTTED, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(POISONOUS_MUSHROOM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(POISONOUS_MUSHROOM_LADDER, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(POISONOUS_MUSHROOM_POTTED, RenderType.getCutout());
    }

    public static void addBlocksToBiomes() {
        //add infested grass to mushroom biomes
        if (Config.INFESTED_GRASS_ENABLED.getValue()) {
            BlockState infestedGrass = INFESTED_GRASS.getDefaultState();
            BlockClusterFeatureConfig config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(infestedGrass), new SimpleBlockPlacer())).tries(32).build();
            Biome[] biomes = {Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE};
            for (Biome biome : biomes) {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(config).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(2))));
            }
        }
        //add infested flower to mushroom biomes
        if (Config.INFESTED_FLOWER_ENABLED.getValue()) {
            BlockState infestedFlower = INFESTED_FLOWER.getDefaultState();
            BlockClusterFeatureConfig config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(infestedFlower), new SimpleBlockPlacer())).tries(32).build();
            Biome[] biomes = {Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE};
            for (Biome biome : biomes) {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(config).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(4))));
            }
        }
    }

}
