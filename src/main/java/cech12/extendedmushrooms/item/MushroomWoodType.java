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
            ModItems.MUSHROOM_BOAT,
            ModItems.MUSHROOM_CHEST_BOAT,
            RegistryObject.create(ForgeRegistries.BLOCKS.getKey(Blocks.MUSHROOM_STEM), ForgeRegistries.BLOCKS)),
    GLOWSHROOM(1, "glowshroom",
            ModItems.GLOWSHROOM_BOAT,
            ModItems.GLOWSHROOM_CHEST_BOAT,
            () -> 8),
    PARROT_WAXCAP(2, "parrot_waxcap",
            ModItems.PARROT_WAXCAP_BOAT,
            ModItems.PARROT_WAXCAP_CHEST_BOAT),
    HONEY_WAXCAP(3, "honey_waxcap",
            ModItems.HONEY_WAXCAP_BOAT,
            ModItems.HONEY_WAXCAP_CHEST_BOAT);

    private static final MushroomWoodType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(MushroomWoodType::getId)).toArray(MushroomWoodType[]::new);

    private final int id;
    private final String name;
    private final RegistryObject<Block> stemBlock;
    private final RegistryObject<Item> boatItem;
    private final RegistryObject<Item> chestBoatItem;
    private final Supplier<Integer> lightValue;
    private final BlockSetType blockSetType;
    private final WoodType woodType;

    MushroomWoodType(int id, String name, RegistryObject<Item> boatItem, RegistryObject<Item> chestBoatItem) {
        this(id, name, boatItem, chestBoatItem, () -> 0);
    }

    MushroomWoodType(int id, String name, RegistryObject<Item> boatItem, RegistryObject<Item> chestBoatItem, Supplier<Integer> lightValue) {
        this(id, name, boatItem, chestBoatItem, lightValue, null);
    }

    MushroomWoodType(int id, String name, RegistryObject<Item> boatItem, RegistryObject<Item> chestBoatItem, RegistryObject<Block> stemBlock) {
        this(id, name, boatItem, chestBoatItem, () -> 0, stemBlock);
    }

    MushroomWoodType(int id, String name, RegistryObject<Item> boatItem, RegistryObject<Item> chestBoatItem, Supplier<Integer> lightValue, RegistryObject<Block> stemBlock) {
        this.id = id;
        this.name = name;
        this.stemBlock = stemBlock;
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

    public String getName() {
        return this.name;
    }

    public Block getStemBlock() {
        if (this.stemBlock != null) {
            return this.stemBlock.get();
        }
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.STEM).get();
    }

    public ResourceLocation getStemBlockId() {
        if (this.stemBlock != null) {
            return this.stemBlock.getId();
        }
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.STEM).getId();
    }

    public Block getStrippedStemBlock() {
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.STRIPPED_STEM).get();
    }

    public ResourceLocation getStrippedStemBlockId() {
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.STRIPPED_STEM).getId();
    }

    public Block getPlanksBlock() {
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.PLANKS).get();
    }

    public ResourceLocation getPlanksBlockId() {
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.PLANKS).getId();
    }

    public Block getStandingSignBlock() {
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.STANDING_SIGN).get();
    }

    public Block getHangingSignBlock() {
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.HANGING_SIGN).get();
    }

    public Block getWallSignBlock() {
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.WALL_SIGN).get();
    }

    public Block getWallHangingSignBlock() {
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.WALL_HANGING_SIGN).get();
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
