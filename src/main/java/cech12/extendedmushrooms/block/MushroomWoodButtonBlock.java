package cech12.extendedmushrooms.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;

import javax.annotation.Nonnull;

public class MushroomWoodButtonBlock extends WoodButtonBlock {

    public MushroomWoodButtonBlock() {
        super(generateBlockProperties());
    }

    public MushroomWoodButtonBlock(final int lightValue) {
        super(generateBlockProperties().setLightLevel((state) -> lightValue));
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD);
    }
}
