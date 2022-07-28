package cech12.extendedmushrooms.compat;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.config.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;

public class ModCompat {

    public static final Mod[] MODS = {
            new ILikeWood(),
            new Quark()
    };

    private static boolean inDevMode() {
        return ExtendedMushrooms.DEVELOPMENT_MODE;
    }

    private static boolean checkValue(Class<?> clazz, ForgeConfigSpec.IntValue config) {
        int configValue = config.get();
        if (configValue == 1 || (inDevMode() && configValue != 0)) {
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

    public interface VariantBookshelfMod {}
    public interface VariantChestsMod {}
    public interface VariantTrappedChestsMod {}
    public interface VariantLadderMod {}
    public interface VerticalPlanksMod {}
    public interface VerticalSlabsMod {}

}
