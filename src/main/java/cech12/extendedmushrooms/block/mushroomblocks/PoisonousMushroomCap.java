package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomType;
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

import net.minecraft.block.AbstractBlock.Properties;

public class PoisonousMushroomCap extends AbstractEffectMushroomCap {

    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    private static final Direction[] DIRECTION_ORDER = {Direction.UP, Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.SOUTH};

    public PoisonousMushroomCap(MushroomType type, Properties properties) {
        super(type, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TRIGGERED, false));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return super.isRandomlyTicking(state) || !state.getValue(TRIGGERED);
    }

    @Override
    public void randomTick(BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random random) {
        super.randomTick(state, worldIn, pos, random);
        //reset TRIGGERED state
        if (state.getValue(TRIGGERED)) {
            worldIn.setBlock(pos, state.setValue(TRIGGERED, false), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
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
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn instanceof ServerWorld && !state.getValue(TRIGGERED) && !state.getValue(AbstractEffectMushroomCap.PERSISTENT)) {
            this.generateEffectCloud((ServerWorld)worldIn, state, pos, hit.getDirection());
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void attack(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if (worldIn instanceof ServerWorld && !state.getValue(TRIGGERED) && !state.getValue(AbstractEffectMushroomCap.PERSISTENT)) {
            this.generateEffectCloud((ServerWorld)worldIn, state, pos, Direction.UP);
        }
    }

    @Override
    public void stepOn(World worldIn, BlockPos pos, Entity entityIn) {
        BlockState state = worldIn.getBlockState(pos);
        if (worldIn instanceof ServerWorld && !state.getValue(TRIGGERED) && !state.getValue(AbstractEffectMushroomCap.PERSISTENT) && this.isTriggeringEntity(entityIn)) {
            this.generateEffectCloud((ServerWorld)worldIn, state, pos, Direction.UP);
        }
    }

    @Override
    public void playerWillDestroy(World worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull PlayerEntity player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        if (worldIn instanceof ServerWorld && !state.getValue(TRIGGERED) && !state.getValue(AbstractEffectMushroomCap.PERSISTENT)) {
            this.generateEffectCloud((ServerWorld)worldIn, state, pos, null);
        }
    }

    @Override
    public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        super.fallOn(worldIn, pos, entityIn, fallDistance);
        BlockState state = worldIn.getBlockState(pos);
        if (worldIn instanceof ServerWorld && fallDistance >= 1 && !state.getValue(TRIGGERED) && this.isTriggeringEntity(entityIn)) { //also for persistent
            this.generateEffectCloud((ServerWorld)worldIn, state, pos, Direction.UP);
        }
    }
}
