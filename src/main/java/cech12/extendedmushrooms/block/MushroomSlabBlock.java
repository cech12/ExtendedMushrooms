package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class MushroomSlabBlock extends SlabBlock implements IBlockItemGetter {

    private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_slab");

    public MushroomSlabBlock() {
        super(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
        setRegistryName(REGISTRY_NAME);
    }

    @Override
    public Item getBlockItem() {
        Item item = new BlockItem(this, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS));
        item.setRegistryName(REGISTRY_NAME);
        return item;
    }
}
