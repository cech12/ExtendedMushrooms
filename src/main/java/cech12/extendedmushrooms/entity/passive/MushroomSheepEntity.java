package cech12.extendedmushrooms.entity.passive;

import cech12.extendedmushrooms.init.ModEntityTypes;
import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.config.Config;
import cech12.extendedmushrooms.entity.ai.goal.EatMyceliumGoal;
import cech12.extendedmushrooms.item.MushroomType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MushroomSheepEntity extends Sheep {

    private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(MushroomSheepEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ItemStack> MUSHROOM_TYPE = SynchedEntityData.defineId(MushroomSheepEntity.class, EntityDataSerializers.ITEM_STACK);

    private int sheepTimer;
    private EatMyceliumGoal eatMyceliumGoal;

    public MushroomSheepEntity(EntityType<? extends Sheep> type, Level worldIn) {
        super(type, worldIn);
    }

    /**
     * Replaces the given sheep entity with a mushroom sheep entity.
     */
    public static void replaceSheep(@Nonnull Sheep sheep, @Nullable MushroomType mushroomType) {
        sheep.setSpeed(0);
        Level world = sheep.level;
        //create mushroom sheep
        MushroomSheepEntity mushroomSheep = ModEntityTypes.MUSHROOM_SHEEP.get().create(world);
        if (mushroomSheep != null && world instanceof ServerLevel) {
            mushroomSheep.copyPosition(sheep);
            mushroomSheep.finalizeSpawn((ServerLevel)world, world.getCurrentDifficultyAt(sheep.blockPosition()), MobSpawnType.CONVERSION, null, null);
            mushroomSheep.setAge(sheep.getAge());
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
            sheep.remove(RemovalReason.DISCARDED);
            world.addFreshEntity(mushroomSheep);
            mushroomSheep.playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, 2.0F, 1.0F);
        }
    }

    @Override
    protected void registerGoals() {
        //only for super.updateAITasks() to avoid NullPointerException
        this.eatBlockGoal = new EatBlockGoal(this); //changed field eatGrassGoal in accesstransformer.cfg
        this.eatMyceliumGoal = new EatMyceliumGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        //add temptGoal for each mushroom
        Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(Tags.Items.MUSHROOMS).forEach(mushroom ->
                this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(mushroom), false))
        );
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, this.eatMyceliumGoal);
        // EatMushroomGoal is added via LivingSpawnEvent.EnteringChunk event!
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    protected void customServerAiStep() {
        this.sheepTimer = this.eatMyceliumGoal.getEatingTimer();
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        super.aiStep();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHEARED, false);
        this.entityData.define(MUSHROOM_TYPE, new ItemStack(MushroomType.byId(0).getItem()));
    }

    @Nonnull
    @Override
    public ResourceLocation getDefaultLootTable() {
        if (this.isSheared()) {
            return this.getType().getDefaultLootTable();
        } else {
            return this.getMushroomType().getSheepLootTable();
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getHeadEatPositionScale(float p_70894_1_) {
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
    public float getHeadEatAngleScale(float p_70890_1_) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f = ((float)(this.sheepTimer - 4) - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5F) + 0.21991149F * Mth.sin(f * 28.7F);
        } else {
            return this.sheepTimer > 0 ? ((float)Math.PI / 5F) : this.getXRot() * ((float)Math.PI / 180F);
        }
    }

    @Override
    public @Nonnull InteractionResult mobInteract(Player player, @Nonnull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        InteractionResult superResult = super.mobInteract(player, hand);
        if (superResult.consumesAction() && Config.SHEEP_ABSORB_MUSHROOM_TYPE_ENABLED.get() && itemStack.is(Tags.Items.MUSHROOMS)) {
            //change mushroom type
            MushroomType type = MushroomType.byItemOrNull(itemStack.getItem());
            if (type != null && type != this.getMushroomType()) {
                this.setMushroomType(type);
                this.activateMushroomEffect(type);
                return InteractionResult.SUCCESS;
            }
        }
        return superResult;
    }

    /**
     * Activate mushroom effects like poison on this sheep.
     * When a given MushroomType has no effect, nothing happens.
     */
    public void activateMushroomEffect(MushroomType mushroomType) {
        if (new ItemStack(mushroomType.getItem()).is(ModTags.ForgeItems.MUSHROOMS_POISONOUS)) {
            this.addEffect(new MobEffectInstance(MobEffects.POISON, 200));
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Tags.Items.MUSHROOMS);
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Sheared", this.isSheared());
        compound.putInt("Mushroom", this.getMushroomType().getId());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSheared(compound.getBoolean("Sheared"));
        this.setMushroomType(MushroomType.byId(compound.getInt("Mushroom")));
    }

    /**
     * Gets the wool color of this sheep.
     */
    public MushroomType getMushroomType() {
        return MushroomType.byItem(this.entityData.get(MUSHROOM_TYPE).getItem());
    }

    /**
     * Sets the wool color of this sheep
     */
    public void setMushroomType(MushroomType mushroomType) {
        this.entityData.set(MUSHROOM_TYPE, new ItemStack(mushroomType.getItem()));
        //set also the DyeColor to be compatible with other mods
        super.setColor(mushroomType.getColor());
    }

    @Nonnull
    @Override
    public DyeColor getColor() {
        //get the DyeColor from mushroom type to be compatible with other mods
        return this.getMushroomType().getColor();
    }

    /**
     * @deprecated Method is disabled. Use setMushroomType to set the color.
     */
    @Deprecated
    @Override
    public void setColor(@Nonnull DyeColor color) {
        //disable setting the fleece color for mushroom sheeps
    }

    /**
     * returns true if a sheeps wool has been sheared
     */
    @Override
    public boolean isSheared() {
        return this.entityData.get(SHEARED);
    }

    /**
     * make a sheep sheared if set to true
     */
    @Override
    public void setSheared(boolean sheared) {
        this.entityData.set(SHEARED, sheared);
        super.setSheared(sheared); //set sheared value of super class to be compatible with other mods
    }

    /**
     * Chooses a "vanilla" sheep color based on the provided random.
     */
    public static MushroomType getRandomMushroomType(RandomSource random) {
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
    public Sheep getBreedOffspring(@Nonnull ServerLevel world, @Nonnull AgeableMob ageable) {
        if (ageable instanceof MushroomSheepEntity) {
            // only create a mushroom sheep, when both parents are mushroom sheeps.
            MushroomSheepEntity child = ModEntityTypes.MUSHROOM_SHEEP.get().create(world);
            if (child != null) {
                child.setMushroomType(this.getMushroomTypeMixFromParents(this, (MushroomSheepEntity) ageable));
                return child;
            }
        } else {
            //when other entity is no mushroom sheep, create a normal sheep with its color.
            Sheep child = EntityType.SHEEP.create(world);
            if (child != null) {
                child.setColor(((Sheep) ageable).getColor());
                return child;
            }
        }
        return null;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, @Nonnull DifficultyInstance difficultyIn, @Nonnull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        this.setMushroomType(getRandomMushroomType(worldIn.getRandom()));
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    /**
     * Attempts to mix both parent sheep to come up with a mixed dye color.
     */
    private MushroomType getMushroomTypeMixFromParents(MushroomSheepEntity father, MushroomSheepEntity mother) {
        if (father.getRandom().nextBoolean()) {
            return father.getMushroomType();
        } else {
            return mother.getMushroomType();
        }
    }

    @Override
    @Nonnull
    public List<ItemStack> onSheared(@Nullable Player player, @Nonnull ItemStack item, @Nonnull Level world, @Nonnull BlockPos pos, int fortune) {
        List<ItemStack> ret = new ArrayList<>();
        if (!this.level.isClientSide) {
            this.setSheared(true);
            int i = 1 + this.random.nextInt(3);
            for(int j = 0; j < i; ++j) {
                ret.add(new ItemStack(this.getMushroomType().getCapBlock()));
            }
        }
        this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }

}
