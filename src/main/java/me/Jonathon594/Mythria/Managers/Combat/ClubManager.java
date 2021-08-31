package me.Jonathon594.Mythria.Managers.Combat;

import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.AxeAbilityCrit;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.AxeAbilitySweep;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public class ClubManager extends MeleeCombatManager {

    @Override
    public ICombatAbility getAbility(EnumAttackType type, boolean isDual, boolean blocking) {
        return MeleeCombatManager.BASIC_ATTACK_ABILITY;
    }
}
