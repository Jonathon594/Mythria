package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Client.ClientUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SPacketProfileCache extends NBTPacket {
    public SPacketProfileCache(CompoundNBT nbt) {
        super(nbt);
    }

    public SPacketProfileCache(PacketBuffer packetBuffer) {
        super(packetBuffer);
    }


    public static void handle(SPacketProfileCache msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> ClientUtil.handleUpdateProfileCache(msg));
        contextSupplier.get().setPacketHandled(true);
    }

}
