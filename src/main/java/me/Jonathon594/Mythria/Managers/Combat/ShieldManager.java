package me.Jonathon594.Mythria.Managers.Combat;

import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.ShieldAbilityBash;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public class ShieldManager extends MeleeCombatManager {
    public static final ShieldAbilityBash SHIELD_ABILITY_BASH = new ShieldAbilityBash();

    @Override
    public ICombatAbility getAbility(EnumAttackType type, boolean isDual, boolean blocking) {
        if (!blocking) return MeleeCombatManager.BASIC_ATTACK_ABILITY;

        switch (type) {
            case BASIC:
                return SHIELD_ABILITY_BASH;
        }

        return MeleeCombatManager.BASIC_ATTACK_ABILITY;
    }
}
