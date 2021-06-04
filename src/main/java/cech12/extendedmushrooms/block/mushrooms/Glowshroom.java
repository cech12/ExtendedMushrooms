package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;

public class Glowshroom extends MegaMushroom {

    public static BigMushroomFeatureConfig getConfig() {
        return new BigMushroomFeatureConfig(new SimpleBlockStateProvider(getDefaultCapState(ExtendedMushroomsBlocks.GLOWSHROOM_CAP)),
                new SimpleBlockStateProvider(getDefaultStemState(ExtendedMushroomsBlocks.GLOWSHROOM_STEM)), 2);
    }

    @Nonnull
    @Override
    protected ConfiguredFeature<?, ?> getBigMushroomFeature() {
        return ModFeatures.Configured.BIG_GLOWSHROOM;
    }

    @Nonnull
    @Override
    protected ConfiguredFeature<?, ?> getMegaMushroomFeature() {
        return ModFeatures.Configured.MEGA_GLOWSHROOM;
    }


}
