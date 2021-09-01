package me.Jonathon594.Mythria.Managers.Combat;

import me.Jonathon594.Mythria.Enum.AttackClass;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.DaggerAbilityCrit;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.DaggerAbilityStab;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.DaggerAbilitySweep;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public class DaggerManager extends MeleeCombatManager {
    public static final DaggerAbilityCrit DAGGER_ABILITY_CRIT = new DaggerAbilityCrit();
    public static final DaggerAbilitySweep DAGGER_ABILITY_SWEEP = new DaggerAbilitySweep();
    public static final DaggerAbilityStab DAGGER_ABILITY_STAB = new DaggerAbilityStab();

    @Override
    public ICombatAbility getAbility(EnumAttackType type, boolean isDual, boolean blocking, AttackClass attackClass) {
        switch (type) {
            case FORWARD:
                if (attackClass.equals(AttackClass.LIGHT)) return DAGGER_ABILITY_STAB;
                break;
            case SPRINT:
                if (attackClass.equals(AttackClass.LIGHT)) return DAGGER_ABILITY_CRIT;
                break;
            case SIDE:
                if (attackClass.equals(AttackClass.HEAVY)) return DAGGER_ABILITY_SWEEP;
                break;
        }
        return BASIC_ATTACK_ABILITY;
    }
}
