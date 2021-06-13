package cech12.extendedmushrooms.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class MushroomTrapdoorBlock extends TrapDoorBlock {

    public MushroomTrapdoorBlock(Block.Properties properties) {
        super(properties);
    }

    //TODO workaround until https://github.com/MinecraftForge/MinecraftForge/issues/7775 is fixed!
    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        if (state.get(OPEN)) {
            BlockState down = world.getBlockState(pos.down());
            if (down.getBlock() instanceof LadderBlock)
                return down.get(LadderBlock.FACING) == state.get(HORIZONTAL_FACING);
        }
        return super.isLadder(state, world, pos, entity);
    }
}
