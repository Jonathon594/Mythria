package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.Skill;
import me.Jonathon594.Mythria.Managers.MeleeCombatManager;

public interface IWeapon {
    default boolean canHeavyAttack() {
        return true;
    }

    MeleeCombatManager getCombatManager();

    AttributeFlag getFlagForParrying();

    Skill getUsageSkill();
}
