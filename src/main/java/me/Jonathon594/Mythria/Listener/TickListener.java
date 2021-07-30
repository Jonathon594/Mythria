package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Managers.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TickListener {
    @SubscribeEvent
    public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final PlayerEntity player = event.player;
        if (event.phase.equals(TickEvent.Phase.END)) {
            //tick Mythria Players
            MythriaPlayerProvider.getMythriaPlayer(player).onTick();
            //Todo VesselModule.onPlayerTick(event);
            //Todo SkillManager.onPlayerTick(event);

            if (!player.world.isRemote) {
                //Todo AbilityManager.onPlayerTickServer(event.player);
                //Todo ParryManager.onPlayerTickServer(event.player);
            }

            LimitedInventoryManager.onPlayerTickEvent(event);
            Profile profile = ProfileProvider.getProfile(player);
            if (profile != null) {
                profile.getAbilityHandler().tick();

                if (!player.world.isRemote) {
                    BlessingManager.onPlayerTick(event, profile);
                    profile.getHealthData().onUpdate((ServerPlayerEntity) player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        TimeManager.onTick(event);
        StatManager.onTick(event);
        TaskManager.onTick(event);
    }
}
