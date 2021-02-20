package me.Jonathon594.Mythria.Managers;

import net.minecraft.client.gui.IngameGui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;


public class IngameGuiManager {
    public static ArrayList<IngameGui> overlayList = new ArrayList<>();

    public static void addOverlay(IngameGui overlay) {
        overlayList.add(overlay);
    }

    public static void onClientTick() {
        for (IngameGui gui : overlayList) {
            gui.tick();
        }
    }

    public static void renderOverlays(RenderGameOverlayEvent event) {
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
            event.setCanceled(true);
            for (IngameGui overlay : overlayList) {
                overlay.renderIngameGui(event.getMatrixStack(), event.getPartialTicks());
            }
        }
    }
}
