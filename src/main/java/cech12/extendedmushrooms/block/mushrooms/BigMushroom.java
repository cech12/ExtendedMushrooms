package cech12.extendedmushrooms.block.mushrooms;

import cech12.extendedmushrooms.MushroomUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.level.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;
import java.util.Optional;

public abstract class BigMushroom {

    public BigMushroom() {
    }

    @Nonnull
    public abstract ResourceKey<ConfiguredFeature<?, ?>> getBigMushroomFeature();

    protected static BlockState getDefaultStemState(Block stemBlock) {
        return stemBlock.defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false);
    }

    protected static BlockState getDefaultCapState(Block capBlock) {
        return capBlock.defaultBlockState().setValue(HugeMushroomBlock.DOWN, false);
    }

    public boolean growMushroom(ServerLevel world, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockState blockState, RandomSource random) {
        if (!MushroomUtils.isValidMushroomPosition(world, blockPos)) {
            return false;
        }
        Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(this.getBigMushroomFeature());
        if (optional.isEmpty()) {
            return false;
        }
        SaplingGrowTreeEvent event = ForgeEventFactory.blockGrowFeature(world, random, blockPos, optional.get());
        if (event.getResult().equals(Event.Result.DENY) || event.getFeature() == null) return false;
        ConfiguredFeature<?, ?> feature = event.getFeature().value();
        world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 4);
        if (feature.place(world, chunkGenerator, random, blockPos)) {
            return true;
        } else {
            world.setBlock(blockPos, blockState, 4);
            return false;
        }
    }

}
