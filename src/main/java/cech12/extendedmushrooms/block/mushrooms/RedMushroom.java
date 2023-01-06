package cech12.extendedmushrooms.block.mushrooms;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;

public class RedMushroom extends MegaMushroom {

    public static HugeMushroomFeatureConfiguration getConfig() {
        return new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(getDefaultCapState(Blocks.RED_MUSHROOM_BLOCK)),
                BlockStateProvider.simple(getDefaultStemState(Blocks.MUSHROOM_STEM)), 2);
    }

    @Nonnull
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getMegaMushroomFeature() {
        //TODO return ModFeatures.MEGA_RED_MUSHROOM_CONFIGURED;
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation("huge_brown_mushroom"));
    }

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        //vanilla mushroom
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation("huge_red_mushroom"));
    }

}
