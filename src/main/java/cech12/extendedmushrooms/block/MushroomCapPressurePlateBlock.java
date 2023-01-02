package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.config.ServerConfig;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class MushroomCapPressurePlateBlock extends PressurePlateBlock {

    /**
     * PressurePlateBlock has protected constructor.
     */
    public MushroomCapPressurePlateBlock() {
        super(Sensitivity.EVERYTHING, generateBlockProperties(), SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON);
    }

    public MushroomCapPressurePlateBlock(final int lightValue) {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, generateBlockProperties().lightLevel((state) -> lightValue), SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON);
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Properties.of(Material.WOOL).noCollission().strength(0.5F).sound(SoundType.WOOL);
    }

    @Override
    public void fallOn(@Nonnull Level level, @Nonnull BlockState blockState, @Nonnull BlockPos blockPos, @Nonnull Entity entity, float fallDistance) {
        //negate some fall damage
        super.fallOn(level, blockState, blockPos, entity, fallDistance * 0.8F);
    }

    @Override
    protected void playOnSound(@Nonnull LevelAccessor level, @Nonnull BlockPos pos) {
        if (ServerConfig.MUSHROOM_CAP_PRESSURE_PLATE_PLAY_SOUND.get()) {
            level.playSound(null, pos, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.8F);
        }
    }

    @Override
    protected void playOffSound(@Nonnull LevelAccessor level, @Nonnull BlockPos pos) {
        if (ServerConfig.MUSHROOM_CAP_PRESSURE_PLATE_PLAY_SOUND.get()) {
            level.playSound(null, pos, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS, 0.3F, 0.7F);
        }
    }

}
