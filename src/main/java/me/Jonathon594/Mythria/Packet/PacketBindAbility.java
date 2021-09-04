package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.ClientUtil;
import me.Jonathon594.Mythria.MythriaPacketHandler;
import me.Jonathon594.Mythria.MythriaRegistries;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketBindAbility {
    private final int slot;
    private Ability ability;

    public PacketBindAbility(int slot, Ability ability) {
        this.slot = slot;
        this.ability = ability;

        if (slot > 48) throw new IndexOutOfBoundsException();
    }

    public PacketBindAbility(PacketBuffer buf) {
        slot = buf.readInt();

        String name = buf.readString();
        if (name.isEmpty()) ability = null;
        ability = MythriaRegistries.ABILITIES.getValue(new ResourceLocation(name));
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(slot);
        buf.writeString(ability == null ? "" : ability.getRegistryName().toString());
    }

    public static void handle(PacketBindAbility msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkDirection direction = contextSupplier.get().getDirection();
        if (direction.equals(NetworkDirection.PLAY_TO_SERVER)) {
            contextSupplier.get().enqueueWork(() -> {
                Profile profile = ProfileProvider.getProfile(contextSupplier.get().getSender());
                if (msg.ability == null || (profile.getAbilities().contains(msg.ability) && msg.ability.canBeBound())) {
                    profile.setBoundAbility(msg.slot, msg.ability);
                } else {
                    msg.ability = profile.getBoundAbility(msg.slot);
                    MythriaPacketHandler.sendTo(msg, contextSupplier.get().getSender());
                }
            });
        } else if (direction.equals(NetworkDirection.PLAY_TO_CLIENT)) {
            contextSupplier.get().enqueueWork(() -> {
                ClientUtil.getClientProfile().setBoundAbility(msg.slot, msg.ability);
            });
        }
    }
}
