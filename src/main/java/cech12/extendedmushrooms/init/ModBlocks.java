package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.IBlockItemProperty;
import cech12.extendedmushrooms.block.MushroomPlanksBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid=ExtendedMushrooms.MOD_ID)
public final class ModBlocks {

    private static final Block[] blocks = {
            new MushroomPlanksBlock()
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
            if (block instanceof IBlockItemProperty) {
                Item item = new BlockItem(block, ((IBlockItemProperty) block).getBlockItemProperties());
                ResourceLocation registryName = block.getRegistryName();
                if (registryName != null) {
                    item.setRegistryName(registryName);
                }
                registry.register(item);
            }
        }
    }


}
