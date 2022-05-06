package cech12.extendedmushrooms.entity.ai.goal;

import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class EatMushroomGoal extends Goal {

    private final MobEntity eaterEntity;
    private final World entityWorld;

    /**
     * Number of ticks since the entity started to eat a mushroom
     */
    int eatingTimer;

    public EatMushroomGoal(MobEntity eaterEntity) {
        this.eaterEntity = eaterEntity;
        this.entityWorld = eaterEntity.level;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    private boolean isEntityOnMushroom() {
        return this.entityWorld.getBlockState(this.eaterEntity.blockPosition()).getBlock().asItem().is(Tags.Items.MUSHROOMS);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
        return this.eaterEntity.getRandom().nextInt(this.eaterEntity.isBaby() ? 50 : 1000) == 0 && this.isEntityOnMushroom();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.eatingTimer = 40;
        this.entityWorld.broadcastEntityEvent(this.eaterEntity, (byte) 10);
        this.eaterEntity.getNavigation().stop();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void stop() {
        this.eatingTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return this.eatingTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat a mushroom
     */
    public int getEatingTimer() {
        return this.eatingTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        this.eatingTimer = Math.max(0, this.eatingTimer - 1);
        if (this.eatingTimer == 4) {
            BlockPos blockPos = getBlockPos();
            if (this.isEntityOnMushroom()) {
                MushroomType mushroomType = MushroomType.byItemOrNull(this.entityWorld.getBlockState(this.eaterEntity.blockPosition()).getBlock().asItem());
                if (this.entityWorld.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    this.entityWorld.destroyBlock(blockPos, false);
                }
                this.eaterEntity.ate();
                if (Config.SHEEP_ABSORB_MUSHROOM_TYPE_ENABLED.get()) {
                    if (this.eaterEntity instanceof SheepEntity && mushroomType != null) {
                        if (this.eaterEntity instanceof MushroomSheepEntity) {
                            MushroomSheepEntity mushroomSheep = (MushroomSheepEntity) this.eaterEntity;
                            mushroomSheep.setMushroomType(mushroomType);
                            mushroomSheep.activateMushroomEffect(mushroomType);
                        } else {
                            MushroomSheepEntity.replaceSheep((SheepEntity) this.eaterEntity, mushroomType);
                        }
                    }
                }
            }
        }
    }

    private BlockPos getBlockPos() {
        return new BlockPos(this.eaterEntity.getX(), this.eaterEntity.getY(), this.eaterEntity.getZ());
    }

}
