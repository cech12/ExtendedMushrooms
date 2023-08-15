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
            ModItems.MUSHROOM_BOAT,
            ModItems.MUSHROOM_CHEST_BOAT),
    GLOWSHROOM(1, "glowshroom",
            ModBlocks.GLOWSHROOM_STEM,
            ModBlocks.GLOWSHROOM_STEM_STRIPPED,
            ModBlocks.GLOWSHROOM_PLANKS,
            ModItems.GLOWSHROOM_BOAT,
            ModItems.GLOWSHROOM_CHEST_BOAT,
            () -> ModBlocks.GLOWSHROOM_STEM.get().getLightEmission(ModBlocks.GLOWSHROOM_STEM.get().defaultBlockState(), null, null)),
    POISONOUS_MUSHROOM(2, "poisonous_mushroom",
            ModBlocks.POISONOUS_MUSHROOM_STEM,
            ModBlocks.POISONOUS_MUSHROOM_STEM_STRIPPED,
            ModBlocks.POISONOUS_MUSHROOM_PLANKS,
            ModItems.POISONOUS_MUSHROOM_BOAT,
            ModItems.POISONOUS_MUSHROOM_CHEST_BOAT),
    HONEY_WAXCAP(3, "honey_fungus",
            ModBlocks.HONEY_WAXCAP_STEM,
            ModBlocks.HONEY_WAXCAP_STEM_STRIPPED,
            ModBlocks.HONEY_WAXCAP_PLANKS,
            ModItems.HONEY_WAXCAP_BOAT,
            ModItems.HONEY_WAXCAP_CHEST_BOAT);

    private static final MushroomWoodType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(MushroomWoodType::getId)).toArray(MushroomWoodType[]::new);

    private final int id;
    private final String name;
    private final RegistryObject<Block> stemBlock;
    private final RegistryObject<Block> strippedStemBlock;
    private final RegistryObject<Block> planksBlock;
    private final RegistryObject<Item> boatItem;
    private final RegistryObject<Item> chestBoatItem;
    private final Supplier<Integer> lightValue;
    private final BlockSetType blockSetType;
    private final WoodType woodType;

    MushroomWoodType(int id, String name, RegistryObject<Block> stemBlock, RegistryObject<Block> strippedStemBlock, RegistryObject<Block> planksBlock, RegistryObject<Item> boatItem, RegistryObject<Item> chestBoatItem) {
        this(id, name, stemBlock, strippedStemBlock, planksBlock, boatItem, chestBoatItem, () -> 0);
    }

    MushroomWoodType(int id, String name, RegistryObject<Block> stemBlock, RegistryObject<Block> strippedStemBlock, RegistryObject<Block> planksBlock, RegistryObject<Item> boatItem, RegistryObject<Item> chestBoatItem, Supplier<Integer> lightValue) {
        this.id = id;
        this.name = name;
        this.stemBlock = stemBlock;
        this.strippedStemBlock = strippedStemBlock;
        this.planksBlock = planksBlock;
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

    public ResourceLocation getStrippedStemBlockId() {
        return this.strippedStemBlock.getId();
    }

    public Block getPlanksBlock() {
        return this.planksBlock.get();
    }

    public ResourceLocation getPlanksBlockId() {
        return this.planksBlock.getId();
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
