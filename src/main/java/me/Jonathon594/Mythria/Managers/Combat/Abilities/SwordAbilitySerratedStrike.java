package me.Jonathon594.Mythria.Managers.Combat.Abilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.TaskManager;
import me.Jonathon594.Mythria.Managers.Tasks.SweepTask;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;

public class SwordAbilitySerratedStrike implements ICombatAbility {
    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {

    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {
        if (target instanceof LivingEntity) {
            postEvent.setShouldSweep(true);
            EntityDamageSource source = postEvent.getSource();
            LivingEntity LivingEntity = (LivingEntity) target;
            float damage = postEvent.getDamage();
            Hand other = postEvent.getHand() == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
            TaskManager.addScheduleTask(new SweepTask(4, player, LivingEntity, source, damage, other));
            TaskManager.addScheduleTask(new SweepTask(8, player, LivingEntity, source, damage, postEvent.getHand()));
            postEvent.setKnockBack(0);
        }
    }

    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.SWORD_SERRATED_STRIKE;
    }

    @Override
    public double getStaminaMultiplier() {
        return 3;
    }
}
