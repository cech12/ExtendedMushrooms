package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class MushroomFenceBlock extends FenceBlock implements IBlockItemGetter {

    private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_fence");

    public MushroomFenceBlock() {
        super(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
        setRegistryName(REGISTRY_NAME);
    }

    @Override
    public Item getBlockItem() {
        Item item = new BlockItem(this, (new Item.Properties()).group(ItemGroup.DECORATIONS));
        item.setRegistryName(REGISTRY_NAME);
        return item;
    }
}
