package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;

public class HoneyFungus extends BigMushroom {

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        return ModFeatures.BIG_HONEY_FUNGUS_CONFIGURED;
    }

}
