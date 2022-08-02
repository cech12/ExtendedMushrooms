package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import cech12.extendedmushrooms.loot_modifiers.MushroomCapLootModifier;
import cech12.extendedmushrooms.loot_modifiers.MushroomStemLootModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ExtendedMushrooms.MOD_ID);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> MUSHROOM_CAP_HARVEST_SERIALIZER = SERIALIZERS.register("mushroom_cap_harvest", MushroomCapLootModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> MUSHROOM_STEM_HARVEST_SERIALIZER = SERIALIZERS.register("mushroom_stem_harvest", MushroomStemLootModifier.CODEC);

}
