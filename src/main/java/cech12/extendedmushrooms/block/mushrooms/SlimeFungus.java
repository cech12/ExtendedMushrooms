package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;

public class SlimeFungus extends BigMushroom {

    public static HugeMushroomFeatureConfiguration getConfig() {
        return new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(getDefaultCapState(ModBlocks.SLIME_FUNGUS_CAP.get())),
                BlockStateProvider.simple(getDefaultStemState(ModBlocks.POISONOUS_MUSHROOM_STEM.get())), 2);
    }

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        //TODO return ModFeatures.BIG_SLIME_FUNGUS_CONFIGURED;
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation("huge_brown_mushroom"));
    }

}
