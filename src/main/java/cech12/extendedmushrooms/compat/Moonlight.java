package cech12.extendedmushrooms.compat;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.resources.ResourceLocation;

public class Moonlight {

    private Moonlight() {}

    public static void init() {
        //register wood types in moonlight library to be compatible with Every Compat
        for (MushroomWoodType woodType : MushroomWoodType.values()) {
            WoodType.Finder finder = WoodType.Finder.simple(new ResourceLocation(ExtendedMushrooms.MOD_ID, woodType.getSerializedName()), woodType.getPlanksBlockId(), woodType.getStemBlockId());
            finder.addChild("wood", woodType.getStemBlockId());
            finder.addChild("stripped_wood", woodType.getStrippedStemBlockId());
            finder.addChild("stripped_log", woodType.getStrippedStemBlockId());
            BlockSetAPI.addBlockTypeFinder(WoodType.class, finder);
        }
    }

}
