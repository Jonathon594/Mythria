package me.Jonathon594.Mythria.Managers.Combat.Abilities;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.AttributeFlag;
import me.Jonathon594.Mythria.Event.CombatEvent;
import me.Jonathon594.Mythria.Interface.ICombatAbility;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class DaggerAbilityThroatSlice implements ICombatAbility {
    @Override
    public AttributeFlag getRequiredFlag() {
        return AttributeFlag.DAGGER_ABILITY_THROAT_SLICE;
    }

    @Override
    public double getStaminaMultiplier() {
        return 2;
    }

    @Override
    public void onCombatPost(PlayerEntity player, Profile profile, Entity target, CombatEvent.Post postEvent) {

    }

    @Override
    public void onCombatPre(PlayerEntity player, Profile profile, Entity target, CombatEvent.Pre preEvent) {
        if (target instanceof LivingEntity) {
            float targetYaw = MathHelper.wrapDegrees(target.getRotationYawHead());
            float angleTo = (float) MathHelper.wrapDegrees(
                    Math.toDegrees(Math.atan2(target.getPosZ() - player.getPosZ(), target.getPosX() - player.getPosX())) - 90);
            float deltaA = MythriaUtil.getDeltaA(targetYaw, angleTo);

            if (Math.abs(deltaA) < 45) {
                preEvent.setForceCrit(true);

                if (target instanceof PlayerEntity) {
                    PlayerEntity targetPlayer = (PlayerEntity) target;
                    Profile targetProfile = ProfileProvider.getProfile(targetPlayer);
                    targetProfile.setBleeding(8);
                }
            }
        }
        preEvent.setFail(true);
    }
}
