package me.Jonathon594.Mythria.Client.Listener;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayer;
import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Client.ClientUtil;
import me.Jonathon594.Mythria.Client.PlayerRenderManager;
import me.Jonathon594.Mythria.Client.Screen.ScreenHud;
import me.Jonathon594.Mythria.Enum.ControlMode;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderListener {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void guiPostScreenEvent(final GuiScreenEvent.InitGuiEvent.Post event) {
        ClientUtil.drawLockedInventorySlots(event);
    }

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent event) {
        MythriaPlayer mythriaPlayer = MythriaPlayerProvider.getMythriaPlayer(mc.player);

        if (event.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
            ScreenHud.INSTANCE.render(event.getMatrixStack(), event.getPartialTicks());
            event.setCanceled(true);
        }

        if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR) && mythriaPlayer.getControlMode().equals(ControlMode.ABILITY)) {
            event.setCanceled(true);
            ScreenHud.INSTANCE.renderAbilityHotbar(event.getMatrixStack(), event.getPartialTicks());
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
