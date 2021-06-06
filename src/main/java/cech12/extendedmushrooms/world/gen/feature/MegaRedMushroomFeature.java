package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MegaRedMushroomFeature extends MegaMushroomFeature {

    public MegaRedMushroomFeature(Codec<BigMushroomFeatureConfig> config) {
        super(config);
    }

    @Override
    protected int getCapRadius(Random random) {
        return 4 + random.nextInt(2);
    }

    /**
     * @return value between 0.0 and 1.0 - cap size related to size of mushroom
     */
    protected float getCapHeightFactor() {
        return 0.5F;
    }

    @Override
    protected boolean canPlaceCap(IWorld world, BlockPos blockPos, int size, int radius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        int capCenterHeight = (int) (size * (1.0F - getCapHeightFactor()));
        int capSize = (int) (size * getCapHeightFactor());
        Cap cap = new Cap(mutableBlockPos.setPos(blockPos).move(0, capCenterHeight, 0), capSize, radius);
        for (CapPosition capPos: cap.capPositions) {
            if (!world.getBlockState(capPos.blockPos).canBeReplacedByLeaves(world, capPos.blockPos)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void placeCap(IWorld world, Random random, BlockPos blockPos, int size, int radius, BlockPos.Mutable mutableBlockPos, BigMushroomFeatureConfig config) {
        int capCenterHeight = (int) (size * (1.0F - getCapHeightFactor()));
        int capSize = (int) (size * getCapHeightFactor());
        Cap cap = new Cap(mutableBlockPos.setPos(blockPos).move(0, capCenterHeight, 0), capSize, radius);
        for (CapPosition capPos: cap.capPositions) {
            this.placeCapBlockIfPossible(world, random, config, capPos.blockPos, capPos.west, capPos.east, capPos.north, capPos.south, !cap.hasCapPosition(capPos.blockPos.up()));
        }
    }


    private static class Cap {

        private final BlockPos center;
        private final int size;
        private final float radius;

        private final float a_q;
        private final float b_q;
        private final float c_q;

        private final List<CapPosition> capPositions = new ArrayList<>();

        Cap (BlockPos center, int size, int radius) {
            this.center = center;
            this.size = size;
            this.radius = radius + 0.5F;
            this.a_q = radius * radius;
            this.b_q = (size * 2) * (size * 2) / 4.0F;
            this.c_q = this.a_q;

            this.generateBlockPositions();
        }

        private boolean isInsideEllipsoid(BlockPos pos) {
            float x = pos.getX() - 0.5F;
            float y = pos.getY();
            float z = pos.getZ() - 0.5F;
            //ellipsoid formula
            return x*x/this.a_q + y*y/this.b_q + z*z/this.c_q < 1.0F;
        }

        private void generateBlockPositions() {
            for (int y = 0; y <= (this.size) + 2; y++) {
                for (int x = (int) (-this.radius - 1.5); x <= this.radius + 2; x++) {
                    for (int z = (int) (-this.radius - 1.5); z <= this.radius + 2; z++) {
                        BlockPos ellPos = new BlockPos(x, y, z);
                        if (!this.isInsideEllipsoid(ellPos)) {
                            boolean down = this.isInsideEllipsoid(ellPos.down());
                            boolean west = this.isInsideEllipsoid(ellPos.west());
                            boolean east = this.isInsideEllipsoid(ellPos.east());
                            boolean north = this.isInsideEllipsoid(ellPos.north());
                            boolean south = this.isInsideEllipsoid(ellPos.south());
                            if (down || west || east || north || south) {
                                this.capPositions.add(new CapPosition(this.center.add(ellPos), !west, !east, !north, !south));
                            }
                        }
                    }
                }
            }
        }

        private boolean hasCapPosition(BlockPos pos) {
            for (CapPosition capPosition : this.capPositions) {
                if (capPosition.blockPos.equals(pos)) {
                    return true;
                }
            }
            return false;
        }

    }

    private static class CapPosition {

        BlockPos blockPos;
        boolean west;
        boolean east;
        boolean north;
        boolean south;

        private CapPosition(BlockPos pos, boolean west, boolean east, boolean north, boolean south) {
            this.blockPos = pos;
            this.west = west;
            this.east = east;
            this.north = north;
            this.south = south;
        }

    }

}
