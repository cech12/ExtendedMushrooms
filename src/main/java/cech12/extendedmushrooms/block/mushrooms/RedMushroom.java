package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

public class RedMushroom extends MegaMushroom {

    public static HugeMushroomFeatureConfiguration getConfig() {
        return new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(getDefaultCapState(Blocks.RED_MUSHROOM_BLOCK)),
                BlockStateProvider.simple(getDefaultStemState(Blocks.MUSHROOM_STEM)), 2);
    }

    @Nonnull
    @Override
    protected RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> getMegaMushroomFeature() {
        return ModFeatures.MEGA_RED_MUSHROOM_CONFIGURED;
    }

    @Nonnull
    @Override
    public RegistryObject<ConfiguredFeature<HugeMushroomFeatureConfiguration, ?>> getBigMushroomFeature() {
        //vanilla mushroom
        return RegistryObject.create(new ResourceLocation("huge_red_mushroom"), Registry.CONFIGURED_FEATURE_REGISTRY, "minecraft");
    }

}
