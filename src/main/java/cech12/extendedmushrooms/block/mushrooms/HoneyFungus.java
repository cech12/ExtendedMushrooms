package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.init.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nonnull;

public class HoneyFungus extends BigMushroom {

    @Nonnull
    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature() {
        return ModFeatures.BIG_HONEY_FUNGUS_CONFIGURED;
    }

    @Override
    protected void afterGrowing(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, RandomSource random) {
        //spawn a bee with a chance of 1%
        if (random.nextInt(100) == 0) {
            Bee bee = EntityType.BEE.create(world);
            BlockPos entityPos = getEmptyPositionAround(world, blockPos);
            if (bee != null && entityPos != null) {
                bee.setPos(entityPos.getCenter());
                bee.finalizeSpawn(world, world.getCurrentDifficultyAt(blockPos), MobSpawnType.NATURAL, null, null);
                world.addFreshEntity(bee);
            }
        }
    }

}
