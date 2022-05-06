package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.config.Config;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.PressurePlateBlock.Sensitivity;

public class MushroomCapPressurePlateBlock extends PressurePlateBlock {

    /**
     * PressurePlateBlock has protected constructor.
     */
    public MushroomCapPressurePlateBlock() {
        super(Sensitivity.EVERYTHING, generateBlockProperties());
    }

    public MushroomCapPressurePlateBlock(final int lightValue) {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, generateBlockProperties().lightLevel((state) -> lightValue));
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Properties.of(Material.WOOL).noCollission().strength(0.5F).sound(SoundType.WOOL);
    }

    @Override
    public void fallOn(World world, BlockPos pos, Entity entity, float fallDistance) {
        //negate some fall damage
        super.fallOn(world, pos, entity, fallDistance * 0.8F);
    }

    @Override
    protected void playOnSound(IWorld world, @Nonnull BlockPos pos) {
        if (Config.MUSHROOM_CAP_PRESSURE_PLATE_PLAY_SOUND.get()) {
            world.playSound(null, pos, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.8F);
        }
    }

    @Override
    protected void playOffSound(IWorld world, @Nonnull BlockPos pos) {
        if (Config.MUSHROOM_CAP_PRESSURE_PLATE_PLAY_SOUND.get()) {
            world.playSound(null, pos, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.7F);
        }
    }

}
