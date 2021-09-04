package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Capability.MythriaPlayer.MythriaPlayerProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketAbilityBookState {
    private final boolean abilityBookOpen;

    public CPacketAbilityBookState(boolean abilityBookOpen) {
        this.abilityBookOpen = abilityBookOpen;
    }

    public CPacketAbilityBookState(PacketBuffer packetBuffer) {
        abilityBookOpen = packetBuffer.readBoolean();
    }

    public void encode(PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(abilityBookOpen);
    }

    public static void handle(CPacketAbilityBookState msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayerEntity sender = contextSupplier.get().getSender();
            MythriaPlayerProvider.getMythriaPlayer(sender).setAbilityBookOpen(msg.abilityBookOpen);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
