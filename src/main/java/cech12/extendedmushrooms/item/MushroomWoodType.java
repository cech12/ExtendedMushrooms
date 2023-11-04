package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.item.Item;
import net.minecraft.util.StringRepresentable;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;

public enum MushroomWoodType implements StringRepresentable {

    MUSHROOM(0, "mushroom",
            RegistryObject.create(ForgeRegistries.BLOCKS.getKey(Blocks.MUSHROOM_STEM), ForgeRegistries.BLOCKS),
            ModBlocks.STRIPPED_MUSHROOM_STEM,
            ModBlocks.MUSHROOM_PLANKS,
            ModBlocks.MUSHROOM_SLAB,
            ModBlocks.MUSHROOM_STAIRS,
            ModBlocks.MUSHROOM_FENCE,
            ModBlocks.MUSHROOM_FENCE_GATE,
            ModBlocks.MUSHROOM_DOOR,
            ModBlocks.MUSHROOM_TRAPDOOR,
            ModBlocks.MUSHROOM_BUTTON,
            ModBlocks.MUSHROOM_PRESSURE_PLATE,
            ModBlocks.MUSHROOM_STANDING_SIGN,
            ModBlocks.MUSHROOM_HANGING_SIGN,
            ModItems.MUSHROOM_BOAT,
            ModItems.MUSHROOM_CHEST_BOAT),
    GLOWSHROOM(1, "glowshroom",
            ModBlocks.GLOWSHROOM_STEM,
            ModBlocks.GLOWSHROOM_STEM_STRIPPED,
            ModBlocks.GLOWSHROOM_PLANKS,
            ModBlocks.GLOWSHROOM_SLAB,
            ModBlocks.GLOWSHROOM_STAIRS,
            ModBlocks.GLOWSHROOM_FENCE,
            ModBlocks.GLOWSHROOM_FENCE_GATE,
            ModBlocks.GLOWSHROOM_DOOR,
            ModBlocks.GLOWSHROOM_TRAPDOOR,
            ModBlocks.GLOWSHROOM_BUTTON,
            ModBlocks.GLOWSHROOM_PRESSURE_PLATE,
            ModBlocks.GLOWSHROOM_STANDING_SIGN,
            ModBlocks.GLOWSHROOM_HANGING_SIGN,
            ModItems.GLOWSHROOM_BOAT,
            ModItems.GLOWSHROOM_CHEST_BOAT,
            () -> ModBlocks.GLOWSHROOM_STEM.get().getLightEmission(ModBlocks.GLOWSHROOM_STEM.get().defaultBlockState(), null, null)),
    POISONOUS_MUSHROOM(2, "poisonous_mushroom",
            ModBlocks.POISONOUS_MUSHROOM_STEM,
            ModBlocks.POISONOUS_MUSHROOM_STEM_STRIPPED,
            ModBlocks.POISONOUS_MUSHROOM_PLANKS,
            ModBlocks.POISONOUS_MUSHROOM_SLAB,
            ModBlocks.POISONOUS_MUSHROOM_STAIRS,
            ModBlocks.POISONOUS_MUSHROOM_FENCE,
            ModBlocks.POISONOUS_MUSHROOM_FENCE_GATE,
            ModBlocks.POISONOUS_MUSHROOM_DOOR,
            ModBlocks.POISONOUS_MUSHROOM_TRAPDOOR,
            ModBlocks.POISONOUS_MUSHROOM_BUTTON,
            ModBlocks.POISONOUS_MUSHROOM_PRESSURE_PLATE,
            ModBlocks.POISONOUS_MUSHROOM_STANDING_SIGN,
            ModBlocks.POISONOUS_MUSHROOM_HANGING_SIGN,
            ModItems.POISONOUS_MUSHROOM_BOAT,
            ModItems.POISONOUS_MUSHROOM_CHEST_BOAT),
    HONEY_FUNGUS(3, "honey_fungus",
            ModBlocks.HONEY_FUNGUS_STEM,
            ModBlocks.HONEY_FUNGUS_STEM_STRIPPED,
            ModBlocks.HONEY_FUNGUS_PLANKS,
            ModBlocks.HONEY_FUNGUS_SLAB,
            ModBlocks.HONEY_FUNGUS_STAIRS,
            ModBlocks.HONEY_FUNGUS_FENCE,
            ModBlocks.HONEY_FUNGUS_FENCE_GATE,
            ModBlocks.HONEY_FUNGUS_DOOR,
            ModBlocks.HONEY_FUNGUS_TRAPDOOR,
            ModBlocks.HONEY_FUNGUS_BUTTON,
            ModBlocks.HONEY_FUNGUS_PRESSURE_PLATE,
            ModBlocks.HONEY_FUNGUS_STANDING_SIGN,
            ModBlocks.HONEY_FUNGUS_HANGING_SIGN,
            ModItems.HONEY_FUNGUS_BOAT,
            ModItems.HONEY_FUNGUS_CHEST_BOAT);

    private static final MushroomWoodType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(MushroomWoodType::getId)).toArray(MushroomWoodType[]::new);

    private final int id;
    private final String name;
    private final RegistryObject<Block> stemBlock;
    private final RegistryObject<Block> strippedStemBlock;
    private final RegistryObject<Block> planksBlock;
    private final RegistryObject<Block> slabBlock;
    private final RegistryObject<Block> stairsBlock;
    private final RegistryObject<Block> fenceBlock;
    private final RegistryObject<Block> fenceGateBlock;
    private final RegistryObject<Block> doorBlock;
    private final RegistryObject<Block> trapdoorBlock;
    private final RegistryObject<Block> buttonBlock;
    private final RegistryObject<Block> pressurePlateBlock;
    private final RegistryObject<Block> signBlock;
    private final RegistryObject<Block> hangingSignBlock;
    private final RegistryObject<Item> boatItem;
    private final RegistryObject<Item> chestBoatItem;
    private final Supplier<Integer> lightValue;
    private final BlockSetType blockSetType;
    private final WoodType woodType;

    MushroomWoodType(int id, String name, RegistryObject<Block> stemBlock, RegistryObject<Block> strippedStemBlock, RegistryObject<Block> planksBlock, RegistryObject<Block> slabBlock, RegistryObject<Block> stairsBlock,
                     RegistryObject<Block> fenceBlock, RegistryObject<Block> fenceGateBlock, RegistryObject<Block> doorBlock, RegistryObject<Block> trapdoorBlock, RegistryObject<Block> buttonBlock,
                     RegistryObject<Block> pressurePlateBlock, RegistryObject<Block> signBlock, RegistryObject<Block> hangingSignBlock, RegistryObject<Item> boatItem, RegistryObject<Item> chestBoatItem) {
        this(id, name, stemBlock, strippedStemBlock, planksBlock, slabBlock, stairsBlock, fenceBlock, fenceGateBlock, doorBlock, trapdoorBlock, buttonBlock, pressurePlateBlock, signBlock, hangingSignBlock, boatItem, chestBoatItem, () -> 0);
    }

    MushroomWoodType(int id, String name, RegistryObject<Block> stemBlock, RegistryObject<Block> strippedStemBlock, RegistryObject<Block> planksBlock, RegistryObject<Block> slabBlock, RegistryObject<Block> stairsBlock,
                     RegistryObject<Block> fenceBlock, RegistryObject<Block> fenceGateBlock, RegistryObject<Block> doorBlock, RegistryObject<Block> trapdoorBlock, RegistryObject<Block> buttonBlock,
                     RegistryObject<Block> pressurePlateBlock, RegistryObject<Block> signBlock, RegistryObject<Block> hangingSignBlock, RegistryObject<Item> boatItem, RegistryObject<Item> chestBoatItem,
                     Supplier<Integer> lightValue) {
        this.id = id;
        this.name = name;
        this.stemBlock = stemBlock;
        this.strippedStemBlock = strippedStemBlock;
        this.planksBlock = planksBlock;
        this.slabBlock = slabBlock;
        this.stairsBlock = stairsBlock;
        this.fenceBlock = fenceBlock;
        this.fenceGateBlock = fenceGateBlock;
        this.doorBlock = doorBlock;
        this.trapdoorBlock = trapdoorBlock;
        this.buttonBlock = buttonBlock;
        this.pressurePlateBlock = pressurePlateBlock;
        this.signBlock = signBlock;
        this.hangingSignBlock = hangingSignBlock;
        this.boatItem = boatItem;
        this.chestBoatItem = chestBoatItem;
        this.lightValue = lightValue;
        ResourceLocation resourceLocation = new ResourceLocation(ExtendedMushrooms.MOD_ID, name);
        this.blockSetType = BlockSetType.register(new BlockSetType(resourceLocation.toString()));
        this.woodType = WoodType.register(new WoodType(resourceLocation.toString(), this.blockSetType));
    }

    public int getId() {
        return this.id;
    }

    public Block getStemBlock() {
        return this.stemBlock.get();
    }

    public ResourceLocation getStemBlockId() {
        return this.stemBlock.getId();
    }


    public Block getStrippedStemBlock() {
        return this.strippedStemBlock.get();
    }

    public ResourceLocation getStrippedStemBlockId() {
        return this.strippedStemBlock.getId();
    }

    public Block getPlanksBlock() {
        return this.planksBlock.get();
    }

    public ResourceLocation getPlanksBlockId() {
        return this.planksBlock.getId();
    }

    public Block getSlabBlock() {
        return slabBlock.get();
    }

    public Block getStairsBlock() {
        return stairsBlock.get();
    }

    public Block getFenceBlock() {
        return fenceBlock.get();
    }

    public Block getFenceGateBlock() {
        return fenceGateBlock.get();
    }

    public Block getDoorBlock() {
        return doorBlock.get();
    }

    public Block getTrapdoorBlock() {
        return trapdoorBlock.get();
    }

    public Block getButtonBlock() {
        return buttonBlock.get();
    }

    public Block getPressurePlateBlock() {
        return pressurePlateBlock.get();
    }

    public Block getSignBlock() {
        return signBlock.get();
    }

    public Block getHangingSignBlock() {
        return hangingSignBlock.get();
    }

    public Item getBoatItem() {
        return this.boatItem.get().asItem();
    }

    public Item getChestBoatItem() {
        return this.chestBoatItem.get().asItem();
    }

    public int getLightValue() {
        return this.lightValue.get();
    }

    public BlockSetType getBlockSetType() {
        return this.blockSetType;
    }

    public WoodType getWoodType() {
        return this.woodType;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static MushroomWoodType byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            id = 0;
        }
        return VALUES[id];
    }

    public static MushroomWoodType byName(String name) {
        for (MushroomWoodType mushroomType : VALUES) {
            if (mushroomType.getSerializedName().equals(name)) {
                return mushroomType;
            }
        }
        return MUSHROOM;
    }
}
