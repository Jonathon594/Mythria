package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Container.SimpleCraftingContainer;
import me.Jonathon594.Mythria.Managers.ChatManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketAction {
    private final Action action;

    public CPacketAction(Action action) {
        this.action = action;
    }

    public CPacketAction(PacketBuffer packetBuffer) {
        action = Action.values()[packetBuffer.readByte()];
    }

    public void encode(PacketBuffer packetBuffer) {
        packetBuffer.writeByte(action.ordinal());
    }

    public static void handle(CPacketAction msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayerEntity sender = contextSupplier.get().getSender();
            switch (msg.action) {
                case TOGGLE_CHAT:
                    ChatManager.toggleChatChannel(sender);
                case SIMPLE_CRAFTING:
                    sender.openContainer(new SimpleNamedContainerProvider((windowID, invPlayer, p_220283_4_) ->
                            new SimpleCraftingContainer(windowID, invPlayer), new TranslationTextComponent("container.simple_crafting")));
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }

    public enum Action {
        TOGGLE_CHAT, SIMPLE_CRAFTING
    }
}
