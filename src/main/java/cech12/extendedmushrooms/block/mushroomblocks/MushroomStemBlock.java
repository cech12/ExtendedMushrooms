package cech12.extendedmushrooms.block.mushroomblocks;

import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nullable;

/**
 * To make a difference for data generation.
 * Only mushroom stem blocks should be an object of this class. Stripped variants not!
 */
public class MushroomStemBlock extends HugeMushroomBlock {

    private final MushroomWoodType type;

    public MushroomStemBlock(MushroomWoodType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public MushroomWoodType getType() {
        return this.type;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
