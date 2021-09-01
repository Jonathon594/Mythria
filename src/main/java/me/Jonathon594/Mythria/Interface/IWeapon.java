package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public interface IWeapon {
    MeleeCombatManager getCombatManager();

    MythicSkills getUsageSkill();

    AttributeFlag getFlagForParrying();

    default boolean canHeavyAttack() {
        return true;
    }
}
