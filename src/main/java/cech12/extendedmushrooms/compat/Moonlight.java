package cech12.extendedmushrooms.compat;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.Optional;

public class Moonlight {

    public static final Logger LOGGER = LogManager.getLogger("ExtendedMushrooms - Moonlight");

    private Moonlight() {}

    public static void init() {
        //register wood types in moonlight library to be compatible with Every Compat
        for (MushroomWoodType woodType : MushroomWoodType.values()) {
            BlockSetAPI.addBlockTypeFinder(WoodType.class, () -> {
                try {
                    //constructor has protected access - use reflections
                    Class<?> cl = Class.forName("net.mehvahdjukaar.moonlight.api.set.wood.WoodType");
                    Constructor<?> cons = cl.getDeclaredConstructor(ResourceLocation.class, Block.class, Block.class);
                    cons.setAccessible(true);
                    WoodType w = (WoodType) cons.newInstance(new ResourceLocation(ExtendedMushrooms.MOD_ID, woodType.getSerializedName()), woodType.getPlanksBlock(), woodType.getStemBlock());
                    //w.addChild("wood", woodType.getStemBlock()); //block should not be added twice
                    //w.addChild("stripped_wood", woodType.getStrippedStemBlock()); //block should not be added twice
                    w.addChild("stripped_log", woodType.getStrippedStemBlock());
                    w.addChild("slab", woodType.getSlabBlock());
                    w.addChild("stairs", woodType.getStairsBlock());
                    w.addChild("fence", woodType.getFenceBlock());
                    w.addChild("fence_gate", woodType.getFenceGateBlock());
                    w.addChild("door", woodType.getDoorBlock());
                    w.addChild("trapdoor", woodType.getTrapdoorBlock());
                    w.addChild("button", woodType.getButtonBlock());
                    w.addChild("pressure_plate", woodType.getPressurePlateBlock());
                    w.addChild("hanging_sign", woodType.getHangingSignBlock());
                    w.addChild("sign", woodType.getSignBlock());
                    //w.addChild("boat", woodType.getBoatItem()); //not available at calling moment
                    //w.addChild("chest_boat", woodType.getChestBoatItem()); //not available at calling moment
                    return Optional.of(w);
                } catch (Exception ex) {
                    LOGGER.error("Failed to load ExtendedMushrooms wood types for Moonlight lib.", ex);
                }
                return Optional.empty();
            });
        }
    }

}
