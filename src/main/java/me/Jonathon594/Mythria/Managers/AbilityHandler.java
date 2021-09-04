package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Ability.AbilityInstance;
import me.Jonathon594.Mythria.Ability.InstantAbility;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.HashSet;

public class AbilityHandler {
    private final HashSet<AbilityInstance> abilityInstances = new HashSet<>();

    //instance variables
    private final HashMap<Fluid, Boolean> fluidWalkingMap = new HashMap<>();

    public AbilityHandler() {
        for (Fluid fluid : ForgeRegistries.FLUIDS.getValues()) {
            fluidWalkingMap.put(fluid, false);
        }
    }

    public void addAbilityInstance(Ability ability, PlayerEntity player) {
        abilityInstances.add(new AbilityInstance(ability, player));
    }

    public boolean canWalkOnFluid(Fluid fluid) {
        return false;
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

    public void setFluidWalkingState(Fluid fluid, boolean state) {
        fluidWalkingMap.put(fluid, state);
    }

    public void tick() {
        for (AbilityInstance instance : getAbilityInstances()) {
            instance.tick();
        }
    }
}
