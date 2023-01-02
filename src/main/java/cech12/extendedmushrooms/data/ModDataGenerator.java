package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.data.recipes.RecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        if (evt.includeServer()) {
            CompletableFuture<HolderLookup.Provider> lookupProvider = evt.getLookupProvider();
            DataGenerator gen = evt.getGenerator();
            PackOutput packOutput = gen.getPackOutput();
            ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
            evt.getGenerator().addProvider(true, new BlockLootProvider(gen));
            evt.getGenerator().addProvider(true, new BlockModelProvider(packOutput, existingFileHelper));
            evt.getGenerator().addProvider(true, new BlockStateProvider(packOutput, existingFileHelper));
            BlockTagProvider blockTags = new BlockTagProvider(packOutput, lookupProvider, existingFileHelper);
            evt.getGenerator().addProvider(true, blockTags);
            evt.getGenerator().addProvider(true, new EntityLootProvider(gen));
            evt.getGenerator().addProvider(true, new ItemModelProvider(packOutput, existingFileHelper));
            evt.getGenerator().addProvider(true, new ItemTagProvider(packOutput, lookupProvider, blockTags, existingFileHelper));
            evt.getGenerator().addProvider(true, new RecipeProvider(packOutput));
            evt.getGenerator().addProvider(true, new BiomeTagProvider(packOutput, lookupProvider, existingFileHelper));
            BiomeModifierProvider.generateBiomeModifiers(evt);
        }
    }

}
