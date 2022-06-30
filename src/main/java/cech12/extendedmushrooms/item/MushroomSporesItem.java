package cech12.extendedmushrooms.item;

import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;

public class MushroomSporesItem extends Item {

    public MushroomSporesItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public @Nonnull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Block block = world.getBlockState(blockPos).getBlock();
        ItemStack itemStack = context.getItemInHand();

        // replace dirt with mycelium
        if (block == Blocks.DIRT) {
            //play sound
            world.playSound(context.getPlayer(), blockPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            //change dirt to mycelium
            world.setBlockAndUpdate(blockPos, Blocks.MYCELIUM.defaultBlockState());
            //remove one item
            itemStack.setCount(itemStack.getCount() - 1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Nonnull
    @Override
    public InteractionResult interactLivingEntity(@Nonnull ItemStack stack, @Nonnull Player playerIn, LivingEntity target, @Nonnull InteractionHand hand) {
        boolean itemUsed = false;
        Level world = target.level;
        if (!world.isClientSide && world instanceof ServerLevel) {
            if (target instanceof Cow && !(target instanceof MushroomCow)) {
                // Cow to Mooshroom
                target.setSpeed(0);
                //create mooshroom
                MushroomCow mooshroom = EntityType.MOOSHROOM.create(target.level);
                if (mooshroom != null) {
                    mooshroom.copyPosition(target);
                    mooshroom.finalizeSpawn((ServerLevel)world, world.getCurrentDifficultyAt(target.blockPosition()), MobSpawnType.CONVERSION, null, null);
                    mooshroom.setAge(((Cow) target).getAge());
                    if (target.hasCustomName()) {
                        mooshroom.setCustomName(target.getCustomName());
                        mooshroom.setCustomNameVisible(target.isCustomNameVisible());
                    }
                    mooshroom.setHealth(target.getHealth());
                    //replace cow with new mooshroom
                    target.remove(Entity.RemovalReason.DISCARDED);
                    world.addFreshEntity(mooshroom);
                    mooshroom.playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, 2.0F, 1.0F);
                    //remove one item
                    stack.setCount(stack.getCount() - 1);
                    itemUsed = true;
                }
            } else if (target instanceof Sheep && !(target instanceof MushroomSheepEntity)) {
                //Sheep to Mushroom Sheep
                MushroomSheepEntity.replaceSheep((Sheep) target, null);
                itemUsed = true;
            }
        }
        if (itemUsed) {
            //remove one item
            stack.setCount(stack.getCount() - 1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

}
