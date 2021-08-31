package me.Jonathon594.Mythria.Managers.Combat.Abilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.TaskManager;
import me.Jonathon594.Mythria.Managers.Tasks.AttackTask;
import me.Jonathon594.Mythria.Managers.Tasks.SweepTask;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class DaggerAbilityDualSwiftStrike implements ICombatAbility {
    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.DAGGER_ABILITY_SWIFT_STRIKE;
    }

    @Override
    public double getStaminaMultiplier() {
        return 3;
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {
        if (target instanceof LivingEntity) {
            postEvent.setShouldSweep(true);
            LivingEntity LivingEntity = (LivingEntity) target;
            Hand hand = postEvent.getHand();
            Hand other = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
            TaskManager.addScheduleTask(new AttackTask(4, player, LivingEntity, other, EnumAttackType.BASIC, postEvent.getAttackClass()));
            TaskManager.addScheduleTask(new AttackTask(8, player, LivingEntity, hand, EnumAttackType.BASIC, postEvent.getAttackClass()));
            TaskManager.addScheduleTask(new SweepTask(12, player, LivingEntity, postEvent.getSource(), postEvent.getDamage(), other));
            TaskManager.addScheduleTask(new AttackTask(16, player, LivingEntity, hand, EnumAttackType.BASIC, postEvent.getAttackClass()));
            postEvent.setKnockBack(0);

        }
    }

    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {
    }
}
