package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomeModifiers {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ExtendedMushrooms.MOD_ID);

    static RegistryObject<Codec<ModEntityTypes.MushroomSheepBiomeModifier>> MUSHROOM_SHEEP = BIOME_MODIFIERS.register("mushroom_sheep", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModEntityTypes.MushroomSheepBiomeModifier::biomes)
            ).apply(builder, ModEntityTypes.MushroomSheepBiomeModifier::new)));

}
