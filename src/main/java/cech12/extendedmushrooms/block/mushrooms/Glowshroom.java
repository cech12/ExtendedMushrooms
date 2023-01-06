package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
    public ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        //TODO return ModFeatures.BIG_GLOWSHROOM_CONFIGURED;
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation("huge_brown_mushroom"));
    }

    @Nonnull
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getMegaMushroomFeature() {
        //TODO return ModFeatures.MEGA_GLOWSHROOM_CONFIGURED;
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation("huge_brown_mushroom"));
    }


}
