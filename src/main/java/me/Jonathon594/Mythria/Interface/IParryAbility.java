package me.Jonathon594.Mythria.Interface;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Event.ParryEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public interface IParryAbility {
    void onTrigger(PlayerEntity player, Profile profile, LivingEntity target, Hand hand, ParryEvent event);
}
