package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class HoneyFungusCap extends AbstractEffectMushroomCap {

    public HoneyFungusCap(MushroomType type, Properties properties) {
        super(type, properties);
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        if (other.getBlock() == Blocks.SLIME_BLOCK || other.getBlock() == ModBlocks.SLIME_FUNGUS_CAP.get()) return false;
        return state.isStickyBlock() || other.isStickyBlock();
    }

    @Nullable
    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
        return BlockPathTypes.STICKY_HONEY;
    }

    @Nonnull
    @Override
    protected List<MobEffectInstance> getEffects(@Nonnull RandomSource random) {
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<MobEffectInstance> effects = new LinkedList<>();
        effects.add(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration));
        return effects;
    }

    @Override
    public void fallOn(Level level, @Nonnull BlockState blockState, @Nonnull BlockPos blockPos, Entity entity, float fallDistance) {
        entity.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
        if (!level.isClientSide) {
            level.broadcastEntityEvent(entity, (byte)54);
        }
        if (entity.causeFallDamage(fallDistance, 0.2F, DamageSource.FALL)) {
            entity.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5F, this.soundType.getPitch() * 0.75F);
        }
    }

    @Override
    @Deprecated
    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(@Nonnull BlockState self, BlockState otherBlock, @Nonnull Direction direction) {
        //don't render sides between same blocks
        return otherBlock.is(this) || super.skipRendering(self, otherBlock, direction);
    }

}
