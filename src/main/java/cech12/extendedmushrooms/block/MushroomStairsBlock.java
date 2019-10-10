package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class MushroomStairsBlock extends StairsBlock implements IBlockItemGetter {

    public static final String REGISTRY_NAME = "mushroom_stairs";

    public MushroomStairsBlock() {
        super(ModBlocks.MUSHROOM_PLANKS.getDefaultState(), Block.Properties.from(ModBlocks.MUSHROOM_PLANKS));
        setRegistryName(new ResourceLocation(ExtendedMushrooms.MOD_ID, REGISTRY_NAME));
    }

    @Override
    public Item getBlockItem() {
        Item item = new BlockItem(this, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS));
        item.setRegistryName(REGISTRY_NAME);
        ComposterBlock.CHANCES.put(item, 0.15F);
        return item;
    }
}
