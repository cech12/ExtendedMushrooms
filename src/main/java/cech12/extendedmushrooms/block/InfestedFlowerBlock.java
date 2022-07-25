package cech12.extendedmushrooms.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;

public class InfestedFlowerBlock extends FlowerBlock {

    public InfestedFlowerBlock(MobEffect effect, int effectDuration, Properties properties) {
        super(effect, effectDuration, properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.MYCELIUM || super.mayPlaceOn(state, level, pos);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState blockState, @Nonnull Level level, @Nonnull BlockPos blockPos, @Nonnull Random random) {
        super.animateTick(blockState, level, blockPos, random);
        if (random.nextInt(15) == 0) {
            Vec3 offset = blockState.getOffset(level, blockPos).add(0.3125, 0, 0.3125);
            level.addParticle(ParticleTypes.MYCELIUM,
                    blockPos.getX() + offset.x + (random.nextDouble() * 0.375),
                    blockPos.getY() + offset.y + (random.nextDouble() * 0.2),
                    blockPos.getZ() + offset.z + (random.nextDouble() * 0.375), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        return 60;
    }

}
