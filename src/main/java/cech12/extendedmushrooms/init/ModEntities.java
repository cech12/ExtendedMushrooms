package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.api.entity.ExtendedMushroomsEntityTypes;
import cech12.extendedmushrooms.client.renderer.entity.MushroomSheepRenderer;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import static cech12.extendedmushrooms.api.entity.ExtendedMushroomsEntityTypes.*;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        registerEntity("mushroom_sheep", MUSHROOM_SHEEP);
    }

    private static <T extends Entity> EntityType<T> registerEntity(String key, EntityType<T> entityType) {
        entityType.setRegistryName(new ResourceLocation(ExtendedMushrooms.MOD_ID, key));
        ForgeRegistries.ENTITIES.register(entityType);
        return entityType;
    }

    /**
     * Setup renderers for entities. Is called at mod initialisation.
     */
    @OnlyIn(Dist.CLIENT)
    public static void setupRenderers() {
        RenderingRegistry.registerEntityRenderingHandler((EntityType<MushroomSheepEntity>) MUSHROOM_SHEEP, MushroomSheepRenderer::new);
    }

    /**
     * Add registered entities to biomes. Is called at mod initialisation.
     */
    public static void addEntitiesToBiomes() {
        //add Mushroom Sheep to Mushroom Biomes
        Biome[] biomes = {Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE};
        for (Biome biome : biomes) {
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ExtendedMushroomsEntityTypes.MUSHROOM_SHEEP, 8, 4, 8));
        }
    }
}
