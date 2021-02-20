package me.Jonathon594.Mythria.DataTypes;

import net.minecraft.entity.player.PlayerEntity;

public class SexPair {
    private final PlayerEntity male;
    private final PlayerEntity female;

    public SexPair(PlayerEntity male, PlayerEntity female) {
        this.male = male;
        this.female = female;
    }

    public PlayerEntity getFemale() {
        return female;
    }

    public PlayerEntity getMale() {
        return male;
    }
}
