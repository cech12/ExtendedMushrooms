package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BushBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.io.IOException;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    private static final ResourceLocation ITEM_GENERATED = new ResourceLocation("item/generated");
    private static final ResourceLocation SPAWN_EGG = new ResourceLocation("item/template_spawn_egg");

    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ExtendedMushrooms.MOD_ID, existingFileHelper);
    }

    private static ResourceLocation getResourceLocation(String path) {
        return new ResourceLocation(ExtendedMushrooms.MOD_ID, path);
    }

    private static ResourceLocation getBlockResourceLocation(String name) {
        return getResourceLocation("block/" + name);
    }

    private static ResourceLocation getItemResourceLocation(String name) {
        return getResourceLocation("item/" + name);
    }

    @Override
    protected void registerModels() {
        for (Item item : ForgeRegistries.ITEMS) {
            if (!ExtendedMushrooms.MOD_ID.equals(item.getRegistryName().getNamespace())) {
                continue;
            }
            String name = item.getRegistryName().getPath();
            if (item instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) item;
                Block block = blockItem.getBlock();
                if (block instanceof BushBlock || block instanceof LadderBlock) { //mushrooms, grass, flowers, ladders
                    //block items with block texture
                    singleTexture(name, ITEM_GENERATED, "layer0", getBlockResourceLocation(name));
                } else if (block instanceof DoorBlock) {
                    //block items with item texture
                    singleTexture(name, ITEM_GENERATED, "layer0", getItemResourceLocation(name));
                } else if (block instanceof HugeMushroomBlock //stems, caps
                        || block instanceof AbstractButtonBlock
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
    public void act(DirectoryCache cache) throws IOException {
        super.act(cache);
    }

    @Override
    @Nonnull
    public String getName() {
        return "Extended Mushrooms Item Models";
    }
}
