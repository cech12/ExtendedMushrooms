package cech12.extendedmushrooms.client.renderer.entity;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.client.renderer.entity.layers.MushroomSheepWoolLayer;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.SheepModel;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class MushroomSheepRenderer extends MobRenderer<MushroomSheepEntity, SheepModel<MushroomSheepEntity>> {

    private static final ResourceLocation SHEARED_SHEEP_TEXTURES = new ResourceLocation(ExtendedMushrooms.MOD_ID, "textures/entity/sheep/mushroom_sheep.png");

    public MushroomSheepRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new SheepModel<>(), 0.7F);
        this.addLayer(new MushroomSheepWoolLayer(this));
    }

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull MushroomSheepEntity entity) {
        return SHEARED_SHEEP_TEXTURES;
    }

}
