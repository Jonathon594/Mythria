package me.Jonathon594.Mythria.Managers.Combat;

import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.UnarmedAbilityForcePunch;
import me.Jonathon594.Mythria.Managers.Combat.Abilities.UnarmedAbilityRapidStrike;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public class UnarmedManager extends MeleeCombatManager {
    public static final UnarmedAbilityForcePunch UNARMED_ABILITY_FORCE_PUNCH = new UnarmedAbilityForcePunch();
    public static final UnarmedAbilityRapidStrike UNARMED_ABILITY_RAPID_STRIKE = new UnarmedAbilityRapidStrike();

    @Override
    public ICombatAbility getAbility(EnumAttackType type, boolean isDual, boolean blocking) {
        switch (type) {
            case FORWARD:
                return UNARMED_ABILITY_FORCE_PUNCH;
            case BACKWARD:
                return UNARMED_ABILITY_RAPID_STRIKE;
        }
        return BASIC_ATTACK_ABILITY;
    }
}
