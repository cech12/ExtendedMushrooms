package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nonnull;

public class MushroomWoodButtonBlock extends ButtonBlock {

    public MushroomWoodButtonBlock(final MushroomWoodType woodType) {
        super(generateBlockProperties(), woodType.getBlockSetType(), 30, true);
    }

    public MushroomWoodButtonBlock(final MushroomWoodType woodType, final int lightValue) {
        super(generateBlockProperties().lightLevel((state) -> lightValue), woodType.getBlockSetType(), 30, true);
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Block.Properties.of().noCollission().strength(0.5F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY);
    }
}
