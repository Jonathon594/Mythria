package me.Jonathon594.Mythria.Client.Listener;

import me.Jonathon594.Mythria.Client.Manager.ClientManager;
import me.Jonathon594.Mythria.Managers.IngameGuiManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientTickListener {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        IngameGuiManager.onClientTick();
    }

    @SubscribeEvent
    public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final PlayerEntity player = event.player;
        if (event.phase.equals(TickEvent.Phase.END)) {
            if (!player.world.isRemote) {
            } else {
                //Todo WallJumpManager.onPlayerTick(event);
            }
        } else {
            if (player.world.isRemote) {
                ClientManager.onPlayerTickClient();
                //Todo CombatKeyManager.onPlayerTickClient();
                //Todo TorpidityManager.onPlayerTickClient();
                //Todo SexManager.onPlayerTickClient();
                //Todo CombatManager.onPlayerTickClient(player);
            }
        }
    }
}
