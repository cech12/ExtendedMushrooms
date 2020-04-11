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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class MushroomBoatItem extends Item {

    private static final Predicate<Entity> field_219989_a = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);
    private final MushroomWoodType type;

    public MushroomBoatItem(MushroomWoodType type) {
        super((new Item.Properties()).maxStackSize(1).group(ItemGroup.TRANSPORTATION));
        this.type = type;
        DispenserBlock.registerDispenseBehavior(this, new DispenseBehavior(type));
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.resultPass(itemstack);
        } else {
            Vec3d vec3d = playerIn.getLook(1.0F);
            List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn, playerIn.getBoundingBox().expand(vec3d.scale(5.0D)).grow(1.0D), field_219989_a);
            if (!list.isEmpty()) {
                Vec3d vec3d1 = playerIn.getEyePosition(1.0F);

                for(Entity entity : list) {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().grow(entity.getCollisionBorderSize());
                    if (axisalignedbb.contains(vec3d1)) {
                        return ActionResult.resultPass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                MushroomBoatEntity boat = (MushroomBoatEntity) ExtendedMushroomsEntityTypes.MUSHROOM_BOAT.create(worldIn);
                if (boat == null) {
                    return ActionResult.resultPass(itemstack);
                }
                boat.setPositionAndRotation(raytraceresult.getHitVec().x, raytraceresult.getHitVec().y, raytraceresult.getHitVec().z, playerIn.rotationYaw, 0);
                boat.setMushroomWoodType(this.type);
                if (!worldIn.hasNoCollisions(boat, boat.getBoundingBox().grow(-0.1D))) {
                    return ActionResult.resultFail(itemstack);
                } else {
                    if (!worldIn.isRemote) {
                        worldIn.addEntity(boat);
                    }
                    if (!playerIn.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    playerIn.addStat(Stats.ITEM_USED.get(this));
                    return ActionResult.resultSuccess(itemstack);
                }
            } else {
                return ActionResult.resultPass(itemstack);
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
        public ItemStack dispenseStack(IBlockSource source, @Nonnull ItemStack stack) {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            World world = source.getWorld();
            double d0 = source.getX() + (double)((float)direction.getXOffset() * 1.125F);
            double d1 = source.getY() + (double)((float)direction.getYOffset() * 1.125F);
            double d2 = source.getZ() + (double)((float)direction.getZOffset() * 1.125F);
            BlockPos blockpos = source.getBlockPos().offset(direction);
            double d3;
            if (world.getFluidState(blockpos).isTagged(FluidTags.WATER)) {
                d3 = 1.0D;
            } else {
                if (!world.getBlockState(blockpos).isAir() || !world.getFluidState(blockpos.down()).isTagged(FluidTags.WATER)) {
                    return this.dispenseItemBehaviour.dispense(source, stack);
                }

                d3 = 0.0D;
            }
            MushroomBoatEntity boat = (MushroomBoatEntity) ExtendedMushroomsEntityTypes.MUSHROOM_BOAT.create(world);
            if (boat != null) {
                boat.setPositionAndRotation(d0, d1 + d3, d2, direction.getHorizontalAngle(), 0);
                boat.setMushroomWoodType(this.type);
                world.addEntity(boat);
                stack.shrink(1);
            }
            return stack;
        }

        /**
         * Play the dispense sound from the specified block.
         */
        protected void playDispenseSound(IBlockSource source) {
            source.getWorld().playEvent(1000, source.getBlockPos(), 0);
        }
    }

}
