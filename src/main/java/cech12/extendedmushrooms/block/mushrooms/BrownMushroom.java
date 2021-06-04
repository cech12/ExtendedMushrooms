package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
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
        return ModFeatures.Configured.MEGA_BROWN_MUSHROOM;
    }

    @Nonnull
    @Override
    protected ConfiguredFeature<?, ?> getBigMushroomFeature() {
        //vanilla mushroom
        return WorldGenRegistries.CONFIGURED_FEATURE.getOptional(new ResourceLocation("huge_brown_mushroom"))
                .orElseGet(() -> Feature.HUGE_BROWN_MUSHROOM.withConfiguration(getConfig()));
    }
}
