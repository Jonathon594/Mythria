package me.Jonathon594.Mythria.Packet;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Client.ClientUtil;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SPacketUpdateConsumables {
    private final Consumable consumable;
    private final double value;

    public SPacketUpdateConsumables(Consumable consumable, double value) {
        this.consumable = consumable;
        this.value = value;
    }

    public SPacketUpdateConsumables(PacketBuffer packetBuffer) {
        consumable = Consumable.values()[packetBuffer.readInt()];
        value = packetBuffer.readDouble();
    }


    public static void handle(SPacketUpdateConsumables msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ClientUtil.handleUpdateConsumables(msg);
        });
        contextSupplier.get().setPacketHandled(true);
    }

    public Consumable getConsumable() {
        return consumable;
    }

    public double getValue() {
        return value;
    }

    public void encode(ByteBuf buf) {
        buf.writeInt(consumable.ordinal());
        buf.writeDouble(value);
    }
}
