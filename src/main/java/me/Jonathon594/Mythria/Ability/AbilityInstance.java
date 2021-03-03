package me.Jonathon594.Mythria.Ability;

import net.minecraft.entity.player.PlayerEntity;

public class AbilityInstance {
    private final Ability ability;
    private final PlayerEntity owner;
    private int cooldown = 0;

    public AbilityInstance(Ability ability, PlayerEntity owner) {
        this.ability = ability;
        this.owner = owner;
    }

    public void tick() {
        ability.tick(this);
        if (cooldown > 0) cooldown--;
    }

    public Ability getAbility() {
        return ability;
    }

    public PlayerEntity getOwner() {
        return owner;
    }

    public boolean isOnCooldown() {
        return cooldown > 0;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
