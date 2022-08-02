package cech12.extendedmushrooms.mixin;

import cech12.extendedmushrooms.MushroomUtils;
import cech12.extendedmushrooms.block.mushrooms.BrownMushroom;
import cech12.extendedmushrooms.block.mushrooms.RedMushroom;
import cech12.extendedmushrooms.init.ModBlocks;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MushroomBlock.class)
public class MixinMushroomBlock {

    /**
     * Add a tree like automatic growing.
     * The automatic multiplication still remaining.
     */
    @Inject(at = @At("HEAD"), method = "randomTick", cancellable = true)
    public void tickProxy(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
        //skip growing & multiplication if part of Fairy Ring
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            mutablePos.set(pos).move(direction);
            if (world.getBlockState(mutablePos).getBlock() == ModBlocks.FAIRY_RING.get()) {
                ci.cancel();
                return;
            }
        }
        //automatic growing of mushrooms
        //Forge: prevent loading unloaded chunks
        if (world.isAreaLoaded(pos, 7) && random.nextInt(25) == 0) {
            if (state.getBlock() instanceof MushroomBlock) {
                ((MushroomBlock) state.getBlock()).performBonemeal(world, random, pos, state);
            }
        }
        //automatic multiplication follows in tick method when ci.canceled NOT called
        //ci.cancel();
    }

    /**
     * Change grow behaviour to enable mega mushrooms can be grown out of vanilla mushrooms.
     */
    @Inject(at = @At("HEAD"), method = "growMushroom", cancellable = true)
    public void growProxy(ServerLevel world, BlockPos pos, BlockState state, RandomSource random, CallbackInfoReturnable<Boolean> cir) {
        if (!MushroomUtils.isValidMushroomPosition(world, pos)) {
            cir.setReturnValue(false);
        } else if (state.getBlock() == Blocks.BROWN_MUSHROOM) {
            (new BrownMushroom()).growMushroom(world, world.getChunkSource().getGenerator(), pos, state, random);
            cir.setReturnValue(true);
        } else if (state.getBlock() == Blocks.RED_MUSHROOM) {
            (new RedMushroom()).growMushroom(world, world.getChunkSource().getGenerator(), pos, state, random);
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
        cir.cancel();
    }

}
