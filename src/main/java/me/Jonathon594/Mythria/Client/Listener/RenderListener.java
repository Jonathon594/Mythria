package me.Jonathon594.Mythria.Client.Listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.Jonathon594.Mythria.Client.ClientUtil;
import me.Jonathon594.Mythria.Client.PlayerRenderManager;
import me.Jonathon594.Mythria.Client.Screen.ScreenHud;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderListener {
    @SubscribeEvent
    public static void guiPostScreenEvent(final GuiScreenEvent.InitGuiEvent.Post event) {
        ClientUtil.drawLockedInventorySlots(event);
    }

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
            ScreenHud.INSTANCE.render(event.getMatrixStack(), event.getPartialTicks());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRenderPlayer(final RenderPlayerEvent.Pre event) {
        event.setCanceled(true);
        if (event.getPlayer().isSpectator()) {
            return;
        }

        PlayerRenderManager.renderPlayer(event);
    }
}
