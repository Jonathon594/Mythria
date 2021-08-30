package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Managers.AttributeManager;
import me.Jonathon594.Mythria.Managers.Combat.ParryManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketParry {
    private boolean parry;

    public CPacketParry(boolean parry) {
        this.parry = parry;
    }

    public CPacketParry(PacketBuffer buffer) {
        this.parry = buffer.readBoolean();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBoolean(parry);
    }

    public static void handle(CPacketParry msg, Supplier<NetworkEvent.Context> contextSupplier) {
        ParryManager.onPacket(contextSupplier.get().getSender(), msg.parry);
    }
}
