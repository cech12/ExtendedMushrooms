package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.*;
import cech12.extendedmushrooms.block.mushroomblocks.GlowshroomCap;
import cech12.extendedmushrooms.block.mushroomblocks.HoneyWaxcapCap;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomCapBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStemBlock;
import cech12.extendedmushrooms.block.mushroomblocks.MushroomStrippedStemBlock;
import cech12.extendedmushrooms.block.mushroomblocks.DeadlyFibrecapCap;
import cech12.extendedmushrooms.block.mushroomblocks.ParrotWaxcapCap;
import cech12.extendedmushrooms.block.mushrooms.BigMushroom;
import cech12.extendedmushrooms.block.mushrooms.DeadlyFibrecap;
import cech12.extendedmushrooms.block.mushrooms.Glowshroom;
import cech12.extendedmushrooms.block.mushrooms.HoneyWaxcap;
import cech12.extendedmushrooms.block.mushrooms.ParrotWaxcap;
import cech12.extendedmushrooms.item.MushroomType;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExtendedMushrooms.MOD_ID);

    public static final RegistryObject<Block> FAIRY_RING = registerBlock("fairy_ring", FairyRingBlock::new);

    public static final RegistryObject<Block> INFESTED_GRASS = registerBlockWithItem("infested_grass", () -> new InfestedGrassBlock(Block.Properties.of().mapColor(MapColor.PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS).offsetType(Block.OffsetType.XYZ)));
    public static final RegistryObject<Block> INFESTED_FLOWER = registerBlockWithItem("infested_flower", () -> new InfestedFlowerBlock(() -> MobEffects.MOVEMENT_SLOWDOWN, 9, Block.Properties.of().mapColor(MapColor.PLANT).noCollission().strength(0.0F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> INFESTED_FLOWER_POTTED = registerBlock("infested_flower_potted", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, INFESTED_FLOWER, Block.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));

    private static final Map<RegistryObject<Block>, RegistryObject<Block>> BLOCK_STRIPPING_MAP = new HashMap<>();

    static {

        registerCapBlocks("brown_mushroom", MushroomType.BROWN_MUSHROOM, MushroomWoodType.MUSHROOM, DyeColor.BROWN, MapColor.COLOR_BROWN, SoundType.WOOL, null);
        registerCapBlocks("red_mushroom", MushroomType.RED_MUSHROOM, MushroomWoodType.MUSHROOM, DyeColor.RED, MapColor.COLOR_RED, SoundType.WOOL, null);
        registerWoodBlocks("mushroom", MushroomWoodType.MUSHROOM, MapColor.WOOD, SoundType.WOOD, (state) -> 0, RegistryObject.create(ForgeRegistries.BLOCKS.getKey(Blocks.MUSHROOM_STEM), ForgeRegistries.BLOCKS));

        registerMushroomBlocks("glowshroom", new Glowshroom(), MapColor.COLOR_BLUE, (state) -> 8);
        registerCapBlocks("glowshroom", MushroomType.GLOWSHROOM, MushroomWoodType.GLOWSHROOM, DyeColor.BLUE, MapColor.COLOR_BLUE, SoundType.WOOL, GlowshroomCap::new, (state) -> 8);
        registerWoodBlocks("glowshroom", MushroomWoodType.GLOWSHROOM, MapColor.COLOR_LIGHT_BLUE, SoundType.WOOD, (state) -> 8, null);

        registerMushroomBlocks("deadly_fibrecap", new DeadlyFibrecap(), MapColor.TERRACOTTA_WHITE);
        registerCapBlocks("deadly_fibrecap", MushroomType.DEADLY_FIBRECAP, MushroomWoodType.PARROT_WAXCAP, DyeColor.WHITE, MapColor.TERRACOTTA_WHITE, SoundType.WOOL, DeadlyFibrecapCap::new);

        registerMushroomBlocks("parrot_waxcap", new ParrotWaxcap(), MapColor.COLOR_LIGHT_GREEN);
        registerCapBlocks("parrot_waxcap", MushroomType.PARROT_WAXCAP, MushroomWoodType.PARROT_WAXCAP, DyeColor.LIME, MapColor.COLOR_LIGHT_GREEN, SoundType.SLIME_BLOCK, ParrotWaxcapCap::new);
        registerWoodBlocks("parrot_waxcap", MushroomWoodType.PARROT_WAXCAP, MapColor.COLOR_GREEN, SoundType.WOOD);

        registerMushroomBlocks("honey_waxcap", new HoneyWaxcap(), MapColor.COLOR_ORANGE);
        registerCapBlocks("honey_waxcap", MushroomType.HONEY_WAXCAP, MushroomWoodType.HONEY_WAXCAP, DyeColor.ORANGE, MapColor.COLOR_ORANGE, SoundType.HONEY_BLOCK, HoneyWaxcapCap::new);
        registerWoodBlocks("honey_waxcap", MushroomWoodType.HONEY_WAXCAP, MapColor.COLOR_ORANGE, SoundType.WOOD);

        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(INFESTED_FLOWER.getId()), INFESTED_FLOWER_POTTED);
    }

    public static RegistryObject<Block> getMushroomBlock(String mushroomName, BlockType blockType) {
        return RegistryObject.create(new ResourceLocation(ExtendedMushrooms.MOD_ID, blockType.getName(mushroomName)), ForgeRegistries.BLOCKS);
    }

    public static Block getStrippedBlock(Block block) {
        return BLOCK_STRIPPING_MAP.entrySet().stream().filter(entry -> entry.getKey().get().equals(block)).findFirst().map(entry -> entry.getValue().get()).orElse(null);
    }

    public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
        for (RegistryObject<Block> registryObject : BLOCKS.getEntries()) {
            Block block = registryObject.get();
            if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
                if (block instanceof MushroomWoodButtonBlock
                        || block instanceof MushroomCapButtonBlock
                        || block instanceof DoorBlock
                        || block instanceof MushroomFenceBlock
                        || block instanceof MushroomFenceGateBlock
                        || block instanceof MushroomPlanksBlock
                        || block instanceof MushroomWoodPressurePlateBlock
                        || block instanceof MushroomCapPressurePlateBlock
                        || block instanceof MushroomSlabBlock
                        || block instanceof MushroomStairsBlock
                        || block instanceof TrapDoorBlock
                        || block instanceof MushroomStemBlock
                        || block instanceof MushroomStrippedStemBlock
                ) {
                    event.accept(block);
                }
            }
            if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
                if (block instanceof MushroomCarpetBlock) {
                    event.accept(block);
                }
            }
            if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
                if (block instanceof EMMushroomBlock
                        || block instanceof MushroomCapBlock
                        || block instanceof MushroomStemBlock
                ) {
                    event.accept(block);
                }
            }
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(INFESTED_GRASS);
            event.accept(INFESTED_FLOWER);
        }
    }


    private static void registerMushroomBlocks(String name, BigMushroom bigMushroom, MapColor color) {
        registerMushroomBlocks(name, bigMushroom, color, (state) -> 0);
    }

    private static void registerMushroomBlocks(String name, BigMushroom bigMushroom, MapColor mapColor, ToIntFunction<BlockState> lightLevel) {
        RegistryObject<Block> mushroom = registerBlockWithItem(BlockType.MUSHROOM.getName(name), () -> new EMMushroomBlock(bigMushroom, Block.Properties.of().mapColor(mapColor).noCollission().randomTicks().strength(0.0F).sound(SoundType.GRASS).lightLevel(lightLevel).hasPostProcess((a, b, c)->true)));
        RegistryObject<Block> pottedMushroom = registerBlock(BlockType.POTTED_MUSHROOM.getName(name), () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, mushroom, Block.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).lightLevel(lightLevel)));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(mushroom.getId(), pottedMushroom);
    }

    private static void registerCapBlocks(String name, MushroomType mushroomType, MushroomWoodType mushroomWoodType, DyeColor dyeColor, MapColor mapColor, SoundType sound, BiFunction<MushroomType, Block.Properties, MushroomCapBlock> capBlock) {
        registerCapBlocks(name, mushroomType, mushroomWoodType, dyeColor, mapColor, sound, capBlock, (state) -> 0);
    }

    private static void registerCapBlocks(String name, MushroomType mushroomType, MushroomWoodType mushroomWoodType, DyeColor dyeColor, MapColor mapColor, SoundType sound, BiFunction<MushroomType, Block.Properties, MushroomCapBlock> capBlock, ToIntFunction<BlockState> lightLevel) {
        if (capBlock != null) {
            registerBlockWithItem(BlockType.CAP.getName(name), () -> capBlock.apply(mushroomType, Block.Properties.of().mapColor(mapColor).strength(0.2F).sound(sound).lightLevel(lightLevel)));
        }
        registerBlockWithItem(BlockType.CAP_BUTTON.getName(name), () -> new MushroomCapButtonBlock(mushroomWoodType, lightLevel));
        registerBlockWithItem(BlockType.CAP_CARPET.getName(name), () -> new MushroomCarpetBlock(dyeColor, Block.Properties.of().mapColor(mapColor).strength(0.1F).sound(sound).lightLevel(lightLevel)));
        registerBlockWithItem(BlockType.CAP_PRESSURE_PLATE.getName(name), () -> new MushroomCapPressurePlateBlock(mushroomWoodType, lightLevel));
    }

    private static void registerWoodBlocks(String name, MushroomWoodType mushroomWoodType, MapColor mapColor, SoundType sound) {
        registerWoodBlocks(name, mushroomWoodType, mapColor, sound, (state) -> 0, null);
    }

    private static void registerWoodBlocks(String name, MushroomWoodType mushroomWoodType, MapColor mapColor, SoundType sound, ToIntFunction<BlockState> lightLevel, @Nullable RegistryObject<Block> alternativeStem) {
        RegistryObject<Block> stem = alternativeStem;
        if (stem == null) {
            stem = registerBlockWithItem(BlockType.STEM.getName(name), () -> new MushroomStemBlock(mushroomWoodType, Block.Properties.of().mapColor(mapColor).strength(0.2F).sound(sound).lightLevel(lightLevel)));
        }
        RegistryObject<Block> strippedStem = registerBlockWithItem(BlockType.STRIPPED_STEM.getName(name), () -> new MushroomStrippedStemBlock(mushroomWoodType, Block.Properties.of().mapColor(mapColor).strength(0.2F).sound(sound).lightLevel(lightLevel)));
        registerBlockWithItem(BlockType.BUTTON.getName(name), () -> new MushroomWoodButtonBlock(mushroomWoodType, lightLevel));
        registerBlockWithItem(BlockType.DOOR.getName(name), () -> new DoorBlock(Block.Properties.of().mapColor(mapColor).strength(3.0F).sound(sound).lightLevel(lightLevel), mushroomWoodType.getBlockSetType()));
        registerBlockWithItem(BlockType.FENCE.getName(name), () -> new MushroomFenceBlock(Block.Properties.of().mapColor(mapColor).strength(2.0F, 3.0F).sound(sound).lightLevel(lightLevel)));
        registerBlockWithItem(BlockType.FENCE_GATE.getName(name), () -> new MushroomFenceGateBlock(Block.Properties.of().mapColor(mapColor).strength(2.0F, 3.0F).sound(sound).lightLevel(lightLevel)));
        RegistryObject<Block> hangingSign = registerBlock(BlockType.HANGING_SIGN.getName(name), () -> new MushroomCeilingHangingSignBlock(Block.Properties.of().mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lightLevel(lightLevel), mushroomWoodType.getWoodType()));
        RegistryObject<Block> planks = registerBlockWithItem(BlockType.PLANKS.getName(name), () -> new MushroomPlanksBlock(Block.Properties.of().mapColor(mapColor).strength(0.2F).sound(sound).lightLevel(lightLevel)));
        registerBlockWithItem(BlockType.PRESSURE_PLATE.getName(name), () -> new MushroomWoodPressurePlateBlock(mushroomWoodType, lightLevel));
        registerBlockWithItem(BlockType.SLAB.getName(name), () -> new MushroomSlabBlock(Block.Properties.of().mapColor(mapColor).strength(2.0F, 3.0F).sound(sound).lightLevel(lightLevel)));
        registerBlockWithItem(BlockType.STAIRS.getName(name), () -> new MushroomStairsBlock(() -> planks.get().defaultBlockState(), Block.Properties.copy(planks.get())));
        RegistryObject<Block> standingSign = registerBlock(BlockType.STANDING_SIGN.getName(name), () -> new MushroomStandingSignBlock(Block.Properties.of().mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lightLevel(lightLevel), mushroomWoodType.getWoodType()));
        registerBlockWithItem(BlockType.TRAPDOOR.getName(name), () -> new TrapDoorBlock(Block.Properties.of().mapColor(mapColor).strength(3.0F).sound(sound).lightLevel(lightLevel), mushroomWoodType.getBlockSetType()));
        registerBlock(BlockType.WALL_HANGING_SIGN.getName(name), () -> new MushroomWallHangingSignBlock(Block.Properties.of().mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lootFrom(hangingSign).lightLevel(lightLevel), mushroomWoodType.getWoodType()));
        registerBlock(BlockType.WALL_SIGN.getName(name), () -> new MushroomWallSignBlock(Block.Properties.of().mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava().lootFrom(standingSign).lightLevel(lightLevel), mushroomWoodType.getWoodType()));
        BLOCK_STRIPPING_MAP.put(stem, strippedStem);
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<? extends Block> block) {
        return BLOCKS.register(name, block);
    }

    private static RegistryObject<Block> registerBlockWithItem(String name, Supplier<? extends Block> block) {
        RegistryObject<Block> registeredBlock = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

    public enum BlockType {

        MUSHROOM("{0}"),
        POTTED_MUSHROOM("{0}_potted"),
        CAP("{0}_cap"),
        CAP_BUTTON("{0}_cap_button"),
        CAP_CARPET("{0}_cap_carpet"),
        CAP_PRESSURE_PLATE("{0}_cap_pressure_plate"),
        STEM("{0}_stem"),
        STRIPPED_STEM("stripped_{0}_stem"),
        BUTTON("{0}_button"),
        DOOR("{0}_door"),
        FENCE("{0}_fence"),
        FENCE_GATE("{0}_fence_gate"),
        HANGING_SIGN("{0}_hanging_sign"),
        PLANKS("{0}_planks"),
        PRESSURE_PLATE("{0}_pressure_plate"),
        SLAB("{0}_slab"),
        STAIRS("{0}_stairs"),
        STANDING_SIGN("{0}_sign"),
        TRAPDOOR("{0}_trapdoor"),
        WALL_HANGING_SIGN("{0}_wall_hanging_sign"),
        WALL_SIGN("{0}_wall_sign");

        private final String pattern;

        BlockType(String pattern) {
            this.pattern = pattern;
        }

        String getName(String mushroomName) {
            return this.pattern.replace("{0}", mushroomName);
        }

    }

}
