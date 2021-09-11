package me.Jonathon594.Mythria.Ability;

import net.minecraft.entity.player.PlayerEntity;

public class AbilityInstance {
    private final Ability ability;
    private final PlayerEntity owner;

    public int getCooldown() {
        return cooldown;
    }

    private int cooldown = 0;
    private int lastCooldown = 0;

    public AbilityInstance(Ability ability, PlayerEntity owner) {
        this.ability = ability;
        this.owner = owner;
    }

    public Ability getAbility() {
        return ability;
    }

    public int getLastCooldown() {
        return lastCooldown;
    }

    public PlayerEntity getOwner() {
        return owner;
    }

    public boolean isOnCooldown() {
        return cooldown > 0;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
        this.lastCooldown = cooldown;
    }

    public void tick() {
        ability.tick(this);
        if (cooldown > 0) cooldown--;
    }
}
