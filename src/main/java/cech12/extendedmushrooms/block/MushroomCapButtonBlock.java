package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.function.ToIntFunction;

public class MushroomCapButtonBlock extends ButtonBlock {

    /**
     * WoodenButtonBlock has protected constructor.
     */
    public MushroomCapButtonBlock(final MushroomWoodType woodType) {
        super(generateBlockProperties(), woodType.getBlockSetType(), 30, true);
    }

    public MushroomCapButtonBlock(final MushroomWoodType woodType, final ToIntFunction<BlockState> lightLevel) {
        super(generateBlockProperties().lightLevel(lightLevel), woodType.getBlockSetType(), 30, true);
    }

    @Nonnull
    static private Properties generateBlockProperties() {
        return Block.Properties.of().noCollission().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return SoundType.WOOL;
    }

}
