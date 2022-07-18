package cech12.extendedmushrooms.client.renderer.blockentity;

import cech12.extendedmushrooms.block.VariantChestBlock;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.tileentity.VariantChestTileEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class VariantChestBlockEntityRenderer extends AbstractVariantChestBlockEntityRenderer<VariantChestTileEntity> {

    public VariantChestBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    @Nonnull
    protected ResourceLocation getTexture(VariantChestTileEntity tileEntity, ChestType chestType) {
        MushroomWoodType woodType = tileEntity.woodType;
        if (woodType == null) {
            woodType = ((VariantChestBlock) tileEntity.getBlockState().getBlock()).getWoodType();
        }
        ChestTextures textures = getCachedTextures(woodType);
        return switch (chestType) {
            case LEFT -> textures.left;
            case RIGHT -> textures.right;
            default -> textures.single;
        };
    }
}
