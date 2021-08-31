package me.Jonathon594.Mythria.Managers.Combat.Abilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class ShieldAbilityBash implements ICombatAbility {
    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.SHIELD_ABILITY_BASH;
    }

    @Override
    public double getStaminaMultiplier() {
        return 2;
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {
    }

    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {
        preEvent.setDamage(4);
    }
}
