package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.tileentity.VariantTrappedChestTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class VariantTrappedChestBlock extends ChestBlock {

    private final MushroomWoodType woodType;

    public VariantTrappedChestBlock(MushroomWoodType woodType, Properties properties) {
        super(properties, () -> (BlockEntityType<? extends ChestBlockEntity>) ExtendedMushroomsTileEntities.VARIANT_TRAPPED_CHEST);
        this.woodType = woodType;
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new VariantTrappedChestTileEntity(pos, state);
    }

    public MushroomWoodType getWoodType() {
        return this.woodType;
    }

    @OnlyIn(Dist.CLIENT)
    public void setISTER(Item.Properties props) {
        props.setISTER(() -> () -> new BlockEntityWithoutLevelRenderer() {
            private VariantTrappedChestTileEntity tile;

            @Override
            //render
            public void renderByItem(@Nonnull ItemStack stack, ItemTransforms.TransformType transformType, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource buffer, int x, int y) {
                if (tile == null) {
                    tile = new VariantTrappedChestTileEntity(woodType);
                }
                BlockEntityRenderDispatcher.instance.renderItem(tile, matrix, buffer, x, y);
            }
        });
    }

    @Override
    @Nonnull
    protected Stat<ResourceLocation> getOpenChestStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    @Override
    public boolean isSignalSource(@Nonnull BlockState state) {
        return true;
    }

    @Override
    public int getSignal(@Nonnull BlockState blockState, @Nonnull BlockGetter blockAccess, @Nonnull BlockPos pos, @Nonnull Direction side) {
        return Mth.clamp(ChestBlockEntity.getOpenCount(blockAccess, pos), 0, 15);
    }

    @Override
    public int getDirectSignal(@Nonnull BlockState blockState, @Nonnull BlockGetter blockAccess, @Nonnull BlockPos pos, @Nonnull Direction side) {
        return side == Direction.UP ? blockState.getSignal(blockAccess, pos, side) : 0;
    }
}
