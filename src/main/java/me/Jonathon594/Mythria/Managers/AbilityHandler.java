package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Ability.AbilityInstance;
import me.Jonathon594.Mythria.Ability.InstantAbility;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashSet;

public class AbilityHandler {
    private final HashSet<AbilityInstance> abilityInstances = new HashSet<>();

    public AbilityHandler() {
    }

    public void addAbilityInstance(Ability ability, PlayerEntity player) {
        abilityInstances.add(new AbilityInstance(ability, player));
    }

    public AbilityInstance getAbilityInstance(Ability ability) {
        for (AbilityInstance abilityInstance : getAbilityInstances()) {
            if (abilityInstance.getAbility().equals(ability)) return abilityInstance;
        }
        return null;
    }

    public HashSet<AbilityInstance> getAbilityInstances() {
        return abilityInstances;
    }

    public void onAbilityInstant(InstantAbility ability) {
        AbilityInstance abilityInstance = getAbilityInstance(ability);
        if (abilityInstance == null) return;
        ability.onInstantActivate(abilityInstance);
    }

    public void onCastEnd(int hand, EnumAttackType type) {

    }

    public void onCastStart(int hand, EnumAttackType type) {

    }

    public void tick() {
        for (AbilityInstance instance : getAbilityInstances()) {
            instance.tick();
        }
    }
}
