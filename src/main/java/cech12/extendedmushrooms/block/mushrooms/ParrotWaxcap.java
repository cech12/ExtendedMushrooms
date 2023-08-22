package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;

public class ParrotWaxcap extends BigMushroom {

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        return ModFeatures.BIG_PARROT_WAXCAP_CONFIGURED;
    }

    @Override
    protected void afterGrowing(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, RandomSource random) {
        //spawn a parrot with a chance of 5%
        spawnMobAroundPositionByChance(20, EntityType.PARROT, world, blockPos, random);
    }

}
