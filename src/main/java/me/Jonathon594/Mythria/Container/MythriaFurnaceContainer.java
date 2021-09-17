package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Interface.IHeatableItem;
import me.Jonathon594.Mythria.Managers.SmeltingManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.IntReferenceHolder;

public class MythriaFurnaceContainer extends Container {
    private final IInventory inventory;
    private final IIntArray furnaceData;

    public MythriaFurnaceContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray furnaceData) {
        super(MythriaContainerType.FURNACE, id);
        this.inventory = inventory;
        this.furnaceData = furnaceData;


        for (int i = 0; i < 4; i++) {
            this.addSlot(new Slot(inventory, i, 52 + i * 18, 17) {
                @Override
                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() instanceof IHeatableItem;
                }
            });
        }

        this.addSlot(new Slot(inventory, 4, 79, 55) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return SmeltingManager.hasFuelData(stack.getItem());
            }
        });

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col)
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));

        }

        for (int row = 0; row < 9; ++row) {
            this.addSlot(new Slot(playerInventory, row, 8 + row * 18, 142));
        }

        this.trackIntArray(furnaceData);
    }

    public MythriaFurnaceContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new Inventory(5), new IntArray(3));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public int getBurnLeftScaled() {
        int i = getMaxTicks();
        if (i == 0) return 0;

        return getTicksLeft() * 13 / i;
    }

    public int getTemperature() {
        return furnaceData.get(0);
    }

    public int getMaxTicks() {
        return furnaceData.get(1);
    }

    public int getTicksLeft() {
        return furnaceData.get(2);
    }

    public boolean isBurning() {
        return getTicksLeft() > 0;
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack copy = slot.getStack();
            original = copy.copy();

            if (index > 4) {
                if (this.inventorySlots.get(4).isItemValid(copy)) {
                    if (!this.mergeItemStack(copy, 4, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.inventorySlots.get(0).isItemValid(copy)) {
                    if (!this.mergeItemStack(copy, 0, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (index < 14) {
                        if (!this.mergeItemStack(copy, 14, 32, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else {
                        if (!this.mergeItemStack(copy, 5, 14, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            } else if (!this.mergeItemStack(copy, 5, 32, false)) {
                return ItemStack.EMPTY;
            }

            if (copy.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (copy.getCount() == original.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, copy);
        }
        return original;
    }
}
