package me.Jonathon594.Mythria.Client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class Keybindings {
    public static final KeyBinding SHOW_PROFILE = new KeyBinding("Show Profile", GLFW.GLFW_KEY_P, "Mythria");
    public static final KeyBinding SHOW_SKILLS = new KeyBinding("Show Skills", GLFW.GLFW_KEY_K, "Mythria");
    public static final KeyBinding RELOAD = new KeyBinding("Reload Ranged Weapon", GLFW.GLFW_KEY_R, "Mythria");
    public static final KeyBinding CRAFTING = new KeyBinding("Simple Crafting", GLFW.GLFW_KEY_C, "Mythria");

    public static final KeyBinding TOGGLE_WIELDING_MODE = new KeyBinding("Toggle Wielding Mode", GLFW.GLFW_KEY_X, "Mythria");
    public static final KeyBinding TOGGLE_ABILITY_MODE = new KeyBinding("Toggle Ability Mode", GLFW.GLFW_KEY_Z, "Mythria");

    public static final KeyBinding PARRY = new KeyBinding("Parry", GLFW.GLFW_MOUSE_BUTTON_4, "Mythria");

    public static void init() {
        ClientRegistry.registerKeyBinding(SHOW_PROFILE);
        ClientRegistry.registerKeyBinding(SHOW_SKILLS);
        ClientRegistry.registerKeyBinding(RELOAD);
        ClientRegistry.registerKeyBinding(CRAFTING);
        ClientRegistry.registerKeyBinding(TOGGLE_WIELDING_MODE);
        ClientRegistry.registerKeyBinding(TOGGLE_ABILITY_MODE);
        ClientRegistry.registerKeyBinding(PARRY);
    }
}
