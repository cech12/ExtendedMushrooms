package cech12.extendedmushrooms.mixin;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MushroomBlock.class)
public class MixinMushroomBlock {

    /**
     * Add a tree like automatic growing.
     * The automatic multiplication still remaining.
     */
    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void tickProxy(BlockState blockState, ServerWorld world, BlockPos blockPos, Random random, CallbackInfo ci) {
        ExtendedMushrooms.LOGGER.fatal("MushroomBlockTick");
        //automatic growing of mushrooms
        //Forge: prevent loading unloaded chunks
        if (world.isAreaLoaded(blockPos, 7) && random.nextInt(25) == 0) {
            if (blockState.getBlock() instanceof MushroomBlock) {
                ((MushroomBlock) blockState.getBlock()).grow(world, random, blockPos, blockState);
            }
        }
        //automatic multiplication follows in tick method when ci.canceled NOT called
        //ci.cancel();
    }


}
