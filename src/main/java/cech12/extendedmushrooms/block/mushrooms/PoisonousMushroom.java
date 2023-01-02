package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

public class PoisonousMushroom extends MegaMushroom {

    public static HugeMushroomFeatureConfiguration getConfig() {
        return new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(getDefaultCapState(ModBlocks.POISONOUS_MUSHROOM_CAP.get())),
                BlockStateProvider.simple(getDefaultStemState(ModBlocks.POISONOUS_MUSHROOM_STEM.get())), 2);
    }

    @Nonnull
    @Override
    public RegistryObject<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        return ModFeatures.BIG_POISONOUS_MUSHROOM_CONFIGURED;
    }

    @Nonnull
    @Override
    protected RegistryObject<ConfiguredFeature<?, ?>> getMegaMushroomFeature() {
        return ModFeatures.MEGA_POISONOUS_MUSHROOM_CONFIGURED;
    }

}
