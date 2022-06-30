package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

import java.util.Random;

/**
 * Like MegaRedMushroomFeature, only size and cap height is different
 */
public class MegaPoisonousMushroomFeature extends MegaRedMushroomFeature {

    public MegaPoisonousMushroomFeature(Codec<HugeMushroomFeatureConfiguration> config) {
        super(config);
    }

    @Override
    protected int getSize(Random random) {
        return (int) (super.getSize(random) * 1.2);
    }

    @Override
    protected float getCapHeightFactor() {
        return 0.25F;
    }
}
