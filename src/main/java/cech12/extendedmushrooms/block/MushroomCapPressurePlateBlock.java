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

public class MushroomCapPressurePlateBlock extends PressurePlateBlock {

    /**
     * PressurePlateBlock has protected constructor.
     */
    public MushroomCapPressurePlateBlock() {
        super(Sensitivity.EVERYTHING, generateBlockProperties());
    }

    public MushroomCapPressurePlateBlock(final int lightValue) {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, generateBlockProperties().lightValue(lightValue));
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Properties.create(Material.WOOL).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH);
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        //negate some fall damage
        super.onFallenUpon(world, pos, entity, fallDistance * 0.8F);
    }

    @Override
    protected void playClickOnSound(IWorld world, @Nonnull BlockPos pos) {
        if (Config.MUSHROOM_CAP_PRESSURE_PLATE_PLAY_SOUND.getValue()) {
            world.playSound(null, pos, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.8F);
        }
    }

    @Override
    protected void playClickOffSound(IWorld world, @Nonnull BlockPos pos) {
        if (Config.MUSHROOM_CAP_PRESSURE_PLATE_PLAY_SOUND.getValue()) {
            world.playSound(null, pos, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.7F);
        }
    }

}
