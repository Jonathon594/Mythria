package me.Jonathon594.Mythria.Client;

import me.Jonathon594.Mythria.Client.Renderer.PlayerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class PlayerRenderManager {
    public static final PlayerRenderer RENDER_MYTHRIA_PLAYER = new PlayerRenderer(Minecraft.getInstance().getRenderManager());

    public static void renderPlayer(RenderPlayerEvent.Pre event) {
        RENDER_MYTHRIA_PLAYER.render(event.getPlayer(), event.getPlayer().rotationYaw, event.getPartialRenderTick(), event.getMatrixStack(), event.getBuffers(), event.getLight());
    }
}
