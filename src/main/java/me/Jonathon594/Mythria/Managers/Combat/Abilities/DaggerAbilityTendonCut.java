package me.Jonathon594.Mythria.Managers.Combat.Abilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.DataTypes.HealthConditionType;
import me.Jonathon594.Mythria.Enum.AnatomySlot;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Managers.HealthManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class DaggerAbilityTendonCut implements ICombatAbility {
    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {
        float targetYaw = MathHelper.wrapDegrees(target.rotationYaw);
        float angleTo = (float) MathHelper.wrapDegrees(
                Math.toDegrees(Math.atan2(target.getPosZ() - player.getPosZ(), target.getPosX() - player.getPosX())) - 90);
        float deltaA = MythriaUtil.getDeltaA(targetYaw, angleTo);

        if (Math.abs(deltaA) < 90) {
            preEvent.setForceCrit(true);
        } else {
            preEvent.setFail(true);
        }
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {
        if (target instanceof LivingEntity) {
            LivingEntity LivingEntity = (LivingEntity) target;
            ItemStack leggings = LivingEntity.getItemStackFromSlot(EquipmentSlotType.LEGS);

            if (leggings.isEmpty()) {
                if (target instanceof PlayerEntity) {
                    PlayerEntity targetPlayer = (PlayerEntity) target;
                    Profile targetProfile = ProfileProvider.getProfile(targetPlayer);
                    HealthConditionType type = HealthManager.getHealthCondition("severed_tendon");
                    Random random = new Random();
                    AnatomySlot slot = type.getValidSlots().get(random.nextInt(type.getValidSlots().size()));
                    targetProfile.getHealthData().addHealthCondition((ServerPlayerEntity) targetPlayer, type, slot);
                } else {
                    LivingEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, Integer.MAX_VALUE, 1, false, false));
                }
            }
        }
    }

    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.DAGGER_ABILITY_TENDON_CUT;
    }

    @Override
    public double getStaminaMultiplier() {
        return 3;
    }
}
