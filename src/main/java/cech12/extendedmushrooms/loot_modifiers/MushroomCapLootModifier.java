package cech12.extendedmushrooms.loot_modifiers;

import cech12.extendedmushrooms.api.tags.ExtendedMushroomsTags;
import cech12.extendedmushrooms.config.Config;
import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class MushroomCapLootModifier extends LootModifier {

    public MushroomCapLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        //only called when shears are used
        if (Config.MUSHROOM_CAPS_WITH_SHEARS_ENABLED.getValue()) {
            BlockState blockState = context.get(LootParameters.BLOCK_STATE);
            if (blockState != null && blockState.isIn(ExtendedMushroomsTags.ForgeBlocks.MUSHROOM_CAPS)) {
                ItemStack tool = context.get(LootParameters.TOOL);
                //to avoid endless loop: test for silk touch enchantment
                if (tool != null && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool) <= 0) {
                    //generate fake tool with silk touch enchantment
                    ItemStack fakeTool = tool.copy();
                    fakeTool.addEnchantment(Enchantments.SILK_TOUCH, 1);
                    //generate loot with this tool
                    LootContext.Builder builder = new LootContext.Builder(context).withParameter(LootParameters.TOOL, fakeTool);
                    LootContext ctx = builder.build(LootParameterSets.BLOCK);
                    LootTable loottable = context.getWorld().getServer().getLootTableManager()
                            .getLootTableFromLocation(blockState.getBlock().getLootTable());
                    return loottable.generate(ctx);
                }
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<MushroomCapLootModifier> {
        @Override
        public MushroomCapLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions)
        {
            return new MushroomCapLootModifier(conditions);
        }
    }

}
