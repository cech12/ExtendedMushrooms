package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.data.recipes.RecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        if (evt.includeServer()) {
            DataGenerator gen = evt.getGenerator();
            ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
            evt.getGenerator().addProvider(true, new BlockLootProvider(gen));
            evt.getGenerator().addProvider(true, new BlockModelProvider(gen, existingFileHelper));
            evt.getGenerator().addProvider(true, new BlockStateProvider(gen, existingFileHelper));
            BlockTagProvider blockTags = new BlockTagProvider(gen, existingFileHelper);
            evt.getGenerator().addProvider(true, blockTags);
            evt.getGenerator().addProvider(true, new EntityLootProvider(gen));
            evt.getGenerator().addProvider(true, new ItemModelProvider(gen, existingFileHelper));
            evt.getGenerator().addProvider(true, new ItemTagProvider(gen, blockTags, existingFileHelper));
            evt.getGenerator().addProvider(true, new RecipeProvider(gen));
            evt.getGenerator().addProvider(true, new BiomeTagProvider(gen, existingFileHelper));
            BiomeModifierProvider.generateBiomeModifiers(evt);
        }
    }

}
