package cech12.extendedmushrooms.entity.item;

import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MushroomBoatEntity extends BoatEntity {

    private static final DataParameter<Integer> MUSHROOM_WOOD_TYPE = EntityDataManager.createKey(MushroomBoatEntity.class, DataSerializers.VARINT);

    public MushroomBoatEntity(EntityType<? extends MushroomBoatEntity> p_i50129_1_, World p_i50129_2_) {
        super(p_i50129_1_, p_i50129_2_);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(MUSHROOM_WOOD_TYPE, MushroomWoodType.MUSHROOM.getId());
    }

    @Nonnull
    @Override
    public Item getItemBoat() {
        return this.getMushroomWoodType().getBoatItem();
    }

    @Nullable
    @Override
    public ItemEntity entityDropItem(IItemProvider itemIn) {
        //replace planks
        if (itemIn.asItem().isIn(ItemTags.PLANKS)) {
            return super.entityDropItem(this.getMushroomWoodType().getPlanksBlock());
        }
        return super.entityDropItem(itemIn);
    }

    @Override
    protected void writeAdditional(@Nonnull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("MushroomWoodType", this.getMushroomWoodType().getName());
    }

    @Override
    protected void readAdditional(@Nonnull CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("MushroomWoodType", 8)) {
            this.setMushroomWoodType(MushroomWoodType.byName(compound.getString("MushroomWoodType")));
        }
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        //important!!!
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Deprecated
    @Override
    public void setBoatType(@Nonnull BoatEntity.Type boatType) {
        //deactivate boat type
    }

    @Deprecated
    @Nonnull
    @Override
    public BoatEntity.Type getBoatType() {
        //deactivate boat type
        return Type.OAK;
    }

    public void setMushroomWoodType(MushroomWoodType type) {
        this.dataManager.set(MUSHROOM_WOOD_TYPE, type.getId());
    }

    public MushroomWoodType getMushroomWoodType() {
        return MushroomWoodType.byId(this.dataManager.get(MUSHROOM_WOOD_TYPE));
    }

}
