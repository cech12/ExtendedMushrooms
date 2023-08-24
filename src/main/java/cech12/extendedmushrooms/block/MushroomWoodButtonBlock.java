package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nonnull;
import java.util.function.ToIntFunction;

public class MushroomWoodButtonBlock extends ButtonBlock {

    public MushroomWoodButtonBlock(final MushroomWoodType woodType, final ToIntFunction<BlockState> lightLevel) {
        super(generateBlockProperties().lightLevel(lightLevel), woodType.getBlockSetType(), 30, true);
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Block.Properties.of().noCollission().strength(0.5F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY);
    }
}
