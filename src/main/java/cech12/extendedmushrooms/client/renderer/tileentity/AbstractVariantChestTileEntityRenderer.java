package cech12.extendedmushrooms.client.renderer.tileentity;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.item.MushroomWoodType;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashMap;

public abstract class AbstractVariantChestTileEntityRenderer<T extends ChestTileEntity> extends TileEntityRenderer<T> {

    private static final HashMap<MushroomWoodType, ChestTextures> TEXTURES = new HashMap<>();

    private final ModelRenderer lid;
    private final ModelRenderer bottom;
    private final ModelRenderer lock;
    private final ModelRenderer doubleLeftLid;
    private final ModelRenderer doubleLeftBottom;
    private final ModelRenderer doubleLeftLock;
    private final ModelRenderer doubleRightLid;
    private final ModelRenderer doubleRightBottom;
    private final ModelRenderer doubleRightLock;

    public AbstractVariantChestTileEntityRenderer(TileEntityRendererDispatcher p_i226008_1_) {
        super(p_i226008_1_);

        this.bottom = new ModelRenderer(64, 64, 0, 19);
        this.bottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
        this.lid = new ModelRenderer(64, 64, 0, 0);
        this.lid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
        this.lid.y = 9.0F;
        this.lid.z = 1.0F;
        this.lock = new ModelRenderer(64, 64, 0, 0);
        this.lock.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
        this.lock.y = 8.0F;
        this.doubleLeftBottom = new ModelRenderer(64, 64, 0, 19);
        this.doubleLeftBottom.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.doubleLeftLid = new ModelRenderer(64, 64, 0, 0);
        this.doubleLeftLid.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.doubleLeftLid.y = 9.0F;
        this.doubleLeftLid.z = 1.0F;
        this.doubleLeftLock = new ModelRenderer(64, 64, 0, 0);
        this.doubleLeftLock.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.doubleLeftLock.y = 8.0F;
        this.doubleRightBottom = new ModelRenderer(64, 64, 0, 19);
        this.doubleRightBottom.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
        this.doubleRightLid = new ModelRenderer(64, 64, 0, 0);
        this.doubleRightLid.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.doubleRightLid.y = 9.0F;
        this.doubleRightLid.z = 1.0F;
        this.doubleRightLock = new ModelRenderer(64, 64, 0, 0);
        this.doubleRightLock.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.doubleRightLock.y = 8.0F;
    }

    public void render(T tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        World world = tileEntityIn.getLevel();
        boolean flag = world != null;
        BlockState blockstate = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        ChestType chesttype = blockstate.hasProperty(ChestBlock.TYPE) ? blockstate.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockstate.getBlock();
        if (block instanceof AbstractChestBlock) {
            AbstractChestBlock<?> abstractchestblock = (AbstractChestBlock<?>)block;
            boolean flag1 = chesttype != ChestType.SINGLE;
            matrixStackIn.pushPose();
            float f = blockstate.getValue(ChestBlock.FACING).toYRot();
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f));
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> icallbackwrapper;
            if (flag) {
                icallbackwrapper = abstractchestblock.combine(blockstate, world, tileEntityIn.getBlockPos(), true);
            } else {
                icallbackwrapper = TileEntityMerger.ICallback::acceptNone;
            }

            float f1 = icallbackwrapper.apply(ChestBlock.opennessCombiner(tileEntityIn)).get(partialTicks);
            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            int i = icallbackwrapper.apply(new DualBrightnessCallback<>()).applyAsInt(combinedLightIn);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(this.getTexture(tileEntityIn, chesttype)));
            if (flag1) {
                if (chesttype == ChestType.LEFT) {
                    this.render(matrixStackIn, ivertexbuilder, this.doubleRightLid, this.doubleRightLock, this.doubleRightBottom, f1, i, combinedOverlayIn);
                } else {
                    this.render(matrixStackIn, ivertexbuilder, this.doubleLeftLid, this.doubleLeftLock, this.doubleLeftBottom, f1, i, combinedOverlayIn);
                }
            } else {
                this.render(matrixStackIn, ivertexbuilder, this.lid, this.lock, this.bottom, f1, i, combinedOverlayIn);
            }

            matrixStackIn.popPose();
        }
    }

    private void render(MatrixStack p_228871_1_, IVertexBuilder p_228871_2_, ModelRenderer p_228871_3_, ModelRenderer p_228871_4_, ModelRenderer p_228871_5_, float p_228871_6_, int p_228871_7_, int p_228871_8_) {
        p_228871_3_.xRot = -(p_228871_6_ * ((float)Math.PI / 2F));
        p_228871_4_.xRot = p_228871_3_.xRot;
        p_228871_3_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
        p_228871_4_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
        p_228871_5_.render(p_228871_1_, p_228871_2_, p_228871_7_, p_228871_8_);
    }

    protected abstract ResourceLocation getTexture(T tileEntity, ChestType chestType);

    protected ChestTextures getCachedTextures(MushroomWoodType woodType) {
        if (!TEXTURES.containsKey(woodType)) {
            TEXTURES.put(woodType, new ChestTextures(woodType));
        }
        return TEXTURES.get(woodType);
    }

    static class ChestTextures {

        public final ResourceLocation left;
        public final ResourceLocation right;
        public final ResourceLocation single;
        public final ResourceLocation trapped_left;
        public final ResourceLocation trapped_right;
        public final ResourceLocation trapped_single;

        public ChestTextures(MushroomWoodType woodType) {
            String basePath = "textures/entity/chest/" + woodType.getSerializedName();
            this.left = new ResourceLocation(ExtendedMushrooms.MOD_ID,basePath + "/left.png");
            this.right = new ResourceLocation(ExtendedMushrooms.MOD_ID,basePath + "/right.png");
            this.single = new ResourceLocation(ExtendedMushrooms.MOD_ID,basePath + "/single.png");
            this.trapped_left = new ResourceLocation(ExtendedMushrooms.MOD_ID,basePath + "/trapped_left.png");
            this.trapped_right = new ResourceLocation(ExtendedMushrooms.MOD_ID,basePath + "/trapped_right.png");
            this.trapped_single = new ResourceLocation(ExtendedMushrooms.MOD_ID,basePath + "/trapped_single.png");
        }

    }

}
