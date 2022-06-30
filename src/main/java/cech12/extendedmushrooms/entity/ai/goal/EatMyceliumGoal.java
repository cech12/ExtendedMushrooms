package cech12.extendedmushrooms.entity.ai.goal;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.function.Predicate;

public class EatMyceliumGoal extends Goal {
    private static final Predicate<BlockState> IS_MYCELIUM = BlockStatePredicate.forBlock(Blocks.MYCELIUM);
    private final Mob eaterEntity;
    private final Level entityWorld;
    private int eatingTimer;

    public EatMyceliumGoal(Mob eaterEntityIn) {
        this.eaterEntity = eaterEntityIn;
        this.entityWorld = eaterEntityIn.level;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if (this.eaterEntity.getRandom().nextInt(this.eaterEntity.isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = this.eaterEntity.blockPosition();
            if (IS_MYCELIUM.test(this.entityWorld.getBlockState(blockpos))) {
                return true;
            } else {
                return this.entityWorld.getBlockState(blockpos.below()).getBlock() == Blocks.MYCELIUM;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.eatingTimer = 40;
        this.entityWorld.broadcastEntityEvent(this.eaterEntity, (byte)10);
        this.eaterEntity.getNavigation().stop();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.eatingTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
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
            BlockPos blockpos = this.eaterEntity.blockPosition();
            if (IS_MYCELIUM.test(this.entityWorld.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.eaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }

                this.eaterEntity.ate();
            } else {
                BlockPos blockpos1 = blockpos.below();
                if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.MYCELIUM) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.eaterEntity)) {
                        this.entityWorld.levelEvent(2001, blockpos1, Block.getId(Blocks.MYCELIUM.defaultBlockState()));
                        this.entityWorld.setBlock(blockpos1, Blocks.DIRT.defaultBlockState(), 2);
                    }

                    this.eaterEntity.ate();
                }
            }

        }
    }

}
