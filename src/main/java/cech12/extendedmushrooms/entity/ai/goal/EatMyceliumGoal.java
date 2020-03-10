package cech12.extendedmushrooms.entity.ai.goal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.function.Predicate;

public class EatMyceliumGoal extends Goal {
    private static final Predicate<BlockState> IS_MYCELIUM = BlockStateMatcher.forBlock(Blocks.MYCELIUM);
    private final MobEntity eaterEntity;
    private final World entityWorld;
    private int eatingTimer;

    public EatMyceliumGoal(MobEntity eaterEntityIn) {
        this.eaterEntity = eaterEntityIn;
        this.entityWorld = eaterEntityIn.world;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        if (this.eaterEntity.getRNG().nextInt(this.eaterEntity.isChild() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = new BlockPos(this.eaterEntity);
            if (IS_MYCELIUM.test(this.entityWorld.getBlockState(blockpos))) {
                return true;
            } else {
                return this.entityWorld.getBlockState(blockpos.down()).getBlock() == Blocks.MYCELIUM;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.eatingTimer = 40;
        this.entityWorld.setEntityState(this.eaterEntity, (byte)10);
        this.eaterEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.eatingTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.eatingTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat mycelium
     */
    public int getEatingTimer() {
        return this.eatingTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.eatingTimer = Math.max(0, this.eatingTimer - 1);
        if (this.eatingTimer == 4) {
            BlockPos blockpos = new BlockPos(this.eaterEntity);
            if (IS_MYCELIUM.test(this.entityWorld.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.eaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }

                this.eaterEntity.eatGrassBonus();
            } else {
                BlockPos blockpos1 = blockpos.down();
                if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.MYCELIUM) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.eaterEntity)) {
                        this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.MYCELIUM.getDefaultState()));
                        this.entityWorld.setBlockState(blockpos1, Blocks.DIRT.getDefaultState(), 2);
                    }

                    this.eaterEntity.eatGrassBonus();
                }
            }

        }
    }

}
