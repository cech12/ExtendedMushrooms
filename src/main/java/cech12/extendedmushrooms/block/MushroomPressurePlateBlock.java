package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class MushroomPressurePlateBlock extends PressurePlateBlock implements IBlockItemGetter {

    private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_pressure_plate");

    public MushroomPressurePlateBlock() {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.WOOL).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD));
        setRegistryName(REGISTRY_NAME);
    }

    @Override
    public Item getBlockItem() {
        Item item = new BlockItem(this, (new Item.Properties()).group(ItemGroup.REDSTONE));
        item.setRegistryName(REGISTRY_NAME);
        return item;
    }
}
