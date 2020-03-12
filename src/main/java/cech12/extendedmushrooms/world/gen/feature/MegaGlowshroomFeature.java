package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

import java.util.Random;
import java.util.function.Function;

/**
 * Like MegaBrownMushroomFeature, only size and cap radius is different
 */
public class MegaGlowshroomFeature extends MegaBrownMushroomFeature {

    public MegaGlowshroomFeature(Function<Dynamic<?>, ? extends BigMushroomFeatureConfig> config) {
        super(config);
    }

    @Override
    protected int getSize(Random random) {
        int i = random.nextInt(4) + 7;
        if (random.nextInt(12) == 0) {
            i = random.nextInt(6) + 9;
        }
        return i;
    }

    @Override
    protected int getCapRadius(Random random) {
        return 2 + random.nextInt(2);
    }
}
