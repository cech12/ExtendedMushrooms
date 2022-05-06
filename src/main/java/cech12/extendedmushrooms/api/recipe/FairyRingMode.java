package cech12.extendedmushrooms.api.recipe;

public enum FairyRingMode {

    NORMAL(0, "normal"),
    FAIRY(1, "fairy"),
    WITCH(2, "witch");

    private final int id;
    private final String name;

    FairyRingMode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static FairyRingMode byName(String modeName) {
        for (FairyRingMode mode : FairyRingMode.values()) {
            if (mode.name.equals(modeName)) {
                return mode;
            }
        }
        return null;
    }

}
