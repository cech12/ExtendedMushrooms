package cech12.extendedmushrooms.mixin;

import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractHugeMushroomFeature.class)
public interface IMixinAbstractBigMushroomFeature {

    @Invoker(value = "getTreeRadiusForHeight")
    int invoke_getTreeRadiusForHeight(int p_225563_1_, int p_225563_2_, int p_225563_3_, int p_225563_4_);

}
