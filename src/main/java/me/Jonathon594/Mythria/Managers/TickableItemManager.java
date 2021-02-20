package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Interface.ITickableItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TickableItemManager {
    @SubscribeEvent
    public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            final ItemStack is = player.inventory.getStackInSlot(i);
            tickitem(player, is);
        }

        Container openContainer = player.openContainer;
        if (openContainer != null) {
            for (Slot slot : openContainer.inventorySlots) {
                if (slot.inventory == player.inventory) continue;
                ItemStack is = slot.getStack();
                tickitem(player, is);
            }
        }
    }

    private static void tickitem(PlayerEntity player, ItemStack is) {
        Item item = is.getItem();
        if (item instanceof ITickableItem) {
            ((ITickableItem) item).tick(player, is);
        }
    }
}
