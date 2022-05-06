package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.api.entity.ExtendedMushroomsEntityTypes;
import cech12.extendedmushrooms.entity.item.MushroomBoatEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class MushroomBoatItem extends Item {

    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);
    private final MushroomWoodType type;

    public MushroomBoatItem(MushroomWoodType type) {
        super((new Item.Properties()).stacksTo(1).tab(ItemGroup.TAB_TRANSPORTATION));
        this.type = type;
        DispenserBlock.registerBehavior(this, new DispenseBehavior(type));
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    @Nonnull
    public ActionResult<ItemStack> use(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.pass(itemstack);
        } else {
            Vector3d vec3d = playerIn.getViewVector(1.0F);
            List<Entity> list = worldIn.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vec3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vector3d vec3d1 = playerIn.getEyePosition(1.0F);

                for(Entity entity : list) {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (axisalignedbb.contains(vec3d1)) {
                        return ActionResult.pass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                MushroomBoatEntity boat = (MushroomBoatEntity) ExtendedMushroomsEntityTypes.MUSHROOM_BOAT.create(worldIn);
                if (boat == null) {
                    return ActionResult.pass(itemstack);
                }
                boat.absMoveTo(raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z, playerIn.yRot, 0);
                boat.setMushroomWoodType(this.type);
                if (!worldIn.noCollision(boat, boat.getBoundingBox().inflate(-0.1D))) {
                    return ActionResult.fail(itemstack);
                } else {
                    if (!worldIn.isClientSide) {
                        worldIn.addFreshEntity(boat);
                    }
                    if (!playerIn.abilities.instabuild) {
                        itemstack.shrink(1);
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.success(itemstack);
                }
            } else {
                return ActionResult.pass(itemstack);
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
        public ItemStack execute(IBlockSource source, @Nonnull ItemStack stack) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            World world = source.getLevel();
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
        protected void playSound(IBlockSource source) {
            source.getLevel().levelEvent(1000, source.getPos(), 0);
        }
    }

}
