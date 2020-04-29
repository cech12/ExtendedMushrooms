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

    public ModFeatureEnabledCondition(String feature) {
        this.feature = feature;
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test() {
        switch (this.feature) {
            case "verticalPlanks":
                return ModCompat.isVerticalPlanksModLoaded();
            case "verticalSlab":
                return ModCompat.isVerticalSlabsModLoaded();
        }
        return false;
    }

    public static class Serializer implements IConditionSerializer<ModFeatureEnabledCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, ModFeatureEnabledCondition value) {
            json.addProperty("feature", value.feature);
        }

        @Override
        public ModFeatureEnabledCondition read(JsonObject json) {
            return new ModFeatureEnabledCondition(JSONUtils.getString(json, "feature"));
        }

        @Override
        public ResourceLocation getID() {
            return ModFeatureEnabledCondition.ID;
        }
    }
}

