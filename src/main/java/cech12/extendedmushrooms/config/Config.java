package cech12.extendedmushrooms.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static ForgeConfigSpec COMMON;

    public static List<IResettableConfigType> allValues = new ArrayList<>();

    public static final ConfigType.Boolean INFESTED_GRASS_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean MUSHROOM_SHEEP_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer MUSHROOM_SHEEP_SPAWN_WEIGHT = new ConfigType.Integer(8);
    public static final ConfigType.Integer MUSHROOM_SHEEP_SPAWN_MIN_GROUP_COUNT = new ConfigType.Integer(4);
    public static final ConfigType.Integer MUSHROOM_SHEEP_SPAWN_MAX_GROUP_COUNT = new ConfigType.Integer(8);

    public static final ConfigType.Boolean VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer VANILLA_MEGA_MUSHROOM_GENERATION_CHANCE = new ConfigType.Integer(15);

    static {
        final ForgeConfigSpec.Builder common = new ForgeConfigSpec.Builder();

        common.comment("Enable or disable certain blocks or entities.").push("Accessibility Config");

        INFESTED_GRASS_ENABLED.configObj = common
                .comment("Whether or not Infested Grass should be generated in mushroom biomes.")
                .define("infestedGrassEnabled", INFESTED_GRASS_ENABLED.getDefaultValue());

        MUSHROOM_SHEEP_ENABLED.configObj = common
                .comment("Whether or not Mushroom Sheep entity should spawn in mushroom biomes.")
                .define("mushroomSheepEnabled", MUSHROOM_SHEEP_ENABLED.getDefaultValue());

        VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED.configObj = common
                .comment("Whether or not vanilla mega mushrooms should be generated in mushroom biomes.")
                .define("vanillaMegaMushroomGenerationEnabled", VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED.getDefaultValue());

        common.pop();

        common.comment("Various options that affect blocks and entities.").push("Balance Options");

        MUSHROOM_SHEEP_SPAWN_WEIGHT.configObj = common
                .comment("Spawn weight of Mushroom Sheep. (Example: Mooshroom in mushroom biome has weight of 8)")
                .defineInRange("mushroomSheepSpawnWeight", MUSHROOM_SHEEP_SPAWN_WEIGHT.getDefaultValue(), 1, 100);
        MUSHROOM_SHEEP_SPAWN_MIN_GROUP_COUNT.configObj = common
                .comment("Minimal group size of spawning Mushroom Sheep herds.")
                .defineInRange("mushroomSheepSpawnMinGroupCount", MUSHROOM_SHEEP_SPAWN_MIN_GROUP_COUNT.getDefaultValue(), 1, 20);
        MUSHROOM_SHEEP_SPAWN_MAX_GROUP_COUNT.configObj = common
                .comment("Maximal group size of spawning Mushroom Sheep herds.")
                .defineInRange("mushroomSheepSpawnMaxGroupCount", MUSHROOM_SHEEP_SPAWN_MAX_GROUP_COUNT.getDefaultValue(), 1, 20);

        VANILLA_MEGA_MUSHROOM_GENERATION_CHANCE.configObj = common
                .comment("Chance of generate vanilla mega mushrooms in mushroom biomes. (1 - high chance; 100 - low chance)")
                .defineInRange("vanillaMegaMushroomGenerationChance", VANILLA_MEGA_MUSHROOM_GENERATION_CHANCE.getDefaultValue(), 1, 100);

        common.pop();

        COMMON = common.build();
    }

    public static void resetConfig() {
        for (IResettableConfigType par : allValues){
            par.reset();
        }
    }
}
