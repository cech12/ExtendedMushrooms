package cech12.extendedmushrooms.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class InfestedFlowerBlock extends FlowerBlock {

    public InfestedFlowerBlock(Effect effect, int effectDuration, Properties properties) {
        super(effect, effectDuration, properties);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.MYCELIUM || super.isValidGround(state, world, pos);
    }

    @Override
    public boolean canBeReplacedByLeaves(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canBeReplacedByLogs(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random) {
        super.animateTick(blockState, world, blockPos, random);
        if (random.nextInt(15) == 0) {
            Vec3d offset = blockState.getOffset(world, blockPos).add(0.3125, 0, 0.3125);
            world.addParticle(ParticleTypes.MYCELIUM,
                    blockPos.getX() + offset.x + (random.nextDouble() * 0.375),
                    blockPos.getY() + offset.y + (random.nextDouble() * 0.2),
                    blockPos.getZ() + offset.z + (random.nextDouble() * 0.375), 0.0D, 0.0D, 0.0D);
        }
    }
}
