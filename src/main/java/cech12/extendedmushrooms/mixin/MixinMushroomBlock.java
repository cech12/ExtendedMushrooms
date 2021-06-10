package cech12.extendedmushrooms.mixin;

import cech12.extendedmushrooms.MushroomUtils;
import cech12.extendedmushrooms.block.mushrooms.BrownMushroom;
import cech12.extendedmushrooms.block.mushrooms.RedMushroom;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MushroomBlock.class)
public class MixinMushroomBlock {

    /**
     * Add a tree like automatic growing.
     * The automatic multiplication still remaining.
     */
    @Inject(at = @At("HEAD"), method = "randomTick", cancellable = true)
    public void tickProxy(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        //automatic growing of mushrooms
        //Forge: prevent loading unloaded chunks
        if (world.isAreaLoaded(pos, 7) && random.nextInt(25) == 0) {
            if (state.getBlock() instanceof MushroomBlock) {
                ((MushroomBlock) state.getBlock()).grow(world, random, pos, state);
            }
        }
        //automatic multiplication follows in tick method when ci.canceled NOT called
        //ci.cancel();
    }

    /**
     * Change grow behaviour to enable mega mushrooms can be grown out of vanilla mushrooms.
     */
    @Inject(at = @At("HEAD"), method = "grow", cancellable = true)
    public void growProxy(ServerWorld world, BlockPos pos, BlockState state, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (!MushroomUtils.isValidMushroomPosition(world, pos)) {
            cir.setReturnValue(false);
        } else if (state.getBlock() == Blocks.BROWN_MUSHROOM) {
            (new BrownMushroom()).growMushroom(world, world.getChunkProvider().getChunkGenerator(), pos, state, random);
            cir.setReturnValue(true);
        } else if (state.getBlock() == Blocks.RED_MUSHROOM) {
            (new RedMushroom()).growMushroom(world, world.getChunkProvider().getChunkGenerator(), pos, state, random);
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
        cir.cancel();
    }

}
