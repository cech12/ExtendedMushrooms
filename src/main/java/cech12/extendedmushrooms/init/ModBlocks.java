package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid=ExtendedMushrooms.MOD_ID)
public final class ModBlocks {

    public static final Block MUSHROOM_PLANKS = new MushroomPlanksBlock();
    public static final Block STRIPPED_MUSHROOM_STEM = new StrippedMushroomStem();

    private static final Block[] blocks = {
            //overriding Minecraft mushrooms (a bit hacky :D)
            //new ExtendedMushroomBlock(Blocks.BROWN_MUSHROOM),
            //new ExtendedMushroomBlock(Blocks.RED_MUSHROOM),
            //mod blocks
            new MushroomButtonBlock(),
            new MushroomDoorBlock(),
            new MushroomFenceBlock(),
            new MushroomFenceGateBlock(),
            MUSHROOM_PLANKS,
            new MushroomPressurePlateBlock(),
            new MushroomSlabBlock(),
            new MushroomStairsBlock(),
            new MushroomTrapdoorBlock(),
            STRIPPED_MUSHROOM_STEM
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

    /**
     * Add stripping behaviour to mushroom stems
     */
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockState blockState = event.getWorld().getBlockState(event.getPos());
        ItemStack itemStack = event.getPlayer().getHeldItem(event.getHand());
        //check for mushroom stem and axe
        if (blockState.getBlock() == Blocks.MUSHROOM_STEM && itemStack.getItem() instanceof AxeItem) {
            //play sound
            event.getWorld().playSound(event.getPlayer(), event.getPos(), SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!event.getWorld().isRemote) {
                //replace block
                event.getWorld().setBlockState(event.getPos(), STRIPPED_MUSHROOM_STEM.getDefaultState(), 11);
                //do the item damage
                if (event.getPlayer() != null) {
                    itemStack.damageItem(1, event.getPlayer(), (p_220040_1_) -> {
                        p_220040_1_.sendBreakAnimation(event.getHand());
                    });
                }
            }
            event.setCanceled(true);
            event.setCancellationResult(ActionResultType.SUCCESS);
        }
    }

    // wait for Pull Request: https://github.com/MinecraftForge/MinecraftForge/pull/6212
    /*
    @SubscribeEvent
    public static void onRandomTick(BlockEvent.RandomTickEvent event) {
        if (event.getState().getBlock() instanceof MushroomBlock) {
            if (event.getRandom().nextInt(7) == 0) {
                ((MushroomBlock) block).grow(event.getWorld().getWorld(), event.getRandom(), event.getPos(), event.getState());
                event.setCanceled(true);
            }
        }
    }
     */

}
