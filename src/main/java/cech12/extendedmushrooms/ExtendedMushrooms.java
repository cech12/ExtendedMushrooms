package cech12.extendedmushrooms;

import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import cech12.extendedmushrooms.init.ModEntities;
import cech12.extendedmushrooms.init.ModVanillaCompat;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks.STRIPPED_MUSHROOM_STEM;

@Mod(ExtendedMushrooms.MOD_ID)
@Mod.EventBusSubscriber
public class ExtendedMushrooms {

    public static final String MOD_ID = "extendedmushrooms";

    public ExtendedMushrooms() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ModVanillaCompat.setup();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ModEntities.setupRenderers();
    }

    /**
     * Add stripping behaviour to mushroom stems
     */
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockState blockState = event.getWorld().getBlockState(event.getPos());
        ItemStack itemStack = event.getPlayer().getHeldItem(event.getHand());
        //check for mushroom stem and axe
        if (blockState.getBlock() == Blocks.MUSHROOM_STEM && itemStack.getToolTypes().contains(ToolType.AXE)) {
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

    /**
     * Remove dye behaviour from mushroom sheeps.
     */
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getPlayer().getHeldItem(event.getHand());
        Entity entity = event.getTarget();
        //check for dye item and mushroom sheep entity
        if (entity instanceof MushroomSheepEntity && itemStack.getItem() instanceof DyeItem) {
            event.setCanceled(true);
            event.setCancellationResult(ActionResultType.FAIL);
        }
    }

}
