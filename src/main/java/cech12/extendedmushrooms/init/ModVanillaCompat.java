package cech12.extendedmushrooms.init;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.util.IItemProvider;

import static cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks.*;
import static cech12.extendedmushrooms.api.item.ExtendedMushroomsItems.*;

public class ModVanillaCompat {

    public static void setup() {

        //registerStrippable(Blocks.MUSHROOM_STEM, STRIPPED_MUSHROOM_STEM);

        registerFlammable(Blocks.BROWN_MUSHROOM_BLOCK, 30, 60);
        registerFlammable(Blocks.RED_MUSHROOM_BLOCK, 30, 60);
        registerFlammable(Blocks.MUSHROOM_STEM, 5, 5);

        registerFlammable(MUSHROOM_FENCE, 5, 20);
        registerFlammable(MUSHROOM_FENCE_GATE, 5, 20);
        registerFlammable(MUSHROOM_PLANKS, 5, 20);
        registerFlammable(MUSHROOM_SLAB, 5, 20);
        registerFlammable(MUSHROOM_STAIRS, 5, 20);
        registerFlammable(STRIPPED_MUSHROOM_STEM, 5, 5);

        registerCompostable(0.15F, MUSHROOM_BUTTON);
        registerCompostable(0.3F, MUSHROOM_DOOR);
        registerCompostable(0.3F, MUSHROOM_FENCE);
        registerCompostable(0.3F, MUSHROOM_FENCE_GATE);
        registerCompostable(0.3F, MUSHROOM_PLANKS);
        registerCompostable(0.15F, MUSHROOM_PRESSURE_PLATE);
        registerCompostable(0.15F, MUSHROOM_SLAB);
        registerCompostable(0.15F, MUSHROOM_STAIRS);
        registerCompostable(0.15F, MUSHROOM_TRAPDOOR);
        registerCompostable(0.85F, STRIPPED_MUSHROOM_STEM);

        registerCompostable(0.65F, GRILLED_MUSHROOM);
        registerCompostable(0.85F, MUSHROOM_BREAD);
        registerCompostable(0.15F, MUSHROOM_SPORES);

    }

    //TODO AxeItem.BLOCK_STRIPPING_MAP is protected
    // realized in ExtendedMushrooms#onRightClickBlock
    /*
    public static void registerStrippable(Block log, Block stripped_log) {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
        AxeItem.BLOCK_STRIPPING_MAP.put(log, stripped_log);
    }
     */

    public static void registerCompostable(float chance, IItemProvider itemIn) {
        ComposterBlock.CHANCES.put(itemIn.asItem(), chance);
    }

    public static void registerFlammable(Block blockIn, int encouragement, int flammability) {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(blockIn, encouragement, flammability);
    }


}
