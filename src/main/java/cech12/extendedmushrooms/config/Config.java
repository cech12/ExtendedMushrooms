package cech12.extendedmushrooms.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec COMMON;

    public static final ForgeConfigSpec.BooleanValue MUSHROOM_CAPS_WITH_SHEARS_ENABLED;
    public static final ForgeConfigSpec.BooleanValue MUSHROOM_STEMS_WITHOUT_SILK_TOUCH_ENABLED;

    public static final ForgeConfigSpec.BooleanValue INFESTED_GRASS_ENABLED;
    public static final ForgeConfigSpec.BooleanValue INFESTED_FLOWER_ENABLED;

    public static final ForgeConfigSpec.IntValue VARIANT_BOOKSHELF_ENABLED;
    public static final ForgeConfigSpec.IntValue VARIANT_CHESTS_ENABLED;
    public static final ForgeConfigSpec.IntValue VARIANT_TRAPPED_CHESTS_ENABLED;
    public static final ForgeConfigSpec.IntValue VARIANT_LADDER_ENABLED;
    public static final ForgeConfigSpec.IntValue VERTICAL_PLANKS_ENABLED;
    public static final ForgeConfigSpec.IntValue VERTICAL_SLABS_ENABLED;

    public static final ForgeConfigSpec.BooleanValue MUSHROOM_SHEEP_ENABLED;
    public static final ForgeConfigSpec.IntValue MUSHROOM_SHEEP_SPAWN_WEIGHT;
    public static final ForgeConfigSpec.IntValue MUSHROOM_SHEEP_SPAWN_MIN_GROUP_COUNT;
    public static final ForgeConfigSpec.IntValue MUSHROOM_SHEEP_SPAWN_MAX_GROUP_COUNT;
    public static final ForgeConfigSpec.BooleanValue SHEEP_EAT_MUSHROOM_FROM_GROUND_ENABLED;
    public static final ForgeConfigSpec.BooleanValue SHEEP_ABSORB_MUSHROOM_TYPE_ENABLED;

    public static final ForgeConfigSpec.BooleanValue MUSHROOM_CAP_BUTTON_PLAY_SOUND;
    public static final ForgeConfigSpec.BooleanValue MUSHROOM_CAP_PRESSURE_PLATE_PLAY_SOUND;

    public static final ForgeConfigSpec.BooleanValue VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED;

    public static final ForgeConfigSpec.BooleanValue GLOWSHROOM_GENERATION_ENABLED;
    public static final ForgeConfigSpec.BooleanValue BIG_GLOWSHROOM_GENERATION_ENABLED;
    public static final ForgeConfigSpec.BooleanValue MEGA_GLOWSHROOM_GENERATION_ENABLED;

    public static final ForgeConfigSpec.BooleanValue POISONOUS_MUSHROOM_GENERATION_ENABLED;
    public static final ForgeConfigSpec.BooleanValue BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED;
    public static final ForgeConfigSpec.BooleanValue MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED;

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

        builder.comment("Enable or disable certain blocks or entities.").push("accessibility");

        INFESTED_GRASS_ENABLED = builder
                .comment("Whether or not Infested Grass should be generated in mushroom biomes.")
                .define("infestedGrassEnabled", true);
        INFESTED_FLOWER_ENABLED = builder
                .comment("Whether or not Infested Flower should be generated in mushroom biomes.")
                .define("infestedFlowerEnabled", true);

        VARIANT_BOOKSHELF_ENABLED = builder
                .comment("Whether or not variant bookshelves should be enabled. (0 - disabled, 1 - enabled, 2 - enabled when mod with this feature is installed)")
                .defineInRange("variantBookshelvesEnabled", 2, 0, 2);
        VARIANT_CHESTS_ENABLED = builder
                .comment("Whether or not variant chests should be enabled. (0 - disabled, 1 - enabled, 2 - enabled when mod with this feature is installed)")
                .defineInRange("variantChestsEnabled",2, 0, 2);
        VARIANT_TRAPPED_CHESTS_ENABLED = builder
                .comment("Whether or not variant trapped chests should be enabled. (0 - disabled, 1 - enabled, 2 - enabled when mod with this feature is installed)")
                .defineInRange("variantTrappedChestsEnabled", 2, 0, 2);
        VARIANT_LADDER_ENABLED = builder
                .comment("Whether or not variant ladders should be enabled. (0 - disabled, 1 - enabled, 2 - enabled when mod with this feature is installed)")
                .defineInRange("variantLaddersEnabled", 2, 0, 2);
        VERTICAL_PLANKS_ENABLED = builder
                .comment("Whether or not Vertical Planks should be enabled. (0 - disabled, 1 - enabled, 2 - enabled when mod with this feature is installed)")
                .defineInRange("verticalPlanksEnabled", 2, 0, 2);
        VERTICAL_SLABS_ENABLED = builder
                .comment("Whether or not Vertical Slabs should be enabled. (0 - disabled, 1 - enabled, 2 - enabled when mod with this feature is installed)")
                .defineInRange("verticalSlabsEnabled", 2, 0, 2);

        MUSHROOM_SHEEP_ENABLED = builder
                .comment("Whether or not Mushroom Sheep entity should spawn in mushroom biomes.")
                .define("mushroomSheepEnabled", true);

        VANILLA_MEGA_MUSHROOM_GENERATION_ENABLED = builder
                .comment("Whether or not vanilla mega mushrooms should be generated in mushroom biomes.")
                .define("vanillaMegaMushroomGenerationEnabled", true);

        GLOWSHROOM_GENERATION_ENABLED = builder
                .comment("Whether or not glowshrooms should be generated.")
                .define("glowshroomGenerationEnabled", true);
        BIG_GLOWSHROOM_GENERATION_ENABLED = builder
                .comment("Whether or not big glowshrooms should be generated.")
                .define("bigGlowshroomGenerationEnabled", true);
        MEGA_GLOWSHROOM_GENERATION_ENABLED = builder
                .comment("Whether or not mega glowshrooms should be generated.")
                .define("megaGlowshroomGenerationEnabled", true);

        POISONOUS_MUSHROOM_GENERATION_ENABLED = builder
                .comment("Whether or not poisonous mushrooms should be generated.")
                .define("poisonousMushroomGenerationEnabled", true);
        BIG_POISONOUS_MUSHROOM_GENERATION_ENABLED = builder
                .comment("Whether or not big poisonous mushrooms should be generated.")
                .define("bigPoisonousMushroomGenerationEnabled", true);
        MEGA_POISONOUS_MUSHROOM_GENERATION_ENABLED = builder
                .comment("Whether or not mega poisonous mushrooms should be generated.")
                .define("megaPoisonousMushroomGenerationEnabled", true);

        builder.pop();

        builder.comment("Options that affect blocks and entities.").push("balancing");

        MUSHROOM_SHEEP_SPAWN_WEIGHT = builder
                .comment("Spawn weight of Mushroom Sheep. (Example: Mooshroom in mushroom biome has weight of 8)")
                .defineInRange("mushroomSheepSpawnWeight", 8, 1, 100);
        MUSHROOM_SHEEP_SPAWN_MIN_GROUP_COUNT = builder
                .comment("Minimal group size of spawning Mushroom Sheep herds.")
                .defineInRange("mushroomSheepSpawnMinGroupCount", 4, 1, 20);
        MUSHROOM_SHEEP_SPAWN_MAX_GROUP_COUNT = builder
                .comment("Maximal group size of spawning Mushroom Sheep herds.")
                .defineInRange("mushroomSheepSpawnMaxGroupCount", 8, 1, 20);

        SHEEP_EAT_MUSHROOM_FROM_GROUND_ENABLED = builder
                .comment("Whether or not sheeps and mushroom sheeps can eat mushrooms from ground.")
                .define("sheepEatMushroomFromGroundEnabled", true);
        SHEEP_ABSORB_MUSHROOM_TYPE_ENABLED = builder
                .comment("Whether or not sheeps and mushroom sheeps change their fleece to the mushroom type when eating a mushroom from ground or while feeding.")
                .define("sheepAbsorbMushroomTypeEnabled", true);

        MUSHROOM_CAP_BUTTON_PLAY_SOUND = builder
                .comment("Whether or not mushroom cap buttons should play a sound when activated.")
                .define("mushroomButtonPressurePlatePlaySound", false);
        MUSHROOM_CAP_PRESSURE_PLATE_PLAY_SOUND = builder
                .comment("Whether or not mushroom cap pressure plates should play a sound when stepped on or off.")
                .define("mushroomCapPressurePlatePlaySound", false);

        builder.pop();

        COMMON = builder.build();
    }

}
