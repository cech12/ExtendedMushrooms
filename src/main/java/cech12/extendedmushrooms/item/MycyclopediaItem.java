package cech12.extendedmushrooms.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import java.util.List;

public class MycyclopediaItem extends Item {

    public MycyclopediaItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level worldIn, List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        tooltip.add(getEdition().copy().withStyle(ChatFormatting.GRAY));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level worldIn, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (playerIn instanceof ServerPlayer player) {
            PatchouliAPI.get().openBookGUI(player, ForgeRegistries.ITEMS.getKey(this));
        }
        return InteractionResultHolder.sidedSuccess(stack, worldIn.isClientSide());
    }

    private Component getEdition() {
        try {
            return PatchouliAPI.get().getSubtitle(ForgeRegistries.ITEMS.getKey(this));
        } catch (IllegalArgumentException e) {
            return Component.literal("");
        }
    }

}
