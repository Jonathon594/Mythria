package me.Jonathon594.Mythria.Event;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class ChargeConsumableEvent extends Event {
    private final PlayerEntity player;
    private final Profile profile;
    private final Consumable consumable;
    private double amount;

    public ChargeConsumableEvent(PlayerEntity player, Profile profile, double amount, Consumable consumable) {
        this.player = player;
        this.profile = profile;
        this.amount = amount;
        this.consumable = consumable;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Consumable getConsumable() {
        return consumable;
    }

    public Profile getProfile() {
        return profile;
    }
}
