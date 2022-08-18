package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class BiomeTagProvider extends BiomeTagsProvider {

    public BiomeTagProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, ExtendedMushrooms.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
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
