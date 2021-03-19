package me.Jonathon594.Mythria.Packet;

import me.Jonathon594.Mythria.Items.MythriaBowItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPacketReloadWeapon {
    public CPacketReloadWeapon() {
    }

    public CPacketReloadWeapon(PacketBuffer packetBuffer) {
    }

    public void encode(PacketBuffer packetBuffer) {

    }

    public static void handle(CPacketReloadWeapon msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayerEntity sender = contextSupplier.get().getSender();
            for (Hand hand : Hand.values()) {
                if (attemptReloadBow(sender.getHeldItem(hand), sender, hand)) break;
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }

    private static boolean attemptReloadBow(ItemStack heldItem, ServerPlayerEntity sender, Hand hand) {
        Item item = heldItem.getItem();
        if (item instanceof MythriaBowItem) {
            MythriaBowItem bowItem = (MythriaBowItem) item;
            return bowItem.onReloadAmmo(heldItem, sender, hand);
        }

        return false;
    }
}
