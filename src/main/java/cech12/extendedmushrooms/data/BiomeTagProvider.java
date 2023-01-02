package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class BiomeTagProvider extends BiomeTagsProvider {

    public BiomeTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> generatorIn, ExistingFileHelper existingFileHelper) {
        super(packOutput, generatorIn, ExtendedMushrooms.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider lookupProvider) {
        tag(ModTags.Biomes.HAS_MUSHROOMS)
                .addTag(BiomeTags.IS_OVERWORLD)
                .addTag(BiomeTags.IS_NETHER);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Extended Mushrooms Biome Tags";
    }

}
