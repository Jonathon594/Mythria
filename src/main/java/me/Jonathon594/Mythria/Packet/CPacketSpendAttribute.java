package me.Jonathon594.Mythria.Packet;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Enum.Attribute;
import me.Jonathon594.Mythria.Managers.AttributeManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketSpendAttribute {
    private final int ordinal;

    public CPacketSpendAttribute(Attribute attribute) {
        this.ordinal = attribute.ordinal();
    }

    public CPacketSpendAttribute(PacketBuffer packetBuffer) {
        ordinal = packetBuffer.readInt();
    }

    public static void handle(CPacketSpendAttribute msg, Supplier<NetworkEvent.Context> contextSupplier) {
        AttributeManager.spendAttribute(contextSupplier.get().getSender(), Attribute.values()[msg.ordinal]);
    }

    public void encode(ByteBuf buf) {
        buf.writeInt(ordinal);
    }
}
