package me.Jonathon594.Mythria.Client.Listener;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Screen.ScreenPerks;
import me.Jonathon594.Mythria.Client.Screen.ScreenProfile;
import me.Jonathon594.Mythria.Client.Screen.ScreenProfileCreation;
import me.Jonathon594.Mythria.Client.Keybindings;
import me.Jonathon594.Mythria.Client.Manager.ClientManager;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.CPacketReloadWeapon;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(Dist.CLIENT)
public class KeybindingListener {
    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (Keybindings.SHOW_PROFILE.isPressed()) {
            if (ProfileProvider.getProfile(mc.player).getCreated()) {
                mc.displayGuiScreen(new ScreenProfile());
            } else {
                mc.displayGuiScreen(new ScreenProfileCreation());
            }
        }
        if (Keybindings.SHOW_SKILLS.isPressed()) {
            mc.displayGuiScreen(new ScreenPerks());
        }
        if (Keybindings.RELOAD.isPressed()) {
            MythriaPacketHandler.sendToServer(new CPacketReloadWeapon());
        }
    }
}
