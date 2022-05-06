package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

import net.minecraft.block.AbstractBlock.Properties;

public class MushroomCapButtonBlock extends WoodButtonBlock {

    /**
     * WoodenButtonBlock has protected constructor.
     */
    public MushroomCapButtonBlock() {
        super(generateBlockProperties());
    }

    public MushroomCapButtonBlock(final int lightValue) {
        super(generateBlockProperties().lightLevel((state) -> lightValue));
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Block.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOL);
    }

    @Nonnull
    @Override
    protected SoundEvent getSound(boolean isPressed) {
        if (Config.MUSHROOM_CAP_BUTTON_PLAY_SOUND.get()) {
            return super.getSound(isPressed);
        }
        return ModSounds.NO_SOUND;
    }

}
