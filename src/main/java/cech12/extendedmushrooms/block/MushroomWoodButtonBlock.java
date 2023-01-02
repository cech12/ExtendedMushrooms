package cech12.extendedmushrooms.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nonnull;

public class MushroomWoodButtonBlock extends ButtonBlock {

    public MushroomWoodButtonBlock() {
        super(generateBlockProperties(), 30, true, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
    }

    public MushroomWoodButtonBlock(final int lightValue) {
        super(generateBlockProperties().lightLevel((state) -> lightValue), 30, true, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Block.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD);
    }
}
