package me.Jonathon594.Mythria.Container;

import me.Jonathon594.Mythria.Capability.Profile.Profile;
import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Const.EXPConst;
import me.Jonathon594.Mythria.DataTypes.Perk;
import me.Jonathon594.Mythria.Enum.MythicSkills;
import me.Jonathon594.Mythria.Managers.MaterialManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public abstract class ToolComponentContainer extends Container {
    protected final net.minecraft.inventory.CraftResultInventory CraftResultInventory = new CraftResultInventory();
    protected final Slot component;
    protected final Slot input;
    protected final Slot output;
    protected final CraftingInventory inventory = new CraftingInventory(this, 2, 1) {
        @Override
        public void markDirty() {
            super.markDirty();
            ToolComponentContainer.this.onCraftMatrixChanged(this);
        }
    };
    protected final World world;
    protected long lastSound = 0L;

    protected ToolComponentContainer(@Nullable ContainerType<?> type, int id, PlayerInventory playerInventory) {
        super(type, id);
        this.world = playerInventory.player.world;
        component = addSlot(new Slot(inventory, 1, 15, 52) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return isValidComponent(stack);
            }
        });

        input = addSlot(new Slot(inventory, 0, 15, 15) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return isValidTool(stack);
            }
        });

        output = addSlot(new Slot(CraftResultInventory, 1, 145, 39) {
            @Override
            public ItemStack onTake(PlayerEntity player, ItemStack stack) {
                Map<Item, List<Perk>> attributesForCrafting = MaterialManager.PERKS_FOR_CRAFTING;
                Item item = stack.getItem();
                ItemStack itemstack = ToolComponentContainer.this.input.decrStackSize(1);
                ToolComponentContainer.this.component.decrStackSize(1);
                if (!itemstack.isEmpty()) {
                    ToolComponentContainer.this.updateResult(ToolComponentContainer.this.inventory);
                }

                stack.getItem().onCreated(stack, player.world, player);

                if (world.getGameTime() != lastSound) {
                    world.playSound(null, player.getPosition(), getCraftSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                    lastSound = world.getGameTime();
                }

                if (attributesForCrafting.containsKey(item) && !player.world.isRemote) {
                    Profile profile = ProfileProvider.getProfile(player);
                    for (Perk perk : attributesForCrafting.get(item)) {
                        if (profile.hasPerk(perk)) {
                            for (final Map.Entry<MythicSkills, Integer> s : perk.getRequiredSkills().entrySet())
                                profile.addSkillExperience(s.getKey(), EXPConst.ITEM_CRAFT * stack.getCount(), (ServerPlayerEntity) player, s.getValue());
                        }
                    }
                }

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

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col)
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));

        }

        for (int row = 0; row < 9; ++row) {
            this.addSlot(new Slot(playerInventory, row, 8 + row * 18, 142));
        }
    }

    public abstract ItemStack getResultStack(ItemStack head, ItemStack handle);

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            Item item = itemstack1.getItem();
            itemstack = itemstack1.copy();
            if (index == 2) {
                item.onCreated(itemstack1, playerIn.world, playerIn);
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index == 0 || index == 1) {
                if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 3 && index < 30) {
                if (!this.mergeItemStack(itemstack1, 0, 2, false) &&
                        !this.mergeItemStack(itemstack1, 30, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 30 && index < 39 &&
                    !this.mergeItemStack(itemstack1, 0, 2, false) &&
                    !this.mergeItemStack(itemstack1, 3, 30, false)) {
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
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        CraftResultInventory.removeStackFromSlot(1);
        clearContainer(playerIn, world, inventory);
    }

    public void onCraftMatrixChanged(IInventory inventoryIn) {
        updateResult(inventory);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    private ItemStack getResultItem(CraftingInventory inventory) {
        ItemStack component = inventory.getStackInSlot(0).copy();
        ItemStack handle = inventory.getStackInSlot(1).copy();
        return getResultStack(component, handle);
    }

    protected boolean canCraft() {
        return !getResultItem(inventory).equals(ItemStack.EMPTY);
    }

    protected abstract SoundEvent getCraftSound();

    protected abstract boolean isValidComponent(ItemStack stack);

    protected abstract boolean isValidTool(ItemStack stack);

    protected void updateResult(CraftingInventory inventory) {
        this.output.putStack(getResultItem(inventory));
        this.detectAndSendChanges();
    }
}
