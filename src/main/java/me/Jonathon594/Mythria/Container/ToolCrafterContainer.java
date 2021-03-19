package me.Jonathon594.Mythria.Container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public abstract class ToolCrafterContainer extends CrafterContainer {
    public ToolCrafterContainer(ContainerType<?> type, int windowID, PlayerInventory playerInventory) {
        super(type, windowID, playerInventory);
        if (needsTool()) findTool(playerInventory.player);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index == 1) {
                item.onCreated(itemstack1, playerIn.world, playerIn);
                addExperience(playerIn, itemstack1, getRecipe());
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 2 && index < 29) {
                if (!this.mergeItemStack(itemstack1, 0, 1, false) &&
                        !this.mergeItemStack(itemstack1, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 0, 1, false) &&
                    !this.mergeItemStack(itemstack1, 2, 29, false)) {
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
        return (tool != null && !tool.isEmpty()) || !needsTool();
    }

    private void findTool(PlayerEntity player) {
        for (Hand hand : Hand.values()) {
            ItemStack heldItem = player.getHeldItem(hand);
            if (isValidTool(heldItem)) tool = heldItem;
        }
    }

    @Override
    protected Slot createInputSlot() {
        return addSlot(new Slot(inventory, 0, 20, 34) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return getValidItems().contains(stack.getItem());
            }
        });
    }

    @Override
    protected Slot createOutputSlot() {
        return addSlot(new Slot(CraftResultInventory, 1, 143, 33) {
            @Override
            public ItemStack onTake(PlayerEntity player, ItemStack stack) {
                ToolCrafterContainer.this.onOutputTakeStack(player, stack);
                return super.onTake(player, stack);
            }

            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

            @Override
            public boolean canTakeStack(PlayerEntity playerIn) {
                return canCraft();
            }
        });
    }
}
