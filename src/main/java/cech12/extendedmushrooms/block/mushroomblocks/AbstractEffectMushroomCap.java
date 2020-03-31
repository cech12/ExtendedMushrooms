package cech12.extendedmushrooms.block.mushroomblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public abstract class AbstractEffectMushroomCap extends HugeMushroomBlock {

    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

    public AbstractEffectMushroomCap(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(PERSISTENT, false));
    }

    /**
     * @return Returns a list of effects that the effect cloud has.
     */
    @Nonnull
    abstract protected List<EffectInstance> getEffects(@Nonnull Random random);

    protected boolean shouldDropEffectCloud(BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random random) {
        return random.nextDouble() < 0.05;
    }

    protected int getEffectCloudColor(List<EffectInstance> effects, @Nonnull Random random) {
        return PotionUtils.getPotionColorFromEffectList(effects);
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
    public void randomTick(BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random random) {
        if (!state.get(PERSISTENT) && this.shouldDropEffectCloud(state, worldIn, pos, random)) {
            //find ground block below
            BlockPos.Mutable effectPos = new BlockPos.Mutable(pos.down());
            //block below must not be a solid block
            if (!worldIn.getBlockState(effectPos).isSolid()) {
                //go down until reached a solid block or world bounds
                do {
                    effectPos.move(Direction.DOWN);
                } while (!worldIn.getBlockState(effectPos).isSolid() && effectPos.getY() >= 0);
                //to spawn effect, go one block up
                if (effectPos.getY() >= 0) {
                    effectPos.move(Direction.UP);
                    this.spawnEffectCloud(worldIn, effectPos, random);
                }
            }
        }
    }

    protected void spawnEffectCloud(@Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random random) {
        List<EffectInstance> effects = this.getEffects(random);
        if (!effects.isEmpty()) {
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(worldIn, pos.getX() + random.nextDouble(), pos.getY(), pos.getZ() + random.nextDouble());
            areaeffectcloudentity.setOwner(null);
            areaeffectcloudentity.setDuration(this.getEffectCloudDuration(random));
            areaeffectcloudentity.setRadius(this.getEffectCloudRadius(random));
            areaeffectcloudentity.setRadiusOnUse(-this.getEffectCloudRadiusOnUse(random));
            areaeffectcloudentity.setWaitTime(10);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
            areaeffectcloudentity.setColor(this.getEffectCloudColor(effects, random));
            for (EffectInstance effectinstance : effects) {
                areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
            }
            worldIn.addEntity(areaeffectcloudentity);
        }
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return !state.get(PERSISTENT);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(PERSISTENT);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).with(PERSISTENT, true);
    }

}
