package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Const.EXPConst;
import me.Jonathon594.Mythria.Enum.Consumable;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Enum.StatType;
import me.Jonathon594.Mythria.Event.ChargeConsumableEvent;
import me.Jonathon594.Mythria.Managers.StatManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MythriaListener {
    @SubscribeEvent
    public static void onChargeConsumable(ChargeConsumableEvent event) {
        PlayerEntity player = event.getPlayer();
        Profile profile = event.getProfile();
        if (event.getConsumable().equals(Consumable.STAMINA)) {
            final double fatigueMitigation = StatManager.getTotalFatigueMitigation(profile);
            double eventAmount = event.getAmount();
            double fatigueAmount = eventAmount / (profile.getStat(StatType.MAX_STAMINA) * 10.0);
            profile.setConsumable(Consumable.FATIGUE,
                    profile.getConsumable(Consumable.FATIGUE) + fatigueAmount * (1 - fatigueMitigation));
            profile.addSkillExperience(MythicSkills.AGILITY, eventAmount * EXPConst.STAMINA_USE_TICK, (ServerPlayerEntity) player, 0);
        }
    }
}
