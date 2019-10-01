package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.util.ResourceLocation;

public class MushroomSignBlock extends StandingSignBlock implements IBlockItemGetter {

    public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_sign");

    public MushroomSignBlock() {
        super(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD));
        setRegistryName(REGISTRY_NAME);
    }

    @Override
    public Item getBlockItem() {
        SignItem item = new SignItem((new Item.Properties()).maxStackSize(16).group(ItemGroup.DECORATIONS), this, ModBlocks.MUSHROOM_WALL_SIGN);
        item.setRegistryName(REGISTRY_NAME);
        return item;
    }

}
