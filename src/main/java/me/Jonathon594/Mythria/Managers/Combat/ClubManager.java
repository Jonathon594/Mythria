package me.Jonathon594.Mythria.Managers.Combat;

import me.Jonathon594.Mythria.Enum.AttackClass;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.ClubAbilityBludgeon;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public class ClubManager extends MeleeCombatManager {
    public static final ClubAbilityBludgeon CLUB_ABILITY_BLUDGEON = new ClubAbilityBludgeon();

    @Override
    public ICombatAbility getAbility(EnumAttackType type, boolean isDual, boolean blocking, AttackClass attackClass) {
        switch (type) {
            case FORWARD:
                return CLUB_ABILITY_BLUDGEON;
        }
        return MeleeCombatManager.BASIC_ATTACK_ABILITY;
    }
}
