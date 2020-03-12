package cech12.extendedmushrooms.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class WeightedRandomFeatureConfig extends Feature<WeightedRandomFeature> {

    public WeightedRandomFeatureConfig(Function<Dynamic<?>, ? extends WeightedRandomFeature> config) {
        super(config);
    }

    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, WeightedRandomFeature config) {
        float weightSum = rand.nextFloat() * config.getWeightSum();
        for (WeightedFeature<?> feature : config.features) {
            weightSum -= feature.weight;
            if (weightSum <= 0) {
                return feature.place(worldIn, generator, rand, pos);
            }
        }
        return false;
    }
}
