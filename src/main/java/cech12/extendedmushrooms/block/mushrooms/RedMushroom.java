package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;

public class RedMushroom extends MegaMushroom {

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getMegaMushroomFeature() {
        return ModFeatures.MEGA_RED_MUSHROOM_CONFIGURED;
    }

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        //vanilla mushroom
        return ModFeatures.HUGE_RED_MUSHROOM_CONFIGURED;
    }

}
