package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.block.EMMushroomBlock;
import cech12.extendedmushrooms.block.mushrooms.BigMushroom;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class DeadlyFibrecapBlock extends EMMushroomBlock {

    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public DeadlyFibrecapBlock(BigMushroom bigMushroom, Properties properties) {
        super(bigMushroom, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TRIGGERED, false));
    }

    @Deprecated
    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel worldIn, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
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

    private boolean isTriggeringEntity(Entity entity) {
        //triggers only for living non animal entities
        return entity instanceof LivingEntity && !(entity instanceof Animal);
    }

    private void generateEffectCloud(@Nonnull ServerLevel worldIn, @Nonnull BlockState state, BlockPos pos) {
        RandomSource random = worldIn.getRandom();
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<MobEffectInstance> effects = new LinkedList<>();
        effects.add(new MobEffectInstance(MobEffects.POISON, duration));
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(worldIn, pos.getX() + random.nextDouble(), pos.getY(), pos.getZ() + random.nextDouble());
        areaeffectcloudentity.setOwner(null);
        areaeffectcloudentity.setDuration(600 + random.nextInt(600));
        areaeffectcloudentity.setRadius(1.5F + (random.nextFloat() * 0.5F));
        areaeffectcloudentity.setRadiusOnUse(-(0.4F + (random.nextFloat() * 0.2F)));
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
        areaeffectcloudentity.setFixedColor(PotionUtils.getColor(effects));
        for (MobEffectInstance effectinstance : effects) {
            areaeffectcloudentity.addEffect(new MobEffectInstance(effectinstance));
        }
        worldIn.addFreshEntity(areaeffectcloudentity);
        //set state to triggered
        worldIn.setBlock(pos, state.setValue(TRIGGERED, true), 2);
    }

    @Deprecated
    @Override
    public void entityInside(@Nonnull BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull Entity entityIn) {
        if (worldIn instanceof ServerLevel && !state.getValue(TRIGGERED) && isTriggeringEntity(entityIn)) {
            generateEffectCloud((ServerLevel)worldIn, state, pos);
        }
    }

    @Override
    public void playerWillDestroy(@Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        if (worldIn instanceof ServerLevel && !state.getValue(TRIGGERED)) {
            generateEffectCloud((ServerLevel)worldIn, state, pos);
        }
    }
}
