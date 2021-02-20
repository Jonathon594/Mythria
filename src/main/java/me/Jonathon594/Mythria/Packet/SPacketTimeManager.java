package me.Jonathon594.Mythria.Packet;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Managers.TimeManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SPacketTimeManager {
    private final int day;

    public SPacketTimeManager(int day) {
        this.day = day;
    }

    public SPacketTimeManager(PacketBuffer packetBuffer) {
        day = packetBuffer.readInt();
    }

    public static void handle(SPacketTimeManager msg, Supplier<NetworkEvent.Context> contextSupplier) {
        TimeManager.getCurrentDate().setMGD(msg.day);
        contextSupplier.get().setPacketHandled(true);
    }

    public void encode(ByteBuf buf) {
        buf.writeInt(day);
    }
}
