package cech12.extendedmushrooms.block;

import cech12.extendedmushrooms.api.tileentity.ExtendedMushroomsTileEntities;
import cech12.extendedmushrooms.item.MushroomWoodType;
import cech12.extendedmushrooms.tileentity.VariantTrappedChestTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class VariantTrappedChestBlock extends ChestBlock {

    private final MushroomWoodType woodType;

    public VariantTrappedChestBlock(MushroomWoodType woodType, Properties properties) {
        super(properties, () -> (TileEntityType<? extends ChestTileEntity>) ExtendedMushroomsTileEntities.VARIANT_TRAPPED_CHEST);
        this.woodType = woodType;
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader worldIn) {
        return new VariantTrappedChestTileEntity();
    }

    public MushroomWoodType getWoodType() {
        return this.woodType;
    }

    @OnlyIn(Dist.CLIENT)
    public void setISTER(Item.Properties props) {
        props.setISTER(() -> () -> new ItemStackTileEntityRenderer() {
            private VariantTrappedChestTileEntity tile;

            @Override
            //render
            public void func_239207_a_(@Nonnull ItemStack stack, ItemCameraTransforms.TransformType transformType, @Nonnull MatrixStack matrix, @Nonnull IRenderTypeBuffer buffer, int x, int y) {
                if (tile == null) {
                    tile = new VariantTrappedChestTileEntity(woodType);
                }
                TileEntityRendererDispatcher.instance.renderItem(tile, matrix, buffer, x, y);
            }
        });
    }

    @Override
    @Nonnull
    protected Stat<ResourceLocation> getOpenStat() {
        return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
    }

    @Override
    public boolean canProvidePower(@Nonnull BlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(@Nonnull BlockState blockState, @Nonnull IBlockReader blockAccess, @Nonnull BlockPos pos, @Nonnull Direction side) {
        return MathHelper.clamp(ChestTileEntity.getPlayersUsing(blockAccess, pos), 0, 15);
    }

    @Override
    public int getStrongPower(@Nonnull BlockState blockState, @Nonnull IBlockReader blockAccess, @Nonnull BlockPos pos, @Nonnull Direction side) {
        return side == Direction.UP ? blockState.getWeakPower(blockAccess, pos, side) : 0;
    }
}
