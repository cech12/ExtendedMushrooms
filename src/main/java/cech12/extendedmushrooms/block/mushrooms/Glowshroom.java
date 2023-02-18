package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;

public class Glowshroom extends MegaMushroom {

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        return ModFeatures.BIG_GLOWSHROOM_CONFIGURED;
    }

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getMegaMushroomFeature() {
        return ModFeatures.MEGA_GLOWSHROOM_CONFIGURED;
    }


}
