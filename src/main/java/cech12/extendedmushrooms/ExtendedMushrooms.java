package cech12.extendedmushrooms;

import cech12.extendedmushrooms.init.ModVanillaCompat;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks.STRIPPED_MUSHROOM_STEM;

@Mod(ExtendedMushrooms.MOD_ID)
@Mod.EventBusSubscriber
public class ExtendedMushrooms {

    public static final String MOD_ID = "extendedmushrooms";

    public ExtendedMushrooms() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModVanillaCompat.setup();
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
