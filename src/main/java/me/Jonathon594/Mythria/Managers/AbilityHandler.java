package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Ability.AbilityInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;

public class AbilityHandler {
    private final ArrayList<AbilityInstance> abilityInstances = new ArrayList<>();

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

    public ArrayList<AbilityInstance> getAbilityInstances() {
        return abilityInstances;
    }

    public void tick() {
        for (AbilityInstance instance : getAbilityInstances()) {
            instance.tick();
        }
    }

    public void setFluidWalkingState(Fluid fluid, boolean state) {
        fluidWalkingMap.put(fluid, state);
    }

    public boolean canWalkOnFluid(Fluid fluid) {
        return false;
    }
}
