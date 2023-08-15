package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;

public class SlimeFungus extends BigMushroom {

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        return ModFeatures.BIG_SLIME_FUNGUS_CONFIGURED;
    }

    @Override
    protected void afterGrowing(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, RandomSource random) {
        //spawn a parrot with a chance of 1%
        if (random.nextInt(100) == 0) {
            Parrot parrot = EntityType.PARROT.create(world);
            BlockPos entityPos = getEmptyPositionAround(world, blockPos);
            if (parrot != null && entityPos != null) {
                parrot.setPos(entityPos.getCenter());
                parrot.finalizeSpawn(world, world.getCurrentDifficultyAt(blockPos), MobSpawnType.NATURAL, null, null);
                world.addFreshEntity(parrot);
            }
        }
    }

}
