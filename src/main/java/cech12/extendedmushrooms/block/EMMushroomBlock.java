package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.block.mushrooms.BigMushroom;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class EMMushroomBlock extends MushroomBlock {

    BigMushroom bigMushroom;

    public EMMushroomBlock(BigMushroom bigMushroom, Properties properties) {
        super(properties);
        this.bigMushroom = bigMushroom;
    }

    @Override
    public void performBonemeal(ServerWorld world, Random random, BlockPos blockPos, BlockState state) {
        this.bigMushroom.growMushroom(world, world.getChunkSource().generator, blockPos, state, random);
    }
}
