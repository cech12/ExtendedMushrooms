package cech12.extendedmushrooms.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record ExtendedMushroomFeatureConfiguration(
        BlockStateProvider capProvider,
        BlockStateProvider stemProvider
) implements FeatureConfiguration {

    public static final Codec<ExtendedMushroomFeatureConfiguration> CODEC = RecordCodecBuilder.create((kind) ->
            kind.group(BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter((config) -> config.capProvider),
                    BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter((config) -> config.stemProvider)
            ).apply(kind, ExtendedMushroomFeatureConfiguration::new));

}
