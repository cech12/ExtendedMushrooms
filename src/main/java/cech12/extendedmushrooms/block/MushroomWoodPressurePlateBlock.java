package cech12.extendedmushrooms.block;

import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nonnull;

public class MushroomWoodPressurePlateBlock extends PressurePlateBlock {

    public MushroomWoodPressurePlateBlock() {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, generateBlockProperties());
    }

    public MushroomWoodPressurePlateBlock(final int lightValue) {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, generateBlockProperties().lightLevel((state) -> lightValue));
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD);
    }

}
