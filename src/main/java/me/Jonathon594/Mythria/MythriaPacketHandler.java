package me.Jonathon594.Mythria;

import me.Jonathon594.Mythria.Packet.*;
import me.Jonathon594.Mythria.Util.MythriaResourceLocation;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class MythriaPacketHandler {
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new MythriaResourceLocation("main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register() {
        int disc = 0;
        HANDLER.registerMessage(disc++, CPacketProfileCreation.class, CPacketProfileCreation::encode, CPacketProfileCreation::new,
                CPacketProfileCreation::handle);
        HANDLER.registerMessage(disc++, SPacketProfileCache.class, SPacketProfileCache::encode, SPacketProfileCache::new,
                SPacketProfileCache::handle);
        HANDLER.registerMessage(disc++, SPacketTimeManager.class, SPacketTimeManager::encode, SPacketTimeManager::new,
                SPacketTimeManager::handle);
        HANDLER.registerMessage(disc++, SPacketUpdateConsumables.class, SPacketUpdateConsumables::encode, SPacketUpdateConsumables::new,
                SPacketUpdateConsumables::handle);
        HANDLER.registerMessage(disc++, SPacketUpdateNutrition.class, SPacketUpdateNutrition::encode, SPacketUpdateNutrition::new,
                SPacketUpdateNutrition::handle);
        HANDLER.registerMessage(disc++, SPacketUpdateExperience.class, SPacketUpdateExperience::encode, SPacketUpdateExperience::new,
                SPacketUpdateExperience::handle);
        HANDLER.registerMessage(disc++, CPacketBuyPerk.class, CPacketBuyPerk::encode, CPacketBuyPerk::new, CPacketBuyPerk::handle);
        HANDLER.registerMessage(disc++, CPacketSpendAttribute.class, CPacketSpendAttribute::encode, CPacketSpendAttribute::new,
                CPacketSpendAttribute::handle);
        HANDLER.registerMessage(disc++, CPacketOpenInventory.class, CPacketOpenInventory::encode, CPacketOpenInventory::new,
                CPacketOpenInventory::handle);
        HANDLER.registerMessage(disc++, CPacketReloadWeapon.class, CPacketReloadWeapon::encode, CPacketReloadWeapon::new,
                CPacketReloadWeapon::handle);
        HANDLER.registerMessage(disc++, CPacketAction.class, CPacketAction::encode, CPacketAction::new, CPacketAction::handle);
        HANDLER.registerMessage(disc++, CPacketAttack.class, CPacketAttack::encode, CPacketAttack::new, CPacketAttack::handle);
        HANDLER.registerMessage(disc++, CPacketParry.class, CPacketParry::encode, CPacketParry::new, CPacketParry::handle);
    }

    /**
     * Send a packet to a specific player.<br>
     * Must be called Server side.
     */
    public static <MSG> void sendTo(MSG msg, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer)) {
            HANDLER.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static <MSG> void sendToAll(MSG msg) {
        for (ServerPlayerEntity playerEntity : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers())
            sendTo(msg, playerEntity);
    }

    public static <MSG> void sendToServer(MSG msg) {
        HANDLER.sendToServer(msg);
    }
}
