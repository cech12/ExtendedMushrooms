package cech12.extendedmushrooms.api.entity;

import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ExtendedMushroomsEntityTypes {

    public static EntityType<? extends Entity> MUSHROOM_SHEEP = EntityType.Builder.create(MushroomSheepEntity::new, EntityClassification.CREATURE).size(0.9F, 1.3F).build("mushroom_sheep");

}
