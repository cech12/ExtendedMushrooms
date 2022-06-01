package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SlimeFungusCap extends AbstractEffectMushroomCap {

    public SlimeFungusCap(MushroomType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public boolean isSlimeBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        //TODO When this block is pushed by a piston, an adjacent honey block still sticks to it (mixin honey block?)
        if (other.getBlock() == Blocks.HONEY_BLOCK || other.getBlock() == ExtendedMushroomsBlocks.HONEY_FUNGUS_CAP) return false;
        return state.isStickyBlock() || other.isStickyBlock();
    }

    @Nonnull
    @Override
    protected List<EffectInstance> getEffects(@Nonnull Random random) {
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<EffectInstance> effects = new LinkedList<>();
        effects.add(new EffectInstance(Effects.JUMP, duration));
        return effects;
    }

    @Override
    @Deprecated
    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(@Nonnull BlockState self, BlockState otherBlock, @Nonnull Direction direction) {
        //don't render sides between same blocks
        return otherBlock.is(this) || super.skipRendering(self, otherBlock, direction);
    }

}