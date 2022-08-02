package cech12.extendedmushrooms.compat;

import cech12.extendedmushrooms.ExtendedMushrooms;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
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
    public boolean test(IContext context) {
        return switch (this.feature) {
            case "variantBookshelves" -> this.inverted != ModCompat.isVariantBookshelvesModLoaded().get();
            case "variantChests" -> this.inverted != ModCompat.isVariantChestsModLoaded().get();
            case "variantTrappedChests" -> this.inverted != ModCompat.isVariantTrappedChestsModLoaded().get();
            case "variantLadders" -> this.inverted != ModCompat.isVariantLaddersModLoaded().get();
            case "verticalPlanks" -> this.inverted != ModCompat.isVerticalPlanksModLoaded().get();
            case "verticalSlabs" -> this.inverted != ModCompat.isVerticalSlabsModLoaded().get();
            default -> false;
        };
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
                    GsonHelper.getAsString(json, "feature"),
                    GsonHelper.getAsBoolean(json, "inverted", false));
        }

        @Override
        public ResourceLocation getID() {
            return ModFeatureEnabledCondition.ID;
        }
    }
}

