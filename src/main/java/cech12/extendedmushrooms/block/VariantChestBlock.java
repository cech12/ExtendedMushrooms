package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.tileentity.VariantChestTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import net.minecraft.block.AbstractBlock.Properties;

public class VariantChestBlock extends ChestBlock {

    private final MushroomWoodType woodType;

    public VariantChestBlock(MushroomWoodType woodType, Properties p_i225757_1_) {
        super(p_i225757_1_, () -> (TileEntityType<? extends ChestTileEntity>) ExtendedMushroomsTileEntities.VARIANT_CHEST);
        this.woodType = woodType;
    }

    @Override
    public TileEntity newBlockEntity(@Nonnull IBlockReader worldIn) {
        return new VariantChestTileEntity();
    }

    public MushroomWoodType getWoodType() {
        return this.woodType;
    }

    @OnlyIn(Dist.CLIENT)
    public void setISTER(Item.Properties props) {
        props.setISTER(() -> () -> new ItemStackTileEntityRenderer() {
            private VariantChestTileEntity tile;

            @Override
            //render
            public void renderByItem(@Nonnull ItemStack stack, ItemCameraTransforms.TransformType transformType, @Nonnull MatrixStack matrix, @Nonnull IRenderTypeBuffer buffer, int x, int y) {
                if (tile == null) {
                    tile = new VariantChestTileEntity(woodType);
                }
                TileEntityRendererDispatcher.instance.renderItem(tile, matrix, buffer, x, y);
            }
        });
    }
}
