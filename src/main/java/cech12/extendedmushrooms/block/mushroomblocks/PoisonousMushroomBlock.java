package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.block.EMMushroomBlock;
import cech12.extendedmushrooms.block.mushrooms.PoisonousMushroom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PoisonousMushroomBlock extends EMMushroomBlock {

    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public PoisonousMushroomBlock(Properties properties) {
        super(new PoisonousMushroom(), properties);
        this.setDefaultState(this.getDefaultState().with(TRIGGERED, false));
    }

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random random) {
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

    private boolean isTriggeringEntity(Entity entity) {
        //triggers only for living non animal entities
        return entity instanceof LivingEntity && !(entity instanceof AnimalEntity);
    }

    private void generateEffectCloud(@Nonnull ServerWorld worldIn, @Nonnull BlockState state, BlockPos pos) {
        Random random = worldIn.getRandom();
        int duration = 200 + random.nextInt(200);
        if (random.nextInt(100) == 0) {
            duration += 1200;
        }
        List<EffectInstance> effects = new LinkedList<>();
        effects.add(new EffectInstance(Effects.POISON, duration));
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(worldIn, pos.getX() + random.nextDouble(), pos.getY(), pos.getZ() + random.nextDouble());
        areaeffectcloudentity.setOwner(null);
        areaeffectcloudentity.setDuration(600 + random.nextInt(600));
        areaeffectcloudentity.setRadius(1.5F + (random.nextFloat() * 0.5F));
        areaeffectcloudentity.setRadiusOnUse(-(0.4F + (random.nextFloat() * 0.2F)));
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
        areaeffectcloudentity.setColor(PotionUtils.getPotionColorFromEffectList(effects));
        for (EffectInstance effectinstance : effects) {
            areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
        }
        worldIn.addEntity(areaeffectcloudentity);
        //set state to triggered
        worldIn.setBlockState(pos, state.with(TRIGGERED, true), 2);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (worldIn instanceof ServerWorld && !state.get(TRIGGERED) && isTriggeringEntity(entityIn)) {
            generateEffectCloud((ServerWorld)worldIn, state, pos);
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if (worldIn instanceof ServerWorld && !state.get(TRIGGERED)) {
            generateEffectCloud((ServerWorld)worldIn, state, pos);
        }
    }
}
