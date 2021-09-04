package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Enum.EnumAttackType;
import me.Jonathon594.Mythria.Managers.AbilityHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketCast {
    private final EnumAttackType type;
    private final int hand;
    private final Action action;

    public CPacketCast(int hand, EnumAttackType type, Action action) {
        this.hand = hand;
        this.type = type;
        this.action = action;
    }

    public CPacketCast(PacketBuffer buf) {
        type = EnumAttackType.values()[buf.readInt()];
        hand = buf.readInt();
        action = Action.values()[buf.readInt()];
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(type.ordinal());
        buf.writeInt(hand);
        buf.writeInt(action.ordinal());
    }

    public static void handle(CPacketCast msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayerEntity sender = contextSupplier.get().getSender();
            Profile profile = ProfileProvider.getProfile(sender);
            AbilityHandler abilityHandler = profile.getAbilityHandler();
            if (msg.action == Action.PRESSED) {
                abilityHandler.onCastStart(msg.hand, msg.type);
            } else {
                abilityHandler.onCastEnd(msg.hand, msg.type);
            }
        });
    }

    public enum Action {
        PRESSED, RELEASED
    }
}
