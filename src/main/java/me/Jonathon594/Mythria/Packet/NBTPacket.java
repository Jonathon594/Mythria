package me.Jonathon594.Mythria.Packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;

public abstract class NBTPacket {
    private final CompoundNBT nbt;

    public NBTPacket(CompoundNBT compoundNBT) {
        this.nbt = compoundNBT;
    }

    public NBTPacket(PacketBuffer packetBuffer) {
        nbt = packetBuffer.readCompoundTag();
    }

    public CompoundNBT getNbt() {
        return nbt;
    }

    public void encode(final ByteBuf buf) {
        new PacketBuffer(buf).writeCompoundTag(nbt);
    }
}
