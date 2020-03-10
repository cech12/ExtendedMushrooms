package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;

public enum MushroomType implements IStringSerializable {

    BROWN_MUSHROOM(0, Items.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.MUSHROOM_STEM),
    RED_MUSHROOM(1, Items.RED_MUSHROOM, Blocks.RED_MUSHROOM_BLOCK, Blocks.MUSHROOM_STEM);

    private static final MushroomType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(MushroomType::getId)).toArray(MushroomType[]::new);

    private final int id;
    private final Item item;
    private final Block capBlock;
    private final Block stemBlock;

    MushroomType(int id, Item item, Block capBlock, Block stemBlock) {
        this.id = id;
        this.item = item;
        this.capBlock = capBlock;
        this.stemBlock = stemBlock;
    }

    public int getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public Block getCapBlock() {
        return capBlock;
    }

    public Block getStemBlock() {
        return stemBlock;
    }

    public ResourceLocation getSheepLootTable() {
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, "entities/sheep/" + this.item.getRegistryName().getPath());
    }

    @Override
    public String getName() {
        return item.getRegistryName().getPath();
    }

    public static MushroomType byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            id = 0;
        }
        return VALUES[id];
    }

    public static MushroomType byItemOrNull(Item item) {
        for (MushroomType mushroomType : VALUES) {
            if (mushroomType.item == item) {
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
}
