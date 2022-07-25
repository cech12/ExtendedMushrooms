package cech12.extendedmushrooms.client.renderer.entity;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.entity.item.MushroomBoatEntity;
import cech12.extendedmushrooms.item.MushroomWoodType;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.stream.Stream;

public class MushroomBoatRenderer extends BoatRenderer {

    private final Map<MushroomWoodType, Pair<ResourceLocation, BoatModel>> BOAT_RESOURCES;

    public MushroomBoatRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.BOAT_RESOURCES = Stream.of(MushroomWoodType.values()).collect(ImmutableMap.toImmutableMap(
                (woodType) -> woodType,
                (woodType) -> Pair.of(new ResourceLocation(ExtendedMushrooms.MOD_ID, "textures/entity/boat/" + woodType.getSerializedName() + ".png"),
                        new BoatModel(context.bakeLayer(ModelLayers.createBoatModelName(Boat.Type.OAK))))
        ));
    }

    @Nonnull
    @Override
    public Pair<ResourceLocation, BoatModel> getModelWithLocation(@Nonnull Boat entity) {
        if (entity instanceof MushroomBoatEntity) {
            MushroomWoodType woodType = ((MushroomBoatEntity) entity).getMushroomWoodType();
            if (BOAT_RESOURCES.containsKey(woodType)) {
                return BOAT_RESOURCES.get(woodType);
            }
        }
        return super.getModelWithLocation(entity);
    }
}
