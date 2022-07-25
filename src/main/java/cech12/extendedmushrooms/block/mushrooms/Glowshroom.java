package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

public class Glowshroom extends MegaMushroom {

    public static HugeMushroomFeatureConfiguration getConfig() {
        return new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(getDefaultCapState(ModBlocks.GLOWSHROOM_CAP.get())),
                BlockStateProvider.simple(getDefaultStemState(ModBlocks.GLOWSHROOM_STEM.get())), 2);
    }

    @Nonnull
    @Override
    public RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> getBigMushroomFeature() {
        return ModFeatures.BIG_GLOWSHROOM_CONFIGURED;
    }

    @Nonnull
    @Override
    protected RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> getMegaMushroomFeature() {
        return ModFeatures.MEGA_GLOWSHROOM_CONFIGURED;
    }


}
