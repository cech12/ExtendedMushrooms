package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.data.recipes.RecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        if (evt.includeServer()) {
            CompletableFuture<HolderLookup.Provider> lookupProvider = evt.getLookupProvider();
            PackOutput packOutput = evt.getGenerator().getPackOutput();
            ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
            evt.getGenerator().addProvider(true, new BlockLootProvider(packOutput, lookupProvider));
            evt.getGenerator().addProvider(true, new BlockModelProvider(packOutput, existingFileHelper));
            evt.getGenerator().addProvider(true, new BlockStateProvider(packOutput, existingFileHelper));
            BlockTagProvider blockTags = new BlockTagProvider(packOutput, lookupProvider, existingFileHelper);
            evt.getGenerator().addProvider(true, blockTags);
            evt.getGenerator().addProvider(true, new EntityLootProvider(packOutput, lookupProvider));
            evt.getGenerator().addProvider(true, new ItemModelProvider(packOutput, existingFileHelper));
            evt.getGenerator().addProvider(true, new ItemTagProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
            evt.getGenerator().addProvider(true, new RecipeProvider(packOutput));
            evt.getGenerator().addProvider(true, new ConfiguredFeatureProvider(packOutput, lookupProvider));
            evt.getGenerator().addProvider(true, new PlacedFeatureProvider(packOutput, lookupProvider));
            evt.getGenerator().addProvider(true, new BiomeTagProvider(packOutput, lookupProvider, existingFileHelper));
            evt.getGenerator().addProvider(true, new BiomeModifierProvider(packOutput, lookupProvider));
            evt.getGenerator().addProvider(true, new ForgeAdvancementProvider(packOutput, lookupProvider, existingFileHelper, List.of(new AdvancementGenerator())));
        }
    }

}
