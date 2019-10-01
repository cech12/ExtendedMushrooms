package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class MushroomPlanksBlock extends Block implements IBlockItemProperty {

    public static final String REGISTRY_NAME = "mushroom_planks";

    public MushroomPlanksBlock() {
        super(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(0.2F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(ExtendedMushrooms.MOD_ID, REGISTRY_NAME));
    }

    @Override
    public Item.Properties getBlockItemProperties() {
        return (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS);
    }
}
