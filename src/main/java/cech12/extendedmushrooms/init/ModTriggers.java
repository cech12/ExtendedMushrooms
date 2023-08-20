package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.advancements.criterion.MushroomSheepConversionTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ModTriggers {

    public static final MushroomSheepConversionTrigger MUSHROOM_SHEEP_CONVERSION = CriteriaTriggers.register(new MushroomSheepConversionTrigger());

    public static void init() {
        //must be called to initialize static values
    }
}
