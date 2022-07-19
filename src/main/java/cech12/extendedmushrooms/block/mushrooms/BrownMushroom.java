package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

public class BrownMushroom extends MegaMushroom {

    public static HugeMushroomFeatureConfiguration getConfig() {
        return new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(getDefaultCapState(Blocks.BROWN_MUSHROOM_BLOCK)),
                BlockStateProvider.simple(getDefaultStemState(Blocks.MUSHROOM_STEM)), 3);
    }

    @Nonnull
    @Override
    protected RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> getMegaMushroomFeature() {
        return ModFeatures.Configured.MEGA_BROWN_MUSHROOM;
    }

    @Nonnull
    @Override
    public RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> getBigMushroomFeature() {
        //vanilla mushroom
        return RegistryObject.of(TreeFeatures.HUGE_BROWN_MUSHROOM.value().feature().getRegistryName(), Registry.CONFIGURED_FEATURE_REGISTRY, "minecraft");
    }
}
