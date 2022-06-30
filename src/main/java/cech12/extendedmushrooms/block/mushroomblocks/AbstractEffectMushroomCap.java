package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public abstract class AbstractEffectMushroomCap extends MushroomCapBlock {

    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

    public AbstractEffectMushroomCap(MushroomType type, Properties properties) {
        super(type, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(PERSISTENT, false));
    }

    /**
     * @return Returns a list of effects that the effect cloud has.
     */
    @Nonnull
    abstract protected List<MobEffectInstance> getEffects(@Nonnull Random random);

    protected boolean shouldDropEffectCloud(BlockState state, @Nonnull ServerLevel world, @Nonnull BlockPos pos, @Nonnull Random random) {
        return random.nextDouble() < 0.05;
    }

    protected int getEffectCloudColor(List<MobEffectInstance> effects, @Nonnull Random random) {
        return PotionUtils.getColor(effects);
    }

    protected float getEffectCloudRadius(@Nonnull Random random) {
        return 1.5F + (random.nextFloat() * 0.5F);
    }

    protected float getEffectCloudRadiusOnUse(@Nonnull Random random) {
        return 0.4F + (random.nextFloat() * 0.2F);
    }

    protected int getEffectCloudDuration(@Nonnull Random random) {
        return 600 + random.nextInt(600);
    }

    @Override
    public void randomTick(BlockState state, @Nonnull ServerLevel worldIn, @Nonnull BlockPos pos, @Nonnull Random random) {
        if (!state.getValue(PERSISTENT) && this.shouldDropEffectCloud(state, worldIn, pos, random)) {
            //find ground block below
            BlockPos down = pos.below();
            BlockPos.MutableBlockPos effectPos = new BlockPos.MutableBlockPos(down.getX(), down.getY(), down.getZ());
            //block below must not be a solid block
            if (!worldIn.getBlockState(effectPos).canOcclude()) {
                //go down until reached a solid block or world bounds
                do {
                    effectPos.move(Direction.DOWN);
                } while (!worldIn.getBlockState(effectPos).canOcclude() && effectPos.getY() >= 0);
                //to spawn effect, go one block up
                if (effectPos.getY() >= 0) {
                    effectPos.move(Direction.UP);
                    this.spawnEffectCloud(worldIn, effectPos, random);
                }
            }
        }
    }

    protected void spawnEffectCloud(@Nonnull ServerLevel worldIn, @Nonnull BlockPos pos, @Nonnull Random random) {
        List<MobEffectInstance> effects = this.getEffects(random);
        if (!effects.isEmpty()) {
            AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(worldIn, pos.getX() + random.nextDouble(), pos.getY(), pos.getZ() + random.nextDouble());
            areaeffectcloudentity.setOwner(null);
            areaeffectcloudentity.setDuration(this.getEffectCloudDuration(random));
            areaeffectcloudentity.setRadius(this.getEffectCloudRadius(random));
            areaeffectcloudentity.setRadiusOnUse(-this.getEffectCloudRadiusOnUse(random));
            areaeffectcloudentity.setWaitTime(10);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
            areaeffectcloudentity.setFixedColor(this.getEffectCloudColor(effects, random));
            for (MobEffectInstance effectinstance : effects) {
                areaeffectcloudentity.addEffect(new MobEffectInstance(effectinstance));
            }
            worldIn.addFreshEntity(areaeffectcloudentity);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PERSISTENT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(PERSISTENT, true);
    }

}
