package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;

public class WeightedRandomFeature implements IFeatureConfig {

    //TODO
    /*
    public static final Codec<WeightedRandomFeature> INSTANCE = ConfiguredFeature.field_236264_b_.listOf().fieldOf("features").xmap(WeightedRandomFeature::new, (p_236643_0_) -> {
        return p_236643_0_.features;
    }).codec();
     */

    public final List<WeightedFeature<?>> features;

    public WeightedRandomFeature(List<WeightedFeature<?>> features) {
        this.features = features;
    }

    public float getWeightSum() {
        float sum = 0.0F;
        for (WeightedFeature<?> feature : this.features) {
            sum += feature.weight;
        }
        return sum;
    }
}
