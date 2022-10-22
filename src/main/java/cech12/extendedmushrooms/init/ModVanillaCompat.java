package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.block.EMMushroomBlock;
import cech12.extendedmushrooms.block.InfestedGrassBlock;
import cech12.extendedmushrooms.block.MushroomPlanksBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomCapBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoolCarpetBlock;

public class ModVanillaCompat {

    /**
     * Should only be called in FMLCommonSetupEvent#enqueueWork. Reason: <a href="https://forums.minecraftforge.net/topic/98470-solved-registering-custom-flowers-as-compostable/">ComposterBlock.COMPOSTABLES is not thread safe</a>
     */
    public static void setup() {
        registerFlammable(Blocks.BROWN_MUSHROOM_BLOCK, 30, 60);
        registerFlammable(Blocks.RED_MUSHROOM_BLOCK, 30, 60);
        registerFlammable(Blocks.MUSHROOM_STEM, 5, 5);

        registerCompostable(0.65F, ModItems.GRILLED_MUSHROOM.get());
        registerCompostable(0.85F, ModItems.MUSHROOM_BREAD.get());
        registerCompostable(0.15F, ModItems.MUSHROOM_SPORES.get());

        ModBlocks.BLOCKS.getEntries().forEach(blockRegistryObject -> {
            Block block = blockRegistryObject.get();
            if (block instanceof ButtonBlock || block instanceof PressurePlateBlock || block instanceof SlabBlock
                    || block instanceof StairBlock || block instanceof TrapDoorBlock) {
                registerCompostable(0.15F, block);
            } else if (block instanceof WoolCarpetBlock || block instanceof DoorBlock || block instanceof FenceBlock
                    || block instanceof FenceGateBlock || block instanceof MushroomPlanksBlock || block instanceof InfestedGrassBlock) {
                registerCompostable(0.3F, block);
            } else if (block instanceof MushroomStemBlock || block instanceof EMMushroomBlock) {
                registerCompostable(0.65F, block);
            } else if (block instanceof MushroomCapBlock) {
                registerCompostable(0.85F, block);
            }
        });
    }

    private static void registerCompostable(float chance, ItemLike itemIn) {
        ComposterBlock.COMPOSTABLES.put(itemIn.asItem(), chance);
    }

    private static void registerFlammable(Block blockIn, int encouragement, int flammability) {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFlammable(blockIn, encouragement, flammability);
    }

}
