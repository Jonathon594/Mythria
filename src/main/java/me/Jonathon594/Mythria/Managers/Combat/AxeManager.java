package me.Jonathon594.Mythria.Managers.Combat;

import me.Jonathon594.Mythria.Enum.AttackClass;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.AxeAbilityCrit;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.AxeAbilitySweep;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public class AxeManager extends MeleeCombatManager {
    public static final AxeAbilityCrit AXE_ABILITY_CRIT = new AxeAbilityCrit();
    public static final AxeAbilitySweep AXE_ABILITY_SWEEP = new AxeAbilitySweep();

    @Override
    public ICombatAbility getAbility(EnumAttackType type, boolean isDual, boolean blocking, AttackClass attackClass) {
        switch (type) {
            case SPRINT:
                return AXE_ABILITY_CRIT;
            case BACKWARD:
                return AXE_ABILITY_SWEEP;
            case BASIC:
                break;
        }
        return MeleeCombatManager.BASIC_ATTACK_ABILITY;
    }
}
