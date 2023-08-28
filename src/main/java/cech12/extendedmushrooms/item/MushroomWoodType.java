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
            RegistryObject.create(ForgeRegistries.BLOCKS.getKey(Blocks.MUSHROOM_STEM), ForgeRegistries.BLOCKS)),
    GLOWSHROOM(1, "glowshroom",
            () -> 8),
    PARROT_WAXCAP(2, "parrot_waxcap"),
    HONEY_WAXCAP(3, "honey_waxcap"),
    AMETHYST_DECEIVER(4, "amethyst_deceiver");

    private static final MushroomWoodType[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(MushroomWoodType::getId)).toArray(MushroomWoodType[]::new);

    private final int id;
    private final String name;
    private final RegistryObject<Block> stemBlock;
    private final Supplier<Integer> lightValue;
    private final BlockSetType blockSetType;
    private final WoodType woodType;

    MushroomWoodType(int id, String name) {
        this(id, name, () -> 0);
    }

    MushroomWoodType(int id, String name, Supplier<Integer> lightValue) {
        this(id, name, lightValue, null);
    }

    MushroomWoodType(int id, String name, RegistryObject<Block> stemBlock) {
        this(id, name, () -> 0, stemBlock);
    }

    MushroomWoodType(int id, String name, Supplier<Integer> lightValue, RegistryObject<Block> stemBlock) {
        this.id = id;
        this.name = name;
        this.stemBlock = stemBlock;
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
        return ModItems.getMushroomItem(this.getName(), ModItems.ItemType.BOAT).get();
    }

    public Item getChestBoatItem() {
        return ModItems.getMushroomItem(this.getName(), ModItems.ItemType.CHEST_BOAT).get();
    }

    public Item getSignItem() {
        return ModItems.getMushroomItem(this.getName(), ModItems.ItemType.SIGN).get();
    }

    public Item getHangingSignItem() {
        return ModItems.getMushroomItem(this.getName(), ModItems.ItemType.HANGING_SIGN).get();
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
