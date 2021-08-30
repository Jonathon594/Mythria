package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Event.CombatEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public interface ICombatAbility {
    AttributeFlag getRequiredFlag();

    double getStaminaMultiplier();

    void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent);

    void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent);
}
