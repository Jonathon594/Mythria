package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public interface IWeapon {
    default boolean canHeavyAttack() {
        return true;
    }

    MeleeCombatManager getCombatManager();

    AttributeFlag getFlagForParrying();

    MythicSkills getUsageSkill();
}
