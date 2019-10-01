package cech12.extendedmushrooms;

import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExtendedMushrooms.MOD_ID)
public class ExtendedMushrooms {

    public static final String MOD_ID = "extendedmushrooms";

    //private static final Logger LOGGER = LogManager.getLogger();

    public ExtendedMushrooms() {
        FMLJavaModLoadingContext.get().getModEventBus().register(ModBlocks.class);
        FMLJavaModLoadingContext.get().getModEventBus().register(ModItems.class);
    }

}
