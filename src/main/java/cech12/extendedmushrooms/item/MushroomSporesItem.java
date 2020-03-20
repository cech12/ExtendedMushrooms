package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MushroomSporesItem extends Item {

    public MushroomSporesItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public @Nonnull ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getPos();
        Block block = world.getBlockState(blockPos).getBlock();
        ItemStack itemStack = context.getItem();

        // replace dirt with mycelium
        if (block == Blocks.DIRT) {
            //play sound
            world.playSound(context.getPlayer(), blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            //change dirt to mycelium
            world.setBlockState(blockPos, Blocks.MYCELIUM.getDefaultState());
            //remove one item
            itemStack.setCount(itemStack.getCount() - 1);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        boolean itemUsed = false;
        World world = target.world;
        if (!world.isRemote) {
            if (target instanceof CowEntity && !(target instanceof MooshroomEntity)) {
                // Cow to Mooshroom
                target.setAIMoveSpeed(0);
                //create mooshroom
                MooshroomEntity mooshroom = EntityType.MOOSHROOM.create(target.world);
                if (mooshroom != null) {
                    mooshroom.copyLocationAndAnglesFrom(target);
                    mooshroom.onInitialSpawn(world, world.getDifficultyForLocation(target.getPosition()), SpawnReason.CONVERSION, null, null);
                    mooshroom.setGrowingAge(((CowEntity) target).getGrowingAge());
                    if (target.hasCustomName()) {
                        mooshroom.setCustomName(target.getCustomName());
                        mooshroom.setCustomNameVisible(target.isCustomNameVisible());
                    }
                    mooshroom.setHealth(target.getHealth());
                    //replace cow with new mooshroom
                    target.remove();
                    world.addEntity(mooshroom);
                    mooshroom.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 2.0F, 1.0F);
                    //remove one item
                    stack.setCount(stack.getCount() - 1);
                    itemUsed = true;
                }
            } else if (target instanceof SheepEntity && !(target instanceof MushroomSheepEntity)) {
                //Sheep to Mushroom Sheep
                MushroomSheepEntity.replaceSheep((SheepEntity) target, null);
                itemUsed = true;
            }
        }
        if (itemUsed) {
            //remove one item
            stack.setCount(stack.getCount() - 1);
        }
        return itemUsed;
    }

}
