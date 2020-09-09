package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredRandomFeatureList;
import net.minecraft.world.gen.feature.Feature;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class WeightedRandomFeatureConfig extends Feature<WeightedRandomFeature> {
    //TODO
    public WeightedRandomFeatureConfig(Codec<WeightedRandomFeature> p_i231953_1_) {
        super(p_i231953_1_);
    }

    @Override
    public boolean func_241855_a(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, WeightedRandomFeature p_241855_5_) {
        return false;
    }

    /*
    public static final Codec<WeightedRandomFeatureConfig> field_236600_a_ = RecordCodecBuilder.create((p_236602_0_) -> {
        return p_236602_0_.group(ConfiguredFeature.field_236264_b_.listOf().fieldOf("features").forGetter((p_236603_0_) -> {
            return p_236603_0_.features;
        })).apply(p_236602_0_, WeightedRandomFeatureConfig::new);
    });
    public final List<ConfiguredFeature<?, ?>> features;

    public WeightedRandomFeatureConfig(Codec<WeightedRandomFeature> config) {
        super(config);
    }

    @Override
    //place
    public boolean func_241855_a(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, WeightedRandomFeature config) {
        float weightSum = rand.nextFloat() * config.getWeightSum();
        for (WeightedFeature<?> feature : config.features) {
            weightSum -= feature.weight;
            if (weightSum <= 0) {
                return feature.place(worldIn, generator, rand, pos);
            }
        }
        return false;
    }

     */
}
