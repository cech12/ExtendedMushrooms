package cech12.extendedmushrooms.client.renderer.tileentity;

import cech12.extendedmushrooms.block.VariantChestBlock;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.tileentity.VariantChestTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class VariantChestTileEntityRenderer extends AbstractVariantChestTileEntityRenderer<VariantChestTileEntity> {

    public VariantChestTileEntityRenderer(TileEntityRendererDispatcher dispatcher) {
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
        switch(chestType) {
            case LEFT:
                return textures.left;
            case RIGHT:
                return textures.right;
            default:
                return textures.single;
        }
    }
}
