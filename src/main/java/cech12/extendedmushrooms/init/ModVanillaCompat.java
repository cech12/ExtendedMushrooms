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

        registerFlammable(Blocks.BROWN_MUSHROOM_BLOCK, 30, 60);
        registerFlammable(Blocks.RED_MUSHROOM_BLOCK, 30, 60);
        registerFlammable(Blocks.MUSHROOM_STEM, 5, 5);

        registerFlammable(INFESTED_GRASS, 60, 100);

        registerFlammable(MUSHROOM_FENCE, 5, 20);
        registerFlammable(MUSHROOM_FENCE_GATE, 5, 20);
        registerFlammable(MUSHROOM_PLANKS, 5, 20);
        registerFlammable(MUSHROOM_SLAB, 5, 20);
        registerFlammable(MUSHROOM_STAIRS, 5, 20);
        registerFlammable(STRIPPED_MUSHROOM_STEM, 5, 5);

        registerFlammable(GLOWSHROOM_CAP, 30, 60);
        registerFlammable(GLOWSHROOM_CAP_CARPET, 60, 20);
        registerFlammable(GLOWSHROOM_STEM, 5, 5);
        registerFlammable(GLOWSHROOM_STEM_STRIPPED, 5, 5);
        registerFlammable(GLOWSHROOM_FENCE, 5, 20);
        registerFlammable(GLOWSHROOM_FENCE_GATE, 5, 20);
        registerFlammable(GLOWSHROOM_PLANKS, 5, 20);
        registerFlammable(GLOWSHROOM_SLAB, 5, 20);
        registerFlammable(GLOWSHROOM_STAIRS, 5, 20);

        registerFlammable(POISONOUS_MUSHROOM_CAP, 30, 60);
        registerFlammable(POISONOUS_MUSHROOM_CAP_CARPET, 60, 20);
        registerFlammable(POISONOUS_MUSHROOM_STEM, 5, 5);
        registerFlammable(POISONOUS_MUSHROOM_STEM_STRIPPED, 5, 5);
        registerFlammable(POISONOUS_MUSHROOM_FENCE, 5, 20);
        registerFlammable(POISONOUS_MUSHROOM_FENCE_GATE, 5, 20);
        registerFlammable(POISONOUS_MUSHROOM_PLANKS, 5, 20);
        registerFlammable(POISONOUS_MUSHROOM_SLAB, 5, 20);
        registerFlammable(POISONOUS_MUSHROOM_STAIRS, 5, 20);

        registerCompostable(0.3F, INFESTED_GRASS);

        registerCompostable(0.15F, BROWN_MUSHROOM_BUTTON);
        registerCompostable(0.3F, BROWN_MUSHROOM_CARPET);
        registerCompostable(0.15F, BROWN_MUSHROOM_PRESSURE_PLATE);
        registerCompostable(0.15F, RED_MUSHROOM_BUTTON);
        registerCompostable(0.3F, RED_MUSHROOM_CARPET);
        registerCompostable(0.15F, RED_MUSHROOM_PRESSURE_PLATE);

        registerCompostable(0.15F, MUSHROOM_BUTTON);
        registerCompostable(0.3F, MUSHROOM_DOOR);
        registerCompostable(0.3F, MUSHROOM_FENCE);
        registerCompostable(0.3F, MUSHROOM_FENCE_GATE);
        registerCompostable(0.3F, MUSHROOM_PLANKS);
        registerCompostable(0.15F, MUSHROOM_PRESSURE_PLATE);
        registerCompostable(0.15F, MUSHROOM_SLAB);
        registerCompostable(0.15F, MUSHROOM_STAIRS);
        registerCompostable(0.15F, MUSHROOM_TRAPDOOR);
        registerCompostable(0.65F, STRIPPED_MUSHROOM_STEM);

        registerCompostable(0.65F, GLOWSHROOM);
        registerCompostable(0.85F, GLOWSHROOM_CAP);
        registerCompostable(0.15F, GLOWSHROOM_CAP_BUTTON);
        registerCompostable(0.3F, GLOWSHROOM_CAP_CARPET);
        registerCompostable(0.15F, GLOWSHROOM_CAP_PRESSURE_PLATE);
        registerCompostable(0.65F, GLOWSHROOM_STEM);
        registerCompostable(0.65F, GLOWSHROOM_STEM_STRIPPED);
        registerCompostable(0.15F, GLOWSHROOM_BUTTON);
        registerCompostable(0.3F, GLOWSHROOM_DOOR);
        registerCompostable(0.3F, GLOWSHROOM_FENCE);
        registerCompostable(0.3F, GLOWSHROOM_FENCE_GATE);
        registerCompostable(0.3F, GLOWSHROOM_PLANKS);
        registerCompostable(0.15F, GLOWSHROOM_PRESSURE_PLATE);
        registerCompostable(0.15F, GLOWSHROOM_SLAB);
        registerCompostable(0.15F, GLOWSHROOM_STAIRS);
        registerCompostable(0.15F, GLOWSHROOM_TRAPDOOR);

        registerCompostable(0.65F, POISONOUS_MUSHROOM);
        registerCompostable(0.85F, POISONOUS_MUSHROOM_CAP);
        registerCompostable(0.15F, POISONOUS_MUSHROOM_CAP_BUTTON);
        registerCompostable(0.3F, POISONOUS_MUSHROOM_CAP_CARPET);
        registerCompostable(0.15F, POISONOUS_MUSHROOM_CAP_PRESSURE_PLATE);
        registerCompostable(0.65F, POISONOUS_MUSHROOM_STEM);
        registerCompostable(0.65F, POISONOUS_MUSHROOM_STEM_STRIPPED);
        registerCompostable(0.15F, POISONOUS_MUSHROOM_BUTTON);
        registerCompostable(0.3F, POISONOUS_MUSHROOM_DOOR);
        registerCompostable(0.3F, POISONOUS_MUSHROOM_FENCE);
        registerCompostable(0.3F, POISONOUS_MUSHROOM_FENCE_GATE);
        registerCompostable(0.3F, POISONOUS_MUSHROOM_PLANKS);
        registerCompostable(0.15F, POISONOUS_MUSHROOM_PRESSURE_PLATE);
        registerCompostable(0.15F, POISONOUS_MUSHROOM_SLAB);
        registerCompostable(0.15F, POISONOUS_MUSHROOM_STAIRS);
        registerCompostable(0.15F, POISONOUS_MUSHROOM_TRAPDOOR);

        registerCompostable(0.65F, GRILLED_MUSHROOM);
        registerCompostable(0.85F, MUSHROOM_BREAD);
        registerCompostable(0.15F, MUSHROOM_SPORES);

    }

    public static void registerCompostable(float chance, IItemProvider itemIn) {
        ComposterBlock.CHANCES.put(itemIn.asItem(), chance);
    }

    public static void registerFlammable(Block blockIn, int encouragement, int flammability) {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(blockIn, encouragement, flammability);
    }


}
