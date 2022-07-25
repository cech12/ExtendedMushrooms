package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.api.item.ExtendedMushroomsItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.WoodType;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;

public enum MushroomWoodType implements IStringSerializable {

    MUSHROOM(0, "mushroom",
            ()->Blocks.MUSHROOM_STEM,
            ()->ExtendedMushroomsBlocks.MUSHROOM_PLANKS,
            ()->ExtendedMushroomsItems.MUSHROOM_BOAT),
    GLOWSHROOM(1, "glowshroom",
            ()->ExtendedMushroomsBlocks.GLOWSHROOM_STEM,
            ()->ExtendedMushroomsBlocks.GLOWSHROOM_PLANKS,
            ()->ExtendedMushroomsItems.GLOWSHROOM_BOAT,
            ()->ExtendedMushroomsBlocks.GLOWSHROOM_STEM.getLightValue(ExtendedMushroomsBlocks.GLOWSHROOM_STEM.defaultBlockState(), null, null)),
    POISONOUS_MUSHROOM(2, "poisonous_mushroom",
            ()->ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_STEM,
            ()->ExtendedMushroomsBlocks.POISONOUS_MUSHROOM_PLANKS,
            ()->ExtendedMushroomsItems.POISONOUS_MUSHROOM_BOAT),
    HONEY_FUNGUS(3, "honey_fungus",
            ()->ExtendedMushroomsBlocks.HONEY_FUNGUS_STEM,
            ()->ExtendedMushroomsBlocks.HONEY_FUNGUS_PLANKS,
            ()->ExtendedMushroomsItems.HONEY_FUNGUS_BOAT);

    private static final MushroomWoodType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(MushroomWoodType::getId)).toArray(MushroomWoodType[]::new);

    private final int id;
    private final String name;
    private final Supplier<Block> stemBlock;
    private final Supplier<Block> planksBlock;
    private final Supplier<IItemProvider> boatItem;
    private final Supplier<Integer> lightValue;
    private final WoodType woodType;

    MushroomWoodType(int id, String name, Supplier<Block> stemBlock, Supplier<Block> planksBlock, Supplier<IItemProvider> boatItem) {
        this(id, name, stemBlock, planksBlock, boatItem, () -> 0);
    }

    MushroomWoodType(int id, String name, Supplier<Block> stemBlock, Supplier<Block> planksBlock, Supplier<IItemProvider> boatItem, Supplier<Integer> lightValue) {
        this.id = id;
        this.name = name;
        this.stemBlock = stemBlock;
        this.planksBlock = planksBlock;
        this.boatItem = boatItem;
        this.lightValue = lightValue;
        this.woodType = WoodType.register(WoodType.create(new ResourceLocation(ExtendedMushrooms.MOD_ID, name).toString()));
    }

    public int getId() {
        return this.id;
    }

    public Block getStemBlock() {
        return this.stemBlock.get();
    }

    public Block getPlanksBlock() {
        return this.planksBlock.get();
    }

    public Item getBoatItem() {
        return this.boatItem.get().asItem();
    }

    public int getLightValue() {
        return this.lightValue.get();
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
