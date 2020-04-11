package cech12.extendedmushrooms.api.entity;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.entity.item.MushroomBoatEntity;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ExtendedMushroomsEntityTypes {

    public static EntityType<? extends Entity> MUSHROOM_SHEEP = EntityType.Builder.create(MushroomSheepEntity::new, EntityClassification.CREATURE).size(0.9F, 1.3F).build("mushroom_sheep");

    public static EntityType<? extends Entity> MUSHROOM_BOAT = EntityType.Builder.create(MushroomBoatEntity::new, EntityClassification.MISC).size(1.375F, 0.5625F).build(ExtendedMushrooms.MOD_ID + ":mushroom_boat");

}
