package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
        //TODO When this block is pushed by a piston, an adjacent slime block still sticks to it (mixin slime block?)
        if (other.getBlock() == Blocks.SLIME_BLOCK || other.getBlock() == ExtendedMushroomsBlocks.SLIME_FUNGUS_CAP) return false;
        return state.isStickyBlock() || other.isStickyBlock();
    }

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return PathNodeType.STICKY_HONEY;
    }

    @Nonnull
    @Override
    protected List<EffectInstance> getEffects(@Nonnull Random random) {
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<EffectInstance> effects = new LinkedList<>();
        effects.add(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, duration));
        return effects;
    }

    @Override
    public void fallOn(World world, @Nonnull BlockPos blockPos, Entity entity, float fallDistance) {
        entity.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
        if (!world.isClientSide) {
            world.broadcastEntityEvent(entity, (byte)54);
        }
        if (entity.causeFallDamage(fallDistance, 0.2F)) {
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
