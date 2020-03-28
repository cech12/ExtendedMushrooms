package cech12.extendedmushrooms;

import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.entity.ai.goal.EatMushroomGoal;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModEntities;
import cech12.extendedmushrooms.init.ModFeatures;
import cech12.extendedmushrooms.init.ModVanillaCompat;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExtendedMushrooms.MOD_ID)
@Mod.EventBusSubscriber
public class ExtendedMushrooms {

    public static final String MOD_ID = "extendedmushrooms";

    public ExtendedMushrooms() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON, "extendedmushrooms-common.toml");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ModVanillaCompat.setup();
        ModBlocks.addBlocksToBiomes();
        ModEntities.addEntitiesToBiomes();
        ModFeatures.addFeaturesToBiomes();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ModBlocks.setupRenderLayers();
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
        if (itemStack.getToolTypes().contains(ToolType.AXE)) {
            //get stripped block from stripping map
            Block strippedBlock = ModBlocks.BLOCK_STRIPPING_MAP.get(blockState.getBlock());
            if (strippedBlock != null) {
                //play sound
                event.getWorld().playSound(event.getPlayer(), event.getPos(), SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!event.getWorld().isRemote) {
                    //copy block state orientation
                    BlockState strippedBlockState = strippedBlock.getDefaultState();
                    if (blockState.has(HugeMushroomBlock.UP)) strippedBlockState = strippedBlockState.with(HugeMushroomBlock.UP, blockState.get(HugeMushroomBlock.UP));
                    if (blockState.has(HugeMushroomBlock.DOWN)) strippedBlockState = strippedBlockState.with(HugeMushroomBlock.DOWN, blockState.get(HugeMushroomBlock.DOWN));
                    if (blockState.has(HugeMushroomBlock.NORTH)) strippedBlockState = strippedBlockState.with(HugeMushroomBlock.NORTH, blockState.get(HugeMushroomBlock.NORTH));
                    if (blockState.has(HugeMushroomBlock.EAST)) strippedBlockState = strippedBlockState.with(HugeMushroomBlock.EAST, blockState.get(HugeMushroomBlock.EAST));
                    if (blockState.has(HugeMushroomBlock.SOUTH)) strippedBlockState = strippedBlockState.with(HugeMushroomBlock.SOUTH, blockState.get(HugeMushroomBlock.SOUTH));
                    if (blockState.has(HugeMushroomBlock.WEST)) strippedBlockState = strippedBlockState.with(HugeMushroomBlock.WEST, blockState.get(HugeMushroomBlock.WEST));
                    //replace block
                    event.getWorld().setBlockState(event.getPos(), strippedBlockState, 11);
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

    /**
     * Add eat mushroom goal to sheep entities when configured.
     */
    @SubscribeEvent
    public static void onLivingSpawn(LivingSpawnEvent.EnteringChunk event) {
        if (Config.SHEEP_EAT_MUSHROOM_FROM_GROUND_ENABLED.getValue()) {
            if (event.getEntity() instanceof SheepEntity) { //also mushroom sheep
                SheepEntity sheep = ((SheepEntity) event.getEntity());
                sheep.goalSelector.addGoal(5, new EatMushroomGoal(sheep));
            }
        }
    }

}
