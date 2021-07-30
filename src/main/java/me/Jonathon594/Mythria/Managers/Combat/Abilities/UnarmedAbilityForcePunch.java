package me.Jonathon594.Mythria.Managers.Combat.Abilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class UnarmedAbilityForcePunch implements ICombatAbility {
    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {
        preEvent.setDamage(preEvent.getDamage() + 2);
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {

    }

    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.UNARMED_ABILITY_FORCE_PUNCH;
    }

    @Override
    public double getStaminaMultiplier() {
        return 2;
    }
}
