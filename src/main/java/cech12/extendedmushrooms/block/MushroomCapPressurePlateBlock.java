package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class MushroomCapPressurePlateBlock extends PressurePlateBlock {

    /**
     * PressurePlateBlock has protected constructor.
     */
    public MushroomCapPressurePlateBlock(final MushroomWoodType woodType) {
        super(Sensitivity.EVERYTHING, generateBlockProperties(), woodType.getBlockSetType());
    }

    public MushroomCapPressurePlateBlock(final MushroomWoodType woodType, final int lightValue) {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, generateBlockProperties().lightLevel((state) -> lightValue), woodType.getBlockSetType());
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Properties.of().mapColor(MapColor.WOOL).noCollission().strength(0.5F).sound(SoundType.WOOL);
    }

    @Override
    public void fallOn(@Nonnull Level level, @Nonnull BlockState blockState, @Nonnull BlockPos blockPos, @Nonnull Entity entity, float fallDistance) {
        //negate some fall damage
        super.fallOn(level, blockState, blockPos, entity, fallDistance * 0.8F);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return SoundType.WOOL;
    }

}
