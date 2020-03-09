package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import static cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks.*;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModBlocks {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        MUSHROOM_BUTTON = registerBlock("mushroom_button", ItemGroup.REDSTONE, new MushroomButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
        MUSHROOM_DOOR = registerBlock("mushroom_door", ItemGroup.REDSTONE, new MushroomDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(3.0F).sound(SoundType.WOOD)));
        MUSHROOM_FENCE = registerBlock("mushroom_fence", ItemGroup.DECORATIONS, new FenceBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_FENCE_GATE = registerBlock("mushroom_fence_gate", ItemGroup.REDSTONE, new FenceGateBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_PLANKS = registerBlock("mushroom_planks", ItemGroup.BUILDING_BLOCKS, new Block(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
        MUSHROOM_PRESSURE_PLATE = registerBlock("mushroom_pressure_plate", ItemGroup.REDSTONE, new MushroomPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.WOOL).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
        MUSHROOM_SLAB = registerBlock("mushroom_slab", ItemGroup.BUILDING_BLOCKS, new SlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
        MUSHROOM_STAIRS = registerBlock("mushroom_stairs", ItemGroup.BUILDING_BLOCKS, new StairsBlock(() -> MUSHROOM_PLANKS.getDefaultState(), Block.Properties.from(MUSHROOM_PLANKS)));
        MUSHROOM_TRAPDOOR = registerBlock("mushroom_trapdoor", ItemGroup.REDSTONE, new MushroomTrapdoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(3.0F).sound(SoundType.WOOD)));
        STRIPPED_MUSHROOM_STEM = registerBlock("stripped_mushroom_stem", ItemGroup.BUILDING_BLOCKS, new HugeMushroomBlock(Block.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));

        BROWN_MUSHROOM_BUTTON = registerBlock("brown_mushroom_button", ItemGroup.REDSTONE, new MushroomButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));
        BROWN_MUSHROOM_CARPET = registerBlock("brown_mushroom_carpet", ItemGroup.DECORATIONS, new MushroomCarpetBlock(DyeColor.BROWN, Block.Properties.create(Material.CARPET, MaterialColor.BROWN).hardnessAndResistance(0.1F).sound(SoundType.CLOTH)));
        BROWN_MUSHROOM_PRESSURE_PLATE = registerBlock("brown_mushroom_pressure_plate", ItemGroup.REDSTONE, new MushroomPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOL, MaterialColor.WOOL).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));
        RED_MUSHROOM_BUTTON = registerBlock("red_mushroom_button", ItemGroup.REDSTONE, new MushroomButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));
        RED_MUSHROOM_CARPET = registerBlock("red_mushroom_carpet", ItemGroup.DECORATIONS, new MushroomCarpetBlock(DyeColor.RED, Block.Properties.create(Material.CARPET, MaterialColor.RED).hardnessAndResistance(0.1F).sound(SoundType.CLOTH)));
        RED_MUSHROOM_PRESSURE_PLATE = registerBlock("red_mushroom_pressure_plate", ItemGroup.REDSTONE, new MushroomPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOL, MaterialColor.WOOL).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH)));

    }

    public static Block registerBlock(String name, ItemGroup itemGroup, Block block) {
        BlockItem itemBlock = new BlockItem(block, new Item.Properties().group(itemGroup));
        block.setRegistryName(name);
        itemBlock.setRegistryName(name);
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(itemBlock);
        return block;
    }

}
