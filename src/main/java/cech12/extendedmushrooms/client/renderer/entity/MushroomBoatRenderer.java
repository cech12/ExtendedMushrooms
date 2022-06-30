package cech12.extendedmushrooms.client.renderer.entity;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.entity.item.MushroomBoatEntity;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class MushroomBoatRenderer extends BoatRenderer {

    private static final HashMap<String, ResourceLocation> TEXTURES = new HashMap<>();

    public MushroomBoatRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull Boat entity) {
        if (entity instanceof MushroomBoatEntity) {
            String name = ((MushroomBoatEntity) entity).getMushroomWoodType().getSerializedName();
            if (!TEXTURES.containsKey(name)) {
                TEXTURES.put(name, new ResourceLocation(ExtendedMushrooms.MOD_ID, "textures/entity/boat/" + name + ".png"));
            }
            return TEXTURES.get(name);
        }
        return super.getTextureLocation(entity);
    }
}
