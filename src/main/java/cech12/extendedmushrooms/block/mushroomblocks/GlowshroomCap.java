package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.block.BlockState;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class GlowshroomCap extends AbstractEffectMushroomCap {

    public GlowshroomCap(MushroomType type, Properties properties) {
        super(type, properties);
    }

    @Override
    protected boolean shouldDropEffectCloud(BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random random) {
        if (super.shouldDropEffectCloud(state, world, pos, random)) {
            //only drop effect at night or in dark areas
            BlockPos up = pos.above();
            return !world.isDay() || (!world.getBlockState(up).canOcclude() && world.getMaxLocalRawBrightness(up) <= state.getLightEmission());
        }
        return false;
    }

    @Nonnull
    @Override
    protected List<EffectInstance> getEffects(@Nonnull Random random) {
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<EffectInstance> effects = new LinkedList<>();
        effects.add(new EffectInstance(Effects.NIGHT_VISION, duration));
        return effects;
    }
}
