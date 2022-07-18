package cech12.extendedmushrooms.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.ItemLike;

import static cech12.extendedmushrooms.init.ModBlocks.*;
import static cech12.extendedmushrooms.init.ModItems.*;


public class ModVanillaCompat {

    public static void setup() {

        registerFlammable(Blocks.BROWN_MUSHROOM_BLOCK, 30, 60);
        registerFlammable(Blocks.RED_MUSHROOM_BLOCK, 30, 60);
        registerFlammable(Blocks.MUSHROOM_STEM, 5, 5);

        registerCompostable(0.3F, INFESTED_GRASS.get());

        registerCompostable(0.15F, BROWN_MUSHROOM_BUTTON.get());
        registerCompostable(0.3F, BROWN_MUSHROOM_CARPET.get());
        registerCompostable(0.15F, BROWN_MUSHROOM_PRESSURE_PLATE.get());
        registerCompostable(0.15F, RED_MUSHROOM_BUTTON.get());
        registerCompostable(0.3F, RED_MUSHROOM_CARPET.get());
        registerCompostable(0.15F, RED_MUSHROOM_PRESSURE_PLATE.get());

        registerCompostable(0.15F, MUSHROOM_BUTTON.get());
        registerCompostable(0.3F, MUSHROOM_DOOR.get());
        registerCompostable(0.3F, MUSHROOM_FENCE.get());
        registerCompostable(0.3F, MUSHROOM_FENCE_GATE.get());
        registerCompostable(0.3F, MUSHROOM_PLANKS.get());
        registerCompostable(0.15F, MUSHROOM_PRESSURE_PLATE.get());
        registerCompostable(0.15F, MUSHROOM_SLAB.get());
        registerCompostable(0.15F, MUSHROOM_STAIRS.get());
        registerCompostable(0.15F, MUSHROOM_TRAPDOOR.get());
        registerCompostable(0.15F, MUSHROOM_VERTICAL_PLANKS.get());
        registerCompostable(0.15F, MUSHROOM_VERTICAL_SLAB.get());
        registerCompostable(0.65F, STRIPPED_MUSHROOM_STEM.get());

        registerCompostable(0.65F, GLOWSHROOM.get());
        registerCompostable(0.85F, GLOWSHROOM_CAP.get());
        registerCompostable(0.15F, GLOWSHROOM_CAP_BUTTON.get());
        registerCompostable(0.3F, GLOWSHROOM_CAP_CARPET.get());
        registerCompostable(0.15F, GLOWSHROOM_CAP_PRESSURE_PLATE.get());
        registerCompostable(0.65F, GLOWSHROOM_STEM.get());
        registerCompostable(0.65F, GLOWSHROOM_STEM_STRIPPED.get());
        registerCompostable(0.15F, GLOWSHROOM_BUTTON.get());
        registerCompostable(0.3F, GLOWSHROOM_DOOR.get());
        registerCompostable(0.3F, GLOWSHROOM_FENCE.get());
        registerCompostable(0.3F, GLOWSHROOM_FENCE_GATE.get());
        registerCompostable(0.3F, GLOWSHROOM_PLANKS.get());
        registerCompostable(0.15F, GLOWSHROOM_PRESSURE_PLATE.get());
        registerCompostable(0.15F, GLOWSHROOM_SLAB.get());
        registerCompostable(0.15F, GLOWSHROOM_STAIRS.get());
        registerCompostable(0.15F, GLOWSHROOM_TRAPDOOR.get());
        registerCompostable(0.15F, GLOWSHROOM_VERTICAL_PLANKS.get());
        registerCompostable(0.15F, GLOWSHROOM_VERTICAL_SLAB.get());

        registerCompostable(0.65F, POISONOUS_MUSHROOM.get());
        registerCompostable(0.85F, POISONOUS_MUSHROOM_CAP.get());
        registerCompostable(0.15F, POISONOUS_MUSHROOM_CAP_BUTTON.get());
        registerCompostable(0.3F, POISONOUS_MUSHROOM_CAP_CARPET.get());
        registerCompostable(0.15F, POISONOUS_MUSHROOM_CAP_PRESSURE_PLATE.get());
        registerCompostable(0.65F, POISONOUS_MUSHROOM_STEM.get());
        registerCompostable(0.65F, POISONOUS_MUSHROOM_STEM_STRIPPED.get());
        registerCompostable(0.15F, POISONOUS_MUSHROOM_BUTTON.get());
        registerCompostable(0.3F, POISONOUS_MUSHROOM_DOOR.get());
        registerCompostable(0.3F, POISONOUS_MUSHROOM_FENCE.get());
        registerCompostable(0.3F, POISONOUS_MUSHROOM_FENCE_GATE.get());
        registerCompostable(0.3F, POISONOUS_MUSHROOM_PLANKS.get());
        registerCompostable(0.15F, POISONOUS_MUSHROOM_PRESSURE_PLATE.get());
        registerCompostable(0.15F, POISONOUS_MUSHROOM_SLAB.get());
        registerCompostable(0.15F, POISONOUS_MUSHROOM_STAIRS.get());
        registerCompostable(0.15F, POISONOUS_MUSHROOM_TRAPDOOR.get());
        registerCompostable(0.15F, POISONOUS_MUSHROOM_VERTICAL_PLANKS.get());
        registerCompostable(0.15F, POISONOUS_MUSHROOM_VERTICAL_SLAB.get());

        registerCompostable(0.65F, GRILLED_MUSHROOM.get());
        registerCompostable(0.85F, MUSHROOM_BREAD.get());
        registerCompostable(0.15F, MUSHROOM_SPORES.get());

    }

    public static void registerCompostable(float chance, ItemLike itemIn) {
        ComposterBlock.COMPOSTABLES.put(itemIn.asItem(), chance);
    }

    public static void registerFlammable(Block blockIn, int encouragement, int flammability) {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFlammable(blockIn, encouragement, flammability);
    }

}
