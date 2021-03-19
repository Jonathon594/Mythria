package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Capability.Crucible.Crucible;
import me.Jonathon594.Mythria.Capability.Crucible.CrucibleProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class CrucibleContainer extends Container {
    public final ItemStackHandler containerInv;

    public CrucibleContainer(int windowID, PlayerInventory playerInventory) {
        super(MythriaContainerType.CRUCIBLE, windowID);
        Crucible crucible = CrucibleProvider.getCrucible(playerInventory.getCurrentItem());
        containerInv = crucible.getOreInventory();
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 5; x++) {
                this.addSlot(new CrucibleInput(containerInv, y * 5 + x, 44 + x * 18, 18 + y * 18));
            }
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col)
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));

        }

        for (int row = 0; row < 9; ++row) {
            if (row == playerInventory.currentItem) {
                this.addSlot(new LockedSlotVisible(playerInventory, row, 8 + row * 18, 142));
            } else {
                this.addSlot(new Slot(playerInventory, row, 8 + row * 18, 142));
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index < 10) {
                if (!this.mergeItemStack(itemstack1, 9, 46, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 9 && index < 36) {
                if (!this.mergeItemStack(itemstack1, 0, 10, false) &&
                        !this.mergeItemStack(itemstack1, 37, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 36 && index < 46 && !this.mergeItemStack(itemstack1, 0, 9, false) &&
                    !this.mergeItemStack(itemstack1, 9, 36, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }

            slot.onSlotChanged();
            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
            this.detectAndSendChanges();
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
