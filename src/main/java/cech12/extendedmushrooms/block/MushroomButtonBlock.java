package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class MushroomButtonBlock extends WoodButtonBlock implements IBlockItemGetter {

    private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_button");

    public MushroomButtonBlock() {
        super(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD));
        setRegistryName(REGISTRY_NAME);
    }

    @Override
    public Item getBlockItem() {
        Item item = new BlockItem(this, (new Item.Properties()).group(ItemGroup.REDSTONE));
        item.setRegistryName(REGISTRY_NAME);
        return item;
    }
}
