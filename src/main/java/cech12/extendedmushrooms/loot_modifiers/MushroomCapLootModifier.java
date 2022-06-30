package cech12.extendedmushrooms.loot_modifiers;

import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.config.Config;
import com.google.gson.JsonObject;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
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

public class MushroomCapLootModifier extends LootModifier {

    public MushroomCapLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        //only called when shears are used
        if (Config.MUSHROOM_CAPS_WITH_SHEARS_ENABLED.get()) {
            BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
            if (blockState != null && blockState.is(ModTags.ForgeBlocks.MUSHROOM_CAPS)) {
                ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
                //to avoid endless loop: test for silk touch enchantment
                if (tool != null && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) <= 0) {
                    //generate fake tool with silk touch enchantment
                    ItemStack fakeTool = tool.copy();
                    fakeTool.enchant(Enchantments.SILK_TOUCH, 1);
                    //generate loot with this tool
                    LootContext.Builder builder = new LootContext.Builder(context).withParameter(LootContextParams.TOOL, fakeTool);
                    LootContext ctx = builder.create(LootContextParamSets.BLOCK);
                    LootTable loottable = context.getLevel().getServer().getLootTables()
                            .get(blockState.getBlock().getLootTable());
                    return loottable.getRandomItems(ctx);
                }
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<MushroomCapLootModifier> {
        @Override
        public MushroomCapLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions)
        {
            return new MushroomCapLootModifier(conditions);
        }

        @Override
        public JsonObject write(MushroomCapLootModifier instance) {
            //maybe do something?
            return null;
        }
    }

}
