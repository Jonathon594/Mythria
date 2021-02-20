package me.Jonathon594.Mythria.Client.Listener;

import me.Jonathon594.Mythria.Client.ClientUtil;
import me.Jonathon594.Mythria.Client.PlayerRenderManager;
import me.Jonathon594.Mythria.Managers.IngameGuiManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderListener {
    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent event) {
        IngameGuiManager.renderOverlays(event);
    }


    @SubscribeEvent
    public static void renderHand(RenderHandEvent event) {
        event.setCanceled(true);

        PlayerRenderManager.renderHand(event);
    }


    @SubscribeEvent
    public static void onRenderPlayer(final RenderPlayerEvent.Pre event) {
        event.setCanceled(true);
        if (event.getPlayer().isSpectator()) {
            return;
        }

        PlayerRenderManager.renderPlayer(event);
    }


    @SubscribeEvent
    public static void guiPostScreenEvent(final GuiScreenEvent.InitGuiEvent.Post event) {
        ClientUtil.drawLockedInventorySlots(event);
    }
}
