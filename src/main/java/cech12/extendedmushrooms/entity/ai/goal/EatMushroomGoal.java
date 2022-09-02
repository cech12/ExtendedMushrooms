package cech12.extendedmushrooms.entity.ai.goal;

import cech12.extendedmushrooms.config.ServerConfig;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

public class EatMushroomGoal extends Goal {

    private final Mob eaterEntity;
    private final Level entityWorld;

    /**
     * Number of ticks since the entity started to eat a mushroom
     */
    int eatingTimer;

    public EatMushroomGoal(Mob eaterEntity) {
        this.eaterEntity = eaterEntity;
        this.entityWorld = eaterEntity.level;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    private boolean isEntityOnMushroom() {
        return this.entityWorld.getBlockState(this.eaterEntity.blockPosition()).getBlock().asItem().getDefaultInstance().is(Tags.Items.MUSHROOMS);
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
                if (ServerConfig.SHEEP_ABSORB_MUSHROOM_TYPE_ENABLED.get()) {
                    if (this.eaterEntity instanceof Sheep && mushroomType != null) {
                        if (this.eaterEntity instanceof MushroomSheepEntity mushroomSheep) {
                            mushroomSheep.setMushroomType(mushroomType);
                            mushroomSheep.activateMushroomEffect(mushroomType);
                        } else {
                            MushroomSheepEntity.replaceSheep((Sheep) this.eaterEntity, mushroomType);
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
