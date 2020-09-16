package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.data.recipes.RecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        if (evt.includeServer()) {
            DataGenerator gen = evt.getGenerator();
            ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
            evt.getGenerator().addProvider(new BlockLootProvider(gen));
            evt.getGenerator().addProvider(new BlockModelProvider(gen, existingFileHelper));
            evt.getGenerator().addProvider(new BlockStateProvider(gen, existingFileHelper));
            BlockTagProvider blockTags = new BlockTagProvider(gen, existingFileHelper);
            evt.getGenerator().addProvider(blockTags);
            evt.getGenerator().addProvider(new ItemModelProvider(gen, existingFileHelper));
            evt.getGenerator().addProvider(new ItemTagProvider(gen, blockTags, existingFileHelper));
            evt.getGenerator().addProvider(new RecipeProvider(gen));
        }
    }

}
