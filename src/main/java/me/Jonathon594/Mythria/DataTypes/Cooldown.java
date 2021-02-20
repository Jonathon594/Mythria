package me.Jonathon594.Mythria.DataTypes;

import net.minecraft.entity.player.PlayerEntity;

public class Cooldown {
    private final String type;
    private final long endTime;
    private final PlayerEntity player;

    public Cooldown(String type, long endTime, PlayerEntity player) {
        this.type = type;
        this.endTime = endTime;
        this.player = player;
    }

    public String getType() {
        return type;
    }

    public long getEndTime() {
        return endTime;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}
