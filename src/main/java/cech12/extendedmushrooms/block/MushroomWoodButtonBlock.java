package cech12.extendedmushrooms.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nonnull;

public class MushroomWoodButtonBlock extends WoodButtonBlock {

    public MushroomWoodButtonBlock() {
        super(generateBlockProperties());
    }

    public MushroomWoodButtonBlock(final int lightValue) {
        super(generateBlockProperties().lightLevel((state) -> lightValue));
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Block.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD);
    }
}
