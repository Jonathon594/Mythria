package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.MythriaRegistries;
import me.Jonathon594.Mythria.Perk.Perks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketBuyPerk extends NBTPacket {
    public CPacketBuyPerk(CompoundNBT compound) {
        super(compound);
    }

    public CPacketBuyPerk(PacketBuffer packetBuffer) {
        super(packetBuffer);
    }


    public static void handle(CPacketBuyPerk msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            String name = msg.getNbt().getString("Perk");

            Perk perk = MythriaRegistries.PERKS.getValue(new ResourceLocation(name));
            if (perk == null) return;
            Perks.AttemptBuy(contextSupplier.get().getSender(), perk);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
