package cech12.extendedmushrooms.client;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ExtendedMushrooms.MOD_ID)
public final class ClientTickObserver {

    public static int ticksSinceStart = 0;
    public static float partialTicks = 0;

    private static final Field RENDER_PARTIAL_TICKS_PAUSED = ObfuscationReflectionHelper.findField(Minecraft.class, "field_193996_ah");

    private ClientTickObserver() {}

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.START) {
            partialTicks = event.renderTickTime;
            if (mc.isGamePaused()) {
                // If game is paused, need to use the saved value. The event is always fired with the "true" value which
                // keeps updating when paused. See RenderTickEvent fire site for details
                try {
                    partialTicks = (float) RENDER_PARTIAL_TICKS_PAUSED.get(mc);
                } catch (IllegalAccessException ignored) {}
            }
        }
    }

    @SubscribeEvent
    public static void onClientTickEnd(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            if (!Minecraft.getInstance().isGamePaused()) {
                ticksSinceStart++;
                partialTicks = 0;
            }
        }
    }

}
