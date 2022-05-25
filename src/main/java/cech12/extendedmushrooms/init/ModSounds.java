package cech12.extendedmushrooms.init;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = ExtendedMushrooms.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds {

    private static final ArrayList<SoundEvent> EVENTS = new ArrayList<>(); //must be here at the beginning to initialize the array

    public static SoundEvent FAIRY_RING_CRAFTING = makeSoundEvent("fairy_ring_crafting");
    public static SoundEvent FAIRY_RING_CRAFTING_FINISH = makeSoundEvent("fairy_ring_crafting_finish");
    public static SoundEvent NO_SOUND = makeSoundEvent("no_sound");

    private static SoundEvent makeSoundEvent(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(ExtendedMushrooms.MOD_ID, name);
        SoundEvent soundEvent = new SoundEvent(resourceLocation).setRegistryName(resourceLocation);
        EVENTS.add(soundEvent);
        return soundEvent;
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        for (SoundEvent soundEvent : EVENTS) {
            event.getRegistry().register(soundEvent);
        }
    }

}
