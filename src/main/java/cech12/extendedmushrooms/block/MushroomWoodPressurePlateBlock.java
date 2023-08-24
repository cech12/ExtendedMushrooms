package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import javax.annotation.Nonnull;
import java.util.function.ToIntFunction;

public class MushroomWoodPressurePlateBlock extends PressurePlateBlock {

    public MushroomWoodPressurePlateBlock(final MushroomWoodType woodType, final ToIntFunction<BlockState> lightLevel) {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, generateBlockProperties().lightLevel(lightLevel), woodType.getBlockSetType());
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Properties.of().mapColor(MapColor.WOOD).noCollission().strength(0.5F).sound(SoundType.WOOD);
    }

}
