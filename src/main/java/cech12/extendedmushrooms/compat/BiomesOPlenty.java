package cech12.extendedmushrooms.compat;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class BiomesOPlenty extends ModCompat.Mod implements
        ModCompat.BiomeMod {

    public BiomesOPlenty() {
        super("biomesoplenty");
    }

    private ResourceLocation getLocation(String path) {
        return new ResourceLocation(this.name, path);
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithMushrooms() {
        ArrayList<ModCompat.BiomeConfig> list = new ArrayList<>();

        //overworld
        list.add(new ModCompat.BiomeConfig(getLocation("bayou"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("bog"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:15 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("cherry_blossom_grove"), ModCompat.BiomeConfig.Type.CHANCE, 4)); // b:2 r:4
        list.add(new ModCompat.BiomeConfig(getLocation("coniferous_forest"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("fir_clearing"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("fungal_jungle"), ModCompat.BiomeConfig.Type.CHANCE, 10)); // b:5 r:10
        list.add(new ModCompat.BiomeConfig(getLocation("highland_moor"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("lush_grassland"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("lush_swamp"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("meadow"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("mire"), ModCompat.BiomeConfig.Type.CHANCE, 5)); // b:10 r:5
        list.add(new ModCompat.BiomeConfig(getLocation("mystic_grove"), ModCompat.BiomeConfig.Type.CHANCE, 10)); // b:10 r:20
        list.add(new ModCompat.BiomeConfig(getLocation("ominous_woods"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("pumpkin_patch"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("rainbow_valley"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("rainforest"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("redwood_forest"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("seasonal_forest"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("shield"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("silkglade"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("snowy_coniferous_forest"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("snowy_fir_clearing"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("temperate_rainforest"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("temperate_rainforest_hills"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("tropical_rainforest"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("wetland"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("woodland"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8

        //nether
        list.add(new ModCompat.BiomeConfig(getLocation("ashen_inferno"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("undergarden"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8
        list.add(new ModCompat.BiomeConfig(getLocation("visceral_heap"), ModCompat.BiomeConfig.Type.CHANCE, 8)); // b:4 r:8

        return list;
    }

    @Override
    public List<ModCompat.BiomeConfig> getBiomesWithHugeMushrooms() {
        // mire | brown Placement.COUNT_HEIGHTMAP_DOUBLE.configured(new FrequencyConfig(17))
        // mystic_grove | red Placement.COUNT_EXTRA_HEIGHTMAP.configured(new AtSurfaceWithExtraConfig(1, 0.1F, 1)))
        return null;
    }
}
