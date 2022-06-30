package cech12.extendedmushrooms.loot_modifiers;

import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.config.Config;
import com.google.gson.JsonObject;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class MushroomStemLootModifier extends LootModifier {

    protected MushroomStemLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (Config.MUSHROOM_STEMS_WITHOUT_SILK_TOUCH_ENABLED.get()) {
            BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
            if (blockState != null && blockState.is(ModTags.ForgeBlocks.MUSHROOM_STEMS)) {
                ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
                //to avoid endless loop: test for silk touch enchantment
                if (tool == null || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) <= 0) {
                    //generate fake tool with silk touch enchantment
                    ItemStack fakeTool = (tool != null && tool.isEnchantable()) ? tool.copy() : new ItemStack(Items.DIAMOND_AXE);
                    fakeTool.enchant(Enchantments.SILK_TOUCH, 1);
                    //generate loot with this tool
                    LootContext ctx = new LootContext.Builder(context).withParameter(LootContextParams.TOOL, fakeTool).create(LootContextParamSets.BLOCK);
                    LootTable loottable = context.getLevel().getServer().getLootTables()
                            .get(blockState.getBlock().getLootTable());
                    return loottable.getRandomItems(ctx);
                }
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<MushroomStemLootModifier> {
        @Override
        public MushroomStemLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions)
        {
            return new MushroomStemLootModifier(conditions);
        }

        @Override
        public JsonObject write(MushroomStemLootModifier instance) {
            //maybe do something?
            return null;
        }
    }

}
