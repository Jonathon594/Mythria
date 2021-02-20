package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Managers.ChatManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketAction {
    private Action action;

    public CPacketAction(Action action) {
        this.action = action;
    }

    public CPacketAction(PacketBuffer packetBuffer) {
        action = Action.values()[packetBuffer.readByte()];
    }


    public static void handle(CPacketAction msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            switch (msg.action) {
                case TOGGLE_CHAT:
                    ChatManager.toggleChatChannel(contextSupplier.get().getSender());
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }

    public void encode(PacketBuffer packetBuffer) {
        packetBuffer.writeByte(action.ordinal());
    }

    public enum Action {
        TOGGLE_CHAT
    }
}
