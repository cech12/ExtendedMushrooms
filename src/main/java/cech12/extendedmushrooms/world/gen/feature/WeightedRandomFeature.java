package cech12.extendedmushrooms.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;

public class WeightedRandomFeature implements IFeatureConfig {

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

    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("features"), ops.createList(this.features.stream().map((feature) -> {
            return feature.serialize(ops).getValue();
        })))));
    }

    public static <T> WeightedRandomFeature deserialize(Dynamic<T> ops) {
        List<WeightedFeature<?>> list = ops.get("features").asList(WeightedFeature::deserialize);
        return new WeightedRandomFeature(list);
    }
}
