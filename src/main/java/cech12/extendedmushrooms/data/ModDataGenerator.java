package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.data.recipes.RecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        if (evt.includeServer()) {
            DataGenerator gen = evt.getGenerator();
            evt.getGenerator().addProvider(new BlockLootProvider(gen));
            evt.getGenerator().addProvider(new BlockModelProvider(gen, evt.getExistingFileHelper()));
            evt.getGenerator().addProvider(new BlockStateProvider(gen, evt.getExistingFileHelper()));
            BlockTagProvider blockTags = new BlockTagProvider(gen);
            evt.getGenerator().addProvider(blockTags);
            evt.getGenerator().addProvider(new ItemModelProvider(gen, evt.getExistingFileHelper()));
            evt.getGenerator().addProvider(new ItemTagProvider(gen, blockTags));
            evt.getGenerator().addProvider(new RecipeProvider(gen));
        }
    }

}
