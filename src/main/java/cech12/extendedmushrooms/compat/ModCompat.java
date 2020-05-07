package cech12.extendedmushrooms.compat;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.config.ConfigType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModCompat {

    public static final Mod[] MODS = {
            new Atmospheric(),
            new Autumnity(),
            new BiomesOPlenty(),
            new ExtendedSlabs(),
            new ILikeWood(),
            new Mubble(),
            new OhTheBiomesYoullGo(),
            new Quark(),
            new Traverse(),
            new VanillaThings(),
            new Voyage()
    };

    private static boolean inDevMode() {
        return ExtendedMushrooms.DEVELOPMENT_MODE;
    }

    private static boolean checkValue(Class<?> clazz, ConfigType.Integer config) {
        int configValue = config.getValue();
        if (inDevMode() || configValue == 1) {
            return true;
        } else if (configValue == 2) {
            for (Mod mod : MODS) {
                if (clazz.isInstance(mod) && mod.isLoaded()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<BiomeConfig> getBiomesWithMushrooms() {
        List<BiomeConfig> list = new ArrayList<>();
        for (Mod mod : MODS) {
            if (mod instanceof BiomeMod && mod.isLoaded()) {
                List<BiomeConfig> modList = ((BiomeMod) mod).getBiomesWithMushrooms();
                if (modList != null) {
                    list.addAll(modList);
                }
            }
        }
        return list;
    }

    public static List<BiomeConfig> getBiomesWithHugeMushrooms() {
        List<BiomeConfig> list = new ArrayList<>();
        for (Mod mod : MODS) {
            if (mod instanceof BiomeMod && mod.isLoaded()) {
                List<BiomeConfig> modList = ((BiomeMod) mod).getBiomesWithHugeMushrooms();
                if (modList != null) {
                    list.addAll(modList);
                }
            }
        }
        return list;
    }

    public static boolean isVariantBookshelvesModLoaded() {
        return checkValue(VariantBookshelfMod.class, Config.VARIANT_BOOKSHELF_ENABLED);
    }

    public static boolean isVariantChestsModLoaded() {
        return checkValue(VariantChestsMod.class, Config.VARIANT_CHESTS_ENABLED);
    }

    public static boolean isVariantTrappedChestsModLoaded() {
        return checkValue(VariantTrappedChestsMod.class, Config.VARIANT_TRAPPED_CHESTS_ENABLED);
    }

    public static boolean isVariantLaddersModLoaded() {
        return checkValue(VariantLadderMod.class, Config.VARIANT_LADDER_ENABLED);
    }

    public static boolean isVerticalPlanksModLoaded() {
        return checkValue(VerticalPlanksMod.class, Config.VERTICAL_PLANKS_ENABLED);
    }

    public static boolean isVerticalSlabsModLoaded() {
        return checkValue(VerticalSlabsMod.class, Config.VERTICAL_SLABS_ENABLED);
    }

    public static class Mod {

        protected String name;

        public Mod(String name) {
            this.name = name;
        }

        public boolean isLoaded() {
            return ModList.get().isLoaded(this.name);
        }

    }

    public interface BiomeMod {
        List<BiomeConfig> getBiomesWithMushrooms();
        List<BiomeConfig> getBiomesWithHugeMushrooms();
    }

    public interface VariantBookshelfMod {}
    public interface VariantChestsMod {}
    public interface VariantTrappedChestsMod {}
    public interface VariantLadderMod {}
    public interface VerticalPlanksMod {}
    public interface VerticalSlabsMod {}

    public static class BiomeConfig {
        private final ResourceLocation location;
        private final Type type;
        private final float chance;
        private final int count;

        public BiomeConfig(ResourceLocation location, Type type, float chance) {
            this(location, type, chance, 0);
        }

        public BiomeConfig(ResourceLocation location, Type type, float chance, int count) {
            this.location = location;
            this.type = type;
            this.chance = chance;
            this.count = count;
        }

        public boolean biomeExist() {
            return ForgeRegistries.BIOMES.containsKey(location);
        }

        public Biome getBiome() {
            return ForgeRegistries.BIOMES.getValue(location);
        }

        public ConfiguredPlacement<?> getPlacement(float chanceFactor, float countFactor) {
            if (this.type == Type.CHANCE) {
                return Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(Math.max(1, (int) (this.chance / chanceFactor))));
            } else if (this.type == Type.COUNT_CHANCE) {
                return Placement.COUNT_CHANCE_HEIGHTMAP.configure(new HeightWithChanceConfig(Math.max(1, (int) (this.count * countFactor)), this.chance * chanceFactor));
            } else { //Huge
                return Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(Math.max(1, (int) (this.chance * chanceFactor))));
            }
        }

        enum Type {
            CHANCE, COUNT_CHANCE, HUGE
        }
    }

}
