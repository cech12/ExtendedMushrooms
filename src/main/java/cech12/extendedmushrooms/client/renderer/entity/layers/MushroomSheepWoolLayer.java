package cech12.extendedmushrooms.client.renderer.entity.layers;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.SheepModel;
import net.minecraft.client.renderer.entity.model.SheepWoolModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class MushroomSheepWoolLayer extends LayerRenderer<MushroomSheepEntity, SheepModel<MushroomSheepEntity>> {

    private static final ResourceLocation SHEEP_COLORING_TEXTURE = new ResourceLocation(ExtendedMushrooms.MOD_ID, "textures/entity/sheep/mushroom_sheep_coloring.png");

    private final SheepModel<MushroomSheepEntity> sheepModel = new SheepModel<>();
    private final SheepWoolModel<MushroomSheepEntity> sheepWoolModel = new SheepWoolModel<>();

    public MushroomSheepWoolLayer(IEntityRenderer<MushroomSheepEntity, SheepModel<MushroomSheepEntity>> rendererIn) {
        super(rendererIn);
    }

    public void render(@Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int packedLightIn, MushroomSheepEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isInvisible()) {
            float[] color = entitylivingbaseIn.getMushroomType().getColor().getColorComponentValues();
            renderCopyCutoutModel(this.getEntityModel(), this.sheepModel,
                    SHEEP_COLORING_TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw,
                    headPitch, partialTicks, color[0], color[1], color[2]);
            if (!entitylivingbaseIn.getSheared()) {
                renderCopyCutoutModel(this.getEntityModel(), this.sheepWoolModel,
                        new ResourceLocation(ExtendedMushrooms.MOD_ID, "textures/entity/sheep/" + entitylivingbaseIn.getMushroomType().getName() + ".png"),
                        matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1, 1, 1);
            }
        }
    }
}
