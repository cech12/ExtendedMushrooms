package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.tileentity.VariantChestTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class VariantChestBlock extends ChestBlock {

    private final MushroomWoodType woodType;

    public VariantChestBlock(MushroomWoodType woodType, Properties p_i225757_1_) {
        super(p_i225757_1_, () -> (TileEntityType<? extends ChestTileEntity>) ExtendedMushroomsTileEntities.VARIANT_CHEST);
        this.woodType = woodType;
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader worldIn) {
        return new VariantChestTileEntity();
    }

    public MushroomWoodType getWoodType() {
        return this.woodType;
    }

    public void setISTER(Item.Properties props) {
        props.setISTER(() -> () -> new ItemStackTileEntityRenderer() {
            private VariantChestTileEntity tile;

            @Override
            public void render(@Nonnull ItemStack stack, @Nonnull MatrixStack matrix, @Nonnull IRenderTypeBuffer buffer, int x, int y) {
                if (tile == null) {
                    tile = new VariantChestTileEntity(woodType);
                }
                TileEntityRendererDispatcher.instance.renderItem(tile, matrix, buffer, x, y);
            }
        });
    }
}
