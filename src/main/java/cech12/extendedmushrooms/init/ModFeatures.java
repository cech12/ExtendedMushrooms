package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.world.gen.feature.MegaBrownMushroomFeature;
import cech12.extendedmushrooms.world.gen.feature.MegaRedMushroomFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures {

    public static Feature<BigMushroomFeatureConfig> MEGA_BROWN_MUSHROOM;
    public static Feature<BigMushroomFeatureConfig> MEGA_RED_MUSHROOM;

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        MEGA_BROWN_MUSHROOM = register("mega_brown_mushroom", new MegaBrownMushroomFeature(BigMushroomFeatureConfig::deserialize));
        MEGA_RED_MUSHROOM = register("mega_red_mushroom", new MegaRedMushroomFeature(BigMushroomFeatureConfig::deserialize));
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F feature) {
        feature.setRegistryName(ExtendedMushrooms.MOD_ID, key);
        ForgeRegistries.FEATURES.register(feature);
        return feature;
    }

    public static void addFeaturesToBiomes() {
        //add vanilla mega mushrooms to mushroom biomes
        if (Config.VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED.getValue()) {
            Biome[] biomes = {Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE};
            for (Biome biome : biomes) {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_BOOLEAN_SELECTOR
                        .withConfiguration(new TwoFeatureChoiceConfig(MEGA_RED_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_RED_MUSHROOM), MEGA_BROWN_MUSHROOM.withConfiguration(DefaultBiomeFeatures.BIG_BROWN_MUSHROOM)))
                        .withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(Config.VANILLA_MEGA_MUSHROOM_GENERATION_CHANCE.getValue()))));
            }
        }
    }

}