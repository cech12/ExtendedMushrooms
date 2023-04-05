package cech12.extendedmushrooms.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ServerConfig {
    public static ForgeConfigSpec SERVER_CONFIG;

    public static final ForgeConfigSpec.BooleanValue MUSHROOM_CAPS_WITH_SHEARS_ENABLED;
    public static final ForgeConfigSpec.BooleanValue MUSHROOM_STEMS_WITHOUT_SILK_TOUCH_ENABLED;
    public static final ForgeConfigSpec.BooleanValue SHEEP_EAT_MUSHROOM_FROM_GROUND_ENABLED;
    public static final ForgeConfigSpec.BooleanValue SHEEP_ABSORB_MUSHROOM_TYPE_ENABLED;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Options that affect mod behavior.").push("behavior");

        MUSHROOM_CAPS_WITH_SHEARS_ENABLED = builder
                .comment("Whether or not mushroom caps can be harvested with shears.")
                .define("mushroomCapsWithShearsEnabled", true);
        MUSHROOM_STEMS_WITHOUT_SILK_TOUCH_ENABLED = builder
                .comment("Whether or not mushroom stems can be harvested without silk touch enchantment.")
                .define("mushroomStemsWithoutSilkTouchEnabled", true);

        builder.pop();

        builder.comment("Options that affect blocks and entities.").push("balancing");

        SHEEP_EAT_MUSHROOM_FROM_GROUND_ENABLED = builder
                .comment("Whether or not sheeps and mushroom sheeps can eat mushrooms from ground.")
                .define("sheepEatMushroomFromGroundEnabled", true);
        SHEEP_ABSORB_MUSHROOM_TYPE_ENABLED = builder
                .comment("Whether or not sheeps and mushroom sheeps change their fleece to the mushroom type when eating a mushroom from ground or while feeding.")
                .define("sheepAbsorbMushroomTypeEnabled", true);

        builder.pop();

        SERVER_CONFIG = builder.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        spec.setConfig(configData);
    }

}
