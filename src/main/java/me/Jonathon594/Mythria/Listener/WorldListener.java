package me.Jonathon594.Mythria.Listener;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Managers.ChatManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorld;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber
public class WorldListener {
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void chatReceivedEvent(final ServerChatEvent event) {
        event.setCanceled(true);

        final ServerPlayerEntity sender = event.getPlayer();
        final Profile p = ProfileProvider.getProfile(sender);

        ChatManager.handleForgeChat(event, sender, p);
    }

    @SubscribeEvent
    public static void onWorldLoad(final WorldEvent.Load event) {
        IWorld world = event.getWorld();
        if (world.isRemote())
            return;
        //GlobalSaveData.get().markDirty();
        MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
        IWorldInfo worldInfo = world.getWorldInfo();
        GameRules gameRulesInstance = worldInfo.getGameRulesInstance();
        gameRulesInstance.get(GameRules.DO_LIMITED_CRAFTING).set(true, currentServer);
        gameRulesInstance.get(GameRules.NATURAL_REGENERATION).set(false, currentServer);
    }
}
