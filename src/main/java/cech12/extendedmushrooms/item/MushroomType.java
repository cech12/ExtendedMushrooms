package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.util.StringRepresentable;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;

public enum MushroomType implements StringRepresentable {

    BROWN_MUSHROOM(0,
            ()->Items.BROWN_MUSHROOM,
            ()->Blocks.BROWN_MUSHROOM_BLOCK,
            MushroomWoodType.MUSHROOM, DyeColor.BROWN),
    RED_MUSHROOM(1,
            ()->Items.RED_MUSHROOM,
            ()->Blocks.RED_MUSHROOM_BLOCK,
            MushroomWoodType.MUSHROOM, DyeColor.RED),
    GLOWSHROOM(2,
            ()->ExtendedMushroomsBlocks.GLOWSHROOM,
            ()->ExtendedMushroomsBlocks.GLOWSHROOM_CAP,
            MushroomWoodType.GLOWSHROOM, DyeColor.BLUE,
            ()->ExtendedMushroomsBlocks.GLOWSHROOM_CAP.getLightEmission(ExtendedMushroomsBlocks.GLOWSHROOM_CAP.defaultBlockState(), null, null)),
    POISONOUS_MUSHROOM(3,
            ()->ExtendedMushroomsBlocks.POISONOUS_MUSHROOM,
            ()->ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_CAP,
            MushroomWoodType.POISONOUS_MUSHROOM, DyeColor.PURPLE),
    SLIME_FUNGUS(4,
            ()->ExtendedMushroomsBlocks.HONEY_FUNGUS,
            ()->ExtendedMushroomsBlocks.HONEY_FUNGUS_CAP,
            MushroomWoodType.POISONOUS_MUSHROOM, DyeColor.LIME),
    HONEY_FUNGUS(5,
            ()->ExtendedMushroomsBlocks.HONEY_FUNGUS,
            ()->ExtendedMushroomsBlocks.HONEY_FUNGUS_CAP,
            MushroomWoodType.HONEY_FUNGUS, DyeColor.ORANGE);

    private static final MushroomType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(MushroomType::getId)).toArray(MushroomType[]::new);

    private final int id;
    private final Supplier<ItemLike> item;
    private final Supplier<Block> capBlock;
    private final MushroomWoodType woodType;
    private final DyeColor color;
    private final Supplier<Integer> lightValue;

    MushroomType(int id, Supplier<ItemLike> item, Supplier<Block> capBlock, MushroomWoodType woodType, @Nonnull DyeColor color) {
        this(id, item, capBlock, woodType, color, () -> 0);
    }

    MushroomType(int id, Supplier<ItemLike> item, Supplier<Block> capBlock, MushroomWoodType woodType, @Nonnull DyeColor color, Supplier<Integer> lightValue) {
        this.id = id;
        this.item = item;
        this.capBlock = capBlock;
        this.woodType = woodType;
        this.color = color;
        this.lightValue = lightValue;
    }

    public int getId() {
        return this.id;
    }

    public Item getItem() {
        return this.item.get().asItem();
    }

    public Block getCapBlock() {
        return this.capBlock.get();
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
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, "entities/sheep/" + this.getItem().getRegistryName().getPath());
    }

    @Override
    public String getSerializedName() {
        return this.getItem().getRegistryName().getPath();
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
