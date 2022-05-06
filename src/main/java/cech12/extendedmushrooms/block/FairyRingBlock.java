package cech12.extendedmushrooms.block;


import cech12.extendedmushrooms.api.block.ExtendedMushroomsBlocks;
import cech12.extendedmushrooms.tileentity.FairyRingTileEntity;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class FairyRingBlock extends AirBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public FairyRingBlock() {
        super(Block.Properties.create(Material.AIR).doesNotBlockMovement().noDrops());
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nonnull
    @Deprecated
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Nonnull
    @Deprecated
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Nonnull
    @Deprecated
    @Override
    public BlockRenderType getRenderType(@Nullable BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FairyRingTileEntity();
    }

    @Deprecated
    @Override
    public void onEntityCollision(@Nullable BlockState blockState, World world, @Nonnull BlockPos blockPos, @Nonnull Entity entity) {
        TileEntity tileentity = world.getTileEntity(blockPos);
        if (tileentity instanceof FairyRingTileEntity) {
            ((FairyRingTileEntity) tileentity).onEntityCollision(entity);
        }
    }

    @Deprecated
    @Override
    public void onReplaced(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof FairyRingTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (FairyRingTileEntity)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    /**
     * Called periodically client side on blocks near the player to show effects.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState stateIn, World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof FairyRingTileEntity) {
            ((FairyRingTileEntity) tileentity).animateTick(worldIn, rand);
        }
    }

    @Deprecated
    @Override
    public boolean eventReceived(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity tileentity = world.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Deprecated
    @Override
    public void neighborChanged(@Nonnull BlockState blockState, @Nonnull World world, @Nonnull BlockPos blockPos, @Nonnull Block block, @Nonnull BlockPos neighbourPos, boolean isMoving) {
        //check for 2 mushrooms and 2 Fairy Ring blocks as neighbour. (diagonally the same)
        //check if block below is solid
        boolean mushroomSeen = false;
        int mushrooms = 0;
        boolean fairyRingBlockSeen = false;
        int fairyRingBlocks = 0;
        boolean neighboursFound = false;
        for (Direction direction : DIRECTIONS) {
            Block neighbourBlock = world.getBlockState(blockPos.offset(direction)).getBlock();
            if (neighbourBlock instanceof MushroomBlock) {
                mushrooms++;
                if (mushroomSeen) {
                    neighboursFound = true;
                } else {
                    mushroomSeen = true;
                    fairyRingBlockSeen = false;
                }
            } else if (neighbourBlock == ExtendedMushroomsBlocks.FAIRY_RING) {
                fairyRingBlocks++;
                if (fairyRingBlockSeen) {
                    neighboursFound = true;
                } else {
                    mushroomSeen = false;
                    fairyRingBlockSeen = true;
                }
            } else {
                break;
            }
        }

        if (!neighboursFound || mushrooms != 2 || fairyRingBlocks != 2 || !world.getBlockState(blockPos.down()).isSolid()) {
            //remove me
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
        }

        super.neighborChanged(blockState, world, blockPos, block, neighbourPos, isMoving);
    }


    /**
     * Check for built fairy ring and if true, place fairy ring blocks.
     */
    public static void fairyRingPlaceCheck(IWorld world, BlockPos pos) {
        //check for formed fairy rings
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        boolean[] clockwises = new boolean[]{true, false};
        for (Direction direction : DIRECTIONS) {
            for (boolean clockwise : clockwises) {
                try {
                    FairyRing fairyRing = new FairyRing(world, direction, clockwise, mutablePos.setPos(pos));
                    fairyRing.placeBlocks(world);
                } catch (FairyRing.CannotFormFairyRingException ignore) {}
            }
        }
    }


    private static class FairyRing {

        static class CannotFormFairyRingException extends Exception {}

        /** a list of 12 block positions (first 8 border blocks [B], following 4 center blocks [C]) */
        LinkedList<BlockPos> ringPositions;

        /**
         * Construct a Fairy Ring with a list of 12 block positions.
         *
         * # B B #
         * B C C B
         * B C C B
         * # B B #
         */
        FairyRing(IWorld world, Direction direction, boolean clockwise, BlockPos.Mutable mutablePos) throws CannotFormFairyRingException {
            this.ringPositions = getFairyRingPositions(world, new LinkedList<>(), direction, clockwise, mutablePos);
            if (this.ringPositions == null || ringPositions.size() != 12) {
                throw new CannotFormFairyRingException();
            }
        }

        /**
         * Recursive method to get all important positions of a fairy ring.
         * @param positions - empty linked list which is filled with all positions
         * @param direction - initial direction where it should look for the next block
         * @param clockwise - boolean to say if the ring should be checked clockwise or counter clockwise
         * @param mutablePos - a mutable block position, where the position of the initial block is set
         * @return list of all 12 important positions of the ring (parameter positions) - returns null, when ring cannot be placed
         */
        private static LinkedList<BlockPos> getFairyRingPositions(IWorld world, LinkedList<BlockPos> positions, Direction direction, boolean clockwise, BlockPos.Mutable mutablePos) {
            Direction rotatedDirection = (clockwise) ? direction.rotateY() : direction.rotateYCCW();
            Direction newDirection = direction;

            //check if ring has mushrooms
            if (positions.size() < 8 && !(world.getBlockState(mutablePos).getBlock() instanceof MushroomBlock)) {
                return null;
            } else
            //check if center is filled with air blocks and below must be a solid block
            if (positions.size() >= 8 && (world.getBlockState(mutablePos).getBlock() != Blocks.AIR ||
                        !world.getBlockState(mutablePos.down()).isSolid())) {
                return null;
            }

            //put position in list
            positions.add(new BlockPos(mutablePos));

            if (positions.size() != 8) {
                //don't move forward when at last border block
                mutablePos.move(direction);
            }

            if (positions.size() >= 8 || positions.size() % 2 == 0) {
                //rotate direction for center blocks of for even border blocks
                newDirection = rotatedDirection;
            }

            if (positions.size() == 8 || (positions.size() < 8 && positions.size() % 2 == 0)) {
                // at last border block only move in rotated direction
                // for even border blocks additional move in rotated direction (to go diagonally)
                mutablePos.move(newDirection);
            }

            if (positions.size() < 12) {
                return getFairyRingPositions(world, positions, newDirection, clockwise, mutablePos);
            } else {
                return positions;
            }
        }

        private List<BlockPos> getSortedCenterPositions() {
            List<BlockPos> list = this.ringPositions.subList(8, 12);
            list.sort((o1, o2) -> {
                int result = o1.getX() - o2.getX();
                if (result == 0) {
                    return o1.getZ() - o2.getZ();
                }
                return result;
            });
            return list;
        }

        void placeBlocks(IWorld world) {
            List<BlockPos> list = getSortedCenterPositions();
            for (int i = 0; i < 4; i++) {
                BlockState state = ExtendedMushroomsBlocks.FAIRY_RING.getDefaultState().with(FairyRingBlock.FACING, DIRECTIONS[i]);
                //2 - no block updates to avoid to calling neighborChanged while placing
                world.setBlockState(list.get(i), state, 2);
            }
        }

    }
}
