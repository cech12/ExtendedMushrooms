package cech12.extendedmushrooms.entity.item;

import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MushroomChestBoatEntity extends ChestBoat implements MushroomWoodTypable {

    private static final EntityDataAccessor<Integer> MUSHROOM_WOOD_TYPE = SynchedEntityData.defineId(MushroomChestBoatEntity.class, EntityDataSerializers.INT);

    public MushroomChestBoatEntity(EntityType<? extends MushroomChestBoatEntity> p_i50129_1_, Level p_i50129_2_) {
        super(p_i50129_1_, p_i50129_2_);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MUSHROOM_WOOD_TYPE, MushroomWoodType.MUSHROOM.getId());
    }

    @Nonnull
    @Override
    public Item getDropItem() {
        return this.getMushroomWoodType().getChestBoatItem();
    }

    @Nullable
    @Override
    public ItemEntity spawnAtLocation(ItemLike itemIn) {
        //replace planks
        if (itemIn.asItem().getDefaultInstance().is(ItemTags.PLANKS)) {
            return super.spawnAtLocation(this.getMushroomWoodType().getPlanksBlock());
        }
        return super.spawnAtLocation(itemIn);
    }

    @Override
    protected void addAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("MushroomWoodType", this.getMushroomWoodType().getSerializedName());
    }

    @Override
    protected void readAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("MushroomWoodType", 8)) {
            this.setMushroomWoodType(MushroomWoodType.byName(compound.getString("MushroomWoodType")));
        }
    }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        //important!!!
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Deprecated
    @Override
    public void setVariant(@Nonnull Type boatType) {
        //deactivate boat type
    }

    @Deprecated
    @Nonnull
    @Override
    public Type getVariant() {
        //deactivate boat type
        return Type.OAK;
    }

    public void setMushroomWoodType(MushroomWoodType type) {
        this.entityData.set(MUSHROOM_WOOD_TYPE, type.getId());
    }

    public MushroomWoodType getMushroomWoodType() {
        return MushroomWoodType.byId(this.entityData.get(MUSHROOM_WOOD_TYPE));
    }

}
