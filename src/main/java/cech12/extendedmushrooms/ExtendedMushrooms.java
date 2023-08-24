package cech12.extendedmushrooms;

import cech12.extendedmushrooms.compat.Moonlight;
import cech12.extendedmushrooms.init.ModLootModifiers;
import cech12.extendedmushrooms.init.ModParticles;
import cech12.extendedmushrooms.init.ModRecipeTypes;
import cech12.extendedmushrooms.block.FairyRingBlock;
import cech12.extendedmushrooms.config.ServerConfig;
import cech12.extendedmushrooms.entity.ai.goal.EatMushroomGoal;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import cech12.extendedmushrooms.init.ModBlockEntityTypes;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModEntityTypes;
import cech12.extendedmushrooms.init.ModFeatures;
import cech12.extendedmushrooms.init.ModItems;
import cech12.extendedmushrooms.init.ModSounds;
import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.init.ModTriggers;
import cech12.extendedmushrooms.init.ModVanillaCompat;
import cech12.extendedmushrooms.item.crafting.MushroomBrewingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.Map;

@Mod(ExtendedMushrooms.MOD_ID)
@Mod.EventBusSubscriber
public class ExtendedMushrooms {

    public static final String MOD_ID = "extendedmushrooms";

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private static ResourceLocation compatLoc(String path) {
        return new ResourceLocation("everycomp", path);
    }

    private static ResourceLocation slabCompatLoc(String path) {
        return new ResourceLocation("v_slab_compat", path);
    }

    public ExtendedMushrooms() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_CONFIG);
        ServerConfig.loadConfig(ServerConfig.SERVER_CONFIG, FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).resolve(MOD_ID + "-server.toml"));

        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(eventBus);
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(eventBus);
        ModItems.ITEMS.register(eventBus);
        ModEntityTypes.ENTITY_TYPES.register(eventBus);
        ModFeatures.FEATURES.register(eventBus);
        ModRecipeTypes.RECIPE_TYPES.register(eventBus);
        ModRecipeTypes.RECIPE_SERIALIZERS.register(eventBus);
        ModLootModifiers.SERIALIZERS.register(eventBus);
        ModSounds.SOUND_EVENTS.register(eventBus);
        ModParticles.PARTICLE_TYPES.register(eventBus);
        ModTriggers.init();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::addItemsToTabs);
        eventBus.addListener(this::clientSetup);

        //Initialize Moonlight compatibility only if the library is loaded (Every Compat)
        if (ModList.get().isLoaded("moonlight")) {
            Moonlight.init();
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModVanillaCompat::setup);

        //add potion recipes
        //BrewingRecipeRegistry.addRecipe(new MushroomBrewingRecipe(ModTags.ForgeItems.MUSHROOMS_GLOWSHROOM, Potions.NIGHT_VISION)); //overpowered
        BrewingRecipeRegistry.addRecipe(new MushroomBrewingRecipe(ModTags.ForgeItems.MUSHROOMS_JUMP_BOOSTING, Potions.LEAPING));
        BrewingRecipeRegistry.addRecipe(new MushroomBrewingRecipe(ModTags.ForgeItems.MUSHROOMS_POISONOUS, Potions.POISON));
        BrewingRecipeRegistry.addRecipe(new MushroomBrewingRecipe(ModTags.ForgeItems.MUSHROOMS_SLOWING_DOWN, Potions.SLOWNESS));
    }

    private void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
        ModBlocks.addItemsToTabs(event);
        ModItems.addItemsToTabs(event);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ModEntityTypes.setupRenderers();
        ModBlockEntityTypes.setupRenderers(event);
    }

    @SubscribeEvent
    public static void remapOldIds(MissingMappingsEvent event) {
        Map<ResourceLocation, ResourceLocation> oldResourceLocationMap = Map.ofEntries(
                //old compat blocks
                Map.entry(loc("mushroom_bookshelf"), compatLoc("q/extendedmushrooms/mushroom_bookshelf")),
                Map.entry(loc("mushroom_ladder"), compatLoc("q/extendedmushrooms/mushroom_ladder")),
                Map.entry(loc("mushroom_vertical_planks"), compatLoc("q/extendedmushrooms/vertical_mushroom_planks")),
                Map.entry(loc("mushroom_vertical_slab"), slabCompatLoc("extendedmushrooms/mushroom_vertical_slab")),
                Map.entry(compatLoc("q/extendedmushrooms/mushroom_vertical_slab"), slabCompatLoc("extendedmushrooms/mushroom_vertical_slab")),
                Map.entry(loc("glowshroom_bookshelf"), compatLoc("q/extendedmushrooms/glowshroom_bookshelf")),
                Map.entry(loc("glowshroom_ladder"), compatLoc("q/extendedmushrooms/glowshroom_ladder")),
                Map.entry(loc("glowshroom_vertical_planks"), compatLoc("q/extendedmushrooms/vertical_glowshroom_planks")),
                Map.entry(loc("glowshroom_vertical_slab"), slabCompatLoc("extendedmushrooms/glowshroom_vertical_slab")),
                Map.entry(compatLoc("q/extendedmushrooms/glowshroom_vertical_slab"), slabCompatLoc("extendedmushrooms/glowshroom_vertical_slab")),
                Map.entry(loc("poisonous_mushroom_bookshelf"), compatLoc("q/extendedmushrooms/parrot_waxcap_bookshelf")),
                Map.entry(loc("poisonous_mushroom_ladder"), compatLoc("q/extendedmushrooms/parrot_waxcap_ladder")),
                Map.entry(loc("poisonous_mushroom_vertical_planks"), compatLoc("q/extendedmushrooms/vertical_parrot_waxcap_planks")),
                Map.entry(loc("poisonous_mushroom_vertical_slab"), slabCompatLoc("extendedmushrooms/parrot_waxcap_vertical_slab")),
                Map.entry(compatLoc("q/extendedmushrooms/poisonous_mushroom_vertical_slab"), slabCompatLoc("extendedmushrooms/parrot_waxcap_vertical_slab")),
                Map.entry(loc("honey_fungus_bookshelf"), compatLoc("q/extendedmushrooms/honey_waxcap_bookshelf")),
                Map.entry(loc("honey_fungus_ladder"), compatLoc("q/extendedmushrooms/honey_waxcap_ladder")),
                Map.entry(loc("honey_fungus_vertical_planks"), compatLoc("q/extendedmushrooms/vertical_honey_waxcap_planks")),
                Map.entry(loc("honey_fungus_vertical_slab"), slabCompatLoc("extendedmushrooms/honey_waxcap_vertical_slab")),
                Map.entry(compatLoc("q/extendedmushrooms/honey_fungus_vertical_slab"), slabCompatLoc("extendedmushrooms/honey_waxcap_vertical_slab")),
                //Chests cannot be ported because of their block entity ("variant_chest" & "variant_trapped_chest")
                //Map.entry(loc("mushroom_chest"), compatLoc("q/extendedmushrooms/mushroom_chest")),
                //Map.entry(loc("mushroom_chest_trapped"), compatLoc("q/extendedmushrooms/mushroom_trapped_chest")),
                //Map.entry(loc("glowshroom_chest"), compatLoc("q/extendedmushrooms/glowshroom_chest")),
                //Map.entry(loc("glowshroom_chest_trapped"), compatLoc("q/extendedmushrooms/glowshroom_trapped_chest")),
                //Map.entry(loc("poisonous_mushroom_chest"), compatLoc("q/extendedmushrooms/poisonous_mushroom_chest")),
                //Map.entry(loc("poisonous_mushroom_chest_trapped"), compatLoc("q/extendedmushrooms/poisonous_mushroom_trapped_chest")),
                //Map.entry(loc("honey_fungus_chest"), compatLoc("q/extendedmushrooms/honey_fungus_chest")),
                //Map.entry(loc("honey_fungus_chest_trapped"), compatLoc("q/extendedmushrooms/honey_fungus_trapped_chest")),
                //mushroom renaming of mod version 5.0.0
                Map.entry(compatLoc("q/extendedmushrooms/poisonous_mushroom_bookshelf"), compatLoc("q/extendedmushrooms/parrot_waxcap_bookshelf")),
                Map.entry(compatLoc("q/extendedmushrooms/poisonous_mushroom_ladder"), compatLoc("q/extendedmushrooms/parrot_waxcap_ladder")),
                Map.entry(compatLoc("q/extendedmushrooms/vertical_poisonous_mushroom_planks"), compatLoc("q/extendedmushrooms/vertical_parrot_waxcap_planks")),
                Map.entry(slabCompatLoc("extendedmushrooms/poisonous_mushroom_vertical_slab"), slabCompatLoc("extendedmushrooms/parrot_waxcap_vertical_slab")),
                Map.entry(compatLoc("q/extendedmushrooms/honey_fungus_bookshelf"), compatLoc("q/extendedmushrooms/honey_waxcap_bookshelf")),
                Map.entry(compatLoc("q/extendedmushrooms/honey_fungus_ladder"), compatLoc("q/extendedmushrooms/honey_waxcap_ladder")),
                Map.entry(compatLoc("q/extendedmushrooms/vertical_honey_fungus_planks"), compatLoc("q/extendedmushrooms/vertical_honey_waxcap_planks")),
                Map.entry(slabCompatLoc("extendedmushrooms/honey_fungus_vertical_slab"), slabCompatLoc("extendedmushrooms/honey_waxcap_vertical_slab")),
                Map.entry(loc("poisonous_mushroom"), loc("deadly_fibrecap")),
                Map.entry(loc("poisonous_mushroom_potted"), loc("deadly_fibrecap_potted")),
                Map.entry(loc("poisonous_mushroom_cap"), loc("deadly_fibrecap_cap")),
                Map.entry(loc("poisonous_mushroom_cap_carpet"), loc("deadly_fibrecap_cap_carpet")),
                Map.entry(loc("brown_mushroom_carpet"), loc("brown_mushroom_cap_carpet")),
                Map.entry(loc("red_mushroom_carpet"), loc("red_mushroom_cap_carpet")),
                Map.entry(loc("glowshroom_stem_stripped"), loc("stripped_glowshroom_stem")),
                Map.entry(loc("poisonous_mushroom_stem_stripped"), loc("stripped_parrot_waxcap_stem")),
                Map.entry(loc("honey_fungus_stem_stripped"), loc("stripped_honey_waxcap_stem")),
                //cap buttons & cap pressure plates removed in 5.0.0
                Map.entry(loc("brown_mushroom_button"), loc("mushroom_button")),
                Map.entry(loc("red_mushroom_button"), loc("mushroom_button")),
                Map.entry(loc("glowshroom_cap_button"), loc("glowshroom_button")),
                Map.entry(loc("poisonous_mushroom_cap_button"), loc("mushroom_button")),
                Map.entry(loc("slime_fungus_cap_button"), loc("parrot_waxcap_button")),
                Map.entry(loc("honey_fungus_cap_button"), loc("honey_waxcap_button")),
                Map.entry(loc("brown_mushroom_pressure_plate"), loc("mushroom_pressure_plate")),
                Map.entry(loc("red_mushroom_pressure_plate"), loc("mushroom_pressure_plate")),
                Map.entry(loc("glowshroom_cap_pressure_plate"), loc("glowshroom_pressure_plate")),
                Map.entry(loc("poisonous_mushroom_cap_pressure_plate"), loc("mushroom_pressure_plate")),
                Map.entry(loc("slime_fungus_cap_pressure_plate"), loc("parrot_waxcap_pressure_plate")),
                Map.entry(loc("honey_fungus_cap_pressure_plate"), loc("honey_waxcap_pressure_plate"))
        );
        //mushroom renaming of mod version 5.0.0
        Map<String, String> resourceRenamingMap = Map.ofEntries(
                Map.entry("poisonous_mushroom", "parrot_waxcap"),
                Map.entry("slime_fungus", "parrot_waxcap"),
                Map.entry("honey_fungus", "honey_waxcap")
        );
        event.getMappings(ForgeRegistries.ITEMS.getRegistryKey(), MOD_ID).forEach(itemMapping -> oldResourceLocationMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(itemMapping.getKey()))
                .map(Map.Entry::getValue)
                .filter(ForgeRegistries.ITEMS::containsKey)
                .findFirst()
                .ifPresentOrElse(remap -> itemMapping.remap(ForgeRegistries.ITEMS.getValue(remap)), () -> resourceRenamingMap.entrySet().stream()
                        .filter(entry -> itemMapping.getKey().getPath().contains(entry.getKey()))
                        .map(entry -> new ResourceLocation(MOD_ID, itemMapping.getKey().getPath().replace(entry.getKey(), entry.getValue())))
                        .filter(ForgeRegistries.ITEMS::containsKey)
                        .findFirst()
                        .ifPresent(remap -> itemMapping.remap(ForgeRegistries.ITEMS.getValue(remap)))));
        event.getMappings(ForgeRegistries.BLOCKS.getRegistryKey(), MOD_ID).forEach(blockMapping -> oldResourceLocationMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(blockMapping.getKey()))
                .map(Map.Entry::getValue)
                .filter(ForgeRegistries.BLOCKS::containsKey)
                .findFirst()
                .ifPresentOrElse(remap -> blockMapping.remap(ForgeRegistries.BLOCKS.getValue(remap)), () -> resourceRenamingMap.entrySet().stream()
                        .filter(entry -> blockMapping.getKey().getPath().contains(entry.getKey()))
                        .map(entry -> new ResourceLocation(MOD_ID, blockMapping.getKey().getPath().replace(entry.getKey(), entry.getValue())))
                        .filter(ForgeRegistries.BLOCKS::containsKey)
                        .findFirst()
                        .ifPresent(remap -> blockMapping.remap(ForgeRegistries.BLOCKS.getValue(remap)))));
    }

    /**
     * Add stripping behaviour to mushroom stems
     */
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        BlockState blockState = event.getLevel().getBlockState(event.getPos());
        ItemStack itemStack = event.getEntity().getItemInHand(event.getHand());
        //check for mushroom stem and axe
        if (itemStack.canPerformAction(ToolActions.AXE_STRIP)) {
            //get stripped block from stripping map
            Block strippedBlock = ModBlocks.getStrippedBlock(blockState.getBlock());
            if (strippedBlock != null) {
                //play sound
                event.getLevel().playSound(event.getEntity(), event.getPos(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!event.getLevel().isClientSide) {
                    //copy block state orientation
                    BlockState strippedBlockState = strippedBlock.defaultBlockState();
                    if (blockState.hasProperty(HugeMushroomBlock.UP)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.UP, blockState.getValue(HugeMushroomBlock.UP));
                    if (blockState.hasProperty(HugeMushroomBlock.DOWN)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.DOWN, blockState.getValue(HugeMushroomBlock.DOWN));
                    if (blockState.hasProperty(HugeMushroomBlock.NORTH)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.NORTH, blockState.getValue(HugeMushroomBlock.NORTH));
                    if (blockState.hasProperty(HugeMushroomBlock.EAST)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.EAST, blockState.getValue(HugeMushroomBlock.EAST));
                    if (blockState.hasProperty(HugeMushroomBlock.SOUTH)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.SOUTH, blockState.getValue(HugeMushroomBlock.SOUTH));
                    if (blockState.hasProperty(HugeMushroomBlock.WEST)) strippedBlockState = strippedBlockState.setValue(HugeMushroomBlock.WEST, blockState.getValue(HugeMushroomBlock.WEST));
                    //replace block
                    event.getLevel().setBlock(event.getPos(), strippedBlockState, 11);
                    //do the item damage
                    if (event.getEntity() != null) {
                        itemStack.hurtAndBreak(1, event.getEntity(), (player) -> player.broadcastBreakEvent(event.getHand()));
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
        ItemStack itemStack = event.getEntity().getItemInHand(event.getHand());
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
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (ServerConfig.SHEEP_EAT_MUSHROOM_FROM_GROUND_ENABLED.get()) {
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
        LevelAccessor world = event.getLevel();
        BlockPos blockPos = event.getPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != ModBlocks.FAIRY_RING.get()) {
            for (Direction direction : event.getNotifiedSides()) {
                BlockPos neighbourPos = blockPos.relative(direction);
                if (FairyRingBlock.isFairyRingMushroom(world.getBlockState(neighbourPos).getBlock().asItem())) {
                    //neighbour is mushroom?
                    FairyRingBlock.fairyRingPlaceCheck(world, neighbourPos);
                } else if (FairyRingBlock.isFairyRingMushroom(world.getBlockState(neighbourPos.above()).getBlock().asItem())) {
                    //for ground blocks - block above neighbour is mushroom?
                    FairyRingBlock.fairyRingPlaceCheck(world, neighbourPos.above());
                }
            }
        }
    }

}
