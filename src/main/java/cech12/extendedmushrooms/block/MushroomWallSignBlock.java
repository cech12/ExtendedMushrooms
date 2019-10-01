package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class MushroomWallSignBlock extends WallSignBlock {

    public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_wall_sign");

    public MushroomWallSignBlock() {
        super(Block.Properties.create(Material.WOOD).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD).lootFrom(ModBlocks.MUSHROOM_SIGN));
        setRegistryName(REGISTRY_NAME);
    }

}
