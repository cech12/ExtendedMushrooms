package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import javax.annotation.Nonnull;

public class MushroomWoodPressurePlateBlock extends PressurePlateBlock {

    public MushroomWoodPressurePlateBlock(final MushroomWoodType woodType) {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, generateBlockProperties(), woodType.getBlockSetType());
    }

    public MushroomWoodPressurePlateBlock(final MushroomWoodType woodType, final int lightValue) {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, generateBlockProperties().lightLevel((state) -> lightValue), woodType.getBlockSetType());
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Properties.of().mapColor(MapColor.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD);
    }

}
