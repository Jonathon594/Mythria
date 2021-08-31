package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Managers.Combat.ParryManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AbilityManager {
    public static void handleCombatSkills(LivingHurtEvent event) {
        DamageSource ds = event.getSource();
        Entity trueSource = ds.getTrueSource();
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity == null) return;

        if (event.isCanceled())
            return;
        if (event.getEntity().world.isRemote)
            return;

        if (trueSource instanceof PlayerEntity) {

        }
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity;
            Profile profile = ProfileProvider.getProfile(player);

            if (trueSource instanceof LivingEntity) {
                ParryManager.onReceiveDamage(player, profile, event, (LivingEntity) trueSource);
            }
        }
    }

    public static void onLivingHurtByPlayer(Profile profile, LivingHurtEvent event, PlayerEntity source) {

    }
}
