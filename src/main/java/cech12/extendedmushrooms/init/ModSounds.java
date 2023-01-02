package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ExtendedMushrooms.MOD_ID);

    public static RegistryObject<SoundEvent> FAIRY_RING_CRAFTING = makeSoundEvent("fairy_ring_crafting");
    public static RegistryObject<SoundEvent> FAIRY_RING_CRAFTING_FINISH = makeSoundEvent("fairy_ring_crafting_finish");
    public static RegistryObject<SoundEvent> NO_SOUND = makeSoundEvent("no_sound");

    private static RegistryObject<SoundEvent> makeSoundEvent(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(ExtendedMushrooms.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(resourceLocation));
    }

}
