package me.Jonathon594.Mythria.Managers.Combat;

import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.DaggerAbilityCrit;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.DaggerAbilitySweep;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public class DaggerManager extends MeleeCombatManager {
    public static final DaggerAbilityCrit DAGGER_ABILITY_CRIT = new DaggerAbilityCrit();
    public static final DaggerAbilitySweep DAGGER_ABILITY_SWEEP = new DaggerAbilitySweep();

    @Override
    public ICombatAbility getAbility(EnumAttackType type, boolean isDual, boolean blocking) {
        switch (type) {
            case SPRINT:
                return DAGGER_ABILITY_CRIT;
            case SIDE:
                return DAGGER_ABILITY_SWEEP;
        }
        return BASIC_ATTACK_ABILITY;
    }
}
