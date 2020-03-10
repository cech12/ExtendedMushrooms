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

public class MushroomSheepWoolLayer extends LayerRenderer<MushroomSheepEntity, SheepModel<MushroomSheepEntity>> {

    private final SheepWoolModel<MushroomSheepEntity> sheepModel = new SheepWoolModel<>();

    public MushroomSheepWoolLayer(IEntityRenderer<MushroomSheepEntity, SheepModel<MushroomSheepEntity>> rendererIn) {
        super(rendererIn);
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MushroomSheepEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.getSheared() && !entitylivingbaseIn.isInvisible()) {
            renderCopyCutoutModel(this.getEntityModel(), this.sheepModel,
                    new ResourceLocation(ExtendedMushrooms.MOD_ID, "textures/entity/sheep/" + entitylivingbaseIn.getMushroomType().getName() + ".png"),
                    matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1, 1, 1);
        }
    }
}
