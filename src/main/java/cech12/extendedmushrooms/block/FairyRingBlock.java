package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.init.ModBlockEntityTypes;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.blockentity.FairyRingBlockEntity;
import cech12.extendedmushrooms.init.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class FairyRingBlock extends AirBlock implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public FairyRingBlock() {
        super(Block.Properties.of(Material.AIR).noCollission().noLootTable());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    public static boolean isFairyRingMushroom(Item item) {
        ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        return tagManager != null && tagManager.getTag(ModTags.Items.FAIRY_RING_MUSHROOMS).contains(item);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new FairyRingBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> entityType) {
        return createTickerHelper(entityType, ModBlockEntityTypes.FAIRY_RING.get(), FairyRingBlockEntity::tick);
    }

    @Nullable
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(@Nonnull ServerLevel level, @Nonnull T blockEntity) {
        return null;
    }

    @Nonnull
    @Deprecated
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Nonnull
    @Deprecated
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Nonnull
    @Deprecated
    @Override
    public RenderShape getRenderShape(@Nullable BlockState state) {
        return RenderShape.MODEL;
    }

    @Deprecated
    @Override
    public void entityInside(@Nullable BlockState blockState, Level world, @Nonnull BlockPos blockPos, @Nonnull Entity entity) {
        BlockEntity blockEntity = world.getBlockEntity(blockPos);
        if (blockEntity instanceof FairyRingBlockEntity) {
            ((FairyRingBlockEntity) blockEntity).onEntityCollision(entity);
        }
    }

    @Deprecated
    @Override
    public void onRemove(BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = worldIn.getBlockEntity(pos);
            if (blockEntity instanceof FairyRingBlockEntity) {
                Containers.dropContents(worldIn, pos, (FairyRingBlockEntity)blockEntity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    /**
     * Called periodically client side on blocks near the player to show effects.
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull BlockState stateIn, Level worldIn, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        BlockEntity blockEntity = worldIn.getBlockEntity(pos);
        if (blockEntity instanceof FairyRingBlockEntity) {
            ((FairyRingBlockEntity) blockEntity).animateTick(worldIn, rand);
        }
    }

    @Deprecated
    @Override
    public boolean triggerEvent(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.triggerEvent(id, param);
    }

    @Deprecated
    @Override
    public void neighborChanged(@Nonnull BlockState blockState, @Nonnull Level world, @Nonnull BlockPos blockPos, @Nonnull Block block, @Nonnull BlockPos neighbourPos, boolean isMoving) {
        //check for 2 mushrooms and 2 Fairy Ring blocks as neighbour. (diagonally the same)
        //check if block below is solid
        boolean mushroomSeen = false;
        int mushrooms = 0;
        boolean fairyRingBlockSeen = false;
        int fairyRingBlocks = 0;
        boolean neighboursFound = false;
        for (Direction direction : DIRECTIONS) {
            Block neighbourBlock = world.getBlockState(blockPos.relative(direction)).getBlock();
            if (isFairyRingMushroom(neighbourBlock.asItem())) {
                mushrooms++;
                if (mushroomSeen) {
                    neighboursFound = true;
                } else {
                    mushroomSeen = true;
                    fairyRingBlockSeen = false;
                }
            } else if (neighbourBlock == ModBlocks.FAIRY_RING.get()) {
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

        if (!neighboursFound || mushrooms != 2 || fairyRingBlocks != 2 || !world.getBlockState(blockPos.below()).canOcclude()) {
            //remove me
            world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
        }

        super.neighborChanged(blockState, world, blockPos, block, neighbourPos, isMoving);
    }


    /**
     * Check for built fairy ring and if true, place fairy ring blocks.
     */
    public static void fairyRingPlaceCheck(LevelAccessor world, BlockPos pos) {
        //check for formed fairy rings
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        boolean[] clockwises = new boolean[]{true, false};
        for (Direction direction : DIRECTIONS) {
            for (boolean clockwise : clockwises) {
                try {
                    FairyRing fairyRing = new FairyRing(world, direction, clockwise, mutablePos.set(pos));
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
        FairyRing(LevelAccessor world, Direction direction, boolean clockwise, BlockPos.MutableBlockPos mutablePos) throws CannotFormFairyRingException {
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
        private static LinkedList<BlockPos> getFairyRingPositions(LevelAccessor world, LinkedList<BlockPos> positions, Direction direction, boolean clockwise, BlockPos.MutableBlockPos mutablePos) {
            Direction rotatedDirection = (clockwise) ? direction.getClockWise() : direction.getCounterClockWise();
            Direction newDirection = direction;

            //check if ring has mushrooms
            if (positions.size() < 8 && !isFairyRingMushroom(world.getBlockState(mutablePos).getBlock().asItem())) {
                return null;
            } else
            //check if center is filled with air blocks and below must be a solid block
            if (positions.size() >= 8 && (world.getBlockState(mutablePos).getBlock() != Blocks.AIR ||
                        !world.getBlockState(mutablePos.below()).canOcclude())) {
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

        void placeBlocks(LevelAccessor world) {
            List<BlockPos> list = getSortedCenterPositions();
            for (int i = 0; i < 4; i++) {
                BlockState state = ModBlocks.FAIRY_RING.get().defaultBlockState().setValue(FairyRingBlock.FACING, DIRECTIONS[i]);
                //2 - no block updates to avoid to calling neighborChanged while placing
                world.setBlock(list.get(i), state, 2);
            }
        }

    }
}
