package cech12.extendedmushrooms.compat;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Voyage extends ModCompat.Mod implements
        ModCompat.BiomeMod {

    public Voyage() {
        super("voyage");
    }

    private ResourceLocation getLocation(String path) {
        return new ResourceLocation(this.name, path);
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithMushrooms() {
        ArrayList<ModCompat.BiomeConfig> list = new ArrayList<>();

        String[] biomes = {
                "mount",
                "bog",
                "lagoon_warm",
                "lagoon_lukewarm",
                "lagoon_cold",
                "steppe",
                "rocky_peaks",
                "desert_mixed",
                "desert_mixed_hills",
                "flower_plains",
                "desert_mountains",
                "desert_polar",
                "forest_lush",
                "rock_field"
        };
        for (String biome : biomes) {
            list.add(new ModCompat.BiomeConfig(getLocation(biome), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        }

        //extra mushrooms
        list.add(new ModCompat.BiomeConfig(getLocation("bog"), ModCompat.BiomeConfig.Type.COUNT_CHANCE, 0.25F, 8)); // b:8|0.25 r:8|0.125

        return list;
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithHugeMushrooms() {
        return null;
    }
}
