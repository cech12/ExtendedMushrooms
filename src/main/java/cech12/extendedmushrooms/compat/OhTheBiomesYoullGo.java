package cech12.extendedmushrooms.compat;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class OhTheBiomesYoullGo extends ModCompat.Mod implements
        ModCompat.BiomeMod {

    public OhTheBiomesYoullGo() {
        super("byg");
    }

    private ResourceLocation getLocation(String path) {
        return new ResourceLocation(this.name, path);
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithMushrooms() {
        ArrayList<ModCompat.BiomeConfig> list = new ArrayList<>();

        String[] biomes = {
                "alliumfields",
                "baobabsavanna",
                "bluetaiga",
                "giantbluesprucetaiga",
                "bog",
                "borealforest",
                "coniferousforest",
                "dovermountains",
                "deciduousforest",
                "grasslandplateau",
                "guianashield",
                "jacarandaforest",
                "marshlands",
                "meadow",
                "prairie",
                "reddesert",
                "redoakforest",
                "seasonalbirchforest",
                "seasonaldeciduousforest",
                "seasonalforest",
                "giantseasonalsprucetaiga",
                "seasonaltaiga",
                "shatteredglacier",
                "shrublands",
                "skyrishighlands",
                "snowybluetaiga",
                "snowygiantbluetaiga",
                "snowyconiferousforest",
                "snowydeciduousforest",
                "tropicalfungalforest", "tropicalfungalforest", "tropicalfungalforest", //multiple times!
                "tropicalrainforest",
                //sub biomes
                "bluetaigahills",
                "borealforesthills",
                "deciduousforesthills",
                "jacarandaforesthills",
                "prairieclearing",
                "redoakforesthills",
                "seasonalbirchforesthills",
                "seasonaldeciduousforesthills",
                "seasonalforesthills",
                "seasonaltaigahills",
                "snowybluetaigahills",
                "snowyconiferousforesthills",
                "snowydeciduousforesthills",
                "tropicalfungalrainforesthills", "tropicalfungalrainforesthills", "tropicalfungalrainforesthills", //multiple times!
                "tropicalrainforesthills",
                //non default
                "tropicalisland"
        };
        for (String biome : biomes) {
            list.add(new ModCompat.BiomeConfig(getLocation(biome), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        }

        //addTaigaGrassAndMushrooms
        String[] taigaBiomes = {
                "bluetaiga",
                "giantbluesprucetaiga",
                "coniferousforest",
                "dovermountains",
                "giantseasonalsprucetaiga",
                "seasonaltaiga",
                "shatteredglacier",
                "snowybluetaiga",
                "snowygiantbluetaiga",
                "snowyconiferousforest",
                //sub biomes
                "bluetaigahills",
                "seasonaltaigahills",
                "snowybluetaigahills",
                "snowyconiferousforesthills"
        };
        for (String biome : taigaBiomes) {
            list.add(new ModCompat.BiomeConfig(getLocation(biome), ModCompat.BiomeConfig.Type.COUNT_CHANCE, 0.25F, 1));
        }

        //TODO nether
        // warpeddesert, sythiantorrids
        // this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.5F, 0, 0, 128))));

        return list;
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithHugeMushrooms() {
        ArrayList<ModCompat.BiomeConfig> list = new ArrayList<>();

        String[] biomes = {
                "tropicalfungalforest", //addHugeMushrooms 5 times
                "tropicalfungalrainforesthills" //addHugeMushrooms 5 times
        };
        for (String biome : biomes) {
            list.add(new ModCompat.BiomeConfig(getLocation(biome), ModCompat.BiomeConfig.Type.HUGE, 0.5F)); //12 * 0.5 = 6 times (OK!)
        }

        return list;
    }
}
