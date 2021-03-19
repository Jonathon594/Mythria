package me.Jonathon594.Mythria.Capability.Tool;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class Tool implements ITool {
    final ItemStackHandler inventory = new ItemStackHandler(1);

    @Override
    public void fromNBT(CompoundNBT nbt) {
        inventory.deserializeNBT(nbt.getCompound("inventory"));
    }

    @Override
    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("inventory", inventory.serializeNBT());
        return nbt;
    }
}
