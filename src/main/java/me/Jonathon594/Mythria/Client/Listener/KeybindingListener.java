package me.Jonathon594.Mythria.Client.Listener;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Keybindings;
import me.Jonathon594.Mythria.Client.Manager.InputManager;
import me.Jonathon594.Mythria.Client.Screen.ScreenPerks;
import me.Jonathon594.Mythria.Client.Screen.ScreenProfile;
import me.Jonathon594.Mythria.Client.Screen.ScreenProfileCreation;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.Packet.CPacketAction;
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
        if (mc.player == null) return;
        Profile profile = ProfileProvider.getProfile(mc.player);
        if (Keybindings.SHOW_PROFILE.isPressed()) {
            if (profile.getCreated()) {
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
        if (Keybindings.CRAFTING.isPressed()) {
            MythriaPacketHandler.sendToServer(new CPacketAction(CPacketAction.Action.SIMPLE_CRAFTING));
        }
        if (Keybindings.TOGGLE_WIELDING_MODE.isPressed()) {
            InputManager.onToggleCombatMode();
        }
        if (Keybindings.TOGGLE_ABILITY_MODE.isPressed()) {
            InputManager.onToggleControlMode();
        }

        if (Keybindings.NEXT_ABILITY_PRESET.isPressed()) {
            profile.setAbilityPreset(profile.getAbilityPreset() + 1);
        }
        if (Keybindings.PREVIOUS_ABILITY_PRESET.isPressed()) {
            profile.setAbilityPreset(profile.getAbilityPreset() - 1);
        }
    }
}
