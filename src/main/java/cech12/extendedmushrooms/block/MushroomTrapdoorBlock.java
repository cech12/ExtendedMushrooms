package cech12.extendedmushrooms.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

public class MushroomTrapdoorBlock extends TrapDoorBlock {

    public MushroomTrapdoorBlock(Block.Properties properties) {
        super(properties);
    }

    //TODO workaround until https://github.com/MinecraftForge/MinecraftForge/issues/7775 is fixed!
    @Override
    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
        if (state.getValue(OPEN)) {
            BlockState down = world.getBlockState(pos.below());
            if (down.getBlock() instanceof LadderBlock)
                return down.getValue(LadderBlock.FACING) == state.getValue(FACING);
        }
        return super.isLadder(state, world, pos, entity);
    }
}
