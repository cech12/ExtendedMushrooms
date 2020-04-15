package cech12.extendedmushrooms.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static ForgeConfigSpec COMMON;

    public static List<IResettableConfigType> allValues = new ArrayList<>();

    public static final ConfigType.Boolean MUSHROOM_CAPS_WITH_SHEARS_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean MUSHROOM_STEMS_WITHOUT_SILK_TOUCH_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean INFESTED_GRASS_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean INFESTED_FLOWER_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean MUSHROOM_SHEEP_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer MUSHROOM_SHEEP_SPAWN_WEIGHT = new ConfigType.Integer(8);
    public static final ConfigType.Integer MUSHROOM_SHEEP_SPAWN_MIN_GROUP_COUNT = new ConfigType.Integer(4);
    public static final ConfigType.Integer MUSHROOM_SHEEP_SPAWN_MAX_GROUP_COUNT = new ConfigType.Integer(8);
    public static final ConfigType.Boolean SHEEP_EAT_MUSHROOM_FROM_GROUND_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean SHEEP_ABSORB_MUSHROOM_TYPE_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean MUSHROOM_CAP_BUTTON_PLAY_SOUND = new ConfigType.Boolean(false);
    public static final ConfigType.Boolean MUSHROOM_CAP_PRESSURE_PLATE_PLAY_SOUND = new ConfigType.Boolean(false);

    public static final ConfigType.Integer BIG_MUSHROOM_GENERATION_CHANCE = new ConfigType.Integer(12);
    public static final ConfigType.Integer MEGA_MUSHROOM_GENERATION_CHANCE = new ConfigType.Integer(12);

    public static final ConfigType.Boolean VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double MEGA_BROWN_MUSHROOM_GENERATION_WEIGHT = new ConfigType.Double(1.0);
    public static final ConfigType.Double MEGA_RED_MUSHROOM_GENERATION_WEIGHT = new ConfigType.Double(1.0);

    public static final ConfigType.Boolean GLOWSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double GLOWSHROOM_GENERATION_CHANCE_FACTOR = new ConfigType.Double(0.15);
    public static final ConfigType.Double GLOWSHROOM_GENERATION_COUNT_FACTOR = new ConfigType.Double(0.15);
    public static final ConfigType.Boolean BIG_GLOWSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double BIG_GLOWSHROOM_GENERATION_WEIGHT = new ConfigType.Double(0.15);
    public static final ConfigType.Boolean MEGA_GLOWSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double MEGA_GLOWSHROOM_GENERATION_WEIGHT = new ConfigType.Double(0.15);

    public static final ConfigType.Boolean POISONOUS_MUSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double POISONOUS_MUSHROOM_GENERATION_CHANCE_FACTOR = new ConfigType.Double(0.25);
    public static final ConfigType.Double POISONOUS_MUSHROOM_GENERATION_COUNT_FACTOR = new ConfigType.Double(0.25);
    public static final ConfigType.Boolean BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double BIG_POISONOUS_MUSHROOM_GENERATION_WEIGHT = new ConfigType.Double(0.25);
    public static final ConfigType.Boolean MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double MEGA_POISONOUS_MUSHROOM_GENERATION_WEIGHT = new ConfigType.Double(0.25);

    static {
        final ForgeConfigSpec.Builder common = new ForgeConfigSpec.Builder();

        common.comment("Various options that affect mod behaviour.").push("Behaviour Options");

        MUSHROOM_CAPS_WITH_SHEARS_ENABLED.configObj = common
                .comment("Whether or not mushroom caps can be harvested with shears.")
                .define("mushroomCapsWithShearsEnabled", MUSHROOM_CAPS_WITH_SHEARS_ENABLED.getDefaultValue());
        MUSHROOM_STEMS_WITHOUT_SILK_TOUCH_ENABLED.configObj = common
                .comment("Whether or not mushroom stems can be harvested without silk touch enchantment.")
                .define("mushroomStemsWithoutSilkTouchEnabled", MUSHROOM_STEMS_WITHOUT_SILK_TOUCH_ENABLED.getDefaultValue());

        common.pop();

        common.comment("Enable or disable certain blocks or entities.").push("Accessibility Config");

        INFESTED_GRASS_ENABLED.configObj = common
                .comment("Whether or not Infested Grass should be generated in mushroom biomes.")
                .define("infestedGrassEnabled", INFESTED_GRASS_ENABLED.getDefaultValue());
        INFESTED_FLOWER_ENABLED.configObj = common
                .comment("Whether or not Infested Flower should be generated in mushroom biomes.")
                .define("infestedFlowerEnabled", INFESTED_FLOWER_ENABLED.getDefaultValue());

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

        POISONOUS_MUSHROOM_GENERATION_ENABLED.configObj = common
                .comment("Whether or not poisonous mushrooms should be generated.")
                .define("poisonousMushroomGenerationEnabled", POISONOUS_MUSHROOM_GENERATION_ENABLED.getDefaultValue());
        BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED.configObj = common
                .comment("Whether or not big poisonous mushrooms should be generated.")
                .define("bigPoisonousMushroomGenerationEnabled", BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED.getDefaultValue());
        MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED.configObj = common
                .comment("Whether or not mega poisonous mushrooms should be generated.")
                .define("megaPoisonousMushroomGenerationEnabled", MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED.getDefaultValue());

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

        SHEEP_EAT_MUSHROOM_FROM_GROUND_ENABLED.configObj = common
                .comment("Whether or not sheeps and mushroom sheeps can eat mushrooms from ground.")
                .define("sheepEatMushroomFromGroundEnabled", SHEEP_EAT_MUSHROOM_FROM_GROUND_ENABLED.getDefaultValue());
        SHEEP_ABSORB_MUSHROOM_TYPE_ENABLED.configObj = common
                .comment("Whether or not sheeps and mushroom sheeps change their fleece to the mushroom type when eating a mushroom from ground or while feeding.")
                .define("sheepAbsorbMushroomTypeEnabled", SHEEP_ABSORB_MUSHROOM_TYPE_ENABLED.getDefaultValue());

        MUSHROOM_CAP_BUTTON_PLAY_SOUND.configObj = common
                .comment("Whether or not mushroom cap buttons should play a sound when activated.")
                .define("mushroomButtonPressurePlatePlaySound", MUSHROOM_CAP_BUTTON_PLAY_SOUND.getDefaultValue());
        MUSHROOM_CAP_PRESSURE_PLATE_PLAY_SOUND.configObj = common
                .comment("Whether or not mushroom cap pressure plates should play a sound when stepped on or off.")
                .define("mushroomCapPressurePlatePlaySound", MUSHROOM_CAP_PRESSURE_PLATE_PLAY_SOUND.getDefaultValue());

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

        POISONOUS_MUSHROOM_GENERATION_CHANCE_FACTOR.configObj = common
                .comment("Chance factor of poisonous mushrooms generation. (1.0: generation chance of vanilla brown mushroom)")
                .defineInRange("poisonousMushroomGenerationChanceFactor", POISONOUS_MUSHROOM_GENERATION_CHANCE_FACTOR.getDefaultValue(), 0.0, 10.0);
        POISONOUS_MUSHROOM_GENERATION_COUNT_FACTOR.configObj = common
                .comment("Count factor of poisonous mushrooms generation. (1.0: generation count of vanilla brown mushroom)")
                .defineInRange("poisonousMushroomGenerationCountFactor", POISONOUS_MUSHROOM_GENERATION_COUNT_FACTOR.getDefaultValue(), 0.0, 10.0);
        BIG_POISONOUS_MUSHROOM_GENERATION_WEIGHT.configObj = common
                .comment("Weight of big poisonous mushrooms generation.")
                .defineInRange("bigPoisonousMushroomGenerationWeight", BIG_POISONOUS_MUSHROOM_GENERATION_WEIGHT.getDefaultValue(), 0.0, 10.0);
        MEGA_POISONOUS_MUSHROOM_GENERATION_WEIGHT.configObj = common
                .comment("Weight of mega poisonous mushrooms generation.")
                .defineInRange("megaPoisonousMushroomGenerationWeight", MEGA_POISONOUS_MUSHROOM_GENERATION_WEIGHT.getDefaultValue(), 0.0, 10.0);

        common.pop();

        COMMON = common.build();
    }

    public static void resetConfig() {
        for (IResettableConfigType par : allValues){
            par.reset();
        }
    }
}
