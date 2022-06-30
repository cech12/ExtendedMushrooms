package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.tileentity.VariantChestTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class VariantChestBlock extends ChestBlock {

    private final MushroomWoodType woodType;

    public VariantChestBlock(MushroomWoodType woodType, Properties p_i225757_1_) {
        super(p_i225757_1_, () -> (BlockEntityType<? extends ChestBlockEntity>) ExtendedMushroomsTileEntities.VARIANT_CHEST);
        this.woodType = woodType;
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new VariantChestTileEntity(pos, state);
    }

    public MushroomWoodType getWoodType() {
        return this.woodType;
    }

    @OnlyIn(Dist.CLIENT)
    public void setISTER(Item.Properties props) {
        props.setISTER(() -> () -> new BlockEntityWithoutLevelRenderer() {
            private VariantChestTileEntity tile;

            @Override
            //render
            public void renderByItem(@Nonnull ItemStack stack, ItemTransforms.TransformType transformType, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource buffer, int x, int y) {
                if (tile == null) {
                    tile = new VariantChestTileEntity(woodType);
                }
                BlockEntityRenderDispatcher.instance.renderItem(tile, matrix, buffer, x, y);
            }
        });
    }
}
