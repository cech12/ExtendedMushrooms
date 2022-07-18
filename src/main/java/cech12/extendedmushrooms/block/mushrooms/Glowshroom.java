package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;

public class Glowshroom extends MegaMushroom {

    public static HugeMushroomFeatureConfiguration getConfig() {
        return new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(getDefaultCapState(ModBlocks.GLOWSHROOM_CAP.get())),
                BlockStateProvider.simple(getDefaultStemState(ModBlocks.GLOWSHROOM_STEM.get())), 2);
    }

    @Nonnull
    @Override
    public Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> getBigMushroomFeature() {
        return ModFeatures.Configured.BIG_GLOWSHROOM;
    }

    @Nonnull
    @Override
    protected Holder<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> getMegaMushroomFeature() {
        return ModFeatures.Configured.MEGA_GLOWSHROOM;
    }


}
