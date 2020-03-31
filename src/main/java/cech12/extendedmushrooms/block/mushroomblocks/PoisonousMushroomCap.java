package cech12.extendedmushrooms.block.mushroomblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PoisonousMushroomCap extends AbstractEffectMushroomCap {

    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    private static final Direction[] DIRECTION_ORDER = {Direction.UP, Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.SOUTH};

    public PoisonousMushroomCap(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(TRIGGERED, false));
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return super.ticksRandomly(state) || !state.get(TRIGGERED);
    }

    @Override
    public void randomTick(BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random random) {
        super.randomTick(state, worldIn, pos, random);
        //reset TRIGGERED state
        if (state.get(TRIGGERED)) {
            worldIn.setBlockState(pos, state.with(TRIGGERED, false), 2);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(TRIGGERED);
    }

    @Nonnull
    @Override
    protected List<EffectInstance> getEffects(@Nonnull Random random) {
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<EffectInstance> effects = new LinkedList<>();
        effects.add(new EffectInstance(Effects.POISON, duration));
        return effects;
    }

    private boolean isTriggeringEntity(Entity entity) {
        //triggers only for living non animal entities
        return entity instanceof LivingEntity && !(entity instanceof AnimalEntity);
    }

    /**
     * Generates an effect cloud on given face side.
     * When side is blocked by a block, all directions are checked and when possible there will be generated a cloud.
     * When face is null, the effect cloud is generated at block position.
     */
    private void generateEffectCloud(@Nonnull ServerWorld worldIn, @Nonnull BlockState state, BlockPos pos, @Nullable Direction face) {
        boolean posFound = false;
        BlockPos.Mutable effectPos = new BlockPos.Mutable();
        if (face != null) {
            LinkedList<Direction> directions = new LinkedList<>(Arrays.asList(DIRECTION_ORDER));
            directions.remove(face);
            directions.addFirst(face);
            for (Direction direction : directions) {
                effectPos.setPos(pos).move(direction);
                if (!worldIn.getBlockState(effectPos).isSolid()) {
                    posFound = true;
                    break;
                }
            }
        } else {
            effectPos.setPos(pos);
            if (!worldIn.getBlockState(effectPos).isSolid()) {
                posFound = true;
            }
        }

        if (posFound) {
            this.spawnEffectCloud(worldIn, effectPos, worldIn.getRandom());
            worldIn.setBlockState(pos, state.with(TRIGGERED, true), 2);
        }
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn instanceof ServerWorld && !state.get(TRIGGERED) && !state.get(AbstractEffectMushroomCap.PERSISTENT)) {
            this.generateEffectCloud((ServerWorld)worldIn, state, pos, hit.getFace());
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if (worldIn instanceof ServerWorld && !state.get(TRIGGERED) && !state.get(AbstractEffectMushroomCap.PERSISTENT)) {
            this.generateEffectCloud((ServerWorld)worldIn, state, pos, Direction.UP);
        }
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        BlockState state = worldIn.getBlockState(pos);
        if (worldIn instanceof ServerWorld && !state.get(TRIGGERED) && !state.get(AbstractEffectMushroomCap.PERSISTENT) && this.isTriggeringEntity(entityIn)) {
            this.generateEffectCloud((ServerWorld)worldIn, state, pos, Direction.UP);
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if (worldIn instanceof ServerWorld && !state.get(TRIGGERED) && !state.get(AbstractEffectMushroomCap.PERSISTENT)) {
            this.generateEffectCloud((ServerWorld)worldIn, state, pos, null);
        }
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        BlockState state = worldIn.getBlockState(pos);
        if (worldIn instanceof ServerWorld && fallDistance >= 1 && !state.get(TRIGGERED) && this.isTriggeringEntity(entityIn)) { //also for persistent
            this.generateEffectCloud((ServerWorld)worldIn, state, pos, Direction.UP);
        }
    }
}
