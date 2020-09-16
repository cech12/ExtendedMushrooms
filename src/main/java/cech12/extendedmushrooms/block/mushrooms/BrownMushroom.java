package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nonnull;

public class BrownMushroom extends MegaMushroom {

    public static BigMushroomFeatureConfig getConfig() {
        return new BigMushroomFeatureConfig(new SimpleBlockStateProvider(getDefaultCapState(Blocks.BROWN_MUSHROOM_BLOCK)),
                new SimpleBlockStateProvider(getDefaultStemState(Blocks.MUSHROOM_STEM)), 3);
    }

    @Nonnull
    @Override
    protected ConfiguredFeature<?, ?> getMegaMushroomFeature() {
        return ModFeatures.MEGA_BROWN_MUSHROOM.withConfiguration(getConfig());
    }

    @Nonnull
    @Override
    protected ConfiguredFeature<?, ?> getBigMushroomFeature() {
        //vanilla mushroom
        return Feature.HUGE_BROWN_MUSHROOM.withConfiguration(getConfig());
    }
}
