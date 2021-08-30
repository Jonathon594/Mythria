package me.Jonathon594.Mythria.Event;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.eventbus.api.Event;

public class ParryEvent extends Event {
    private final PlayerEntity player;
    private final Profile profile;
    private final LivingEntity damageSource;
    private final Hand hand;
    private float damage;

    public ParryEvent(PlayerEntity player, Profile profile, LivingEntity damageSource, Hand hand, float damage) {
        this.player = player;
        this.profile = profile;
        this.damageSource = damageSource;
        this.hand = hand;
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public LivingEntity getDamageSource() {
        return damageSource;
    }

    public Hand getHand() {
        return hand;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public Profile getProfile() {
        return profile;
    }
}
