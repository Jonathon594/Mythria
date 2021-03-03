package me.Jonathon594.Mythria.Managers;

import me.Jonathon594.Mythria.Capability.Profile.ProfileProvider;
import me.Jonathon594.Mythria.Client.Interface.ISlotData;
import me.Jonathon594.Mythria.Container.LockedSlot;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import java.util.ArrayList;
import java.util.List;

public class LimitedInventoryManager {

    private static boolean doStackMatch(final ItemStack iStack, final ItemStack item) {
        boolean flag = false;
        if (!iStack.isEmpty() && !item.isEmpty())
            flag = iStack.isItemEqual(item) && ItemStack.areItemsEqual(iStack, item);
        return flag;
    }

    private static int findNextEmptySlot(final PlayerEntity p) {
        final NonNullList<ItemStack> items = p.inventory.mainInventory;
        for (int i = 0; i < items.size(); i++) {
            final ItemStack current = p.inventory.getStackInSlot(i);
            if (current.isEmpty() && isSlotOpen(p, i))
                return i;
        }
        return -1;
    }

    public static boolean isSlotOpen(final PlayerEntity p, final int i) {
        if (p.isCreative() || p.isSpectator()) return true;
        final ArrayList<Integer> freeSlots = getOpenSlots(p);
        if (freeSlots.contains(i))
            return true;
        if (i > 35 && i < 40)
            if (isArmorSlotOpen(p, i - 36)) return true;
        return i > 39;
    }

    public static boolean isArmorSlotOpen(PlayerEntity p, int i) {
        if (p.isCreative() || p.isSpectator()) return true;
        return ProfileProvider.getProfile(p).getGenetic().isSlotOpen(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, i));
    }

    public static void onOpenContainer(PlayerEntity player, Container container) {
        final List<Slot> slotList = container.inventorySlots;
        if (slotList != null && slotList.size() > 0)
            for (int i = 0; i < slotList.size(); i++) {
                final Slot slot = container.inventorySlots.get(i);

                if (!isAtMaxSlots(player)) {
                    if (slot != null && isSameInventory(player.inventory, slot)) {
                        final PlayerInventory ip = player.inventory;
                        if (!isSlotOpen(player, slot.getSlotIndex())) {
                            final LockedSlot replacementSlot = new LockedSlot(ip, slot.getSlotIndex(), slot.xPos,
                                    slot.yPos);
                            replacementSlot.slotNumber = slot.slotNumber;
                            container.inventorySlots.set(i, replacementSlot);
                        } else {
                            final Slot normalSlot = new Slot(ip, slot.getSlotIndex(), slot.xPos, slot.yPos);
                            normalSlot.slotNumber = slot.slotNumber;
                            container.inventorySlots.set(i, normalSlot);
                        }
                    }
                }

                if (container instanceof ChestContainer) {
                    ChestContainer chest = (ChestContainer) container;
                    IInventory lowerChestInventory = chest.getLowerChestInventory();
                    if (slot != null && isSameInventory(lowerChestInventory, slot)) {
                        final Slot replacementSlot = new Slot(lowerChestInventory, slot.getSlotIndex(), slot.xPos,
                                slot.yPos) {
                            @Override
                            public boolean isItemValid(ItemStack stack) {
                                return (!(stack.getItem() instanceof BlockItem) || stack.getMaxStackSize() > 1);
                            }
                        };
                        replacementSlot.slotNumber = slot.slotNumber;
                        container.inventorySlots.set(i, replacementSlot);
                    }
                }
            }
    }

    public static boolean isSameInventory(IInventory inventory, Slot slot) {
        return slot.inventory == inventory;
    }

    public static void onPlayerPickupItem(final EntityItemPickupEvent event) {
        if (event.isCanceled())
            return;
        final PlayerEntity p = event.getPlayer();

        if (p.inventory.isEmpty())
            return;
        final ItemEntity eItem = event.getItem();
        final ItemStack iStack = eItem.getItem();
        if (iStack.isEmpty())
            return;

        final int slot = searchForPossibleSlot(iStack, p);
        event.setCanceled(slot == -1 || !isSlotOpen(p, slot));
    }

    public static void onPlayerTickEvent(final TickEvent.PlayerTickEvent event) {
        final PlayerEntity p = event.player;

        if (isAtMaxSlots(p))
            return;
        if (p.world.isRemote)
            return;

        final NonNullList<ItemStack> items = p.inventory.mainInventory;
        for (int i = 0; i < items.size(); i++) {
            final ItemStack current = p.inventory.getStackInSlot(i);
            if (current.getCount() > current.getMaxStackSize()) {
                p.entityDropItem(current, 0.0f);
                p.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
            }
            if (!isSlotOpen(p, i)) {
                final int slot = findNextEmptySlot(p);
                if (slot <= -1) {
                    // SOMETHING
                    p.entityDropItem(current, 0.0f);
                    // something
                    p.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                } else
                    p.inventory.setInventorySlotContents(slot, p.inventory.removeStackFromSlot(i));
            }
        }
    }


    public static boolean isAtMaxSlots(final PlayerEntity p) {
        return getOpenSlots(p).size() == 36 || p.isCreative() || p.isSpectator();
    }

    private static ArrayList<Integer> getOpenSlots(final PlayerEntity p) {
        final ArrayList<Integer> slots = new ArrayList<>();
        final int openSlotsHotbar = 1 + getSlotAmounts(p, false);
        for (int i = 0; i < openSlotsHotbar; i++)
            slots.add(i);
        final int openSlotsBody = 2 + getSlotAmounts(p, true);
        for (int i = 9; i < openSlotsBody + 9; i++)
            slots.add(i);
        return slots;
    }

    private static int getSlotAmounts(PlayerEntity p, boolean backpack) {
        int i = 0;
        for (ItemStack is : p.getArmorInventoryList()) {
            if (is == null || is.isEmpty()) continue;
            i += getEquipSlotsGrantedFromItem(is)[backpack ? 0 : 1];
        }
        return i;
    }

    private static int[] getEquipSlotsGrantedFromItem(final ItemStack item) {
        if (item.getItem().equals(Items.LEATHER_LEGGINGS))
            return new int[]{0, 6};
        if (item.getItem().equals(Items.CHAINMAIL_LEGGINGS))
            return new int[]{0, 6};
        if (item.getItem().equals(Items.IRON_LEGGINGS))
            return new int[]{0, 3};
        if (item.getItem().equals(Items.GOLDEN_LEGGINGS))
            return new int[]{0, 3};
        if (item.getItem().equals(Items.DIAMOND_LEGGINGS))
            return new int[]{0, 2};
        if (item.getItem().equals(Items.LEATHER_CHESTPLATE))
            return new int[]{9, 0};
        if (item.getItem().equals(Items.CHAINMAIL_CHESTPLATE))
            return new int[]{9, 0};
        if (item.getItem().equals(Items.IRON_CHESTPLATE))
            return new int[]{2, 0};
        if (item.getItem().equals(Items.GOLDEN_CHESTPLATE))
            return new int[]{2, 0};
        if (item.getItem().equals(Items.DIAMOND_CHESTPLATE))
            return new int[]{2, 0};
        if (item.getItem().equals(Items.LEATHER_BOOTS))
            return new int[]{0, 2};

        if (item.getItem() instanceof ISlotData) {
            ISlotData data = (ISlotData) item.getItem();
            return new int[]{data.getAdditionalBackpackSlots(), data.getAdditionalHotbarSlots()};
        }
        return new int[]{0, 0};
    }

    private static int searchForPossibleSlot(final ItemStack iStack, final PlayerEntity p) {
        final NonNullList<ItemStack> iStacks = p.inventory.mainInventory;
        for (int i = 0; i < iStacks.size(); i++) {
            final ItemStack item = p.inventory.getStackInSlot(i);
            if (isSlotOpen(p, i)) {
                if (item.isEmpty())
                    return i;
                if (doStackMatch(iStack, item) && item.getCount() + iStack.getCount() <= item.getMaxStackSize())
                    return i;
            }
        }
        return -1;
    }
}
