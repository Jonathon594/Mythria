package me.Jonathon594.Mythria.Managers.Combat.Abilities.ParryAbilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Event.ParryEvent;
import me.Jonathon594.Mythria.Interface.IParryAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class SwordAbilityCounterstrike implements IParryAbility {
    @Override
    public void onTrigger(PlayerEntity player, Profile profile, LivingEntity target, Hand hand, ParryEvent event) {
        if(profile.hasFlag(AttributeFlag.SWORD_ABILITY_COUNTERSTRIKE)) {
            event.setDamage(0);
            //CombatKeyManager.attackEntityServer((PlayerEntity) player, target, hand, EnumAttackType.BASIC, null, false, false);
            //PacketUtil.swingPlayerArm((PlayerEntity) player, hand); todo
        }
    }
}
