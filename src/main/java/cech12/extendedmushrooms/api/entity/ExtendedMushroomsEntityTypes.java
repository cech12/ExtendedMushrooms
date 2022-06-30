package cech12.extendedmushrooms.api.entity;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.entity.item.MushroomBoatEntity;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;

public class ExtendedMushroomsEntityTypes {

    public static EntityType<? extends Mob> MUSHROOM_SHEEP = EntityType.Builder.of(MushroomSheepEntity::new, MobCategory.CREATURE).sized(0.9F, 1.3F).build("mushroom_sheep");

    public static EntityType<? extends Entity> MUSHROOM_BOAT = EntityType.Builder.of(MushroomBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build(ExtendedMushrooms.MOD_ID + ":mushroom_boat");

}
