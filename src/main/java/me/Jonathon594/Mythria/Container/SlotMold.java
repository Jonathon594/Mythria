package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Items.EmptyMoldItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotMold extends Slot {
    public SlotMold(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof EmptyMoldItem;
    }

    @Override
    public boolean canTakeStack(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
