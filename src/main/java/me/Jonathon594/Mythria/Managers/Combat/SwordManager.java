package me.Jonathon594.Mythria.Managers.Combat;

import me.Jonathon594.Mythria.Enum.AttackClass;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.SwordAbilityCrit;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.SwordAbilitySweep;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public class SwordManager extends MeleeCombatManager {
    public static final SwordAbilityCrit SWORD_ABILITY_CRIT = new SwordAbilityCrit();
    public static final SwordAbilitySweep SWORD_ABILITY_SWEEP = new SwordAbilitySweep();

    @Override
    public ICombatAbility getAbility(EnumAttackType type, boolean isDual, boolean blocking, AttackClass attackClass) {
        switch (type) {
            case SPRINT:
                return SWORD_ABILITY_CRIT;
            case FORWARD:
                return SWORD_ABILITY_SWEEP; //light or heavy
        }
        return MeleeCombatManager.BASIC_ATTACK_ABILITY;
    }
}
