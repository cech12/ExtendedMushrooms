package cech12.extendedmushrooms.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredRandomFeatureList;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;

import java.util.Random;

public class WeightedFeature<FC extends IFeatureConfig> {

    //TODO
    /*
    public static final Codec<MultipleRandomFeatureConfig> INSTANCE = RecordCodecBuilder.create((p_236585_0_) -> {
        return p_236585_0_.apply2(MultipleRandomFeatureConfig::new, ConfiguredRandomFeatureList.field_236430_a_.listOf().fieldOf("features").forGetter((p_236586_0_) -> {
            return p_236586_0_.features;
        }), ConfiguredFeature.field_236264_b_.fieldOf("default").forGetter((p_236584_0_) -> {
            return p_236584_0_.defaultFeature;
        }));
    });
     */

    public final ConfiguredFeature<FC, ?> configuredFeature;
    public final float weight;

    public WeightedFeature(ConfiguredFeature<FC, ?> configuredFeature, float weight) {
        this.configuredFeature = configuredFeature;
        this.weight = weight;
    }


    //TODO
    /*
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGenerator, Random random, BlockPos blockPos) {
        return this.configuredFeature.place(world, chunkGenerator, random, blockPos);
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("name"), ops.createString(this.configuredFeature.feature.getRegistryName().toString()), ops.createString("config"), this.configuredFeature.config.serialize(ops).getValue(), ops.createString("weight"), ops.createFloat(this.weight))));
    }

    public static <T> WeightedFeature<?> deserialize(Dynamic<T> ops) {
        return new WeightedFeature<>(ConfiguredFeature.deserialize(ops), ops.get("weight").asFloat(0));
    }
     */

}
