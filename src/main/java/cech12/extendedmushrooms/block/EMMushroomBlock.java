package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.block.mushrooms.BigMushroom;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;
import java.util.Random;

public class EMMushroomBlock extends MushroomBlock {

    BigMushroom bigMushroom;

    public EMMushroomBlock(BigMushroom bigMushroom, Properties properties) {
        super(properties, bigMushroom::getBigMushroomFeature);
        this.bigMushroom = bigMushroom;
    }

    @Override
    public void performBonemeal(@Nonnull ServerLevel world, @Nonnull Random random, @Nonnull BlockPos blockPos, @Nonnull BlockState state) {
        this.bigMushroom.growMushroom(world, world.getChunkSource().getGenerator(), blockPos, state, random);
    }
}
