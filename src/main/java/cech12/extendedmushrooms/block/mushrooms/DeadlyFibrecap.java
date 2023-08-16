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

public class DeadlyFibrecap extends MegaMushroom {

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        return ModFeatures.BIG_DEADLY_FIBRECAP_CONFIGURED;
    }

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getMegaMushroomFeature() {
        return ModFeatures.MEGA_DEADLY_FIBRECAP_CONFIGURED;
    }

    @Override
    protected void afterGrowing(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, RandomSource random) {
        //spawn a cave spider with a chance of 5%
        spawnMobAroundPositionByChance(20, EntityType.CAVE_SPIDER, world, blockPos, random);
    }

}
