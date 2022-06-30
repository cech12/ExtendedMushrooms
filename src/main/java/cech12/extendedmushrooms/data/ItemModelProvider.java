package cech12.extendedmushrooms.data;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.block.VariantChestBlock;
import cech12.extendedmushrooms.block.VariantTrappedChestBlock;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
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

    private static ResourceLocation getBlockResourceLocation(String name, String removeSuffix, String addSuffix) {
        return getBlockResourceLocation(name.substring(0, name.length() - removeSuffix.length()) + addSuffix);
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
                } else if (block instanceof VariantChestBlock || block instanceof VariantTrappedChestBlock) {
                    getBuilder(name)
                            .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                            .texture("particle", getBlockResourceLocation(name.replace("_trapped", ""), "_chest", "_planks"))
                            .transforms()
                            .transform(ModelBuilder.Perspective.GUI).rotation(30, 45, 0).translation(0, 0, 0).scale(0.625F, 0.625F, 0.625F).end()
                            .transform(ModelBuilder.Perspective.GROUND).rotation(0, 0, 0).translation(0, 3, 0).scale(0.25F, 0.25F, 0.25F).end()
                            .transform(ModelBuilder.Perspective.HEAD).rotation(0, 180, 0).translation(0, 0, 0).scale(1, 1, 1).end()
                            .transform(ModelBuilder.Perspective.FIXED).rotation(0, 180, 0).translation(0, 0, 0).scale(0.5F, 0.5F, 0.5F).end()
                            .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).rotation(75, 315, 0).translation(0, 2.5F, 0).scale(0.375F, 0.375F, 0.375F).end()
                            .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).rotation(0, 315, 0).translation(0, 0, 0).scale(0.4F, 0.4F, 0.4F).end()
                            .end();
                } else if (block instanceof StandingSignBlock) {
                    //SignItem returns standing sign block in getBlock
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
    public void run(HashCache cache) throws IOException {
        super.run(cache);
    }

    @Override
    @Nonnull
    public String getName() {
        return "Extended Mushrooms Item Models";
    }
}
