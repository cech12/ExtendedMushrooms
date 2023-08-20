package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.advancements.criterion.MushroomSheepConversionTrigger;
import cech12.extendedmushrooms.init.ModBlocks;
import cech12.extendedmushrooms.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class AdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {

    public AdvancementGenerator() {
    }

    @Override
    public void generate(@Nonnull HolderLookup.Provider registries, @Nonnull Consumer<Advancement> saver, @Nonnull ExistingFileHelper existingFileHelper) {
        Advancement.Builder.advancement()
                .addCriterion("in_mushroom_fields", PlayerTrigger.TriggerInstance.located(LocationPredicate.inBiome(Biomes.MUSHROOM_FIELDS)))
                .display(new ItemStack(ModItems.MUSHROOM_BOAT.get()), Component.translatable("advancements.mushroom_island.title"), Component.translatable("advancements.mushroom_island.description"), null, FrameType.GOAL, true, true, true)
                .save(saver, new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_island"), existingFileHelper);
        Advancement.Builder.advancement()
                .addCriterion("enter_fairy_ring", EnterBlockTrigger.TriggerInstance.entersBlock(ModBlocks.FAIRY_RING.get()))
                .display(new ItemStack(ModItems.MUSHROOM_SPORES.get()), Component.translatable("advancements.fairy_ring.title"), Component.translatable("advancements.fairy_ring.description"), null, FrameType.GOAL, true, true, true)
                .save(saver, new ResourceLocation(ExtendedMushrooms.MOD_ID, "fairy_ring"), existingFileHelper);
        Advancement.Builder.advancement()
                .addCriterion("mushroom_sheep_conversion", MushroomSheepConversionTrigger.TriggerInstance.mushroomSheepConversion())
                .display(new ItemStack(Items.RED_MUSHROOM), Component.translatable("advancements.mushroom_sheep_conversion.title"), Component.translatable("advancements.mushroom_sheep_conversion.description"), null, FrameType.GOAL, true, true, true)
                .save(saver, new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_sheep_conversion"), existingFileHelper);
    }

}
