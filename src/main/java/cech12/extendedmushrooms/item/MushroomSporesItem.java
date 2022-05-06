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
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;

public class MushroomSporesItem extends Item {

    public MushroomSporesItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public @Nonnull ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Block block = world.getBlockState(blockPos).getBlock();
        ItemStack itemStack = context.getItemInHand();

        // replace dirt with mycelium
        if (block == Blocks.DIRT) {
            //play sound
            world.playSound(context.getPlayer(), blockPos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            //change dirt to mycelium
            world.setBlockAndUpdate(blockPos, Blocks.MYCELIUM.defaultBlockState());
            //remove one item
            itemStack.setCount(itemStack.getCount() - 1);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        boolean itemUsed = false;
        World world = target.level;
        if (!world.isClientSide && world instanceof ServerWorld) {
            if (target instanceof CowEntity && !(target instanceof MooshroomEntity)) {
                // Cow to Mooshroom
                target.setSpeed(0);
                //create mooshroom
                MooshroomEntity mooshroom = EntityType.MOOSHROOM.create(target.level);
                if (mooshroom != null) {
                    mooshroom.copyPosition(target);
                    mooshroom.finalizeSpawn((ServerWorld)world, world.getCurrentDifficultyAt(target.blockPosition()), SpawnReason.CONVERSION, null, null);
                    mooshroom.setAge(((CowEntity) target).getAge());
                    if (target.hasCustomName()) {
                        mooshroom.setCustomName(target.getCustomName());
                        mooshroom.setCustomNameVisible(target.isCustomNameVisible());
                    }
                    mooshroom.setHealth(target.getHealth());
                    //replace cow with new mooshroom
                    target.remove();
                    world.addFreshEntity(mooshroom);
                    mooshroom.playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, 2.0F, 1.0F);
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
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

}
