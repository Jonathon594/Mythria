package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketBindAbility {
    private int slot;
    private Ability ability;

    public CPacketBindAbility(int slot, Ability ability) {
        this.slot = slot;
        this.ability = ability;

        if(slot > 48) throw new IndexOutOfBoundsException();
    }

    public CPacketBindAbility(PacketBuffer buf) {
        slot = buf.readInt();

        String name = buf.readString();
        if(name.isEmpty()) ability = null;
        ability = MythriaRegistries.ABILITIES.getValue(new ResourceLocation(name));
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(slot);
        buf.writeString(ability == null ? "" : ability.getRegistryName().toString());
    }

    public static void handle(CPacketBindAbility msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            Profile profile = ProfileProvider.getProfile(contextSupplier.get().getSender());
            if(msg.ability == null || profile.getAbilities().contains(msg.ability)) {
                profile.setBoundAbility(msg.slot, msg.ability);
            }
        });
    }
}
