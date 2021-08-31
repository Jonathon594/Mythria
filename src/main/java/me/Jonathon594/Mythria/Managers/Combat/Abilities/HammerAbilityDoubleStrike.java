package me.Jonathon594.Mythria.Managers.Combat.Abilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.TaskManager;
import me.Jonathon594.Mythria.Managers.Tasks.RunnableTask;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;

public class HammerAbilityDoubleStrike implements ICombatAbility {
    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.HAMMER_ABILITY_DOUBLE_STRIKE;
    }

    @Override
    public double getStaminaMultiplier() {
        return 3;
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {
        if (target instanceof LivingEntity) {
            Hand other = postEvent.getHand() == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
            TaskManager.addScheduleTask(new RunnableTask(() -> {
                //todo CombatManager.attackEntityServer(player, target, other, EnumAttackType.BASIC, null, false, false);
                target.hurtResistantTime = 0;
                player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
                        SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, player.getSoundCategory(), 1.0F, 1.0F);
                //PacketUtil.swingPlayerArm(player, other);
            }, 15));
            postEvent.setKnockBack(0);
        }
    }

    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {

    }
}
