package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid=ExtendedMushrooms.MOD_ID)
public final class ModBlocks {

    public static final Block MUSHROOM_PLANKS = new MushroomPlanksBlock();

    private static final Block[] blocks = {
            //overriding Minecraft mushrooms (a bit hacky :D)
            new ExtendedMushroomBlock(Blocks.BROWN_MUSHROOM),
            new ExtendedMushroomBlock(Blocks.RED_MUSHROOM),
            //mod blocks
            new MushroomButtonBlock(),
            new MushroomDoorBlock(),
            new MushroomFenceBlock(),
            new MushroomFenceGateBlock(),
            MUSHROOM_PLANKS,
            new MushroomPressurePlateBlock(),
            new MushroomSlabBlock(),
            new MushroomStairsBlock(),
            new MushroomTrapdoorBlock()
    };

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for (Block block : ModBlocks.blocks) {
            event.getRegistry().register(block);
        }
    }

    @SubscribeEvent
    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        for (Block block : ModBlocks.blocks) {
            if (block instanceof IBlockItemGetter) {
                registry.register(((IBlockItemGetter) block).getBlockItem());
            }
        }
    }


}
