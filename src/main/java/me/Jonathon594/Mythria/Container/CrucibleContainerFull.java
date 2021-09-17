package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Capability.Crucible.Crucible;
import me.Jonathon594.Mythria.Capability.Crucible.CrucibleProvider;
import me.Jonathon594.Mythria.Capability.HeatableItem.HeatableProvider;
import me.Jonathon594.Mythria.Capability.Mold.Mold;
import me.Jonathon594.Mythria.Capability.Mold.MoldProvider;
import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.ColorConst;
import me.Jonathon594.Mythria.DataTypes.CastingRecipe;
import me.Jonathon594.Mythria.Items.EmptyMoldItem;
import me.Jonathon594.Mythria.Items.MythriaItems;
import me.Jonathon594.Mythria.Managers.CastingManager;
import me.Jonathon594.Mythria.Util.MythriaUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

public class CrucibleContainerFull extends Container {
    private final Crucible crucible;
    private final PlayerEntity player;

    protected final CraftingInventory containerInv = new CraftingInventory(this, 1, 1) {
        @Override
        public void markDirty() {
            super.markDirty();
            CrucibleContainerFull.this.onCraftMatrixChanged(this);
        }
    };

    public CrucibleContainerFull(int windowID, PlayerInventory playerInventory) {
        super(MythriaContainerType.CRUCIBLE_FULL, windowID);
        crucible = CrucibleProvider.getCrucible(playerInventory.getCurrentItem());
        player = playerInventory.player;
        this.addSlot(new SlotMold(containerInv, 0, 80, 56));

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
            if (index < 1) {
                if (!this.mergeItemStack(itemstack1, 0, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index > 0 && index < 27) {
                if (!this.mergeItemStack(itemstack1, 0, 1, false) &&
                        !this.mergeItemStack(itemstack1, 28, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 36 && index < 46 && !this.mergeItemStack(itemstack1, 0, 1, false) &&
                    !this.mergeItemStack(itemstack1, 1, 28, false)) {
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
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        ItemStack moldStack = containerInv.getStackInSlot(0);
        if (moldStack.isEmpty()) return;
        if (!(moldStack.getItem() instanceof EmptyMoldItem)) return;
        EmptyMoldItem emptyMoldItem = (EmptyMoldItem) moldStack.getItem();
        CastingRecipe recipe = CastingManager.getRecipe(emptyMoldItem.getShape(), crucible.getMaterial());
        if (recipe == null) return;
        Item result = recipe.getResult();
        if (crucible.getAmount() < emptyMoldItem.getVolume()) return;
        ItemStack closedMold = new ItemStack(MythriaItems.CERAMIC_MOLD_CLOSED, 1);
        Mold mold = MoldProvider.getMold(closedMold);
        mold.setOriginalMoldStack(moldStack.copy());
        ItemStack resultStack = new ItemStack(result, 1);
        //Todo Result Modifications
        Profile profile = ProfileProvider.getProfile(player);
        MythriaUtil.addLoreToItemStack(resultStack, false, ColorConst.CONT_COLOR + "Cast by " + profile.getFullName());
        closedMold.setDisplayName(new StringTextComponent(closedMold.getDisplayName().getString() + "(" + resultStack.getDisplayName() + ")"));
        resultStack.setDisplayName(new StringTextComponent("Cast " + result.getDisplayName(resultStack).getString()));
        resultStack.setDamage(resultStack.getMaxDamage() / 3);
        mold.setResultStack(resultStack);
        HeatableProvider.getHeatable(closedMold).setTemperature(HeatableProvider.getHeatable(moldStack).getTemperature());
        crucible.setAmount(crucible.getAmount() - emptyMoldItem.getVolume());
        containerInv.setInventorySlotContents(0, closedMold);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
