package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.util.StringRepresentable;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;

public enum MushroomType implements StringRepresentable {

    BROWN_MUSHROOM(0, "brown_mushroom",
            MushroomWoodType.MUSHROOM,
            DyeColor.BROWN,
            () -> Blocks.BROWN_MUSHROOM,
            RegistryObject.create(ForgeRegistries.BLOCKS.getKey(Blocks.BROWN_MUSHROOM_BLOCK), ForgeRegistries.BLOCKS)),
    RED_MUSHROOM(1, "red_mushroom",
            MushroomWoodType.MUSHROOM,
            DyeColor.RED,
            () -> Blocks.RED_MUSHROOM,
            RegistryObject.create(ForgeRegistries.BLOCKS.getKey(Blocks.RED_MUSHROOM_BLOCK), ForgeRegistries.BLOCKS)),
    GLOWSHROOM(2, "glowshroom",
            MushroomWoodType.GLOWSHROOM,
            DyeColor.BLUE,
            () -> 8),
    DEADLY_FIBRECAP(3, "deadly_fibrecap",
            MushroomWoodType.MUSHROOM,
            DyeColor.WHITE),
    PARROT_WAXCAP(4, "parrot_waxcap",
            MushroomWoodType.PARROT_WAXCAP,
            DyeColor.LIME),
    HONEY_WAXCAP(5, "honey_waxcap",
            MushroomWoodType.HONEY_WAXCAP,
            DyeColor.ORANGE);

    private static final MushroomType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(MushroomType::getId)).toArray(MushroomType[]::new);

    private final int id;
    private final String name;
    private final MushroomWoodType woodType;
    private final DyeColor color;
    private final Supplier<Integer> lightValue;

    private final Supplier<Block> mushroom;
    private final RegistryObject<Block> capBlock;

    MushroomType(int id, String name, MushroomWoodType woodType, @Nonnull DyeColor color) {
        this(id, name, woodType, color, () -> 0, null, null);
    }

    MushroomType(int id, String name, MushroomWoodType woodType, @Nonnull DyeColor color, Supplier<Integer> lightValue) {
        this(id, name, woodType, color, lightValue, null, null);
    }

    MushroomType(int id, String name, MushroomWoodType woodType, @Nonnull DyeColor color, Supplier<Block> item, RegistryObject<Block> capBlock) {
        this(id, name, woodType, color, () -> 0, item, capBlock);
    }

    MushroomType(int id, String name, MushroomWoodType woodType, @Nonnull DyeColor color, Supplier<Integer> lightValue, Supplier<Block> item, RegistryObject<Block> capBlock) {
        this.id = id;
        this.name = name;
        this.woodType = woodType;
        this.color = color;
        this.lightValue = lightValue;
        this.mushroom = item;
        this.capBlock = capBlock;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public Item getItem() {
        return this.getBlock().asItem();
    }

    public Block getBlock() {
        if (this.mushroom != null) {
            return this.mushroom.get();
        }
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.MUSHROOM).get();
    }

    public Block getCapBlock() {
        if (this.capBlock != null) {
            return this.capBlock.get();
        }
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.CAP).get();
    }

    public ResourceLocation getCapBlockId() {
        if (this.capBlock != null) {
            return this.capBlock.getId();
        }
        return ModBlocks.getMushroomBlock(this.name, ModBlocks.BlockType.CAP).getId();
    }

    public MushroomWoodType getWoodType() {
        return this.woodType;
    }

    public DyeColor getColor() {
        return this.color;
    }

    public int getLightValue() {
        return this.lightValue.get();
    }

    public ResourceLocation getSheepLootTable() {
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, "entities/sheep/" + this.name);
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static MushroomType byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            id = 0;
        }
        return VALUES[id];
    }

    public static MushroomType byItemOrNull(Item item) {
        for (MushroomType mushroomType : VALUES) {
            if (mushroomType.getItem() == item) {
                return mushroomType;
            }
        }
        return null;
    }

    public static MushroomType byItem(Item item) {
        MushroomType type = byItemOrNull(item);
        if (type == null) {
            type = BROWN_MUSHROOM;
        }
        return type;
    }

    /**
     * @return an array of all mushroom types that are not brown and not red.
     */
    public static MushroomType[] getSpecialTypes() {
        return Arrays.stream(values()).filter(type -> type != BROWN_MUSHROOM && type != RED_MUSHROOM).toArray(MushroomType[]::new);
    }

}
