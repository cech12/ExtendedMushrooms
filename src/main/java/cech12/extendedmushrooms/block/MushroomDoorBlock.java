package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraft.util.ResourceLocation;

public class MushroomDoorBlock extends DoorBlock implements IBlockItemGetter {

    private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_door");

    public MushroomDoorBlock() {
        super(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(3.0F).sound(SoundType.WOOD));
        setRegistryName(REGISTRY_NAME);
    }

    @Override
    public Item getBlockItem() {
        Item item = new TallBlockItem(this, (new Item.Properties()).group(ItemGroup.REDSTONE));
        item.setRegistryName(REGISTRY_NAME);
        return item;
    }
}
