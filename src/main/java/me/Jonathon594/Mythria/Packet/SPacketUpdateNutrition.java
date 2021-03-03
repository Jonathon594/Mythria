package me.Jonathon594.Mythria.Packet;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Client.ClientUtil;
import me.Jonathon594.Mythria.Enum.Consumable;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SPacketUpdateNutrition {
    private final Consumable.Nutrition nutrition;
    private final double value;

    public SPacketUpdateNutrition(Consumable.Nutrition nutrition, double value) {
        this.nutrition = nutrition;
        this.value = value;
    }

    public SPacketUpdateNutrition(PacketBuffer packetBuffer) {
        nutrition = Consumable.Nutrition.values()[packetBuffer.readInt()];
        value = packetBuffer.readDouble();
    }


    public static void handle(SPacketUpdateNutrition msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> ClientUtil.handleUpdateNutrition(msg));
        contextSupplier.get().setPacketHandled(true);
    }

    public Consumable.Nutrition getNutrition() {
        return nutrition;
    }

    public double getValue() {
        return value;
    }

    public void encode(ByteBuf buf) {
        buf.writeInt(nutrition.ordinal());
        buf.writeDouble(value);
    }
}
