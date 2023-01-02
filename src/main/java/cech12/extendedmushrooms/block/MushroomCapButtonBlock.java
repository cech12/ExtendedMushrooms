package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.config.ServerConfig;
import cech12.extendedmushrooms.init.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.sounds.SoundEvent;

import javax.annotation.Nonnull;

public class MushroomCapButtonBlock extends ButtonBlock {

    /**
     * WoodenButtonBlock has protected constructor.
     */
    public MushroomCapButtonBlock() {
        super(generateBlockProperties(), 30, true, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
    }

    public MushroomCapButtonBlock(final int lightValue) {
        super(generateBlockProperties().lightLevel((state) -> lightValue), 30, true, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Block.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOL);
    }

    @Nonnull
    @Override
    protected SoundEvent getSound(boolean isPressed) {
        if (ServerConfig.MUSHROOM_CAP_BUTTON_PLAY_SOUND.get()) {
            return super.getSound(isPressed);
        }
        return ModSounds.NO_SOUND.get();
    }

}
