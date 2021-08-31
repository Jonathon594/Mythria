package me.Jonathon594.Mythria.Managers.Combat.Abilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.TaskManager;
import me.Jonathon594.Mythria.Managers.Tasks.AttackTask;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class UnarmedAbilityRapidStrike implements ICombatAbility {
    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.UNARMED_ABILITY_RAPID_STRIKE;
    }

    @Override
    public double getStaminaMultiplier() {
        return 3;
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {
        if (target instanceof LivingEntity) {
            LivingEntity LivingEntity = (LivingEntity) target;
            Hand other = postEvent.getHand() == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
            TaskManager.addScheduleTask(new AttackTask(3, player, LivingEntity, other, EnumAttackType.BASIC, postEvent.getAttackClass()));
            TaskManager.addScheduleTask(new AttackTask(6, player, LivingEntity, postEvent.getHand(), EnumAttackType.BASIC, postEvent.getAttackClass()));
            postEvent.setKnockBack(0);
        }
    }

    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {
    }
}
