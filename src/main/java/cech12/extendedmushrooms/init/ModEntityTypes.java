package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.client.renderer.entity.MushroomBoatRenderer;
import cech12.extendedmushrooms.client.renderer.entity.MushroomSheepRenderer;
import cech12.extendedmushrooms.entity.item.MushroomBoatEntity;
import cech12.extendedmushrooms.entity.item.MushroomChestBoatEntity;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid= ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ExtendedMushrooms.MOD_ID);

    public static RegistryObject<EntityType<MushroomBoatEntity>> MUSHROOM_BOAT = ENTITY_TYPES.register("mushroom_boat", () -> EntityType.Builder.of(MushroomBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build(ExtendedMushrooms.MOD_ID + ":mushroom_boat"));
    public static RegistryObject<EntityType<MushroomChestBoatEntity>> MUSHROOM_CHEST_BOAT = ENTITY_TYPES.register("mushroom_chest_boat", () -> EntityType.Builder.of(MushroomChestBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build(ExtendedMushrooms.MOD_ID + ":mushroom_chest_boat"));
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
        EntityRenderers.register(MUSHROOM_BOAT.get(), (context) -> new MushroomBoatRenderer(context, false));
        EntityRenderers.register(MUSHROOM_CHEST_BOAT.get(), (context) -> new MushroomBoatRenderer(context, true));
        EntityRenderers.register(MUSHROOM_SHEEP.get(), MushroomSheepRenderer::new);
    }
}
