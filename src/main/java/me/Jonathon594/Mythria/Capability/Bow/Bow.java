package me.Jonathon594.Mythria.Capability.Bow;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class Bow implements IBow {

    public ItemStack getArrow() {
        return inventory.getStackInSlot(1);
    }

    ItemStackHandler inventory = new ItemStackHandler(2);

    public void setArrow(ItemStack arrow) {
        inventory.setStackInSlot(1, arrow);
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("inventory", inventory.serializeNBT());
        return nbt;
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        inventory.deserializeNBT(nbt.getCompound("inventory"));
    }

    public ItemStack getBowstring() {
        return inventory.getStackInSlot(0);
    }

    public void setBowstring(ItemStack bowstring) {
        inventory.setStackInSlot(0, bowstring);
    }
}
