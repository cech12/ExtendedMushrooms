package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.client.renderer.entity.MushroomBoatRenderer;
import cech12.extendedmushrooms.client.renderer.entity.MushroomSheepRenderer;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.entity.item.MushroomBoatEntity;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ExtendedMushrooms.MOD_ID);

    public static RegistryObject<EntityType<MushroomBoatEntity>> MUSHROOM_BOAT = ENTITY_TYPES.register("mushroom_boat", () -> EntityType.Builder.of(MushroomBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build(ExtendedMushrooms.MOD_ID + ":mushroom_boat"));
    public static RegistryObject<EntityType<MushroomSheepEntity>> MUSHROOM_SHEEP = ENTITY_TYPES.register("mushroom_sheep", () -> EntityType.Builder.of(MushroomSheepEntity::new, MobCategory.CREATURE).sized(0.9F, 1.3F).build("mushroom_sheep"));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MUSHROOM_SHEEP.get(), Sheep.createAttributes().build());
    }

    /**
     * Setup renderers for entities. Is called at mod initialisation.
     */
    @OnlyIn(Dist.CLIENT)
    public static void setupRenderers() {
        EntityRenderers.register(MUSHROOM_BOAT.get(), MushroomBoatRenderer::new);
        EntityRenderers.register(MUSHROOM_SHEEP.get(), MushroomSheepRenderer::new);
    }

    /**
     * Add registered entities to biomes. Is called at mod initialisation.
     */
    public static void addEntitiesToBiomes(BiomeLoadingEvent event) {
        //add Mushroom Sheep to Mushroom Biomes
        if (event.getCategory().equals(Biome.BiomeCategory.MUSHROOM)) {
            if (Config.MUSHROOM_SHEEP_ENABLED.get()) {
                event.getSpawns().addSpawn(MobCategory.CREATURE,
                        new MobSpawnSettings.SpawnerData(MUSHROOM_SHEEP.get(),
                                Config.MUSHROOM_SHEEP_SPAWN_WEIGHT.get(),
                                Config.MUSHROOM_SHEEP_SPAWN_MIN_GROUP_COUNT.get(),
                                Config.MUSHROOM_SHEEP_SPAWN_MAX_GROUP_COUNT.get()));
            }
        }
    }
}
