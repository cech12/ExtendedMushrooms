package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

public class SlimeFungus extends BigMushroom {

    public static HugeMushroomFeatureConfiguration getConfig() {
        return new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(getDefaultCapState(ModBlocks.SLIME_FUNGUS_CAP.get())),
                BlockStateProvider.simple(getDefaultStemState(ModBlocks.POISONOUS_MUSHROOM_STEM.get())), 2);
    }

    @Nonnull
    @Override
    public RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> getBigMushroomFeature() {
        return ModFeatures.Configured.BIG_SLIME_FUNGUS;
    }

}
