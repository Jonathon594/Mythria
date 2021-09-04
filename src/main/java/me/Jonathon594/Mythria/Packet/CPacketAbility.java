package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Ability.Ability;
import me.Jonathon594.Mythria.Ability.InstantAbility;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Managers.AbilityHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketAbility {
    private final int slot;

    public CPacketAbility(int slot) {
        this.slot = slot;
    }

    public CPacketAbility(PacketBuffer buf) {
        slot = buf.readInt();
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(slot);
    }

    public static void handle(CPacketAbility msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            Profile profile = ProfileProvider.getProfile(contextSupplier.get().getSender());
            AbilityHandler abilityHandler = profile.getAbilityHandler();
            Ability ability = profile.getBoundAbility(msg.slot);
            if (ability == null || !(ability instanceof InstantAbility)) return;
            abilityHandler.onAbilityInstant((InstantAbility) ability);
        });
    }

    public enum Action {
        PRESSED, RELEASED
    }
}
