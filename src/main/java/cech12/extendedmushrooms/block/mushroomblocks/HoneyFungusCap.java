package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HoneyFungusCap extends AbstractEffectMushroomCap {

    public HoneyFungusCap(MushroomType type, Properties properties) {
        super(type, properties);
    }

    @Nonnull
    @Override
    protected List<EffectInstance> getEffects(@Nonnull Random random) {
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<EffectInstance> effects = new LinkedList<>();
        effects.add(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, duration));
        return effects;
    }
}
