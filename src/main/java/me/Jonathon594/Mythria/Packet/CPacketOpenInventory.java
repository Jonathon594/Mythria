package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Managers.FoodManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketOpenInventory {
    public CPacketOpenInventory() {
    }

    public CPacketOpenInventory(PacketBuffer packetBuffer) {
    }

    public void encode(PacketBuffer packetBuffer) {

    }

    public static void handle(CPacketOpenInventory msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayerEntity sender = contextSupplier.get().getSender();
            FoodManager.updatePlayerInventory(sender, false);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
