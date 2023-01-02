package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

public class HoneyFungus extends BigMushroom {

    public static HugeMushroomFeatureConfiguration getConfig() {
        return new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(getDefaultCapState(ModBlocks.HONEY_FUNGUS_CAP.get())),
                BlockStateProvider.simple(getDefaultStemState(ModBlocks.HONEY_FUNGUS_STEM.get())), 2);
    }

    @Nonnull
    @Override
    public RegistryObject<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        return ModFeatures.BIG_HONEY_FUNGUS_CONFIGURED;
    }

}
