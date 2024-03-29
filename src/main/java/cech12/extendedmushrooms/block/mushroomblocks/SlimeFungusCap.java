package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

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
        if (other.getBlock() == Blocks.HONEY_BLOCK || other.getBlock() == ModBlocks.HONEY_FUNGUS_CAP.get()) return false;
        return state.isStickyBlock() || other.isStickyBlock();
    }

    @Nonnull
    @Override
    protected List<MobEffectInstance> getEffects(@Nonnull RandomSource random) {
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<MobEffectInstance> effects = new LinkedList<>();
        effects.add(new MobEffectInstance(MobEffects.JUMP, duration));
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
