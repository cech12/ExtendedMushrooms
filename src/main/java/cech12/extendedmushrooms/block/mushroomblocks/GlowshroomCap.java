package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class GlowshroomCap extends AbstractEffectMushroomCap {

    public GlowshroomCap(MushroomType type, Properties properties) {
        super(type, properties);
    }

    @Override
    protected boolean shouldDropEffectCloud(BlockState state, @Nonnull ServerLevel world, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
        if (super.shouldDropEffectCloud(state, world, pos, random)) {
            //only drop effect at night or in dark areas
            BlockPos up = pos.above();
            return !world.isDay() || (!world.getBlockState(up).canOcclude() && world.getMaxLocalRawBrightness(up) <= state.getLightEmission());
        }
        return false;
    }

    @Nonnull
    @Override
    protected List<MobEffectInstance> getEffects(@Nonnull RandomSource random) {
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<MobEffectInstance> effects = new LinkedList<>();
        effects.add(new MobEffectInstance(MobEffects.NIGHT_VISION, duration));
        return effects;
    }
}
