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

    public static final ConfigType.Integer BIG_MUSHROOM_GENERATION_CHANCE = new ConfigType.Integer(12);
    public static final ConfigType.Integer MEGA_MUSHROOM_GENERATION_CHANCE = new ConfigType.Integer(12);

    public static final ConfigType.Boolean VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double MEGA_BROWN_MUSHROOM_GENERATION_WEIGHT = new ConfigType.Double(1.0);
    public static final ConfigType.Double MEGA_RED_MUSHROOM_GENERATION_WEIGHT = new ConfigType.Double(1.0);

    public static final ConfigType.Boolean GLOWSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double GLOWSHROOM_GENERATION_CHANCE_FACTOR = new ConfigType.Double(0.25);
    public static final ConfigType.Double GLOWSHROOM_GENERATION_COUNT_FACTOR = new ConfigType.Double(0.25);
    public static final ConfigType.Boolean BIG_GLOWSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double BIG_GLOWSHROOM_GENERATION_WEIGHT = new ConfigType.Double(0.25);
    public static final ConfigType.Boolean MEGA_GLOWSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double MEGA_GLOWSHROOM_GENERATION_WEIGHT = new ConfigType.Double(0.25);

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

        GLOWSHROOM_GENERATION_ENABLED.configObj = common
                .comment("Whether or not glowshrooms should be generated.")
                .define("glowshroomGenerationEnabled", GLOWSHROOM_GENERATION_ENABLED.getDefaultValue());
        BIG_GLOWSHROOM_GENERATION_ENABLED.configObj = common
                .comment("Whether or not big glowshrooms should be generated.")
                .define("bigGlowshroomGenerationEnabled", BIG_GLOWSHROOM_GENERATION_ENABLED.getDefaultValue());
        MEGA_GLOWSHROOM_GENERATION_ENABLED.configObj = common
                .comment("Whether or not mega glowshrooms should be generated.")
                .define("megaGlowshroomGenerationEnabled", MEGA_GLOWSHROOM_GENERATION_ENABLED.getDefaultValue());

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

        BIG_MUSHROOM_GENERATION_CHANCE.configObj = common
                .comment("Generating chance of big mushrooms in biomes with big mushrooms. (1 - high chance; 100 - low chance)")
                .defineInRange("bigMushroomGenerationChance", BIG_MUSHROOM_GENERATION_CHANCE.getDefaultValue(), 1, 100);
        MEGA_MUSHROOM_GENERATION_CHANCE.configObj = common
                .comment("Generating chance of mega mushrooms in mushroom biomes. (1 - high chance; 100 - low chance)")
                .defineInRange("megaMushroomGenerationChance", MEGA_MUSHROOM_GENERATION_CHANCE.getDefaultValue(), 1, 100);


        MEGA_BROWN_MUSHROOM_GENERATION_WEIGHT.configObj = common
                .comment("Weight of mega brown mushrooms generation.")
                .defineInRange("megaBrownMushroomGenerationWeight", MEGA_BROWN_MUSHROOM_GENERATION_WEIGHT.getDefaultValue(), 0.0, 10.0);
        MEGA_RED_MUSHROOM_GENERATION_WEIGHT.configObj = common
                .comment("Weight of mega red mushrooms generation.")
                .defineInRange("megaRedMushroomGenerationWeight", MEGA_RED_MUSHROOM_GENERATION_WEIGHT.getDefaultValue(), 0.0, 10.0);

        GLOWSHROOM_GENERATION_CHANCE_FACTOR.configObj = common
                .comment("Chance factor of glowshrooms generation. (1.0: generation chance of vanilla brown mushroom)")
                .defineInRange("glowshroomGenerationChanceFactor", GLOWSHROOM_GENERATION_CHANCE_FACTOR.getDefaultValue(), 0.0, 10.0);
        GLOWSHROOM_GENERATION_COUNT_FACTOR.configObj = common
                .comment("Count factor of glowshrooms generation. (1.0: generation count of vanilla brown mushroom)")
                .defineInRange("glowshroomGenerationCountFactor", GLOWSHROOM_GENERATION_COUNT_FACTOR.getDefaultValue(), 0.0, 10.0);
        BIG_GLOWSHROOM_GENERATION_WEIGHT.configObj = common
                .comment("Weight of big glowshrooms generation.")
                .defineInRange("bigGlowshroomGenerationWeight", BIG_GLOWSHROOM_GENERATION_WEIGHT.getDefaultValue(), 0.0, 10.0);
        MEGA_GLOWSHROOM_GENERATION_WEIGHT.configObj = common
                .comment("Weight of mega glowshrooms generation.")
                .defineInRange("megaGlowshroomGenerationWeight", MEGA_GLOWSHROOM_GENERATION_WEIGHT.getDefaultValue(), 0.0, 10.0);

        common.pop();

        COMMON = common.build();
    }

    public static void resetConfig() {
        for (IResettableConfigType par : allValues){
            par.reset();
        }
    }
}
