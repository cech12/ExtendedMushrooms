package cech12.extendedmushrooms.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MushroomSporesItem extends Item {

    public MushroomSporesItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getPos();
        Block block = world.getBlockState(blockPos).getBlock();
        ItemStack itemStack = context.getItem();

        // replace dirt with mycelium
        if (block == Blocks.DIRT) {
            world.setBlockState(blockPos, Blocks.MYCELIUM.getDefaultState());
            //remove one item
            itemStack.setCount(itemStack.getCount() - 1);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        World world = target.world;
        // Cow to Mooshroom
        if (!world.isRemote && target instanceof CowEntity && !(target instanceof MooshroomEntity)) {
            target.setAIMoveSpeed(0);
            //create mooshroom
            MooshroomEntity mooshroom = new MooshroomEntity(EntityType.MOOSHROOM, target.world);
            mooshroom.setLocationAndAngles(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
            mooshroom.setHealth(target.getHealth());
            mooshroom.setGrowingAge(((CowEntity) target).getGrowingAge());
            mooshroom.renderYawOffset = target.renderYawOffset;
            //a small explosion
            world.createExplosion(target, target.posX, target.posY, target.posZ, 0.0F, Explosion.Mode.NONE);
            //replace cow with new mooshroom
            target.remove();
            world.addEntity(mooshroom);
            //remove one item
            stack.setCount(stack.getCount() - 1);
            return true;
        }
        return false;
    }

}
