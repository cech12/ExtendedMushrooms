package cech12.extendedmushrooms.client;

import cech12.extendedmushrooms.ExtendedMushrooms;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ExtendedMushrooms.MOD_ID)
public final class ClientTickObserver {

    public static int ticksSinceStart = 0;
    public static float partialTicks = 0;

    private ClientTickObserver() {}

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.getInstance();
            partialTicks = event.renderTickTime;
            if (mc.isPaused()) {
                // If game is paused, need to use the saved value. The event is always fired with the "true" value which
                // keeps updating when paused. See RenderTickEvent fire site for details
                partialTicks = mc.getFrameTime();
            }
        }
    }

    @SubscribeEvent
    public static void onClientTickEnd(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            if (!Minecraft.getInstance().isPaused()) {
                ticksSinceStart++;
                partialTicks = 0;
            }
        }
    }

}
