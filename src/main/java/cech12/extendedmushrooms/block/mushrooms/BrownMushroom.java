package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class BrownMushroom extends MegaMushroom {

    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getMegaMushroomFeature(Random var1) {
        return ModFeatures.MEGA_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM);
    }

    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getBigMushroomFeature(Random var1, boolean var2) {
        //vanilla mushroom
        return Feature.HUGE_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM);
    }
}
