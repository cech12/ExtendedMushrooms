package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
            RegistryObject.create(Blocks.MUSHROOM_STEM.getRegistryName(), ForgeRegistries.BLOCKS),
            ModBlocks.MUSHROOM_PLANKS,
            ModItems.MUSHROOM_BOAT),
    GLOWSHROOM(1, "glowshroom",
            ModBlocks.GLOWSHROOM_STEM,
            ModBlocks.GLOWSHROOM_PLANKS,
            ModItems.GLOWSHROOM_BOAT,
            () -> ModBlocks.GLOWSHROOM_STEM.get().getLightEmission(ModBlocks.GLOWSHROOM_STEM.get().defaultBlockState(), null, null)),
    POISONOUS_MUSHROOM(2, "poisonous_mushroom",
            ModBlocks.POISONOUS_MUSHROOM_STEM,
            ModBlocks.POISONOUS_MUSHROOM_PLANKS,
            ModItems.POISONOUS_MUSHROOM_BOAT),
    HONEY_FUNGUS(3, "honey_fungus",
            ModBlocks.HONEY_FUNGUS_STEM,
            ModBlocks.HONEY_FUNGUS_PLANKS,
            ModItems.HONEY_FUNGUS_BOAT);

    private static final MushroomWoodType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(MushroomWoodType::getId)).toArray(MushroomWoodType[]::new);

    private final int id;
    private final String name;
    private final RegistryObject<Block> stemBlock;
    private final RegistryObject<Block> planksBlock;
    private final RegistryObject<Item> boatItem;
    private final Supplier<Integer> lightValue;
    private final WoodType woodType;

    MushroomWoodType(int id, String name, RegistryObject<Block> stemBlock, RegistryObject<Block> planksBlock, RegistryObject<Item> boatItem) {
        this(id, name, stemBlock, planksBlock, boatItem, () -> 0);
    }

    MushroomWoodType(int id, String name, RegistryObject<Block> stemBlock, RegistryObject<Block> planksBlock, RegistryObject<Item> boatItem, Supplier<Integer> lightValue) {
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
