package cech12.extendedmushrooms.world.level.levelgen.feature;

import cech12.extendedmushrooms.world.level.levelgen.feature.configurations.ExtendedMushroomFeatureConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;

/**
 * Like MegaRedMushroomFeature, only size and cap height is different
 */
public class MegaPoisonousMushroomFeature extends MegaRedMushroomFeature {

    public MegaPoisonousMushroomFeature(Codec<ExtendedMushroomFeatureConfiguration> config) {
        super(config);
    }

    @Override
    protected int getSize(RandomSource random) {
        return (int) (super.getSize(random) * 1.2);
    }

    @Override
    protected float getCapHeightFactor() {
        return 0.25F;
    }
}
