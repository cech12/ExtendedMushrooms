package cech12.extendedmushrooms.client.renderer.blockentity;

import cech12.extendedmushrooms.block.VariantTrappedChestBlock;
import cech12.extendedmushrooms.blockentity.VariantTrappedChestBlockEntity;
import cech12.extendedmushrooms.item.MushroomWoodType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class VariantTrappedChestBlockEntityRenderer extends AbstractVariantChestBlockEntityRenderer<VariantTrappedChestBlockEntity> {

    public VariantTrappedChestBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    @Nonnull
    protected ResourceLocation getTexture(VariantTrappedChestBlockEntity tileEntity, ChestType chestType) {
        MushroomWoodType woodType = tileEntity.woodType;
        if (woodType == null) {
            woodType = ((VariantTrappedChestBlock) tileEntity.getBlockState().getBlock()).getWoodType();
        }
        ChestTextures textures = getCachedTextures(woodType);
        return switch (chestType) {
            case LEFT -> textures.trapped_left;
            case RIGHT -> textures.trapped_right;
            default -> textures.trapped_single;
        };
    }
}
