package cech12.extendedmushrooms.entity.passive;

import cech12.extendedmushrooms.api.entity.ExtendedMushroomsEntityTypes;
import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.entity.ai.goal.EatMyceliumGoal;
import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MushroomSheepEntity extends SheepEntity {

    private static final DataParameter<Boolean> SHEARED = EntityDataManager.createKey(MushroomSheepEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<ItemStack> MUSHROOM_TYPE = EntityDataManager.createKey(MushroomSheepEntity.class, DataSerializers.ITEMSTACK);

    private int sheepTimer;
    private EatMyceliumGoal eatMyceliumGoal;

    public MushroomSheepEntity(EntityType<? extends SheepEntity> type, World worldIn) {
        super(type, worldIn);
    }

    /**
     * Replaces the given sheep entity with a mushroom sheep entity.
     */
    public static void replaceSheep(@Nonnull SheepEntity sheep, @Nullable MushroomType mushroomType) {
        sheep.setAIMoveSpeed(0);
        World world = sheep.world;
        //create mushroom sheep
        MushroomSheepEntity mushroomSheep = (MushroomSheepEntity) ExtendedMushroomsEntityTypes.MUSHROOM_SHEEP.create(world);
        if (mushroomSheep != null && world instanceof ServerWorld) {
            mushroomSheep.copyLocationAndAnglesFrom(sheep);
            mushroomSheep.onInitialSpawn((ServerWorld)world, world.getDifficultyForLocation(sheep.getPosition()), SpawnReason.CONVERSION, null, null);
            mushroomSheep.setGrowingAge(sheep.getGrowingAge());
            if (sheep.hasCustomName()) {
                mushroomSheep.setCustomName(sheep.getCustomName());
                mushroomSheep.setCustomNameVisible(sheep.isCustomNameVisible());
            }
            mushroomSheep.setHealth(sheep.getHealth());
            //set mushroom type
            if (mushroomType != null) {
                mushroomSheep.setMushroomType(mushroomType);
                mushroomSheep.activateMushroomEffect(mushroomType);
            }
            //replace sheep with new mushroom sheep
            sheep.remove();
            world.addEntity(mushroomSheep);
            mushroomSheep.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 2.0F, 1.0F);
        }
    }

    @Override
    protected void registerGoals() {
        //only for super.updateAITasks() to avoid NullPointerException
        this.eatGrassGoal = new EatGrassGoal(this); //changed field eatGrassGoal in accesstransformer.cfg
        this.eatMyceliumGoal = new EatMyceliumGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        //add temptGoal for each mushroom
        for (Item mushroom : Tags.Items.MUSHROOMS.getAllElements()) {
            this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.fromItems(mushroom), false));
        }
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, this.eatMyceliumGoal);
        // EatMushroomGoal is added via LivingSpawnEvent.EnteringChunk event!
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.eatMyceliumGoal.getEatingTimer();
        super.updateAITasks();
    }

    @Override
    public void livingTick() {
        if (this.world.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        super.livingTick();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHEARED, false);
        this.dataManager.register(MUSHROOM_TYPE, new ItemStack(MushroomType.byId(0).getItem()));
    }

    @Nonnull
    @Override
    public ResourceLocation getLootTable() {
        if (this.getSheared()) {
            return this.getType().getLootTable();
        } else {
            return this.getMushroomType().getSheepLootTable();
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float p_70894_1_) {
        if (this.sheepTimer <= 0) {
            return 0.0F;
        } else if (this.sheepTimer >= 4 && this.sheepTimer <= 36) {
            return 1.0F;
        } else {
            return this.sheepTimer < 4 ? ((float)this.sheepTimer - p_70894_1_) / 4.0F : -((float)(this.sheepTimer - 40) - p_70894_1_) / 4.0F;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float p_70890_1_) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f = ((float)(this.sheepTimer - 4) - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.sheepTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * ((float)Math.PI / 180F);
        }
    }

    @Override
    public @Nonnull ActionResultType getEntityInteractionResult(PlayerEntity player, @Nonnull Hand hand) {
        Item item = player.getHeldItem(hand).getItem();
        ActionResultType superResult = super.getEntityInteractionResult(player, hand);
        if (superResult.isSuccessOrConsume() && Config.SHEEP_ABSORB_MUSHROOM_TYPE_ENABLED.get() && item.isIn(Tags.Items.MUSHROOMS)) {
            //change mushroom type
            MushroomType type = MushroomType.byItemOrNull(item);
            if (type != null && type != this.getMushroomType()) {
                this.setMushroomType(type);
                this.activateMushroomEffect(type);
                return ActionResultType.SUCCESS;
            }
        }
        return superResult;
    }

    /**
     * Activate mushroom effects like poison on this sheep.
     * When a given MushroomType has no effect, nothing happens.
     */
    public void activateMushroomEffect(MushroomType mushroomType) {
        if (mushroomType.getItem().isIn(ModTags.ForgeItems.MUSHROOMS_POISONOUS)) {
            this.addPotionEffect(new EffectInstance(Effects.POISON, 200));
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem().isIn(Tags.Items.MUSHROOMS);
    }

    @Override
    public void writeAdditional(@Nonnull CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Sheared", this.getSheared());
        compound.putInt("Mushroom", this.getMushroomType().getId());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditional(@Nonnull CompoundNBT compound) {
        super.readAdditional(compound);
        this.setSheared(compound.getBoolean("Sheared"));
        this.setMushroomType(MushroomType.byId(compound.getInt("Mushroom")));
    }

    /**
     * Gets the wool color of this sheep.
     */
    public MushroomType getMushroomType() {
        return MushroomType.byItem(this.dataManager.get(MUSHROOM_TYPE).getItem());
    }

    /**
     * Sets the wool color of this sheep
     */
    public void setMushroomType(MushroomType mushroomType) {
        this.dataManager.set(MUSHROOM_TYPE, new ItemStack(mushroomType.getItem()));
        //set also the DyeColor to be compatible with other mods
        super.setFleeceColor(mushroomType.getColor());
    }

    @Nonnull
    @Override
    public DyeColor getFleeceColor() {
        //get the DyeColor from mushroom type to be compatible with other mods
        return this.getMushroomType().getColor();
    }

    /**
     * @deprecated Method is disabled. Use setMushroomType to set the color.
     */
    @Deprecated
    @Override
    public void setFleeceColor(@Nonnull DyeColor color) {
        //disable setting the fleece color for mushroom sheeps
    }

    /**
     * returns true if a sheeps wool has been sheared
     */
    @Override
    public boolean getSheared() {
        return this.dataManager.get(SHEARED);
    }

    /**
     * make a sheep sheared if set to true
     */
    @Override
    public void setSheared(boolean sheared) {
        this.dataManager.set(SHEARED, sheared);
        super.setSheared(sheared); //set sheared value of super class to be compatible with other mods
    }

    /**
     * Chooses a "vanilla" sheep color based on the provided random.
     */
    public static MushroomType getRandomMushroomType(Random random) {
        if (random.nextInt(100) < 5) {
            MushroomType[] specialTypes = MushroomType.getSpecialTypes();
            return specialTypes[random.nextInt(specialTypes.length)];
        } else {
            if (random.nextBoolean()) {
                return MushroomType.BROWN_MUSHROOM;
            } else {
                return MushroomType.RED_MUSHROOM;
            }
        }
    }

    @Override
    public SheepEntity createChild(@Nonnull ServerWorld world, @Nonnull AgeableEntity ageable) {
        if (ageable instanceof MushroomSheepEntity) {
            // only create a mushroom sheep, when both parents are mushroom sheeps.
            MushroomSheepEntity child = (MushroomSheepEntity) ExtendedMushroomsEntityTypes.MUSHROOM_SHEEP.create(world);
            if (child != null) {
                child.setMushroomType(this.getMushroomTypeMixFromParents(this, (MushroomSheepEntity) ageable));
                return child;
            }
        } else {
            //when other entity is no mushroom sheep, create a normal sheep with its color.
            SheepEntity child = EntityType.SHEEP.create(world);
            if (child != null) {
                child.setFleeceColor(((SheepEntity) ageable).getFleeceColor());
                return child;
            }
        }
        return null;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, @Nonnull DifficultyInstance difficultyIn, @Nonnull SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setMushroomType(getRandomMushroomType(worldIn.getRandom()));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    /**
     * Attempts to mix both parent sheep to come up with a mixed dye color.
     */
    private MushroomType getMushroomTypeMixFromParents(MushroomSheepEntity father, MushroomSheepEntity mother) {
        if (father.getRNG().nextBoolean()) {
            return father.getMushroomType();
        } else {
            return mother.getMushroomType();
        }
    }

    @Override
    @Nonnull
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, @Nonnull World world, @Nonnull BlockPos pos, int fortune) {
        List<ItemStack> ret = new ArrayList<>();
        if (!this.world.isRemote) {
            this.setSheared(true);
            int i = 1 + this.rand.nextInt(3);
            for(int j = 0; j < i; ++j) {
                ret.add(new ItemStack(this.getMushroomType().getCapBlock()));
            }
        }
        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }

}
