package cech12.extendedmushrooms.mixin;

import cech12.extendedmushrooms.MushroomUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHugeMushroomFeature.class)
public abstract class MixinAbstractBigMushroomFeature {

    /**
     * Remove dirt check and add tag check
     * The automatic multiplication still remaining.
     */
    @Inject(at = @At("HEAD"), method = "isValidPosition", cancellable = true)
    public void isValidPositionProxy(LevelAccessor level, BlockPos pos, int p_227209_3_, BlockPos.MutableBlockPos p_227209_4_, HugeMushroomFeatureConfiguration p_227209_5_, CallbackInfoReturnable<Boolean> cir) {
        int i = pos.getY();
        if (i >= level.getMinBuildHeight() + 1 && i + p_227209_3_ + 1 < level.getMaxBuildHeight()) {
            BlockState belowBlockState = level.getBlockState(pos.below());
            //dirt check for vanilla world generation (huge mushrooms in dark forests #49)
            if (belowBlockState.is(BlockTags.DIRT) || MushroomUtils.isValidMushroomPosition(level, pos)) {
                IMixinAbstractBigMushroomFeature self = (IMixinAbstractBigMushroomFeature) this;
                for(int j = 0; j <= p_227209_3_; ++j) {
                    int k = self.invoke_getTreeRadiusForHeight(-1, -1, p_227209_5_.foliageRadius, j);

                    for(int l = -k; l <= k; ++l) {
                        for(int i1 = -k; i1 <= k; ++i1) {
                            BlockState blockstate = level.getBlockState(p_227209_4_.setWithOffset(pos, l, j, i1));
                            if (!blockstate.isAir() && !blockstate.is(BlockTags.LEAVES)) {
                                cir.setReturnValue(false);
                                cir.cancel();
                                return;
                            }
                        }
                    }
                }
                cir.setReturnValue(true);
                cir.cancel();
                return;
            }
        }
        cir.setReturnValue(false);
        cir.cancel();
    }

}
