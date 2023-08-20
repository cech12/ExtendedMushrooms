package cech12.extendedmushrooms.advancements.criterion;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.entity.passive.MushroomSheepEntity;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.level.storage.loot.LootContext;

import javax.annotation.Nonnull;

public class MushroomSheepConversionTrigger extends SimpleCriterionTrigger<MushroomSheepConversionTrigger.TriggerInstance> {

    static final ResourceLocation ID = new ResourceLocation(ExtendedMushrooms.MOD_ID, "mushroom_sheep_conversion");

    @Nonnull
    public ResourceLocation getId() {
        return ID;
    }

    @Nonnull
    public MushroomSheepConversionTrigger.TriggerInstance createInstance(@Nonnull JsonObject json, @Nonnull ContextAwarePredicate predicate, @Nonnull DeserializationContext context) {
        ContextAwarePredicate sheep = EntityPredicate.fromJson(json, "sheep", context);
        ContextAwarePredicate mushroomSheep = EntityPredicate.fromJson(json, "mushroom_sheep", context);
        return new MushroomSheepConversionTrigger.TriggerInstance(predicate, sheep, mushroomSheep);
    }

    public void trigger(ServerPlayer player, Sheep sheep, MushroomSheepEntity mushroomSheep) {
        LootContext lootcontext = EntityPredicate.createContext(player, sheep);
        LootContext lootcontext1 = EntityPredicate.createContext(player, mushroomSheep);
        this.trigger(player, (p_24285_) -> p_24285_.matches(lootcontext, lootcontext1));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ContextAwarePredicate sheep;
        private final ContextAwarePredicate mushroomSheep;

        public TriggerInstance(ContextAwarePredicate p_286338_, ContextAwarePredicate sheep, ContextAwarePredicate mushroomSheep) {
            super(MushroomSheepConversionTrigger.ID, p_286338_);
            this.sheep = sheep;
            this.mushroomSheep = mushroomSheep;
        }

        public static MushroomSheepConversionTrigger.TriggerInstance mushroomSheepConversion() {
            return new MushroomSheepConversionTrigger.TriggerInstance(ContextAwarePredicate.ANY, ContextAwarePredicate.ANY, ContextAwarePredicate.ANY);
        }

        public boolean matches(LootContext sheep, LootContext mushroomSheep) {
            if (!this.sheep.matches(sheep)) {
                return false;
            } else {
                return this.mushroomSheep.matches(mushroomSheep);
            }
        }

        @Nonnull
        public JsonObject serializeToJson(@Nonnull SerializationContext context) {
            JsonObject jsonobject = super.serializeToJson(context);
            jsonobject.add("sheep", this.sheep.toJson(context));
            jsonobject.add("mushroom_sheep", this.mushroomSheep.toJson(context));
            return jsonobject;
        }
    }

}
