package cech12.extendedmushrooms.compat;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Traverse extends ModCompat.Mod implements
        ModCompat.BiomeMod {

    public Traverse() {
        super("traverse");
    }

    private ResourceLocation getLocation(String path) {
        return new ResourceLocation(this.name, path);
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithMushrooms() {
        ArrayList<ModCompat.BiomeConfig> list = new ArrayList<>();

        String[] biomes = {
                "arid_highlands",
                "autumnal_wooded_hills",
                "autumnal_woods",
                //"cliffs",
                "coniferous_forest",
                "coniferous_woodland_hills_biome",
                "desert_shrubland",
                "high_coniferous_forest",
                "lush_swamp",
                "meadow",
                "mini_jungle",
                "plains_plateau",
                "rock_edge",
                "rolling_hills",
                "snowy_coniferous_forest",
                "snowy_coniferous_wooded_hills",
                "snowy_high_coniferous_forest",
                //"wooded_island",
                "wooded_plateau",
                "woodlands"
        };
        for (String biome : biomes) {
            list.add(new ModCompat.BiomeConfig(getLocation(biome), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        }

        //extra mushrooms to lush swamp
        list.add(new ModCompat.BiomeConfig(getLocation("lush_swamp"), ModCompat.BiomeConfig.Type.COUNT_CHANCE, 0.25F, 8)); // b:4 r:8

        return list;
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithHugeMushrooms() {
        return null;
    }
}
