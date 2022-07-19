package cech12.extendedmushrooms;

import cech12.extendedmushrooms.init.ModRecipeTypes;
import cech12.extendedmushrooms.api.recipe.FairyRingRecipe;
import cech12.extendedmushrooms.block.FairyRingBlock;
import cech12.extendedmushrooms.compat.ModFeatureEnabledCondition;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.entity.ai.goal.EatMushroomGoal;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import cech12.extendedmushrooms.init.ModBlockEntityTypes;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModEntityTypes;
import cech12.extendedmushrooms.init.ModFeatures;
import cech12.extendedmushrooms.init.ModItems;
import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.init.ModVanillaCompat;
import cech12.extendedmushrooms.item.crafting.MushroomBrewingRecipe;
import cech12.extendedmushrooms.loot_modifiers.MushroomCapLootModifier;
import cech12.extendedmushrooms.loot_modifiers.MushroomStemLootModifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

@Mod(ExtendedMushrooms.MOD_ID)
@Mod.EventBusSubscriber
public class ExtendedMushrooms {

    public static final String MOD_ID = "extendedmushrooms";

    // Use for data generation and development
    public static final boolean DEVELOPMENT_MODE = Boolean.parseBoolean(System.getProperty("extendedmushrooms.developmentMode", "false"));

    public ExtendedMushrooms() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON, "extendedmushrooms-common.toml");

        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(eventBus);
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(eventBus);
        ModItems.ITEMS.register(eventBus);
        ModEntityTypes.ENTITY_TYPES.register(eventBus);
        ModFeatures.CONFIGURED_FEATURES.register(eventBus);
        ModFeatures.PLACED_FEATURES.register(eventBus);
        ModRecipeTypes.RECIPE_SERIALIZERS.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addGenericListener(GlobalLootModifierSerializer.class, this::onRegisterModifierSerializers);

        // Register an event with the mod specific event bus for mod own recipes.
        eventBus.addGenericListener(RecipeSerializer.class, this::registerRecipeSerializers);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ModVanillaCompat.setup();

        //add potion recipes
        //BrewingRecipeRegistry.addRecipe(new MushroomBrewingRecipe(ModTags.ForgeItems.MUSHROOMS_GLOWSHROOM, Potions.NIGHT_VISION)); //overpowered
        BrewingRecipeRegistry.addRecipe(new MushroomBrewingRecipe(ModTags.ForgeItems.MUSHROOMS_JUMP_BOOSTING, Potions.LEAPING));
        BrewingRecipeRegistry.addRecipe(new MushroomBrewingRecipe(ModTags.ForgeItems.MUSHROOMS_POISONOUS, Potions.POISON));
        BrewingRecipeRegistry.addRecipe(new MushroomBrewingRecipe(ModTags.ForgeItems.MUSHROOMS_SLOWING_DOWN, Potions.SLOWNESS));
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ModBlocks.setupRenderLayers();
        ModEntityTypes.setupRenderers();
        ModBlockEntityTypes.setupRenderers(event);
    }

    private void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        //serializer for conditions
        CraftingHelper.register(ModFeatureEnabledCondition.Serializer.INSTANCE);

        // let other mods register recipes
        ModRecipeTypes.FAIRY_RING = Registry.register(Registry.RECIPE_TYPE, ModRecipeTypes.FAIRY_RING_ID, new RecipeType<FairyRingRecipe>() {});
    }

    /**
     * Add some loot modifiers to be compatible with other mods and change some loot behaviour of vanilla Minecraft.
     */
    public void onRegisterModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(
                new MushroomCapLootModifier.Serializer().setRegistryName(MOD_ID, "mushroom_cap_harvest")
        );
        event.getRegistry().register(
                new MushroomStemLootModifier.Serializer().setRegistryName(MOD_ID, "mushroom_stem_harvest")
        );
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
        ModEntityTypes.addEntitiesToBiomes(event);
        ModFeatures.addFeaturesToBiomes(event);
    }

    /**
     * Add stripping behaviour to mushroom stems
     */
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockState blockState = event.getWorld().getBlockState(event.getPos());
        ItemStack itemStack = event.getPlayer().getItemInHand(event.getHand());
        //check for mushroom stem and axe
        if (itemStack.canPerformAction(ToolActions.AXE_STRIP)) {
            //get stripped block from stripping map
            Block strippedBlock = ModBlocks.getStrippedBlock(blockState.getBlock());
            if (strippedBlock != null) {
                //play sound
                event.getWorld().playSound(event.getPlayer(), event.getPos(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!event.getWorld().isClientSide) {
                    //copy block state orientation
                    BlockState strippedBlockState = strippedBlock.defaultBlockState();
                    if (blockState.hasProperty(HugeMushroomBlock.UP)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.UP, blockState.getValue(HugeMushroomBlock.UP));
                    if (blockState.hasProperty(HugeMushroomBlock.DOWN)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.DOWN, blockState.getValue(HugeMushroomBlock.DOWN));
                    if (blockState.hasProperty(HugeMushroomBlock.NORTH)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.NORTH, blockState.getValue(HugeMushroomBlock.NORTH));
                    if (blockState.hasProperty(HugeMushroomBlock.EAST)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.EAST, blockState.getValue(HugeMushroomBlock.EAST));
                    if (blockState.hasProperty(HugeMushroomBlock.SOUTH)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.SOUTH, blockState.getValue(HugeMushroomBlock.SOUTH));
                    if (blockState.hasProperty(HugeMushroomBlock.WEST)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.WEST, blockState.getValue(HugeMushroomBlock.WEST));
                    //replace block
                    event.getWorld().setBlock(event.getPos(), strippedBlockState, 11);
                    //do the item damage
                    if (event.getPlayer() != null) {
                        itemStack.hurtAndBreak(1, event.getPlayer(), (player) -> player.broadcastBreakEvent(event.getHand()));
                    }
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }

    /**
     * Remove dye behaviour from mushroom sheeps.
     */
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getPlayer().getItemInHand(event.getHand());
        Entity entity = event.getTarget();
        //check for dye item and mushroom sheep entity
        if (entity instanceof MushroomSheepEntity && itemStack.getItem() instanceof DyeItem) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.FAIL);
        }
    }

    /**
     * Add eat mushroom goal to sheep entities when configured.
     */
    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (Config.SHEEP_EAT_MUSHROOM_FROM_GROUND_ENABLED.get()) {
            if (event.getEntity() instanceof Sheep sheep) { //also mushroom sheep
                sheep.goalSelector.addGoal(5, new EatMushroomGoal(sheep));
            }
        }
    }

    /**
     * Add Fairy Ring generation to all mushroom blocks
     */
    @SubscribeEvent
    public static void onNeighbourChanged(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor world = event.getWorld();
        BlockPos blockPos = event.getPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != ModBlocks.FAIRY_RING.get()) {
            for (Direction direction : event.getNotifiedSides()) {
                BlockPos neighbourPos = blockPos.relative(direction);
                if (world.getBlockState(neighbourPos).getBlock() instanceof MushroomBlock) {
                    //neighbour is mushroom?
                    FairyRingBlock.fairyRingPlaceCheck(world, neighbourPos);
                } else if (world.getBlockState(neighbourPos.above()).getBlock() instanceof MushroomBlock) {
                    //for ground blocks - block above neighbour is mushroom?
                    FairyRingBlock.fairyRingPlaceCheck(world, neighbourPos.above());
                }
            }
        }
    }

}
