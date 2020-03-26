package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

public class MushroomCapButtonBlock extends WoodButtonBlock {

    /**
     * WoodenButtonBlock has protected constructor.
     */
    public MushroomCapButtonBlock() {
        super(generateBlockProperties());
    }

    public MushroomCapButtonBlock(final int lightValue) {
        super(generateBlockProperties().lightValue(lightValue));
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.CLOTH);
    }

    @Nonnull
    @Override
    protected SoundEvent getSoundEvent(boolean isPressed) {
        if (Config.MUSHROOM_CAP_BUTTON_PLAY_SOUND.getValue()) {
            return super.getSoundEvent(isPressed);
        }
        return ModSounds.NO_SOUND;
    }

}
