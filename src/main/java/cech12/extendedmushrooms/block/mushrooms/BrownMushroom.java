package cech12.extendedmushrooms.block.mushrooms;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;

public class BrownMushroom extends MegaMushroom {

    public static HugeMushroomFeatureConfiguration getConfig() {
        return new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(getDefaultCapState(Blocks.BROWN_MUSHROOM_BLOCK)),
                BlockStateProvider.simple(getDefaultStemState(Blocks.MUSHROOM_STEM)), 3);
    }

    @Nonnull
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getMegaMushroomFeature() {
        //TODO return ModFeatures.MEGA_BROWN_MUSHROOM_CONFIGURED;
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation("huge_brown_mushroom"));
    }

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        //vanilla mushroom
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation("huge_brown_mushroom"));
    }
}
