package cech12.extendedmushrooms.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.Random;

public class WeightedFeature<FC extends IFeatureConfig> {

    public final ConfiguredFeature<FC, ?> configuredFeature;
    public final float weight;

    public WeightedFeature(ConfiguredFeature<FC, ?> configuredFeature, float weight) {
        this.configuredFeature = configuredFeature;
        this.weight = weight;
    }

    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, BlockPos blockPos) {
        return this.configuredFeature.place(world, chunkGenerator, random, blockPos);
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("name"), ops.createString(this.configuredFeature.feature.getRegistryName().toString()), ops.createString("config"), this.configuredFeature.config.serialize(ops).getValue(), ops.createString("weight"), ops.createFloat(this.weight))));
    }

    public static <T> WeightedFeature<?> deserialize(Dynamic<T> ops) {
        return new WeightedFeature<>(ConfiguredFeature.deserialize(ops), ops.get("weight").asFloat(0));
    }

}
