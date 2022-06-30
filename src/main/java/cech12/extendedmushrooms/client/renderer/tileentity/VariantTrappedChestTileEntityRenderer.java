package cech12.extendedmushrooms.client.renderer.tileentity;

import cech12.extendedmushrooms.block.VariantTrappedChestBlock;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.tileentity.VariantTrappedChestTileEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class VariantTrappedChestTileEntityRenderer extends AbstractVariantChestTileEntityRenderer<VariantTrappedChestTileEntity> {

    public VariantTrappedChestTileEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    @Nonnull
    protected ResourceLocation getTexture(VariantTrappedChestTileEntity tileEntity, ChestType chestType) {
        MushroomWoodType woodType = tileEntity.woodType;
        if (woodType == null) {
            woodType = ((VariantTrappedChestBlock) tileEntity.getBlockState().getBlock()).getWoodType();
        }
        ChestTextures textures = getCachedTextures(woodType);
        switch(chestType) {
            case LEFT:
                return textures.trapped_left;
            case RIGHT:
                return textures.trapped_right;
            default:
                return textures.trapped_single;
        }
    }
}
