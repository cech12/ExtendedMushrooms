package cech12.extendedmushrooms.client.renderer.entity.layers;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class MushroomSheepWoolLayer extends RenderLayer<MushroomSheepEntity, SheepModel<MushroomSheepEntity>> {

    private static final ResourceLocation SHEEP_COLORING_TEXTURE = new ResourceLocation(ExtendedMushrooms.MOD_ID, "textures/entity/sheep/mushroom_sheep_coloring.png");

    private final SheepModel<MushroomSheepEntity> sheepModel;
    private final SheepFurModel<MushroomSheepEntity> sheepWoolModel;

    public MushroomSheepWoolLayer(RenderLayerParent<MushroomSheepEntity, SheepModel<MushroomSheepEntity>> rendererIn, EntityModelSet modelSet) {
        super(rendererIn);
        this.sheepModel = new SheepModel<>(modelSet.bakeLayer(ModelLayers.SHEEP));
        this.sheepWoolModel = new SheepFurModel<>(modelSet.bakeLayer(ModelLayers.SHEEP_FUR));
    }

    public void render(@Nonnull PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int packedLightIn, MushroomSheepEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isInvisible()) {
            float[] color = entitylivingbaseIn.getMushroomType().getColor().getTextureDiffuseColors();
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.sheepModel,
                    SHEEP_COLORING_TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw,
                    headPitch, partialTicks, color[0], color[1], color[2]);
            if (!entitylivingbaseIn.isSheared()) {
                coloredCutoutModelCopyLayerRender(this.getParentModel(), this.sheepWoolModel,
                        new ResourceLocation(ExtendedMushrooms.MOD_ID, "textures/entity/sheep/" + entitylivingbaseIn.getMushroomType().getSerializedName() + ".png"),
                        matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, 1, 1, 1);
            }
        }
    }
}
