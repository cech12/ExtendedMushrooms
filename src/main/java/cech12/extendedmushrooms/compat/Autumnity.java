package cech12.extendedmushrooms.compat;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Autumnity extends ModCompat.Mod implements
        ModCompat.BiomeMod {

    public Autumnity() {
        super("autumnity");
    }

    private ResourceLocation getLocation(String path) {
        return new ResourceLocation(this.name, path);
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithMushrooms() {
        ArrayList<ModCompat.BiomeConfig> list = new ArrayList<>();

        list.add(new ModCompat.BiomeConfig(getLocation("maple_forest"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("maple_forest_hills"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("pumpkin_fields"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8

        return list;
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithHugeMushrooms() {
        return null;
    }
}
