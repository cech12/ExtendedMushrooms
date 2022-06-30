package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.api.entity.ExtendedMushroomsEntityTypes;
import cech12.extendedmushrooms.entity.item.MushroomBoatEntity;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class MushroomBoatItem extends Item {

    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final MushroomWoodType type;

    public MushroomBoatItem(MushroomWoodType type) {
        super((new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_TRANSPORTATION));
        this.type = type;
        DispenserBlock.registerBehavior(this, new DispenseBehavior(type));
    }

    @Nonnull
    public InteractionResultHolder<ItemStack> use(@Nonnull Level worldIn, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.ANY);
        if (raytraceresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vec3d = playerIn.getViewVector(1.0F);
            List<Entity> list = worldIn.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vec3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 vec3d1 = playerIn.getEyePosition(1.0F);

                for(Entity entity : list) {
                    AABB axisalignedbb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (axisalignedbb.contains(vec3d1)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == HitResult.Type.BLOCK) {
                MushroomBoatEntity boat = (MushroomBoatEntity) ExtendedMushroomsEntityTypes.MUSHROOM_BOAT.create(worldIn);
                if (boat == null) {
                    return InteractionResultHolder.pass(itemstack);
                }
                boat.absMoveTo(raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z, playerIn.getYRot(), 0);
                boat.setMushroomWoodType(this.type);
                if (!worldIn.noCollision(boat, boat.getBoundingBox().inflate(-0.1D))) {
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (!worldIn.isClientSide) {
                        worldIn.addFreshEntity(boat);
                    }
                    if (!playerIn.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.success(itemstack);
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }

    public static class DispenseBehavior extends DefaultDispenseItemBehavior {

        private final DefaultDispenseItemBehavior dispenseItemBehaviour = new DefaultDispenseItemBehavior();
        private final MushroomWoodType type;

        public DispenseBehavior(MushroomWoodType type) {
            this.type = type;
        }

        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        @Nonnull
        public ItemStack execute(BlockSource source, @Nonnull ItemStack stack) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            Level world = source.getLevel();
            double d0 = source.x() + (double)((float)direction.getStepX() * 1.125F);
            double d1 = source.y() + (double)((float)direction.getStepY() * 1.125F);
            double d2 = source.z() + (double)((float)direction.getStepZ() * 1.125F);
            BlockPos blockpos = source.getPos().relative(direction);
            double d3;
            if (world.getFluidState(blockpos).is(FluidTags.WATER)) {
                d3 = 1.0D;
            } else {
                if (!world.getBlockState(blockpos).isAir() || !world.getFluidState(blockpos.below()).is(FluidTags.WATER)) {
                    return this.dispenseItemBehaviour.dispense(source, stack);
                }

                d3 = 0.0D;
            }
            MushroomBoatEntity boat = (MushroomBoatEntity) ExtendedMushroomsEntityTypes.MUSHROOM_BOAT.create(world);
            if (boat != null) {
                boat.absMoveTo(d0, d1 + d3, d2, direction.toYRot(), 0);
                boat.setMushroomWoodType(this.type);
                world.addFreshEntity(boat);
                stack.shrink(1);
            }
            return stack;
        }

        /**
         * Play the dispense sound from the specified block.
         */
        protected void playSound(BlockSource source) {
            source.getLevel().levelEvent(1000, source.getPos(), 0);
        }
    }

}
