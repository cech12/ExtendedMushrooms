package cech12.extendedmushrooms.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nonnull;

public class MushroomWoodPressurePlateBlock extends PressurePlateBlock {

    public MushroomWoodPressurePlateBlock() {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, generateBlockProperties(), SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON);
    }

    public MushroomWoodPressurePlateBlock(final int lightValue) {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, generateBlockProperties().lightLevel((state) -> lightValue), SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON);
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Properties.of(Material.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD);
    }

}
