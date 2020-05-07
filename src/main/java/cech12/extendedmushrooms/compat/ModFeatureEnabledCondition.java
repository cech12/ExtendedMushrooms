package cech12.extendedmushrooms.compat;

import cech12.extendedmushrooms.ExtendedMushrooms;
import com.google.gson.JsonObject;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

/**
 * Condition that checks whether item is instance of IEnabled, and if it is, whether or not it is enabled.
 * Used to disable recipes for items disabled in config.
 */
public class ModFeatureEnabledCondition implements ICondition {
    private static final ResourceLocation ID = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mod_feature_enabled");
    private final String feature;
    private final boolean inverted;

    public ModFeatureEnabledCondition(String feature) {
        this.feature = feature;
        this.inverted = false;
    }

    public ModFeatureEnabledCondition(String feature, boolean inverted) {
        this.feature = feature;
        this.inverted = inverted;
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test() {
        switch (this.feature) {
            case "variantBookshelves":
                return this.inverted != ModCompat.isVariantBookshelvesModLoaded();
            case "variantChests":
                return this.inverted != ModCompat.isVariantChestsModLoaded();
            case "variantTrappedChests":
                return this.inverted != ModCompat.isVariantTrappedChestsModLoaded();
            case "variantLadders":
                return this.inverted != ModCompat.isVariantLaddersModLoaded();
            case "verticalPlanks":
                return this.inverted != ModCompat.isVerticalPlanksModLoaded();
            case "verticalSlabs":
                return this.inverted != ModCompat.isVerticalSlabsModLoaded();
        }
        return false;
    }

    public static class Serializer implements IConditionSerializer<ModFeatureEnabledCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, ModFeatureEnabledCondition value) {
            json.addProperty("feature", value.feature);
            if (value.inverted) {
                json.addProperty("inverted", true);
            }
        }

        @Override
        public ModFeatureEnabledCondition read(JsonObject json) {
            return new ModFeatureEnabledCondition(
                    JSONUtils.getString(json, "feature"),
                    JSONUtils.getBoolean(json, "inverted", false));
        }

        @Override
        public ResourceLocation getID() {
            return ModFeatureEnabledCondition.ID;
        }
    }
}

