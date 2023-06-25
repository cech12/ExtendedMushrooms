package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    private static final ResourceLocation ITEM_GENERATED = new ResourceLocation("item/generated");
    private static final ResourceLocation SPAWN_EGG = new ResourceLocation("item/template_spawn_egg");

    public ItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, ExtendedMushrooms.MOD_ID, existingFileHelper);
    }

    private static ResourceLocation getResourceLocation(String path) {
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, path);
    }

    private static ResourceLocation getBlockResourceLocation(String name) {
        return getResourceLocation("block/" + name);
    }

    private static ResourceLocation getBlockResourceLocation(String name, String removeSuffix, String addSuffix) {
        return getBlockResourceLocation(name.substring(0, name.length() - removeSuffix.length()) + addSuffix);
    }

    private static ResourceLocation getItemResourceLocation(String name) {
        return getResourceLocation("item/" + name);
    }

    @Override
    protected void registerModels() {
        for (Item item : ForgeRegistries.ITEMS) {
            ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(item);
            if (!ExtendedMushrooms.MOD_ID.equals(resourceLocation.getNamespace())) {
                continue;
            }
            String name = resourceLocation.getPath();
            if (item instanceof BlockItem blockItem) {
                Block block = blockItem.getBlock();
                if (block instanceof BushBlock) { //mushrooms, grass, flowers
                    //block items with block texture
                    singleTexture(name, ITEM_GENERATED, "layer0", getBlockResourceLocation(name));
                } else if (block instanceof StandingSignBlock) {
                    //SignItem returns standing sign block in getBlock
                    singleTexture(name, ITEM_GENERATED, "layer0", getItemResourceLocation(name));
                } else if (block instanceof CeilingHangingSignBlock) {
                    singleTexture(name, ITEM_GENERATED, "layer0", getItemResourceLocation(name));
                } else if (block instanceof DoorBlock) {
                    //block items with item texture
                    singleTexture(name, ITEM_GENERATED, "layer0", getItemResourceLocation(name));
                } else if (block instanceof HugeMushroomBlock //stems, caps
                        || block instanceof ButtonBlock
                        || block instanceof FenceBlock) {
                    //block items with extra block inventory model
                    withExistingParent(name, getBlockResourceLocation(name + "_inventory"));
                } else if (block instanceof TrapDoorBlock) {
                    withExistingParent(name, getBlockResourceLocation(name + "_bottom"));
                } else {
                    //block items with block model
                    withExistingParent(name, getBlockResourceLocation(name));
                }
            } else {
                if (item instanceof SpawnEggItem) {
                    //spawn eggs
                    withExistingParent(name, SPAWN_EGG);
                } else {
                    //items with item texture
                    singleTexture(name, ITEM_GENERATED, "layer0", getItemResourceLocation(name));
                }
            }
        }
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return super.run(cache);
    }

    @Override
    @Nonnull
    public String getName() {
        return "Extended Mushrooms Item Models";
    }
}
