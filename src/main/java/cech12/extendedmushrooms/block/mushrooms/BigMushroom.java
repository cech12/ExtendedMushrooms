package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.MushroomUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction8;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.level.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;
import java.util.Optional;

public abstract class BigMushroom {

    public BigMushroom() {
    }

    @Nonnull
    public abstract ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature();

    protected static BlockState getDefaultStemState(Block stemBlock) {
        return stemBlock.defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false);
    }

    protected static BlockState getDefaultCapState(Block capBlock) {
        return capBlock.defaultBlockState().setValue(HugeMushroomBlock.DOWN, false);
    }

    /**
     * Try to find an empty block at the 8 (flat) positions around the given position.
     * @param level level to check
     * @param center center position
     * @return the BlockPos of the found empty block position. null when none is empty
     */
    protected static BlockPos getEmptyPositionAround(ServerLevel level, BlockPos center) {
        for (Direction8 directions : Direction8.values()) {
            BlockPos pos = center;
            for (Direction direction : directions.getDirections()) {
                pos = pos.relative(direction);
            }
            if (level.isEmptyBlock(pos)) {
                return pos;
            }
        }
        return null;
    }

    /**
     * Spawns by chance a new mob of the given entity type around the given position.
     * @param oneOf chance to spawn the mob (one of 100 means 1%)
     * @param entityType entity type of mob
     * @param level level
     * @param blockPos position
     * @param random random object
     */
    protected static <T extends LivingEntity> void spawnMobAroundPositionByChance(int oneOf, EntityType<T> entityType, ServerLevel level, BlockPos blockPos, RandomSource random) {
        if (random.nextInt(oneOf) == 0) {
            T entity = entityType.create(level);
            BlockPos entityPos = getEmptyPositionAround(level, blockPos);
            if (entity != null && entityPos != null) {
                entity.setPos(entityPos.getCenter());
                if (entity instanceof AgeableMob ageableMob) {
                    ageableMob.finalizeSpawn(level, level.getCurrentDifficultyAt(blockPos), MobSpawnType.NATURAL, null, null);
                }
                level.addFreshEntity(entity);
            }
        }
    }

    public boolean growMushroom(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, RandomSource random) {
        if (!MushroomUtils.isValidMushroomPosition(world, blockPos)) {
            return false;
        }
        Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(this.getBigMushroomFeature());
        if (optional.isEmpty()) {
            return false;
        }
        SaplingGrowTreeEvent event = ForgeEventFactory.blockGrowFeature(world, random, blockPos, optional.get());
        if (event.getResult().equals(Event.Result.DENY) || event.getFeature() == null) return false;
        ConfiguredFeature<?, ?> feature = event.getFeature().value();
        world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 4);
        if (feature.place(world, chunkGenerator, random, blockPos)) {
            afterGrowing(world, chunkGenerator, blockPos, blockState, random);
            return true;
        } else {
            world.setBlock(blockPos, blockState, 4);
            return false;
        }
    }

    /**
     * Called after a mushroom grew up.
     */
    protected void afterGrowing(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, RandomSource random) {
        //could be overridden by extending classes
    }

}
