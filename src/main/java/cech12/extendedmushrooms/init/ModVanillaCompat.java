package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.block.EMMushroomBlock;
import cech12.extendedmushrooms.block.InfestedGrassBlock;
import cech12.extendedmushrooms.block.MushroomPlanksBlock;
import cech12.extendedmushrooms.block.VerticalPlanksBlock;
import cech12.extendedmushrooms.block.VerticalSlabBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomCapBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoolCarpetBlock;


public class ModVanillaCompat {

    public static void setup() {
        registerCompostable(0.65F, ModItems.GRILLED_MUSHROOM.get());
        registerCompostable(0.85F, ModItems.MUSHROOM_BREAD.get());
        registerCompostable(0.15F, ModItems.MUSHROOM_SPORES.get());

        ModBlocks.BLOCKS.getEntries().forEach(blockRegistryObject -> {
            Block block = blockRegistryObject.get();
            if (block instanceof ButtonBlock || block instanceof PressurePlateBlock || block instanceof SlabBlock
                    || block instanceof StairBlock || block instanceof TrapDoorBlock || block instanceof VerticalSlabBlock) {
                registerCompostable(0.15F, block);
            } else if (block instanceof WoolCarpetBlock || block instanceof DoorBlock || block instanceof FenceBlock
                    || block instanceof FenceGateBlock || block instanceof MushroomPlanksBlock || block instanceof VerticalPlanksBlock
                    || block instanceof InfestedGrassBlock) {
                registerCompostable(0.3F, block);
            } else if (block instanceof MushroomStemBlock || block instanceof EMMushroomBlock) {
                registerCompostable(0.65F, block);
            } else if (block instanceof MushroomCapBlock) {
                registerCompostable(0.85F, block);
            }
        });
    }

    public static void registerCompostable(float chance, ItemLike itemIn) {
        ComposterBlock.COMPOSTABLES.put(itemIn.asItem(), chance);
    }

}
