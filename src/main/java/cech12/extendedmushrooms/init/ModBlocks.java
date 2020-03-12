package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.*;
import cech12.extendedmushrooms.block.mushrooms.Glowshroom;
import cech12.extendedmushrooms.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.PressurePlateBlock;
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

import static cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks.*;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModBlocks {

    public static Map<Block, Block> BLOCK_STRIPPING_MAP = new HashMap<>();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        INFESTED_GRASS = registerBlock("infested_grass", ItemGroup.DECORATIONS, new InfestedGrassBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)));

        MUSHROOM_BUTTON = registerBlock("mushroom_button", ItemGroup.REDSTONE, new MushroomButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
        MUSHROOM_DOOR = registerBlock("mushroom_door", ItemGroup.REDSTONE, new MushroomDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(3.0F).sound(SoundType.WOOD)));
        MUSHROOM_FENCE = registerBlock("mushroom_fence", ItemGroup.DECORATIONS, new FenceBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_FENCE_GATE = registerBlock("mushroom_fence_gate", ItemGroup.REDSTONE, new FenceGateBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_PLANKS = registerBlock("mushroom_planks", ItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
        MUSHROOM_PRESSURE_PLATE = registerBlock("mushroom_pressure_plate", ItemGroup.REDSTONE, new MushroomPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.WOOL).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
        MUSHROOM_SLAB = registerBlock("mushroom_slab", ItemGroup.BUILDING_BLOCKS, new SlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_STAIRS = registerBlock("mushroom_stairs", ItemGroup.BUILDING_BLOCKS, new StairsBlock(() -> MUSHROOM_PLANKS.getDefaultState(), Block.Properties.from(MUSHROOM_PLANKS)));
        MUSHROOM_TRAPDOOR = registerBlock("mushroom_trapdoor", ItemGroup.REDSTONE, new MushroomTrapdoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(3.0F).sound(SoundType.WOOD)));
        STRIPPED_MUSHROOM_STEM = registerBlock("stripped_mushroom_stem", ItemGroup.BUILDING_BLOCKS, new HugeMushroomBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
        BLOCK_STRIPPING_MAP.put(Blocks.MUSHROOM_STEM, STRIPPED_MUSHROOM_STEM);

        BROWN_MUSHROOM_BUTTON = registerBlock("brown_mushroom_button", ItemGroup.REDSTONE, new MushroomButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));
        BROWN_MUSHROOM_CARPET = registerBlock("brown_mushroom_carpet", ItemGroup.DECORATIONS, new MushroomCarpetBlock(DyeColor.BROWN, Block.Properties.create(Material.CARPET, MaterialColor.BROWN).hardnessAndResistance(0.1F).sound(SoundType.CLOTH)));
        BROWN_MUSHROOM_PRESSURE_PLATE = registerBlock("brown_mushroom_pressure_plate", ItemGroup.REDSTONE, new MushroomPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOL, MaterialColor.WOOL).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));
        RED_MUSHROOM_BUTTON = registerBlock("red_mushroom_button", ItemGroup.REDSTONE, new MushroomButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));
        RED_MUSHROOM_CARPET = registerBlock("red_mushroom_carpet", ItemGroup.DECORATIONS, new MushroomCarpetBlock(DyeColor.RED, Block.Properties.create(Material.CARPET, MaterialColor.RED).hardnessAndResistance(0.1F).sound(SoundType.CLOTH)));
        RED_MUSHROOM_PRESSURE_PLATE = registerBlock("red_mushroom_pressure_plate", ItemGroup.REDSTONE, new MushroomPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOL, MaterialColor.WOOL).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));

        GLOWSHROOM = registerBlock("glowshroom", ItemGroup.DECORATIONS, new EMMushroomBlock(new Glowshroom(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT).lightValue(8)));
        GLOWSHROOM_CAP = registerBlock("glowshroom_cap", ItemGroup.DECORATIONS, new HugeMushroomBlock(Block.Properties.create(Material.WOOL).hardnessAndResistance(0.2F).sound(SoundType.CLOTH).lightValue(8)));
        GLOWSHROOM_STEM = registerBlock("glowshroom_stem", ItemGroup.BUILDING_BLOCKS, new HugeMushroomBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_STEM_STRIPPED = registerBlock("glowshroom_stem_stripped", ItemGroup.BUILDING_BLOCKS, new HugeMushroomBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD).lightValue(8)));
        BLOCK_STRIPPING_MAP.put(GLOWSHROOM_STEM, GLOWSHROOM_STEM_STRIPPED);
        GLOWSHROOM_BUTTON = registerBlock("glowshroom_button", ItemGroup.REDSTONE, new MushroomButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_DOOR = registerBlock("glowshroom_door", ItemGroup.REDSTONE, new MushroomDoorBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_FENCE = registerBlock("glowshroom_fence", ItemGroup.DECORATIONS, new FenceBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_FENCE_GATE = registerBlock("glowshroom_fence_gate", ItemGroup.REDSTONE, new FenceGateBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_PLANKS = registerBlock("glowshroom_planks", ItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.2F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_PRESSURE_PLATE = registerBlock("glowshroom_pressure_plate", ItemGroup.REDSTONE, new MushroomPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.WOOL).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_SLAB = registerBlock("glowshroom_slab", ItemGroup.BUILDING_BLOCKS, new SlabBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_STAIRS = registerBlock("glowshroom_stairs", ItemGroup.BUILDING_BLOCKS, new StairsBlock(() -> GLOWSHROOM_PLANKS.getDefaultState(), Block.Properties.from(GLOWSHROOM_PLANKS)));
        GLOWSHROOM_TRAPDOOR = registerBlock("glowshroom_trapdoor", ItemGroup.REDSTONE, new MushroomTrapdoorBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD).lightValue(8)));
        GLOWSHROOM_CAP_BUTTON = registerBlock("glowshroom_cap_button", ItemGroup.REDSTONE, new MushroomButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH).lightValue(8)));
        GLOWSHROOM_CAP_CARPET = registerBlock("glowshroom_cap_carpet", ItemGroup.DECORATIONS, new MushroomCarpetBlock(DyeColor.BROWN, Block.Properties.create(Material.CARPET, MaterialColor.BLUE).hardnessAndResistance(0.1F).sound(SoundType.CLOTH).lightValue(8)));
        GLOWSHROOM_CAP_PRESSURE_PLATE = registerBlock("glowshroom_cap_pressure_plate", ItemGroup.REDSTONE, new MushroomPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOL).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH).lightValue(8)));

    }

    private static Block registerBlock(String name, ItemGroup itemGroup, Block block) {
        BlockItem itemBlock = new BlockItem(block, new Item.Properties().group(itemGroup));
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
        RenderTypeLookup.setRenderLayer(GLOWSHROOM, RenderType.getCutout());
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
    }

}
