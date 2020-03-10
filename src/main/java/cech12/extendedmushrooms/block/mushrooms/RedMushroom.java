package cech12.extendedmushrooms.block.mushrooms;

import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

public class RedMushroom extends MegaMushroom{

    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getMegaMushroomFeature(Random var1) {
        //TODO
        return null;
    }

    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getBigMushroomFeature(Random var1, boolean var2) {
        //vanilla mushroom
        return Feature.HUGE_RED_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_RED_MUSHROOM);
    }

}
