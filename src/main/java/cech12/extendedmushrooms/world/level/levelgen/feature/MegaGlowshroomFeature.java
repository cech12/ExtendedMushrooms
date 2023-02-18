package cech12.extendedmushrooms.world.level.levelgen.feature;

import cech12.extendedmushrooms.world.level.levelgen.feature.configurations.ExtendedMushroomFeatureConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;

/**
 * Like MegaBrownMushroomFeature, only size and cap radius is different
 */
public class MegaGlowshroomFeature extends MegaBrownMushroomFeature {

    public MegaGlowshroomFeature(Codec<ExtendedMushroomFeatureConfiguration> config) {
        super(config);
    }

    @Override
    protected int getSize(RandomSource random) {
        int i = random.nextInt(4) + 7;
        if (random.nextInt(12) == 0) {
            i = random.nextInt(6) + 9;
        }
        return i;
    }

    @Override
    protected int getCapRadius(RandomSource random) {
        return 2 + random.nextInt(2);
    }
}
