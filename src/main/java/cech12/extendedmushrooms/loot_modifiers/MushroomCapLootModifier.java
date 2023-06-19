package cech12.extendedmushrooms.loot_modifiers;

import cech12.extendedmushrooms.init.ModTags;
import cech12.extendedmushrooms.config.ServerConfig;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class MushroomCapLootModifier extends LootModifier {

    public static final Supplier<Codec<MushroomCapLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, MushroomCapLootModifier::new))
    );

    public MushroomCapLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        //only called when shears are used
        if (ServerConfig.MUSHROOM_CAPS_WITH_SHEARS_ENABLED.get()) {
            BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
            if (blockState != null && blockState.is(ModTags.ForgeBlocks.MUSHROOM_CAPS)) {
                ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
                //to avoid endless loop: test for silk touch enchantment
                if (tool != null && EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SILK_TOUCH, tool) <= 0) {
                    //generate fake tool with silk touch enchantment
                    ItemStack fakeTool = tool.copy();
                    fakeTool.enchant(Enchantments.SILK_TOUCH, 1);
                    //generate loot with this tool
                    LootParams ctx = new LootParams.Builder(context.getLevel())
                            .withParameter(LootContextParams.TOOL, fakeTool)
                            .withParameter(LootContextParams.ORIGIN, context.getParam(LootContextParams.ORIGIN))
                            .withParameter(LootContextParams.BLOCK_STATE, blockState)
                            .create(LootContextParamSets.BLOCK);
                    LootTable loottable = context.getLevel().getServer().getLootData()
                            .getLootTable(blockState.getBlock().getLootTable());
                    return loottable.getRandomItems(ctx);
                }
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

}
