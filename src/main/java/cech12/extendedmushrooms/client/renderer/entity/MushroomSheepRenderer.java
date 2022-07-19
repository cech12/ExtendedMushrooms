package cech12.extendedmushrooms.client.renderer.entity;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.client.renderer.entity.layers.MushroomSheepWoolLayer;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.SheepModel;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class MushroomSheepRenderer extends MobRenderer<MushroomSheepEntity, SheepModel<MushroomSheepEntity>> {

    private static final ResourceLocation SHEARED_SHEEP_TEXTURES = new ResourceLocation(ExtendedMushrooms.MOD_ID, "textures/entity/sheep/mushroom_sheep.png");

    public MushroomSheepRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new SheepModel<>(renderManagerIn.bakeLayer(ModelLayers.SHEEP)), 0.7F);
        this.addLayer(new MushroomSheepWoolLayer(this, renderManagerIn.getModelSet()));
    }

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull MushroomSheepEntity entity) {
        return SHEARED_SHEEP_TEXTURES;
    }

}
