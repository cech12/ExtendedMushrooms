package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class AmethystDeceiverCap extends AbstractEffectMushroomCap {

    public AmethystDeceiverCap(MushroomType type, Properties properties) {
        super(type, properties);
    }

    @Nonnull
    @Override
    protected List<MobEffectInstance> getEffects(@Nonnull RandomSource random) {
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<MobEffectInstance> effects = new LinkedList<>();
        effects.add(new MobEffectInstance(MobEffects.BLINDNESS, duration));
        return effects;
    }

}
