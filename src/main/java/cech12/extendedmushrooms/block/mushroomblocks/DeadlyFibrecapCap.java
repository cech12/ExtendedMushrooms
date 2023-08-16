package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DeadlyFibrecapCap extends AbstractEffectMushroomCap {

    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    private static final Direction[] DIRECTION_ORDER = {Direction.UP, Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.SOUTH};

    public DeadlyFibrecapCap(MushroomType type, Properties properties) {
        super(type, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TRIGGERED, false));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return super.isRandomlyTicking(state) || !state.getValue(TRIGGERED);
    }

    @Deprecated
    @Override
    public void randomTick(BlockState state, @Nonnull ServerLevel worldIn, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
        super.randomTick(state, worldIn, pos, random);
        //reset TRIGGERED state
        if (state.getValue(TRIGGERED)) {
            worldIn.setBlock(pos, state.setValue(TRIGGERED, false), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TRIGGERED);
    }

    @Nonnull
    @Override
    protected List<MobEffectInstance> getEffects(@Nonnull RandomSource random) {
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<MobEffectInstance> effects = new LinkedList<>();
        effects.add(new MobEffectInstance(MobEffects.POISON, duration));
        return effects;
    }

    private boolean isTriggeringEntity(Entity entity) {
        //triggers only for living non animal entities
        return entity instanceof LivingEntity && !(entity instanceof Animal);
    }

    /**
     * Generates an effect cloud on given face side.
     * When side is blocked by a block, all directions are checked and when possible there will be generated a cloud.
     * When face is null, the effect cloud is generated at block position.
     */
    private void generateEffectCloud(@Nonnull ServerLevel worldIn, @Nonnull BlockState state, BlockPos pos, @Nullable Direction face) {
        boolean posFound = false;
        BlockPos.MutableBlockPos effectPos = new BlockPos.MutableBlockPos();
        if (face != null) {
            LinkedList<Direction> directions = new LinkedList<>(Arrays.asList(DIRECTION_ORDER));
            directions.remove(face);
            directions.addFirst(face);
            for (Direction direction : directions) {
                effectPos.set(pos).move(direction);
                if (!worldIn.getBlockState(effectPos).canOcclude()) {
                    posFound = true;
                    break;
                }
            }
        } else {
            effectPos.set(pos);
            if (!worldIn.getBlockState(effectPos).canOcclude()) {
                posFound = true;
            }
        }

        if (posFound) {
            this.spawnEffectCloud(worldIn, effectPos, worldIn.getRandom());
            worldIn.setBlock(pos, state.setValue(TRIGGERED, true), 2);
        }
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand handIn, @Nonnull BlockHitResult hit) {
        if (worldIn instanceof ServerLevel && !state.getValue(TRIGGERED) && !state.getValue(AbstractEffectMushroomCap.PERSISTENT)) {
            this.generateEffectCloud((ServerLevel)worldIn, state, pos, hit.getDirection());
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void attack(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player) {
        if (level instanceof ServerLevel && !state.getValue(TRIGGERED) && !state.getValue(AbstractEffectMushroomCap.PERSISTENT)) {
            this.generateEffectCloud((ServerLevel)level, state, pos, Direction.UP);
        }
    }

    @Override
    public void stepOn(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Entity entityIn) {
        if (level instanceof ServerLevel && !state.getValue(TRIGGERED) && !state.getValue(AbstractEffectMushroomCap.PERSISTENT) && this.isTriggeringEntity(entityIn)) {
            this.generateEffectCloud((ServerLevel)level, state, pos, Direction.UP);
        }
    }

    @Override
    public void playerWillDestroy(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
        super.playerWillDestroy(level, pos, state, player);
        if (level instanceof ServerLevel && !state.getValue(TRIGGERED) && !state.getValue(AbstractEffectMushroomCap.PERSISTENT)) {
            this.generateEffectCloud((ServerLevel)level, state, pos, null);
        }
    }

    @Override
    public void fallOn(@Nonnull Level level, @Nonnull BlockState blockState, @Nonnull BlockPos blockPos, @Nonnull Entity entity, float fallDistance) {
        super.fallOn(level, blockState, blockPos, entity, fallDistance);
        if (level instanceof ServerLevel && fallDistance >= 1 && !blockState.getValue(TRIGGERED) && this.isTriggeringEntity(entity)) { //also for persistent
            this.generateEffectCloud((ServerLevel)level, blockState, blockPos, Direction.UP);
        }
    }
}
