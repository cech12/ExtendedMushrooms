package cech12.extendedmushrooms.compat;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.config.ConfigType;
import net.minecraftforge.fml.ModList;

public class ModCompat {

    public static final Mod[] MODS = {
            new ExtendedSlabs(),
            new ILikeWood(),
            new Mubble(),
            new Quark(),
            new VanillaThings()
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

    public static boolean isVariantBookshelfModLoaded() {
        return checkValue(VariantBookshelfMod.class, Config.VARIANT_BOOKSHELF_ENABLED);
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

    public interface VariantBookshelfMod {}
    public interface VerticalPlanksMod {}
    public interface VerticalSlabsMod {}


}
