package cech12.extendedmushrooms.compat;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Atmospheric extends ModCompat.Mod implements
        ModCompat.BiomeMod {

    public Atmospheric() {
        super("atmospheric");
    }

    private ResourceLocation getLocation(String path) {
        return new ResourceLocation(this.name, path);
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithMushrooms() {
        ArrayList<ModCompat.BiomeConfig> list = new ArrayList<>();

        String[] biomes = {
                "rosewood_forest",
                "rosewood_mountains",
                "rosewood_plateau",
                "rosewood_forest_plateau",
                "dunes",
                "rocky_dunes",
                "petrified_dunes",
                "flourishing_dunes"
        };
        for (String biome : biomes) {
            list.add(new ModCompat.BiomeConfig(getLocation(biome), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        }

        return list;
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithHugeMushrooms() {
        return null;
    }
}
