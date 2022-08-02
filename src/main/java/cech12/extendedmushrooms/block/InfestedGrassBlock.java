package cech12.extendedmushrooms.block;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class InfestedGrassBlock extends BushBlock {

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);

    /**
     * BushBlock has protected constructor.
     */
    public InfestedGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Vec3 vec3d = state.getOffset(worldIn, pos);
        return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.MYCELIUM || super.mayPlaceOn(state, worldIn, pos);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, Level world, BlockPos blockPos, RandomSource random) {
        super.animateTick(blockState, world, blockPos, random);
        if (random.nextInt(15) == 0) {
            world.addParticle(ParticleTypes.MYCELIUM, (double)blockPos.getX() + (double)random.nextFloat(), (double)blockPos.getY() + 0.2D, (double)blockPos.getZ() + (double)random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }

}
