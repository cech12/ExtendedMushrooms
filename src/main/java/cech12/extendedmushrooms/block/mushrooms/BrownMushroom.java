package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
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
    protected RegistryObject<ConfiguredFeature<?, ?>> getMegaMushroomFeature() {
        return ModFeatures.MEGA_BROWN_MUSHROOM_CONFIGURED;
    }

    @Nonnull
    @Override
    public RegistryObject<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        //vanilla mushroom
        return RegistryObject.create(new ResourceLocation("huge_brown_mushroom"), Registries.CONFIGURED_FEATURE, "minecraft");
    }
}
