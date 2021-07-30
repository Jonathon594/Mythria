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
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Hand;

public class DaggerAbilitySwiftStrike implements ICombatAbility {
    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {
        if (target instanceof LivingEntity) {
            postEvent.setShouldSweep(true);
            LivingEntity LivingEntity = (LivingEntity) target;
            Hand hand = postEvent.getHand();
            TaskManager.addScheduleTask(new AttackTask(8, player, LivingEntity, hand, EnumAttackType.BASIC, null));
            TaskManager.addScheduleTask(new AttackTask(16, player, LivingEntity, hand, EnumAttackType.BASIC, null));
            postEvent.setKnockBack(0);
        }
    }

    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.DAGGER_ABILITY_SWIFT_STRIKE;
    }

    @Override
    public double getStaminaMultiplier() {
        return 3;
    }
}
