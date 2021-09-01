package me.Jonathon594.Mythria.Managers.Combat;

import me.Jonathon594.Mythria.Enum.AttackClass;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.HammerAbilityCrit;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public class HammerManager extends MeleeCombatManager {
    public static final HammerAbilityCrit HAMMER_ABILITY_CRIT = new HammerAbilityCrit();
    @Override
    public ICombatAbility getAbility(EnumAttackType type, boolean isDual, boolean blocking, AttackClass attackClass) {
        switch(type) {
            case SPRINT:
                return HAMMER_ABILITY_CRIT;
        }
        return MeleeCombatManager.BASIC_ATTACK_ABILITY;
    }
}
