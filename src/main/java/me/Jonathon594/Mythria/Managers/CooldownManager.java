package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.DataTypes.Cooldown;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;

public class CooldownManager {
    public static final ArrayList<Cooldown> cooldowns = new ArrayList<>();

    public static void addCooldown(PlayerEntity player, String type, long ms) {
        Cooldown c = new Cooldown(type, ms + System.currentTimeMillis(), player);
        cooldowns.add(c);
    }

    public static void checkCooldowns() {
        if (System.currentTimeMillis() % 1000L < 50) {
            ArrayList<Cooldown> removeQue = new ArrayList<>();

            for (Cooldown c : cooldowns) {
                if (c.getEndTime() < System.currentTimeMillis()) removeQue.add(c); // Remove it
                if (c.getPlayer().equals(null)) removeQue.add(c); // Remove it
            }
            cooldowns.removeAll(removeQue);
        }
    }

    public static boolean hasCooldown(String type, PlayerEntity player) {
        Cooldown c = getCooldown(type, player);
        if (c == null) return false;
        else if (c.getEndTime() > System.currentTimeMillis()) return true;
        cooldowns.remove(c);
        return false;
    }

    private static Cooldown getCooldown(String type, PlayerEntity player) {
        for (Cooldown c : cooldowns) {
            if (c.getPlayer().equals(player) && c.getType().equalsIgnoreCase(type))
                return c;
        }
        return null;
    }
}
