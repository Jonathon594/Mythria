package me.Jonathon594.Mythria.Packet;

import io.netty.buffer.ByteBuf;
import me.Jonathon594.Mythria.Client.ClientUtil;
import me.Jonathon594.Mythria.Enum.Skill;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SPacketUpdateExperience {
    private final Skill skill;
    private final double value;

    public SPacketUpdateExperience(Skill skill, double value) {
        this.skill = skill;
        this.value = value;
    }

    public SPacketUpdateExperience(PacketBuffer packetBuffer) {
        skill = Skill.values()[packetBuffer.readInt()];
        value = packetBuffer.readDouble();
    }

    public void encode(ByteBuf buf) {
        buf.writeInt(skill.ordinal());
        buf.writeDouble(value);
    }

    public Skill getSkill() {
        return skill;
    }

    public double getValue() {
        return value;
    }

    public static void handle(SPacketUpdateExperience msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> ClientUtil.handleUpdateExperience(msg));
        contextSupplier.get().setPacketHandled(true);
    }
}
